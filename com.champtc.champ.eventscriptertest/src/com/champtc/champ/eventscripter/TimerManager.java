package com.champtc.champ.eventscripter;

/**
 * Handles the timer settings: the upper and lower bounds of the interval, units of the bounds (either 
 * minutes or seconds), the interval type of each bound. 
 */
public class TimerManager {
	private String lowerBoundUnits;
	private String upperBoundUnits;
	private String intervalType;
	private String eventSendPreference;
	private int lowerTimerBound;
	private int upperTimerBound;
	
	public TimerManager(){
		lowerBoundUnits = "seconds";
		upperBoundUnits = "seconds";
		intervalType = "every";
		eventSendPreference = "timed";
		lowerTimerBound = 20;
		upperTimerBound = 20;
	}
	
	/**
	 * @return the intervalType
	 */
	public String getIntervalType() {
		return intervalType;
	}

	/**
	 * Sets intervals to send either on a fixed interval or between a range of time. The only valid inputs 
	 * are "every" and "between".
	 * @param intervalType the intervalType to set
	 */
	public void setIntervalType(String intervalType) {
		this.intervalType = intervalType;
	}

	/**
	 * @return the lowerTimerBound
	 */
	public int getLowerTimerBound() {
		return lowerTimerBound;
	}

	/**
	 * @param lowerTimerBound the lowerTimerBound to set
	 */
	public void setLowerTimerBound(int lowerTimerBound) {
		this.lowerTimerBound = lowerTimerBound;
	}

	/**
	 * @return the upperTimerBound
	 */
	public int getUpperTimerBound() {
		return upperTimerBound;
	}

	/**
	 * @param upperTimerBound the upperTimerBound to set
	 */
	public void setUpperTimerBound(int upperTimerBound) {
		this.upperTimerBound = upperTimerBound;
	}

	/**
	 * @return the lowerBoundUnits
	 */
	public String getLowerBoundUnits() {
		return lowerBoundUnits;
	}

	/**
	 * @param lowerBoundUnits the lowerBoundUnits to set
	 */
	public void setLowerBoundUnits(String lowerBoundUnits) {
		this.lowerBoundUnits = lowerBoundUnits;
	}

	/**
	 * @return the upperBoundUnits
	 */
	public String getUpperBoundUnits() {
		return upperBoundUnits;
	}

	/**
	 * @param upperBoundUnits the upperBoundUnits to set
	 */
	public void setUpperBoundUnits(String upperBoundUnits) {
		this.upperBoundUnits = upperBoundUnits;
	}

	/**
	 * @return the eventSendPreferences
	 */
	public String getEventSendPreferences() {
		return eventSendPreference;
	}
	
	
	/**
	 * Sets the interval to send in a timed basis or a manually.
	 * @param eventSendPreferences the eventSendPreferences to set
	 */
	public void setEventSendPreferences(String eventSendPreference) {
		this.eventSendPreference = eventSendPreference;
	}
	
	private class InvalidIntervalTypeException extends Exception{
		public InvalidIntervalTypeException(){super();}
		public InvalidIntervalTypeException(String message){super(message);}
		public InvalidIntervalTypeException(String message, Throwable cause){super(message,cause);}
		public InvalidIntervalTypeException(Throwable cause){super(cause);}
	}
}
