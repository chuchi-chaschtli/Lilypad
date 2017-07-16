package com.valygard.undoable;

public class UndoableString implements Undoable {

	private String str, tmp;

	public UndoableString(String str) {
		this.str = str;
		this.tmp = str;
	}

	@Override
	public void undo() {
		str = "";
	}

	@Override
	public void redo() {
		str = tmp;
	}
	
	@Override
	public String toString() {
		return str;
	}

}
