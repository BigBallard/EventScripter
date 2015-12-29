package com.champtc.champ.eventscripter;

/**
 * Handles the timer settings.
 */
public class TimerManager {
	private String timerUnits;
	private String intervalType;
	private int baseTimerIntervalValue;
	private boolean addRandomTime;
	
	public TimerManager(){
		timerUnits = "seconds";
		intervalType = "default";
		baseTimerIntervalValue = 1;
		addRandomTime = false;
	}
	
	/**
	 * @return the intervalType
	 */
	public String getIntervalType() {
		return intervalType;
	}

	/**
	 * @param intervalType the intervalType to set
	 */
	public void setIntervalType(String intervalType) {
		this.intervalType = intervalType;
	}

	/**
	 * @return the fixedIntervalValue
	 */
	public int getBaseTimerIntervalValue() {
		return baseTimerIntervalValue;
	}

	/**
	 * @param fixedIntervalValue the fixedIntervalValue to set
	 */
	public void setBaseTimerIntervalValue(int baseTimeIntervalValue) {
		this.baseTimerIntervalValue = baseTimeIntervalValue;
	}

	/**
	 * @return the timerUnits
	 */
	public String getTimerUnits() {
		return timerUnits;
	}

	/**
	 * @param timerUnits the timerUnits to set
	 */
	public void setTimerUnits(String timerUnits) {
		this.timerUnits = timerUnits;
	}

	/**
	 * @return the addRandomTime
	 */
	public boolean isAddRandomTime() {
		return addRandomTime;
	}

	/**
	 * @param addRandomTime the addRandomTime to set
	 */
	public void setAddRandomTime(boolean addRandomTime) {
		this.addRandomTime = addRandomTime;
	}
	
	
	
}
