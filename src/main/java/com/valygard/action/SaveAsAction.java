package com.valygard.action;

import java.awt.event.ActionEvent;

import com.valygard.Lilypad;

public class SaveAsAction extends LilypadAction {

	private static final long serialVersionUID = 1L;

	public SaveAsAction(Lilypad editor) {
		super(editor, "Save as...", "save-as.gif");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		editor.saveFileAs();
	}
}
