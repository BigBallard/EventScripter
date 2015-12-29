package com.champtc.champ.eventscripter;

import java.io.File;

/**
 * Manages the source and destination directories and the event file list. Keeps track of total files
 * ,files sent, and files left to send. These numbers are reset when a run process is stopped or a new
 * directory is selected.
 */
public class DirectoryManager {
	private File sourceFolder;
	private File destinationFolder;
	private File[] eventFileList;
	private int totalEventFiles;
	private int sentEventFiles;
	private int eventFilesLeft;
	
	public DirectoryManager(){
		
		totalEventFiles = 0;
		sentEventFiles = 0;
		eventFilesLeft = 0;
	}
	
	/**
	 * 
	 */
	public void updateFileCounts(){
		sentEventFiles ++;
		eventFilesLeft = totalEventFiles - sentEventFiles;
		return;
	}
	
	/**
	 * 
	 */
	public void resetFileCounts(){
		sentEventFiles = 0;
		eventFilesLeft = 0;
		return;
	}
	
	/**
	 * @return the sourceFolder
	 */
	public File getSourceFolder() {
		return sourceFolder;
	}
	/**
	 * @param sourceFolder the sourceFolder to set
	 */
	public void setSourceFolder(File sourceFolder) {
		this.sourceFolder = sourceFolder;
	}
	/**
	 * @return the destinationFolder
	 */
	public File getDestinationFolder() {
		return destinationFolder;
	}
	/**
	 * @param destinationFolder the destinationFolder to set
	 */
	public void setDestinationFolder(File destinationFolder) {
		this.destinationFolder = destinationFolder;
	}
	/**
	 * @return the eventFileList
	 */
	public File[] getEventFileList() {
		return eventFileList;
	}
	/**
	 * @param eventFileList the eventFileList to set
	 */
	public void setEventFileList(File[] eventFileList) {
		setTotalEventFiles(eventFileList);
		this.eventFileList = eventFileList;
	}
	/**
	 * 
	 */
	public boolean isInitialized(){
		
		if(sourceFolder.exists() && destinationFolder.exists() && !eventFileList.toString().isEmpty()){
			return true;
		}
		return false;
	}
	/**
	 * @return the totalEventFiles
	 */
	public int getTotalEventFiles() {
		return totalEventFiles;
	}
	/**
	 * @param totalEventFiles the totalEventFiles to set
	 */
	private void setTotalEventFiles(File[] fileList) {
		int count = 0;
		for(File f: fileList){
			count++;
		}
		this.totalEventFiles = count;
	}
	/**
	 * @return the sentEventFiles
	 */
	public int getSentEventFiles() {
		return sentEventFiles;
	}
	/**
	 * @param sentEventFiles the sentEventFiles to set
	 */
	public void setSentEventFiles(int sentEventFiles) {
		this.sentEventFiles = sentEventFiles;
	}
	/**
	 * @return the eventFilesLeft
	 */
	public int getEventFilesLeft() {
		return eventFilesLeft;
	}
	/**
	 * @param eventFilesLeft the eventFilesLeft to set
	 */
	public void setEventFilesLeft(int eventFilesLeft) {
		this.eventFilesLeft = eventFilesLeft;
	}
	
	
}	

