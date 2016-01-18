package com.champtc.champ.timer;

public interface TimerListener {

		public void timerChanged();
		
		public void sendEvent();
		
		public void timeUpdate(int time);
		
		public void consoleWrite(String message);
		
		
}
