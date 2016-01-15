package com.champtc.champ.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;


public class DirectoryManager {
	
private Set<DirectoryManagerListener> listeners = new HashSet<DirectoryManagerListener>();
	
	private File sourceFolder;
	
	private File destinationFolder;
	
	private File[] fileList;
	
	private AtomicInteger currentIndex = new AtomicInteger(0);
	
	private boolean hasSourceFolder = false;
	
	private boolean hasDestinationFolder = false;
	
	
	public void copyFile(){
		
		File dest = new File(getDestinationFolder().getPath() + "\\" + new String(Paths.get(fileList[currentIndex.get()].getPath()).getFileName().toString()));
		try {
			Files.copy(fileList[currentIndex.get()].toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		fireFileCopiedEvent();
		
		if(currentIndex.get() == getTotalFileCount()){
			fireNoMoreFilesEvent();
			currentIndex.set(0);
		}
		// Check that I have a valid source folder and destination folder
		// check that file list is valid
		// check that current file is valid
		// copy file
		// fire copy event
	
	}
	
	
	/**
	 * 
	 */
	public void resetIndex(){
		currentIndex.set(0);
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
	public boolean setSourceFolder(File sourceFolder) {
		// check for valid folder
		
		if(sourceFolder.exists() && sourceFolder.isDirectory()){
			this.sourceFolder = sourceFolder;
			fileList = sourceFolder.listFiles();
			//set list of files
			
			
			fireSourceFolderEvent();
			//fire event (sourceFolderChanged)
			return true;
		}else{
			return false;
		}
	
		
		
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
	public boolean setDestinationFolder(File destinationFolder) {
		// check for valid folder
		this.destinationFolder = destinationFolder;
		return true;
	}
	
	/**
	 * 
	 */
	public void incrementCurrentIndex(){
		this.currentIndex.set(currentIndex.incrementAndGet());
	}

	/**
	 * @return the currentIndex
	 */
	public int getCurrentIndex() {
		return currentIndex.get();
	}
	

	/**
	 * @param hasSourceFolder the hasSourceFolder to set
	 */
	public void setHasSourceFolder(boolean hasSourceFolder) {
		this.hasSourceFolder = hasSourceFolder;
	}


	/**
	 * @param hasDestinationFolder the hasDestinationFolder to set
	 */
	public void setHasDestinationFolder(boolean hasDestinationFolder) {
		this.hasDestinationFolder = hasDestinationFolder;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean foldersConfigured(){
		return (hasSourceFolder && hasDestinationFolder);
	}


	public int getTotalFileCount(){
		
		//check if null file list
		if(fileList.length == 0){
			return 0;
		}else{
			return fileList.length;
		}
	}

	
	public void addListener(DirectoryManagerListener l){
		listeners.add(l);
	}
	
	public void removeListener(DirectoryManagerListener l){
		listeners.remove(l);
	}
	
	private void fireFileCopiedEvent(){
		for(DirectoryManagerListener l : listeners){
			l.fileCopied();
		}
	}
	
	private void fireSourceFolderEvent(){
		for(DirectoryManagerListener l : listeners){
			l.sourceFolderChanged();
		}
	}
	
	private void fireNoMoreFilesEvent(){
		for(DirectoryManagerListener l : listeners){
			l.noMoreFilesEvent();
		}
	}
}
