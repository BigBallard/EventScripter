package com.champtc.champ.eventscripter.ui;


import java.io.File;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.champtc.champ.eventscripter.ScriptController;

public class MainScripterUI {
	private static final int NON_RESIZABLE = SWT.CLOSE | SWT.TITLE | SWT.MIN | SWT.MAX ;
	private ScriptController controller;
	private boolean isRunning;
	
	public MainScripterUI(){
		controller = new ScriptController();
		isRunning = false;
		
		
		Display mainDisplay = new Display();
		Shell mainScripterShell = new Shell(mainDisplay,NON_RESIZABLE);
		mainScripterShell.setSize(535,450);
		mainScripterShell.setText("Event Scripter");
		
		mainScripterShell.setBackground(mainDisplay.getSystemColor(SWT.COLOR_WHITE));
		
		Composite fileHandlingComposite = new Composite(mainScripterShell, SWT.BORDER);
		fileHandlingComposite.setBounds(5, 5, 520, 125);
		
		Text txtSourceFolder = new Text(fileHandlingComposite, SWT.BORDER);
		txtSourceFolder.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		txtSourceFolder.setBounds(10, 30, 400, 25);
		
		Text txtMonitoredFolder = new Text(fileHandlingComposite, SWT.BORDER);
		txtMonitoredFolder.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		txtMonitoredFolder.setBounds(10, 80, 400, 25);
		
		Button sourceBrowseButton = new Button(fileHandlingComposite, SWT.NONE);
		sourceBrowseButton.setBounds(420, 30, 85, 25);
		sourceBrowseButton.setText("Browse");
		
		Label sourceDirectoryLabel = new Label(fileHandlingComposite, SWT.NONE);
		sourceDirectoryLabel.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		sourceDirectoryLabel.setBounds(10, 10, 200, 17);
		sourceDirectoryLabel.setText("Numbered Source Files Directory");
		
		Button monitoredBrowseButton = new Button(fileHandlingComposite, SWT.NONE);
		monitoredBrowseButton.setBounds(420, 80, 85, 25);
		monitoredBrowseButton.setText("Browse");
		fileHandlingComposite.setTabList(new Control[]{txtSourceFolder, txtMonitoredFolder, sourceBrowseButton, monitoredBrowseButton});
		
		Label monitoredDirectoryLabel = new Label(fileHandlingComposite, SWT.NONE);
		monitoredDirectoryLabel.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		monitoredDirectoryLabel.setBounds(10, 61, 210, 17);
		monitoredDirectoryLabel.setText("DarkLight Monitor Folder");

//		FileHandlingComposite FHC = new FileHandlingComposite(mainScripterShell, SWT.NONE, controller);
//		FHC.setBounds(5, 5, 520, 125);
		
		Composite timerManipulationComposite = new Composite(mainScripterShell, SWT.BORDER);
		timerManipulationComposite.setBounds(5, 135, 230, 125);
		
		Composite innerComposite = new Composite(timerManipulationComposite, SWT.NONE);
		innerComposite.setBounds(10, 29, 195, 98);
		
		Button timeRadioButton = new Button(timerManipulationComposite, SWT.RADIO);
		timeRadioButton.setSelection(true);
		timeRadioButton.setBounds(10, 8, 90, 20);
		timeRadioButton.setText("Timed");
		
		Button manualRadioButton = new Button(timerManipulationComposite, SWT.RADIO);
		manualRadioButton.setBounds(106, 10, 90, 16);
		manualRadioButton.setText("Manual");
		
		
		Spinner intervalSpinner_1 = new Spinner(innerComposite, SWT.BORDER);
		intervalSpinner_1.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		intervalSpinner_1.setSelection(20);
		intervalSpinner_1.setBounds(0, 30, 47, 23);
		intervalSpinner_1.setValues(20, 0, 999, 0, 1, 10);
		
		Spinner intervalSpinner_2 = new Spinner(innerComposite, SWT.BORDER);
		intervalSpinner_2.setEnabled(false);
		intervalSpinner_2.setSelection(20);
		intervalSpinner_2.setBounds(0, 62, 47, 23);
		intervalSpinner_2.setValues(20, 0, 999, 0, 1, 10);
		
		String choices[] = {"Seconds","Minutes"};
		Combo unitCombo_1 = new Combo(innerComposite, SWT.NONE);
		unitCombo_1.setBounds(63, 30, 91, 22);
		unitCombo_1.setItems(choices);
		unitCombo_1.setText("Seconds");
		
		Combo unitCombo_2 = new Combo(innerComposite, SWT.NONE);
		unitCombo_2.setEnabled(false);
		unitCombo_2.setBounds(63, 62, 91, 23);
		unitCombo_2.setItems(choices);
		unitCombo_2.setText("Seconds");
		
		Button betweenSelectionRadio = new Button(innerComposite, SWT.RADIO);
		betweenSelectionRadio.setText("between");
		betweenSelectionRadio.setBounds(130, 5, 90, 16);
		
		Button everySelectionRadio = new Button(innerComposite, SWT.RADIO);
		everySelectionRadio.setSelection(true);
		everySelectionRadio.setBounds(78, 5, 49, 16);
		everySelectionRadio.setText("every");
		
		Label lblCopyText = new Label(innerComposite, SWT.NONE);
		lblCopyText.setBounds(0, 5, 72, 15);
		lblCopyText.setText("Copy next file");
		
		Label lblAnd = new Label(innerComposite, SWT.NONE);
		lblAnd.setBounds(160, 39, 20, 15);
		lblAnd.setText("and");
		
		
		timeRadioButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				controller.timMan.setEventSendPreferences("timed");
				if(timeRadioButton.getSelection()){
					intervalSpinner_1.setEnabled(true);
					unitCombo_1.setEnabled(true);
					innerComposite.setVisible(true);
					if(betweenSelectionRadio.getSelection()){
						intervalSpinner_2.setEnabled(true);
						unitCombo_2.setEnabled(true);
					}
				}
			}
		});
		
		manualRadioButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				controller.timMan.setEventSendPreferences("manual");
				if(manualRadioButton.getSelection()){
					
					intervalSpinner_1.setEnabled(false);
					intervalSpinner_2.setEnabled(false);
					unitCombo_1.setEnabled(false);
					unitCombo_2.setEnabled(false);
					innerComposite.setVisible(false);;
					
					
				}
			}
		});
		
		intervalSpinner_1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				controller.timMan.setLowerTimerBound(intervalSpinner_1.getSelection());
			}
		});
		
		intervalSpinner_2.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				controller.timMan.setUpperTimerBound(intervalSpinner_2.getSelection());
			}
		});
		
		unitCombo_1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				controller.timMan.setLowerBoundUnits(unitCombo_1.getText().toLowerCase());
			}
		});
		
		unitCombo_2.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				controller.timMan.setUpperBoundUnits(unitCombo_2.getText().toLowerCase());
			}
		});
		
		betweenSelectionRadio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				intervalSpinner_2.setEnabled(true);
				unitCombo_2.setEnabled(true);
				controller.timMan.setIntervalType("between");
			}
		});
		
		everySelectionRadio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				intervalSpinner_2.setEnabled(false);
				unitCombo_2.setEnabled(false);
				controller.timMan.setIntervalType("every");
			}
		});
	

		
//		TimerManipulationComposite TMC = new TimerManipulationComposite(mainScripterShell, SWT.NONE, controller);
//		TMC.setBounds(5, 135, 230, 125);
		
		Composite fileStatisticsComposite = new Composite(mainScripterShell, SWT.BORDER);
		fileStatisticsComposite.setBounds(240, 135, 285, 125);
		

		Label lblSourceFiles = new Label(fileStatisticsComposite, SWT.NONE);
		lblSourceFiles.setForeground(SWTResourceManager.getColor(0, 0, 0));
		lblSourceFiles.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblSourceFiles.setBounds(10, 10, 85, 15);
		lblSourceFiles.setText("Source Files");
		
		Label lblTotal = new Label(fileStatisticsComposite, SWT.NONE);
		lblTotal.setAlignment(SWT.RIGHT);
		lblTotal.setBounds(10, 31, 40, 15);
		lblTotal.setText("Total:");
		
		Label lblSent = new Label(fileStatisticsComposite, SWT.NONE);
		lblSent.setAlignment(SWT.RIGHT);
		lblSent.setBounds(10, 52, 40, 15);
		lblSent.setText("Sent:");
		
		Label lblToGo = new Label(fileStatisticsComposite, SWT.NONE);
		lblToGo.setAlignment(SWT.RIGHT);
		lblToGo.setBounds(10, 73, 40, 15);
		lblToGo.setText("To Go:");
		
		Label lblTotalCount = new Label(fileStatisticsComposite, SWT.NONE);
		lblTotalCount.setAlignment(SWT.RIGHT);
		lblTotalCount.setBounds(61, 31, 55, 15);
		lblTotalCount.setText("0");
		
		Label lblSentCount = new Label(fileStatisticsComposite, SWT.NONE);
		lblSentCount.setAlignment(SWT.RIGHT);
		lblSentCount.setBounds(61, 52, 55, 15);
		lblSentCount.setText("0");
		
		Label lblToGoCount = new Label(fileStatisticsComposite, SWT.NONE);
		lblToGoCount.setAlignment(SWT.RIGHT);
		lblToGoCount.setBounds(61, 73, 55, 15);
		lblToGoCount.setText("0");
		
		Label lblTimeToNext = new Label(fileStatisticsComposite, SWT.NONE);
		lblTimeToNext.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblTimeToNext.setBounds(165, 10, 106, 15);
		lblTimeToNext.setText("Time to next file");
		
		Label lblTimeLeft = new Label(fileStatisticsComposite, SWT.NONE);
		lblTimeLeft.setAlignment(SWT.RIGHT);
		lblTimeLeft.setBounds(165, 31, 40, 15);
		lblTimeLeft.setText("0 :");
		
		Label lblTimeLeftUnits = new Label(fileStatisticsComposite, SWT.NONE);
		lblTimeLeftUnits.setBounds(216, 31, 55, 15);
		lblTimeLeftUnits.setText("Seconds");
		
		Composite playerComposite = new Composite(fileStatisticsComposite, SWT.NONE);
		playerComposite.setBounds(169, 52, 102, 30);
		
		Button playButton = new Button(playerComposite, SWT.NONE);
		playButton.setEnabled(false);
		playButton.setImage(SWTResourceManager.getImage(FileStatisticsComposite.class, "/icons/play-arrow.png"));
		playButton.setBounds(72, 0, 30, 30);
		
		Button pauseButton = new Button(playerComposite, SWT.NONE);
		pauseButton.setEnabled(false);
		pauseButton.setImage(SWTResourceManager.getImage(FileStatisticsComposite.class, "/icons/pause.png"));
		pauseButton.setBounds(36, 0, 30, 30);
		
		Button resetButton = new Button(playerComposite, SWT.NONE);
		resetButton.setEnabled(false);
		resetButton.setImage(SWTResourceManager.getImage(FileStatisticsComposite.class, "/icons/back.png"));
		resetButton.setBounds(0, 0, 30, 30);
		
		
//		FileStatisticsComposite FSC = new FileStatisticsComposite(mainScripterShell, SWT.NONE, controller);
//		FSC.setBounds(240, 135, 285, 125);
		
		// listeners
		// FHC
		txtSourceFolder.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(new File(txtSourceFolder.getText()).exists()){
					controller.setSourceFolder(txtSourceFolder.getText());
					controller.dirMan.setHasSourceFolder(true);
				}
				Integer count = controller.dirMan.getTotalEventFiles();
				lblTotalCount.setText(count.toString());
				playButtonCheck(controller, playButton);
				
			}
		});
		txtSourceFolder.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String currentText = txtSourceFolder.getText();
				if(!(new File(currentText).exists())){
					txtSourceFolder.setForeground(mainDisplay.getSystemColor(SWT.COLOR_RED));
					controller.setSourceFolder("");
					controller.dirMan.setHasSourceFolder(false);
				}else{
					txtSourceFolder.setForeground(mainDisplay.getSystemColor(SWT.COLOR_BLACK));
					controller.setSourceFolder(currentText);
					controller.dirMan.setHasSourceFolder(true);
				}
				playButtonCheck(controller, playButton);
			}
		});
		
		txtMonitoredFolder.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(new File(txtMonitoredFolder.getText()).exists()){
					controller.setDestinationFolder(txtMonitoredFolder.getText());
					controller.dirMan.setHasDestinationFolder(true);
				}
				playButtonCheck(controller, playButton);
			}
		});
		txtMonitoredFolder.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String currentText = txtMonitoredFolder.getText();
				if(!(new File(currentText).exists())){
					txtMonitoredFolder.setForeground(mainDisplay.getSystemColor(SWT.COLOR_RED));
					controller.setDestinationFolder("");
					controller.dirMan.setHasDestinationFolder(false);
				}else{
					txtMonitoredFolder.setForeground(mainDisplay.getSystemColor(SWT.COLOR_BLACK));
					controller.setDestinationFolder(currentText);
					controller.dirMan.setHasDestinationFolder(true);
				}
				playButtonCheck(controller, playButton);
			}
		});
		
		sourceBrowseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog DD = new DirectoryDialog(mainScripterShell);
				DD.setFilterPath("c:\\");
				String resultFolder = DD.open();
				txtSourceFolder.setText(resultFolder);
				controller.setSourceFolder(resultFolder);
				
				Integer count = controller.dirMan.getTotalEventFiles();
				lblTotalCount.setText(count.toString());
				lblToGoCount.setText(count.toString());
				controller.dirMan.setHasSourceFolder(true);
				playButtonCheck(controller, playButton);
			}
		});
		
		monitoredBrowseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog DD = new DirectoryDialog(mainScripterShell);
				DD.setFilterPath("c:\\");
				String resultFolder = DD.open();
				txtMonitoredFolder.setText(resultFolder);
				controller.dirMan.setDestinationFolder(new File(resultFolder));
				controller.dirMan.setHasDestinationFolder(true);
				playButtonCheck(controller, playButton);
			}
		});
		
		// TMC
		timeRadioButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				controller.timMan.setEventSendPreferences("timed");
				if(timeRadioButton.getSelection()){
					intervalSpinner_1.setEnabled(true);
					unitCombo_1.setEnabled(true);
					innerComposite.setVisible(true);
					if(betweenSelectionRadio.getSelection()){
						intervalSpinner_2.setEnabled(true);
						unitCombo_2.setEnabled(true);
					}
					
					pauseButton.setVisible(true);
				}
			}
		});
		
		manualRadioButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				controller.timMan.setEventSendPreferences("manual");
				if(manualRadioButton.getSelection()){
					
					intervalSpinner_1.setEnabled(false);
					intervalSpinner_2.setEnabled(false);
					unitCombo_1.setEnabled(false);
					unitCombo_2.setEnabled(false);
					innerComposite.setVisible(false);
					
					pauseButton.setVisible(false);
				}
			}
		});
		
		intervalSpinner_1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				controller.timMan.setLowerTimerBound(intervalSpinner_1.getSelection());
			}
		});
		
		intervalSpinner_2.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				controller.timMan.setUpperTimerBound(intervalSpinner_2.getSelection());
			}
		});
		
		unitCombo_1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				controller.timMan.setLowerBoundUnits(unitCombo_1.getText().toLowerCase());
			}
		});
		
		unitCombo_2.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				controller.timMan.setUpperBoundUnits(unitCombo_2.getText().toLowerCase());
			}
		});
		
		betweenSelectionRadio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				intervalSpinner_2.setEnabled(true);
				unitCombo_2.setEnabled(true);
				controller.timMan.setIntervalType("between");
			}
		});
		
		everySelectionRadio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				intervalSpinner_2.setEnabled(false);
				unitCombo_2.setEnabled(false);
				controller.timMan.setIntervalType("every");
			}
		});
		
		
		playButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(isRunning){
					controller.setPauseFlag(false);
				}else{
					try {
						play();
						resetLabelCounts(lblSentCount, lblToGoCount);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
		pauseButton.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				controller.setPauseFlag(true);
			}
			
		});
		
		resetButton.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				controller.setResetFlag(true);
			}
		});
		
		
		mainScripterShell.open();
		while(!mainScripterShell.isDisposed()){
			if(!mainDisplay.readAndDispatch())
				mainDisplay.sleep();
		}
		mainDisplay.dispose();
	}
	
	/**
	 * 
	 * @param controller
	 * @param playButton
	 */
	public void playButtonCheck(ScriptController controller,Button playButton){
		if(controller.dirMan.foldersConfigured()){
			playButton.setEnabled(true);
		}else{
			playButton.setEnabled(false);
		}
	}
	
	/**
	 * 
	 */
	public void resetLabelCounts(Label sent, Label togo){
		sent.setText("0");
		Integer togoCount = controller.dirMan.getTotalEventFiles();
		togo.setText(togoCount.toString());
		return;
	}
	
	/**
	 * 
	 * @throws IOException
	 */
	public void play() throws IOException{
		
		if(controller.timMan.getEventSendPreferences().equalsIgnoreCase("manual")){
			isRunning = true;
			controller.runOnManual();
			
		}else if(controller.timMan.getEventSendPreferences().equalsIgnoreCase("timed")){
			isRunning = true;
			controller.runOnTimer();
		}
		
		isRunning = false;
		return;
	}
}
