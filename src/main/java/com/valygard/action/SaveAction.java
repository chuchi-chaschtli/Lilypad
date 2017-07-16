package com.valygard.action;

import java.awt.event.ActionEvent;

import com.valygard.Lilypad;

public class SaveAction extends LilypadAction {

	private static final long serialVersionUID = 1L;

	public SaveAction(Lilypad editor) {
		super(editor, "Save", "save.gif");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!editor.getCurrentFile().equals("Untitled"))
			editor.saveFile(editor.getCurrentFile());
		else
			editor.saveFileAs();
	}
}
