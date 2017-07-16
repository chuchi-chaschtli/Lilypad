package com.valygard.action;

import java.awt.event.ActionEvent;

import com.valygard.Lilypad;

public class QuitAction extends LilypadAction {

	private static final long serialVersionUID = 1L;

	public QuitAction(Lilypad editor) {
		super(editor, "Quit");
	}

	public void actionPerformed(ActionEvent e) {
		editor.saveOldFile();
		System.exit(0);
	}
}
