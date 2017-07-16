package com.valygard.action;

import java.awt.event.ActionEvent;

import com.valygard.Lilypad;
import com.valygard.undoable.UndoableManager;
import com.valygard.undoable.UndoableManager.Node;

public class RedoAction extends LilypadAction {

	private static final long serialVersionUID = 1L;

	public RedoAction(Lilypad editor) {
		super(editor, "Redo", "redo.gif");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		UndoableManager manager = editor.getUndoManager();
		manager.setNode(manager.redo());
		Node node = manager.getNode();

		String newValue = "";
		while (manager.getNode().getRight() != null) {
			newValue += manager.getNode();
			manager.setNode(manager.getNode().getRight());
		}
		manager.setNode(node);
		if (node.getRight() == null) {
			newValue += node.toString();
		}
		editor.getTextArea().setText(editor.getTextArea().getText() + newValue);
		editor.refreshEdits();
	}

}
