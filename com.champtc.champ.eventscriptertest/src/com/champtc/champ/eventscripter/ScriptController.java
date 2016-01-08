package com.champtc.champ.eventscripter;

import java.awt.SecondaryLoop;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import com.champtc.champ.eventscripter.ScriptController.RunOnManual;
import com.champtc.champ.eventscripter.ScriptController.RunOnTimer;


/**
 * Application controller that manages a {@link DirectoryManager#DirectoryManager DirectoryManager}
 * and {@link TimerManager#TimerManager TimerManager} to conduct event a play-back or sequence. Contains
 * member variables pauseFlag and resetFlag that are called during a run call.
 *
 */
public class ScriptController {
	/**
	 * The controllers instance of the DirectoryManager
	 */
	public DirectoryManager dirMan;
	/**
	 * The controllers instance of the TimerManager
	 */
	public TimerManager timMan;
	private AtomicBoolean pauseFlag;
	private AtomicBoolean resetFlag;
	private AtomicBoolean sendFlag;
	private AtomicBoolean isRunning;
	private static Runnable timerThread;
	private static Runnable manualThread;
	private static ExecutorService executor;
	
	public ScriptController(){
		dirMan = new DirectoryManager();
		timMan = new TimerManager();
		pauseFlag = new AtomicBoolean(false);
		resetFlag = new AtomicBoolean(false);
		sendFlag = new AtomicBoolean(false);
		isRunning = new AtomicBoolean(false);
		timerThread = new RunOnTimer(this);
		manualThread = new RunOnManual(this);
		executor = Executors.newSingleThreadExecutor();
	}
	
	/**
	 * @return the pauseFlag
	 */
	public boolean hasPauseFlag() {
		return pauseFlag.get();
	}

	/**
	 * @param pauseFlag the pauseFlag to set
	 */
	public void setPauseFlag(boolean pauseFlag) {
		this.pauseFlag.set(pauseFlag);;
	}

	/**
	 * @return the resetFlag
	 */
	public boolean hasResetFlag() {
		return resetFlag.get();
	}

	/**
	 * @param resetFlag the resetFlag to set
	 */
	public void setResetFlag(boolean resetFlag) {
		this.resetFlag.set(resetFlag);;
	}
	
	/**
	 * @return the sendFlag
	 */
	public AtomicBoolean getSendFlag() {
		return sendFlag;
	}

	/**
	 * @param sendFlag the sendFlag to set
	 */
	public void setSendFlag(boolean sendFlag) {
		this.sendFlag.set(sendFlag);
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
		return isRunning.get();
	}

	/**
	 * 
	 * @param status
	 */
	public void setRunningStatus(boolean status) {
		this.isRunning.set(status);
	}

	public void resetControlFlags(){
		pauseFlag.set(false);
		resetFlag.set(false);
		sendFlag.set(false);
	}
	
	/**
	 * 
	 * @throws IOException
	 * @throws InterruptedException 
	 */
	public void play() throws IOException, InterruptedException{
		
		if(timMan.getEventSendPreferences().equalsIgnoreCase("manual")){
			executor.execute(manualThread);
			executor.shutdown();
			executor.awaitTermination(100, TimeUnit.MILLISECONDS);
			
		}else if(timMan.getEventSendPreferences().equalsIgnoreCase("timer")){
			executor.execute(timerThread);
			executor.shutdown();
			executor.awaitTermination(100, TimeUnit.MILLISECONDS);
		}
		
		return;
	}
	
	public void reset(){
		if(isRunning.get()){
			setResetFlag(true);
			while(!executor.isShutdown());
				
			while(!executor.isTerminated());
				
			executor = Executors.newSingleThreadExecutor();
			setRunningStatus(false);
			resetControlFlags();
		}
		return;
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
			controller.setRunningStatus(false);
			controller.dirMan.resetFileCounts();
			return;
		}
	};
	
	/**
	 * 
	 */
	public void runOnManual() throws IOException{
		isRunning.set(true);
		for(File f : dirMan.getEventFileList()){
			
			if(resetFlag.get()){return;}
			
			sendFlag.set(false);
			
			dirMan.updateFileCounts();
			System.out.println("Event " + dirMan.getSentEventFiles());
			File dest = new File(dirMan.getDestinationFolder() + "\\" + f.getName());
			Files.copy(f.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
			
			while(!sendFlag.get()){
				if(resetFlag.get()){return;}
			}
		}
		dirMan.resetFileCounts();
		isRunning.set(false);;
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
				
				controller.setRunningStatus(false);
				controller.dirMan.resetFileCounts();
			}
			return;
		}
	};
	
	/**
	 * 
	 * @throws IOException
	 */
	public void runOnTimer() throws IOException{
		isRunning.set(true);
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
					while(pauseFlag.get()){
						if(resetFlag.get()){return;}
					}
					if(resetFlag.get()){return;}
					//send fileCopiedEvent
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
		isRunning.set(false);;
		return;
	}
	
}
