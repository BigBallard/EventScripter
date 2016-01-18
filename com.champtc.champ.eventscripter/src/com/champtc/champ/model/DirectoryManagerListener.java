package com.champtc.champ.model;

public interface DirectoryManagerListener {
	
	public void sourceFolderChanged();
	
	public void fileCopied();
	
	public void noMoreFilesEvent();
	
	public void consoleWrite(String message);
}
