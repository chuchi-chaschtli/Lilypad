package com.valygard.undoable;

public interface Undoable {

	public void undo();
	
	public void redo();
	
	@Override
	public String toString();
}
