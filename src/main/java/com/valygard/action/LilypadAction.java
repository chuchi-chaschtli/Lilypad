package com.valygard.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.valygard.Lilypad;

public abstract class LilypadAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	protected Lilypad editor;

	public LilypadAction(Lilypad editor, String name) {
		super(name);
		this.editor = editor;
	}

	public LilypadAction(Lilypad editor, String name, String iconPath) {
		super(name, editor.retrieveImage(iconPath));
		this.editor = editor;
	}

	public abstract void actionPerformed(ActionEvent e);

}
