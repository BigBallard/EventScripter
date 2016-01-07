package com.champtc.champ.eventscripter;

import java.awt.SecondaryLoop;
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
	public DirectoryManager dirMan;
	public TimerManager timMan;
	private boolean pauseFlag;
	private boolean resetFlag;
	private boolean sendFlag;
	private boolean isRunning;
	
	public ScriptController(){
		dirMan = new DirectoryManager();
		timMan = new TimerManager();
		pauseFlag = false;
		resetFlag = false;
		sendFlag = false;
		isRunning = false;
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
	
	/**
	 * 
	 * @return
	 */
	public boolean isRunning() {
		return isRunning;
	}

	/**
	 * 
	 * @param isRunning
	 */
	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}


	/**
	 * 
	 * @author Dallas
	 *
	 */
	public static class RunOnManual implements Runnable{
		
		ScriptController controller;
		
		public RunOnManual(ScriptController c){
			controller = c;
		}
		
		@Override
		public void run(){
			
			try {
				controller.runOnManual();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	};
	
	/**
	 * 
	 */
	public void runOnManual() throws IOException{
		
		for(File f : dirMan.getEventFileList()){
			
			while(!sendFlag){
				if(resetFlag){return;}
			}
			if(resetFlag){return;}
			
			sendFlag = false;
			
			dirMan.updateFileCounts();
			System.out.println("Event " + dirMan.getSentEventFiles());
			File dest = new File(dirMan.getDestinationFolder() + "\\" + f.getName());
			Files.copy(f.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
		}
		dirMan.resetFileCounts();
		isRunning = false;
		return;
	}
	
	/**
	 * 
	 * @author Dallas
	 *
	 */
	public static class RunOnTimer implements Runnable{
		
		ScriptController controller;
		
		public RunOnTimer(ScriptController c){
			controller = c;
		};
		
		@Override
		public void run(){
			if(!controller.hasPauseFlag()){
				try {
					controller.runOnTimer();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	};
	
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
			
			int timerValue = randomTimeValue + timMan.getLowerTimerBound();
			try {
				while(timerValue >= 0){
					Thread.sleep(1000L);
					timerValue--;
					while(pauseFlag){
						if(resetFlag){return;}
					}
					if(resetFlag){return;}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			dirMan.updateFileCounts();
			System.out.println("Event " + dirMan.getSentEventFiles());
			File dest = new File(dirMan.getDestinationFolder() + "\\" + f.getName());
			Files.copy(f.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
				
		}
		dirMan.resetFileCounts();
		isRunning = false;
		return;
	}
	
}
