package com.champtc.champ.eventscripter.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import com.champtc.champ.eventscripter.DirectoryManager;
import com.champtc.champ.eventscripter.ScriptController;

public class MainScripterUI {
	private static final int NON_RESIZABLE = SWT.CLOSE | SWT.TITLE | SWT.MIN | SWT.MAX ;
	public ScriptController controller;
	
	public MainScripterUI(){
		controller = new ScriptController();
		
		// create the main shell and display
		Display mainDisplay = new Display();
		Shell mainScripterShell = new Shell(mainDisplay,NON_RESIZABLE);
		mainScripterShell.setSize(600,450);
		mainScripterShell.setText("Event Scripter");
		
		FileHandlingComposite FHC = new FileHandlingComposite(mainScripterShell, SWT.NONE, controller);
		FHC.setBounds(5, 5, 520, 125);


//		// create the timed/manual radial buttons
//		Button timedRadial = new Button(mainScripterShell,SWT.RADIO | SWT.BORDER);
//		timedRadial.setText("Timed");
//		timedRadial.setSize(65,25);
		
//		Button manualRadial = new Button(mainScripterShell, SWT.RADIO | SWT.BORDER);
//		manualRadial.setText("Manual");
//		manualRadial.setSize(65,25);
		
//		// create the time spinner
//		Spinner intervalSpinner = new Spinner(mainScripterShell, SWT.BORDER);
//		intervalSpinner.setValues(0, 0, 100, 0, 1, 20);
//		intervalSpinner.pack();
		
		// create the seconds/minutes combo
//		Combo unitsCombo = new Combo(mainScripterShell, SWT.READ_ONLY);
//		unitsCombo.setTextDirection(SWT.DOWN);
//		String items[] = {"Seconds", "Minutes"};
//		unitsCombo.setItems(items);
//		unitsCombo.pack();
		
//		// create the 'add additional random time' button check
//		Button additionalTimeButton = new Button(mainScripterShell, SWT.CHECK);
//		additionalTimeButton.setText("Add dditional random time");
//		additionalTimeButton.pack();
		
		// create Source files update display
		
		// create the player buttons and actions
				
		mainScripterShell.open();
		while(!mainScripterShell.isDisposed()){
			if(!mainDisplay.readAndDispatch())
				mainDisplay.sleep();
		}
		mainDisplay.dispose();
	}

}
