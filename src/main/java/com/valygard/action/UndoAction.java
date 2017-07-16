package com.valygard.action;

import java.awt.event.ActionEvent;

import com.valygard.Lilypad;
import com.valygard.undoable.UndoableManager;
import com.valygard.undoable.UndoableManager.Node;
import com.valygard.utils.StringUtils;

public class UndoAction extends LilypadAction {

	private static final long serialVersionUID = 1L;

	public UndoAction(Lilypad editor) {
		super(editor, "Undo", "undo.gif");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		UndoableManager manager = editor.getUndoManager();
		manager.setNode(manager.undo());
		Node node = manager.getNode();
		
		String newValue = "";
		while (manager.getNode().getLeft() != null) {
			newValue += manager.getNode();
			manager.setNode(manager.getNode().getLeft());
		}
		manager.setNode(node);
		editor.getTextArea().setText(StringUtils.reverse(newValue));
		editor.refreshEdits();
	}

}
