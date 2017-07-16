package com.valygard.action;

import java.awt.event.ActionEvent;

import com.valygard.Lilypad;

public class NewFileAction extends LilypadAction {

	private static final long serialVersionUID = 1L;

	public NewFileAction(Lilypad editor) {
		super(editor, "New", "new.gif");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (editor.getCurrentFile().equals("Untitled")) {
			if (editor.isChanged()) {
				editor.saveFileAs();
			}
		} else {
			editor.saveOldFile();
		}
		editor.newFile();
	}

}
