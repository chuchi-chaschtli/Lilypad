package com.valygard.undoable;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

public class UndoableManager {

	private Node currentIndex = null;
	private Node parentNode = new Node();

	/**
	 * Creates a new empty manager object
	 */
	public UndoableManager() {
		currentIndex = parentNode;
	}

	/**
	 * Creates a new manager which is a duplicate of the parameter in both
	 * contents and current index.
	 * 
	 * @param manager
	 */
	public UndoableManager(UndoableManager manager) {
		this();
		currentIndex = manager.currentIndex;
	}
	
	public Node getNode() {
		return currentIndex;
	}
	
	public void setNode(Node node) {
		this.currentIndex = node;
	}

	/**
	 * Clears all Undoables contained in this manager.
	 */
	public void clear() {
		currentIndex = parentNode;
	}

	/**
	 * Adds an Undoable
	 * 
	 * @param undoable
	 */
	public void addUndoable(Undoable undoable) {
		Node node = new Node(undoable);
		currentIndex.right = node;
		node.left = currentIndex;
		currentIndex = node;
	}

	/**
	 * Determines if an undo can be performed.
	 * 
	 * @return
	 */
	public boolean canUndo() {
		return currentIndex != parentNode;
	}

	/**
	 * Determines if a redo can be performed.
	 * 
	 * @return
	 */
	public boolean canRedo() {
		return currentIndex.right != null;

	}

	/**
	 * Undoes the Undoable at the current index.
	 * 
	 * @throws CannotUndoException
	 *             if canUndo returns false.
	 */
	public Node undo() {
		if (!canUndo()) {
			throw new CannotUndoException();

		}
		currentIndex.undoable.undo();
		Node undone = moveLeft();
		return undone;
	}

	/**
	 * Moves the internal pointer of the backed linked list to the left.
	 * 
	 * @throws IllegalStateException
	 *             If the left index is null.
	 */
	private Node moveLeft() {
		if (currentIndex.left == null) {
			throw new IllegalStateException("Internal index set to null.");
		}
		currentIndex = currentIndex.left;
		return currentIndex;
	}

	/**
	 * Moves the internal pointer of the backed linked list to the right.
	 * 
	 * @throws IllegalStateException
	 *             If the right index is null.
	 */
	private Node moveRight() {
		if (currentIndex.right == null) {
			throw new IllegalStateException("Internal index set to null.");
		}
		currentIndex = currentIndex.right;
		return currentIndex;
	}

	/**
	 * Redoes the Undoable at the current index.
	 * 
	 * @throws CannotRedoException
	 *             if canRedo returns false.
	 */
	public Node redo() {
		if (!canRedo()) {
			throw new CannotRedoException();
		}
		Node redone = moveRight();
		currentIndex.undoable.redo();
		return redone;
	}

	/**
	 * Inner node-class for doubly-linked list of undoable operations
	 * 
	 * @author Anand
	 * 
	 */
	public class Node {
		private Node left = null;
		private Node right = null;
		private final Undoable undoable;

		public Node(Undoable u) {
			this.undoable = u;
		}

		public Node() {
			this(null);
		}
		
		public Node getLeft() {
			return left;
		}
		
		public Node getRight() {
			return right;
		}
		
		@Override
		public String toString() {
			return undoable.toString();
		}

	}

}
