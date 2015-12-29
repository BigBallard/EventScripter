package com.champtc.champ.eventscripter;

import java.io.File;
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
	
	private DirectoryManager dirMan;
	private TimerManager timMan;
	private String eventSendPreference;
	private final int nanoToSecond = 1000000000;
	
	public ScriptController(){
		dirMan = new DirectoryManager();
		timMan = new TimerManager();
		eventSendPreference = "default";
	}
	
	/**
	 * 
	 */
	public void setSourceFolder(){
		while(true){
			System.out.println("Enter events source folder: ");
			dirMan.setSourceFolder(new File(new Scanner(System.in).nextLine()));
			if(dirMan.getSourceFolder().exists()){				
				break;
			}else{
				System.out.println("Folder does not exist!");
			}
		}
		
		dirMan.setEventFileList(dirMan.getSourceFolder().listFiles());
	}
	
	/**
	 * 
	 */
	public void setDestinationFolder(){
		while(true){
			System.out.println("Enter destination folder: ");
			dirMan.setDestinationFolder(new File(new Scanner(System.in).nextLine()));
			if(dirMan.getDestinationFolder().exists()){
				break;
			}else{
				System.out.println("Folder does not exitst!");
			}
		}
	}
	
	
	/**
	 * Setting the preferences is not required unless the user does not want the default settings set in {@link TimerManager#TimerManager() TimerManagerConstructor}.
	 */
	public void setTimerPreferences(){
		Scanner sc = new Scanner(System.in);
		
		while(true){
			System.out.println("Will events be sent by a timer or manually? (t/m)");
			eventSendPreference = sc.nextLine().toLowerCase();
			
			switch(eventSendPreference){
				case "manual":
				case "m":
					
					sc.close();
					return;
				case "timer":
				case "t":
					System.out.println("Will the time intervals be in minutes or seconds? (m/s)");
					timMan.setTimerUnits(sc.next());
					
					System.out.println("Send event every: ");				
					timMan.setIntervalType(sc.next());
					
					System.out.println("Add additional random time? (y/n)");
					String answer = sc.next();
					
					if(answer.equalsIgnoreCase("y")){
						timMan.setAddRandomTime(true);
					}
					
					sc.close();
					return;
				default:
					System.out.println("Invalid entry.");
			}
		}
	}
	
	public void run() throws IOException{
		if(eventSendPreference.equalsIgnoreCase("manual") || eventSendPreference.equalsIgnoreCase("m")){
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
			
			if(timMan.isAddRandomTime()){
				randomTimeValue = (new Random().nextInt(21) + 1) ;
			}
			
			while(true){
				if((int) (System.nanoTime() / nanoToSecond) - timerClick >= timMan.getBaseTimerIntervalValue() + randomTimeValue){
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
	
	/**
	 * @return the eventSendPreferences
	 */
	public String getEventSendPreferences() {
		return eventSendPreference;
	}
	
	
	/**
	 * @param eventSendPreferences the eventSendPreferences to set
	 */
	public void setEventSendPreferences(String eventSendPreference) {
		this.eventSendPreference = eventSendPreference;
	}
	
}
