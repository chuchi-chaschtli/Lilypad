package com.valygard.action;

import java.awt.event.ActionEvent;

import javax.swing.JFileChooser;

import com.valygard.Lilypad;

public class OpenAction extends LilypadAction {

	private static final long serialVersionUID = 1L;

	public OpenAction(Lilypad editor) {
		super(editor, "Open", "open-folder.png");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		editor.saveOldFile();
		if (editor.getDialog().showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			editor.readInFile(editor.getDialog().getSelectedFile()
					.getAbsolutePath());
		}
		editor.getSaveAsAction().setEnabled(true);
	}
}