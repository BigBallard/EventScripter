package com.champtc.champ.eventscripter;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Random;
import java.util.Scanner;

/**
 * Application controller that manages a DirectoryManager and TimerManager to conduct event 
 * playback or sequence.
 *
 */
public class ScriptController {
	
	public DirectoryManager dirMan;
	public TimerManager timMan;
	private final int nanoToSecond = 1000000000;
	
	public ScriptController(){
		dirMan = new DirectoryManager();
		timMan = new TimerManager();
	}
	
	/**
	 * 
	 */
	public void setSourceFolder(String sourcePath){
		dirMan.setSourceFolder(new File(sourcePath));
		
		File dir = new File(sourcePath);
		File[] files = dir.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				if(name.endsWith(".txt")|| name.endsWith(".csv")){
					return true;
				}else
					return false;
			}
		});
		if(dirMan.getSourceFolder().exists())
		dirMan.setEventFileList(files);
	}
	
	/**
	 * 
	 */
	public void setDestinationFolder(String sourcePath){
		dirMan.setDestinationFolder(new File(sourcePath));
		
	}	
	
	
	/**
	 * Setting the preferences is not required unless the user does not want the default settings set in {@link TimerManager#TimerManager() TimerManagerConstructor}.
	 */
	public void setTimerPreferences(){
		Scanner sc = new Scanner(System.in);
		
		while(true){
			System.out.println("Will events be sent by a timer or manually? (t/m)");
			timMan.setEventSendPreferences(sc.nextLine().toLowerCase());
			
			switch(timMan.getEventSendPreferences()){
				case "manual":
				case "m":
					
					sc.close();
					return;
				case "timer":
				case "t":
					// TODO prompt for lower and upper bound units
					System.out.println("Will the time intervals be in minutes or seconds? (m/s)");
//					timMan.setTimerUnits(sc.next());
					
					System.out.println("Send event between or every? (b/e)");				
					timMan.setIntervalType(sc.next());
					
					
					sc.close();
					return;
				default:
					System.out.println("Invalid entry.");
			}
		}
	}
	
	public void run() throws IOException{
		if(timMan.getEventSendPreferences().equalsIgnoreCase("manual") || timMan.getEventSendPreferences().equalsIgnoreCase("m")){
			runOnManual();
		}else{
			runOnTimer();
		}
		
	}
	
	private void runOnManual() throws IOException{
		
		for(File f : dirMan.getEventFileList()){
			dirMan.updateFileCounts();
			System.out.println("Event " + dirMan.getSentEventFiles());
			File dest = new File(dirMan.getDestinationFolder() + "\\" + f.getName());
			Files.copy(f.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
		}
		dirMan.resetFileCounts();
		return;
	}
	
	private void runOnTimer() throws IOException{
		int randomTimeValue = 0;
		for(File f : dirMan.getEventFileList()){
			int timerClick = (int) (System.nanoTime() / nanoToSecond);
			
			randomTimeValue = (new Random().nextInt(timMan.getUpperTimerBound() - timMan.getLowerTimerBound()) + 0) ;
			
			while(true){
				if((int) (System.nanoTime() / nanoToSecond) - timerClick >= timMan.getLowerTimerBound() + randomTimeValue){
					dirMan.updateFileCounts();
					System.out.println("Event " + dirMan.getSentEventFiles());
					File dest = new File(dirMan.getDestinationFolder() + "\\" + f.getName());
					Files.copy(f.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
					break;
				}
			}
		}
		dirMan.resetFileCounts();
		return;
	}
	
}
