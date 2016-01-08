package com.champtc.champ.timer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import com.champtc.champ.eventscripter.ScriptController;
import com.sun.prism.shape.ShapeRep.InvalidationType;

public class TimerManager {
	private Set<TimerListener> listeners = new HashSet<TimerListener>();
	public enum IntervalUnitType{
		SECONDS, MINUTES
	}
	
	public enum IntervalType{
		EVERY, BETWEEN
	}
	
	public enum EventSendPreference{
		TIMER, MANUAL
	}
	
	private IntervalUnitType lowerUnitType = IntervalUnitType.SECONDS;
	
	private IntervalUnitType upperUnitType = IntervalUnitType.SECONDS;
	
	private EventSendPreference eventSendPreference = EventSendPreference.TIMER;
	
	private int lowerUnit = 5;
	
	private int upperUnit = 5;
	
	private IntervalType intervalType = IntervalType.EVERY;
	
	private AtomicBoolean runningStatus = new AtomicBoolean(false);
	
	private AtomicBoolean stopFlag = new AtomicBoolean(false);
	
	private AtomicBoolean resetFlag = new AtomicBoolean(false);
	
	private ExecutorService executor;
	
	
	public static class RunOnTimer implements Runnable{
		
		TimerManager timer;
		
		public RunOnTimer(TimerManager tm){
			timer = tm;
		};
		
		@Override
		public void run(){
			
			timer.runningStatus.set(true);
			while(timer.isRunning()){
				int randomTimeValue = 0;
				int range = timer.getUpperUnit() - timer.getLowerUnit();
				if(range > 0)
					randomTimeValue = (new Random().nextInt(timer.getUpperUnit() - timer.getLowerUnit()) + 0);
				
				int timerValue = randomTimeValue + timer.getLowerUnit();
				try {
					while(timerValue >= 0){
						Thread.sleep(1000L);
						timerValue--;
						while(timer.paused()){
						}
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				timer.fireSendEvent();
			}
			return;
		}
	};
	
	public void startTimer(){
		stopFlag.set(false);
		
		if(eventSendPreference.equals(EventSendPreference.TIMER)){
			if(intervalType.equals(IntervalType.BETWEEN)){
				if(verifyLegalBounds()){
					runningStatus.set(true);
					
					executor = Executors.newFixedThreadPool(1);
					executor.execute(new RunOnTimer(this));
					executor.shutdown();
					
					try {
						executor.awaitTermination(100, TimeUnit.MILLISECONDS);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					return;
				}else{
					return;
				}
			}else{
				runningStatus.set(true);
				
				executor = Executors.newFixedThreadPool(1);
				executor.execute(new RunOnTimer(this));
				executor.shutdown();
				
				try {
					executor.awaitTermination(100, TimeUnit.MILLISECONDS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				return;
			}
		}else{
			runningStatus.set(true);
		}
		
		// start a job (delay, check stop flag, fire events)
		
		return;
	}
	
	public void resetTimer(){
		fireTimerChangedEvent();
		stopFlag.set(false);
		executor.shutdownNow();
	}
	
	public void pauseTimer(){
		if(stopFlag.get()){
			stopFlag.set(false);
		}else{
			stopFlag.set(true);
		}
	}
	
	public boolean paused(){
		return stopFlag.get();
	}
	
	public boolean isRunning(){
		return runningStatus.get();
	}
	
	public boolean verifyLegalBounds(){
		return(upperUnit >= lowerUnit);
	}
	/**
	 * @return the lowerUnitType
	 */
	public IntervalUnitType getLowerUnitType() {
		return lowerUnitType;
	}

	/**
	 * @param lowerUnitType the lowerUnitType to set
	 */
	public void setLowerUnitType(IntervalUnitType lowerUnitType) {
		this.lowerUnitType = lowerUnitType;
	}

	/**
	 * @return the upperUnitType
	 */
	public IntervalUnitType getUpperUnitType() {
		return upperUnitType;
	}

	/**
	 * @param upperUnitType the upperUnitType to set
	 */
	public void setUpperUnitType(IntervalUnitType upperUnitType) {
		this.upperUnitType = upperUnitType;
	}
	

	/**
	 * @return the eventSendPreference
	 */
	public EventSendPreference getEventSendPreference() {
		return eventSendPreference;
	}

	/**
	 * @param eventSendPreference the eventSendPreference to set
	 */
	public void setEventSendPreference(EventSendPreference eventSendPreference) {
		this.eventSendPreference = eventSendPreference;
	}

	/**
	 * @return the lowerUnit
	 */
	public int getLowerUnit() {
		return lowerUnit;
	}

	/**
	 * @param lowerUnit the lowerUnit to set
	 */
	public boolean setLowerUnit(int lowerUnit) {
		//check for > 0 
		this.lowerUnit = lowerUnit;
		return true;
	}

	/**
	 * @return the upperUnit
	 */
	public int getUpperUnit() {
		return upperUnit;
	}

	/**
	 * @param upperUnit the upperUnit to set
	 */
	public boolean setUpperUnit(int upperUnit) {
		//check return if > 0
		this.upperUnit = upperUnit;
		return true;
	}

	/**
	 * @return the intervalType
	 */
	public IntervalType getIntervalType() {
		return intervalType;
	}

	/**
	 * @param intervalType the intervalType to set
	 */
	public void setIntervalType(IntervalType intervalType) {
		this.intervalType = intervalType;
	}

	public void addListener(TimerListener l){
		listeners.add(l);
	}
	
	public void removeListener(TimerListener l){
		listeners.remove(l);
	}
	
	private void fireTimerChangedEvent(){
		for(TimerListener l : listeners){
			l.timerChanged();
		}
	}
	
	private void fireSendEvent(){
		for(TimerListener l : listeners){
			l.sendEvent();
		}
	}
}
