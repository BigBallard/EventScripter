package com.champtc.champ.ui;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

public class EventScripter {
	
	private static final int NON_RESIZABLE = SWT.CLOSE | SWT.TITLE | SWT.MIN | SWT.MAX ;
	
	public EventScripter() throws IOException {
	  
		Display mainDisplay = new Display();
		
		/** The main shell instance */
		Shell mainScripterShell = new Shell(mainDisplay,NON_RESIZABLE);
		mainScripterShell.setSize(535,370);
		mainScripterShell.setText("Event Scripter");
		mainScripterShell.setImage(SWTResourceManager.getImage(EventScripterComposite.class, "/icons/Icon-Small.png"));
		mainScripterShell.setBackground(mainDisplay.getSystemColor(SWT.COLOR_WHITE));
		
		/** Initialize the Event Scripter components */
		new EventScripterComposite(mainScripterShell, SWT.NONE, mainDisplay);
		
		
		mainScripterShell.open();
		while(!mainScripterShell.isDisposed()){
			if(!mainDisplay.readAndDispatch())
				mainDisplay.sleep();
		}
		mainDisplay.dispose();
	}
}
