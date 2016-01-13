package com.champtc.champ.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class EventScripter {
	
	private static final int NON_RESIZABLE = SWT.CLOSE | SWT.TITLE | SWT.MIN | SWT.MAX ;
	
	public EventScripter() {
		Display mainDisplay = new Display();
		Shell mainScripterShell = new Shell(mainDisplay,NON_RESIZABLE);
		mainScripterShell.setSize(535,293);
		mainScripterShell.setText("Event Scripter");
		
		mainScripterShell.setBackground(mainDisplay.getSystemColor(SWT.COLOR_WHITE));
		
		new EventScripterComposite(mainScripterShell, SWT.NONE, mainDisplay);
		
		mainScripterShell.open();
		while(!mainScripterShell.isDisposed()){
			if(!mainDisplay.readAndDispatch())
				mainDisplay.sleep();
		}
		mainDisplay.dispose();
	}
}
