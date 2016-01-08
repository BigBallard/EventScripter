package com.champtc.champ.eventscripter;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Manages the source and destination directories and the event file list. Keeps track of total files
 * ,files sent, and files left to send. Methods are given to reset the file statistics. Boolean members
 * {@link hasSourceFolder hasSourceFolder} and {@link hasDestinationFolder hasDestinationFolder} are 
 * intended to be used as flags to enable/disable the run methods.
 */
public class DirectoryManager {
	private File sourceFolder;
	private File destinationFolder;
	private File[] eventFileList;
	private int sentEventFiles;
	private int eventFilesLeft;
	boolean hasSourceFolder;
	boolean hasDestinationFolder;
	
	public DirectoryManager(){
		sentEventFiles = 0;
		eventFilesLeft = 0;
		hasSourceFolder = false;
		hasDestinationFolder = false;
	}
	
	/**
	 * Method needs to be used during a run process if an updated file count is desired. Simply
	 * apply after the code that copy's the file(s).
	 */
	public void updateFileCounts(){
		sentEventFiles ++;
		eventFilesLeft = eventFileList.length - sentEventFiles;
		return;
	}
	
	/**
	 * Resets the file counts at any given time the method is called. Only resets the files left 
	 * during a running instance and the files sent.
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
	 * @param sourceFolder the sourceFolder containing the event files.
	 */
	public void setSourceFolder(File sourceFolder) {
		this.sourceFolder = sourceFolder;
		File[] files = sourceFolder.listFiles();
		setEventFileList(files);
	}
	/**
	 * @return the destinationFolder
	 */
	public File getDestinationFolder() {
		return destinationFolder;
	}
	/**
	 * @param destinationFolder the destinationFolder where the the DarkLight monitored folder should be.
	 */
	public void setDestinationFolder(File destinationFolder) {
		this.destinationFolder = destinationFolder;
	}
	/**
	 * @return the eventFileList as a file array.
	 */
	public File[] getEventFileList() {
		return eventFileList;
	}
	/**
	 * @param eventFileList the eventFileList to set. This is implicitly called when the source folder
	 * is set and is not required anywhere else in the code base unless the {@link setSourceFolder setSourceFolder}
	 * is not used.
	 */
	public void setEventFileList(File[] eventFileList) {
		this.eventFileList = eventFileList;
	}
	/**
	 * @return the totalEventFiles as an integer.
	 */
	public int getTotalEventFiles() {
		
		return eventFileList.length;
	}
	/**
	 * @return the sentEventFiles integer value.
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
	 * @return the eventFilesLeft integer value.
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
	
	/**
	 * @return the hasSourceFolder
	 */
	public boolean isHasSourceFolder() {
		return hasSourceFolder;
	}

	/**
	 * @param hasSourceFolder the hasSourceFolder to set
	 */
	public void setHasSourceFolder(boolean hasSourceFolder) {
		this.hasSourceFolder = hasSourceFolder;
	}

	/**
	 * @return the hasDestinationFolder
	 */
	public boolean isHasDestinationFolder() {
		return hasDestinationFolder;
	}

	/**
	 * @param hasDestinationFolder the hasDestinationFolder to set
	 */
	public void setHasDestinationFolder(boolean hasDestinationFolder) {
		this.hasDestinationFolder = hasDestinationFolder;
	}

	/**
	 * @return true if both {@link hasSourceFolder hasSourceFolder} and {@link hasDestinationFolder hasDestinationFolder}
	 * are set to true and false if either {@link hasSourceFolder hasSourceFolder} and {@link hasDestinationFolder hasDestinationFolder}
	 * are set to false.
	 */
	public boolean foldersConfigured(){
		return (hasSourceFolder && hasDestinationFolder);
	}
	
}	

