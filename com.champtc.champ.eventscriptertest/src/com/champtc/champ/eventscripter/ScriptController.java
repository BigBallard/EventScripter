package com.champtc.champ.eventscripter;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Random;


/**
 * Application controller that manages a {@link DirectoryManager#DirectoryManager DirectoryManager}
 * and {@link TimerManager#TimerManager TimerManager} to conduct event a play-back or sequence. Contains
 * member variables pauseFlag and resetFlag that are called during a run call.
 *
 */
public class ScriptController {
	private Util U;
	public DirectoryManager dirMan;
	public TimerManager timMan;
	private boolean pauseFlag;
	private boolean resetFlag;
	
	public ScriptController(){
		U = new Util();
		dirMan = new DirectoryManager();
		timMan = new TimerManager();
		pauseFlag = false;
		resetFlag = false;
	}
	
	/**
	 * @return the pauseFlag
	 */
	public boolean hasPauseFlag() {
		return pauseFlag;
	}

	/**
	 * @param pauseFlag the pauseFlag to set
	 */
	public void setPauseFlag(boolean pauseFlag) {
		this.pauseFlag = pauseFlag;
	}

	/**
	 * @return the resetFlag
	 */
	public boolean hasResetFlag() {
		return resetFlag;
	}

	/**
	 * @param resetFlag the resetFlag to set
	 */
	public void setResetFlag(boolean resetFlag) {
		this.resetFlag = resetFlag;
	}

	/**
	 * 
	 */
	public void setSourceFolder(String sourcePath){
		dirMan.setSourceFolder(new File(sourcePath));
	}
	
	/**
	 * 
	 */
	public void setDestinationFolder(String sourcePath){
		dirMan.setDestinationFolder(new File(sourcePath));
		
	}	
	
	public void runOnManual() throws IOException{

		for(File f : dirMan.getEventFileList()){
			dirMan.updateFileCounts();
			System.out.println("Event " + dirMan.getSentEventFiles());
			File dest = new File(dirMan.getDestinationFolder() + "\\" + f.getName());
			Files.copy(f.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
		}
		dirMan.resetFileCounts();
		return;
	}
	
	public static class RunOnManual implements Runnable{
		
		ScriptController controller;
		Thread thread;
		
		public RunOnManual(ScriptController c, Thread tr){
			thread = tr;
			controller = c;
		};
		
		@Override
		public void run(){
			try {
				controller.runOnTimer();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * @throws IOException
	 */
	public void runOnTimer() throws IOException{
		
		for(File f : dirMan.getEventFileList()){
			int randomTimeValue = 0;
				int range = timMan.getUpperTimerBound() - timMan.getLowerTimerBound();
				if(range > 0)
					randomTimeValue = (new Random().nextInt(timMan.getUpperTimerBound() - timMan.getLowerTimerBound()) + 0);
				
				try {
					Thread.sleep(1000 * (randomTimeValue + timMan.getLowerTimerBound()));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				dirMan.updateFileCounts();
				System.out.println("Event " + dirMan.getSentEventFiles());
				File dest = new File(dirMan.getDestinationFolder() + "\\" + f.getName());
				Files.copy(f.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
					
					
//					if(pauseFlag){
//						while(pauseFlag){
//							if(resetFlag){
//								dirMan.resetFileCounts();
//								return;
//							}
//						}
//						break;
//					}
					
//					if(resetFlag){
//						dirMan.resetFileCounts();
//						return;
//					}
					
		}
		dirMan.resetFileCounts();
		return;
	}
	
}
