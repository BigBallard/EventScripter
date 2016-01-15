package com.champtc.champ.timer;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;


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
				
				int lower = timer.getLowerUnit();
				if(timer.getLowerUnitType().equals(IntervalUnitType.MINUTES))
					lower *= 60;
				
				int upper = timer.getUpperUnit();
				if(timer.getUpperUnitType().equals(IntervalUnitType.MINUTES))
					upper *= 60;
				
				int range = upper - lower;
				if(timer.intervalType.equals(IntervalType.BETWEEN)){
					if(range > 0)
					randomTimeValue = (new Random().nextInt(range) + 0);
				}
				
				int timerValue = randomTimeValue + lower;
				try {
					while(timerValue >  0){
						timer.fireTimeUpdate(timerValue);
						
						long time = 0;
						while(time < 1000L){
							Thread.sleep(1L);
							time++;
							
							while(timer.paused()){
								if(timer.resetFlag.get()){
									return;
								}
							}
							if(timer.resetFlag.get()){
								timer.resetFlag.set(false);
								timer.runningStatus.set(false);
								return;
							}
						}
						
						timerValue--;
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				timer.fireSendEvent();
			}
			timer.runningStatus.set(false);
			return;
		}
	};
	
	public void startTimer(){
		if(isRunning()){
			stopFlag.set(false);
			return;
		}
		
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
		
		resetFlag.set(true);
		runningStatus.set(false);
		stopFlag.set(false);
		fireTimerChangedEvent();
		resetFlag.set(false);
		
		// sends for file statistics reset
	}
	
	public void pauseTimer(){
		stopFlag.set(true);
	}
	
	public boolean paused(){
		return stopFlag.get();
	}
	
	public boolean isRunning(){
		return runningStatus.get();
	}
	
	public boolean verifyLegalBounds(){
		int upper = upperUnit;
		if(upperUnitType.equals(IntervalUnitType.MINUTES))
			upper = upperUnit * 60;
		
		int lower = lowerUnit;
		if(lowerUnitType.equals(IntervalUnitType.MINUTES))
			lower = lowerUnit * 60;
		
		return(upper >= lower);
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
	
	private void fireTimeUpdate(int time){
		for(TimerListener l : listeners){
			l.timeUpdate(time);
		}
	}
}
