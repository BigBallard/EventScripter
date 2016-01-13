package com.champtc.champ.eventscripter;

public class Util {
	
	public Util(){
		return;
	}
	
	public int nanoToSeconds(long nano){
		int seconds = (int)nano / 1000000000;
		return seconds;
	}
	
	public int milliToSeconds(long milli){
		int seconds = (int)milli/1000;
		return seconds;
	}
	
	public int minutesToSeconds(int minutes){
		int seconds = minutes * 60;
		return seconds; 
	}

}
