package com.champtc.champ.eventscripter.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.champtc.champ.eventscripter.ScriptController;

public class MainScripterUI {
	private static final int NON_RESIZABLE = SWT.CLOSE | SWT.TITLE | SWT.MIN | SWT.MAX ;
	public ScriptController controller;
	
	public MainScripterUI(){
		controller = new ScriptController();
		
		Display mainDisplay = new Display();
		Shell mainScripterShell = new Shell(mainDisplay,NON_RESIZABLE);
		mainScripterShell.setSize(535,450);
		mainScripterShell.setText("Event Scripter");
		
		mainScripterShell.setBackground(mainDisplay.getSystemColor(SWT.COLOR_WHITE));
		
		FileHandlingComposite FHC = new FileHandlingComposite(mainScripterShell, SWT.NONE, controller);
		FHC.setBounds(5, 5, 520, 125);
		
		TimerManipulationComposite TMC = new TimerManipulationComposite(mainScripterShell, SWT.NONE, controller);
		TMC.setBounds(5, 135, 230, 125);
		
		FileStatisticsComposite FSC = new FileStatisticsComposite(mainScripterShell, SWT.NONE, controller);
		FSC.setBounds(240, 135, 285, 125);
		

		mainScripterShell.open();
		while(!mainScripterShell.isDisposed()){
			if(!mainDisplay.readAndDispatch())
				mainDisplay.sleep();
		}
		mainDisplay.dispose();
	}

}
