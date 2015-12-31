package com.champtc.champ.eventscripter;

public class Util {
	
	public Util(){
		return;
	}
	
	public int nanoToSecond(long value){
		int seconds = (int)value / 1000000000;
		return seconds;
	}
	
	public int nanoToMinute(int value){
		int seconds = value / 1000000000;
		int remainingSeconds = seconds % 60;
		int minutes = (seconds - remainingSeconds) / 60;
		return value;
	}
	
	public int secondToMinute(int value){
		
		return value;
	}
	
	public int minuteToSecond(int value){
		
		return value;
	}

}
