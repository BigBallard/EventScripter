package com.champtc.champ.ui;

import java.awt.print.Book;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.champtc.champ.model.DirectoryManager;
import com.champtc.champ.model.DirectoryManagerListener;
import com.champtc.champ.timer.TimerListener;
import com.champtc.champ.timer.TimerManager;
import com.champtc.champ.timer.TimerManager.EventSendPreference;
import com.champtc.champ.timer.TimerManager.IntervalType;
import com.champtc.champ.timer.TimerManager.IntervalUnitType;

public class EventScripterComposite extends Composite {

	
	private TimerManager timerManager;
	private DirectoryManager directoryManager;
	private Text txtSourceFolder;
	private Text txtMonitoredFolder;
	private Button sourceBrowseButton;
	private Button monitoredBrowseButton;
	private Spinner intervalSpinner_1;
	private Spinner intervalSpinner_2;
	private Button timeRadioButton;
	private Button manualRadioButton;
	private Composite innerTimerComposite;
	private Combo unitCombo_1;
	private Combo unitCombo_2;
	private Button betweenSelectionRadio;
	private Button everySelectionRadio;
	private Button playButton;
	private Button pauseButton;
	private Button resetButton;
	private Label lblToGoCount;
	private Label lblSentCount;
	private Label lblTotalCount;
	private Label lblTimeLeft;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 * @throws IOException 
	 */
	public EventScripterComposite(Composite parent, int style, Display mainDisplay) throws IOException {
		super(parent, style);
		
		
		
		timerManager = new TimerManager();
		directoryManager = new DirectoryManager();
		initUI(parent, mainDisplay); 
		initProperties();
		initListeners(parent, mainDisplay);
		

	}
	
	public void initProperties() throws IOException{
		File f = new File("config.properties");
		if(new File("config.properties").exists()){
			Properties properties = new Properties();
			File file = new File("config.properties");
			FileReader reader = new FileReader(file);
			properties.load(reader);
			
			if(properties.getProperty("setVal") == "false"){
				return;
			}
			
			innerTimerComposite.setVisible(new Boolean(properties.getProperty("innerTimerComposite-visible")));
			
			txtSourceFolder.setText(properties.getProperty("sourceFolder"));
			directoryManager.setSourceFolder(new File(properties.getProperty("sourceFolder")));
			lblTotalCount.setText(new Integer(directoryManager.getTotalFileCount()).toString());
			lblToGoCount.setText(new Integer(directoryManager.getTotalFileCount()).toString());
			
			txtMonitoredFolder.setText(properties.getProperty("destinationFolder"));
			directoryManager.setDestinationFolder(new File(properties.getProperty("destinationFolder")));
			
			timeRadioButton.setSelection(new Boolean(properties.getProperty("timeRadioButton")));
			manualRadioButton.setSelection(new Boolean(properties.getProperty("manualRadioButton")));
			if(timeRadioButton.getSelection()){
				timerManager.setEventSendPreference(EventSendPreference.TIMER);
			}else{
				timerManager.setEventSendPreference(EventSendPreference.MANUAL);
			}
			
			intervalSpinner_1.setSelection(new Integer(properties.getProperty("intervalSpinner_1")));
			timerManager.setLowerUnit(new Integer(properties.getProperty("intervalSpinner_1")));
			
			intervalSpinner_2.setSelection(new Integer(properties.getProperty("intervalSpinner_2")));
			intervalSpinner_2.setEnabled(new Boolean(properties.getProperty("intervalSpinner_2-enabled")));
			timerManager.setUpperUnit(new Integer(properties.getProperty("intervalSpinner_2")));
			
			unitCombo_1.setText(properties.getProperty("unitCombo_1"));
			timerManager.setLowerUnitType(IntervalUnitType.valueOf((properties.getProperty("unitCombo_1")).toUpperCase()));
			
			
			unitCombo_2.setText(properties.getProperty("unitCombo_2"));
			unitCombo_2.setEnabled(new Boolean(properties.getProperty("unitCombo_2-enabled")));
			timerManager.setUpperUnitType(IntervalUnitType.valueOf((properties.getProperty("unitCombo_2")).toUpperCase()));
			
			everySelectionRadio.setSelection(new Boolean(properties.getProperty("everySelectionRadio")));
			betweenSelectionRadio.setSelection(new Boolean(properties.getProperty("betweenSelectionRadio")));
			if(everySelectionRadio.getSelection()){
				timerManager.setIntervalType(IntervalType.EVERY);
			}else{
				timerManager.setIntervalType(IntervalType.BETWEEN);
			}
			reader.close();
			
		}else{
			File file = new File("config.properties");
			Properties properties = new Properties();
			properties.setProperty("setVal", "false");
			
			properties.setProperty("innerTimerComposite-visible", "");
			properties.setProperty("timerRadioButton", "");
			properties.setProperty("manualRadioButton", "");
			properties.setProperty("intervalSpinner_1", "");
			properties.setProperty("intervalSpinner_2", "");
			properties.setProperty("intervalSpinner_2-enabled", "");
			properties.setProperty("unitCombo_1", "");
			properties.setProperty("unitCombo_2", "");
			properties.setProperty("unitCombo_2-enabled", "");
			properties.setProperty("everySelectionRadio", "");
			properties.setProperty("betweenSelectionRadio", "");
			
			//Timer Manager
			properties.setProperty("lowerUnitType", "");
			properties.setProperty("upperUnitType", "");
			properties.setProperty("eventSendPreferences", "");
			properties.setProperty("intervalType", "");

			//Directory Manager
			properties.setProperty("sourceFolder", "");
			properties.setProperty("destinationFolder", "");
			
			FileWriter writer = new FileWriter(file);
			properties.store(writer, "Default properties");
			writer.close();
		}
		
		playButtonsCheck();
	}
	
	private void initUI(Composite mainScripterShell, Display mainDisplay){
		
		
		// FILE HANDLING COMPOSITE
		// Contains UI regarding interaction between the file system and the DirectoryManager
		Composite fileHandlingComposite = new Composite(mainScripterShell, SWT.BORDER);
		fileHandlingComposite.setBounds(5, 5, 520, 125);
		
		txtSourceFolder = new Text(fileHandlingComposite, SWT.BORDER);
		txtSourceFolder.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		txtSourceFolder.setBounds(10, 30, 400, 25);
		
		txtMonitoredFolder = new Text(fileHandlingComposite, SWT.BORDER);
		txtMonitoredFolder.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		txtMonitoredFolder.setBounds(10, 80, 400, 25);
		
		sourceBrowseButton = new Button(fileHandlingComposite, SWT.NONE);
		sourceBrowseButton.setBounds(420, 30, 85, 25);
		sourceBrowseButton.setText("Browse");
		
		Label sourceDirectoryLabel = new Label(fileHandlingComposite, SWT.NONE);
		sourceDirectoryLabel.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		sourceDirectoryLabel.setBounds(10, 10, 200, 17);
		sourceDirectoryLabel.setText("Numbered Source Files Directory");
		
		monitoredBrowseButton = new Button(fileHandlingComposite, SWT.NONE);
		monitoredBrowseButton.setBounds(420, 80, 85, 25);
		monitoredBrowseButton.setText("Browse");
		
		fileHandlingComposite.setTabList(new Control[]{txtSourceFolder, txtMonitoredFolder, sourceBrowseButton, monitoredBrowseButton});
		
		Label monitoredDirectoryLabel = new Label(fileHandlingComposite, SWT.NONE);
		monitoredDirectoryLabel.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		monitoredDirectoryLabel.setBounds(10, 61, 210, 17);
		monitoredDirectoryLabel.setText("DarkLight Monitor Folder");

		
		// TIMER MANIPULATION COMPOSITE
		// Allows the user to manipulate the TimerManager settings. This is the main composite of the timer.
		// If the user selects for timer based intervals then the inner timer will be active, otherwise if 
		// set to manual, the inner composite will be disables and will keep the user from manipulating the 
		// settings.
		Composite timerManipulationComposite = new Composite(mainScripterShell, SWT.BORDER);
		timerManipulationComposite.setBounds(5, 135, 230, 125);
		
		innerTimerComposite = new Composite(timerManipulationComposite, SWT.NONE);
		innerTimerComposite.setBounds(10, 29, 195, 98);
		
		timeRadioButton = new Button(timerManipulationComposite, SWT.RADIO);
		timeRadioButton.setSelection(true);
		timeRadioButton.setBounds(10, 8, 90, 20);
		timeRadioButton.setText("Timer");
		
		manualRadioButton = new Button(timerManipulationComposite, SWT.RADIO);
		manualRadioButton.setBounds(106, 10, 90, 16);
		manualRadioButton.setText("Manual");
		
		
		intervalSpinner_1 = new Spinner(innerTimerComposite, SWT.BORDER);
		intervalSpinner_1.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		intervalSpinner_1.setBounds(0, 30, 47, 23);
		intervalSpinner_1.setValues(5, 0, 999, 0, 1, 10);
		
		intervalSpinner_2 = new Spinner(innerTimerComposite, SWT.BORDER);
		intervalSpinner_2.setEnabled(false);
		intervalSpinner_2.setBounds(0, 62, 47, 23);
		intervalSpinner_2.setValues(5, 1, 999, 0, 1, 10);
		
		String choices[] = {"Seconds","Minutes"};
		unitCombo_1 = new Combo(innerTimerComposite, SWT.NONE);
		unitCombo_1.setBounds(63, 30, 91, 22);
		unitCombo_1.setItems(choices);
		unitCombo_1.setText("Seconds");
		
		unitCombo_2 = new Combo(innerTimerComposite, SWT.NONE);
		unitCombo_2.setEnabled(false);
		unitCombo_2.setBounds(63, 62, 91, 23);
		unitCombo_2.setItems(choices);
		unitCombo_2.setText("Seconds");
		
		betweenSelectionRadio = new Button(innerTimerComposite, SWT.RADIO);
		betweenSelectionRadio.setText("between");
		betweenSelectionRadio.setBounds(130, 5, 90, 16);
		
		everySelectionRadio = new Button(innerTimerComposite, SWT.RADIO);
		everySelectionRadio.setSelection(true);
		everySelectionRadio.setBounds(78, 5, 49, 16);
		everySelectionRadio.setText("every");
		
		Label lblCopyText = new Label(innerTimerComposite, SWT.NONE);
		lblCopyText.setBounds(0, 5, 72, 15);
		lblCopyText.setText("Copy next file");
		
		Label lblAnd = new Label(innerTimerComposite, SWT.NONE);
		lblAnd.setBounds(160, 39, 20, 15);
		lblAnd.setText("and");
		
		
		// FILE STATISTICS COMPOSITE 
		// Displays and updates the file counts during a running timer or series of manual sends. Will continue
		// the count until the 'reset' button is selected.
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
		
		lblTotalCount = new Label(fileStatisticsComposite, SWT.NONE);
		lblTotalCount.setAlignment(SWT.RIGHT);
		lblTotalCount.setBounds(61, 31, 55, 15);
		lblTotalCount.setText("0");
		
		lblSentCount = new Label(fileStatisticsComposite, SWT.NONE);
		lblSentCount.setAlignment(SWT.RIGHT);
		lblSentCount.setBounds(61, 52, 55, 15);
		lblSentCount.setText("0");
		
		lblToGoCount = new Label(fileStatisticsComposite, SWT.NONE);
		lblToGoCount.setAlignment(SWT.RIGHT);
		lblToGoCount.setBounds(61, 73, 55, 15);
		lblToGoCount.setText("0");
		
		Label lblTimeToNext = new Label(fileStatisticsComposite, SWT.NONE);
		lblTimeToNext.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblTimeToNext.setBounds(165, 10, 106, 15);
		lblTimeToNext.setText("Time to next file");
		
		lblTimeLeft = new Label(fileStatisticsComposite, SWT.NONE);
		lblTimeLeft.setAlignment(SWT.RIGHT);
		lblTimeLeft.setBounds(190, 31, 40, 15);
		lblTimeLeft.setText("0:00");
		
		
		
		// PLAYER COMPOSITE
		// Is contained in the file statistics composite. When application is opened the buttons will be disabled
		// until, at minimum, the source and destination folders are set. Once set, the play button will be enabled to play
		// on the default settings. If the send preference is set to manual, the pause button will be disabled. When
		// play is selected for timer based sending, the pause and reset buttons will be enabled and play disabled.
		Composite playerComposite = new Composite(fileStatisticsComposite, SWT.NONE);
		playerComposite.setBounds(169, 52, 102, 30);
		
		playButton = new Button(playerComposite, SWT.NONE);
		playButton.setEnabled(false);
		playButton.setImage(SWTResourceManager.getImage(EventScripterComposite.class, "/icons/play-arrow.png"));
		playButton.setBounds(72, 0, 30, 30);
		
		pauseButton = new Button(playerComposite, SWT.NONE);
		pauseButton.setEnabled(false);
		pauseButton.setImage(SWTResourceManager.getImage(EventScripterComposite.class, "/icons/pause.png"));
		pauseButton.setBounds(36, 0, 30, 30);
		
		resetButton = new Button(playerComposite, SWT.NONE);
		resetButton.setEnabled(false);
		resetButton.setImage(SWTResourceManager.getImage(EventScripterComposite.class, "/icons/back.png"));
		resetButton.setBounds(0, 0, 30, 30);
		

	}
	

	
	private void initListeners(Composite mainScripterShell, Display mainDisplay) {
		
		mainScripterShell.addListener(SWT.Close, new Listener() {
			public void handleEvent(Event event) {
				
				if(timerManager.paused()){
					MessageBox closeMessage = new MessageBox(getShell(), SWT.APPLICATION_MODAL | SWT.YES | SWT.NO);
					closeMessage.setText("TIMER PAUSED!");
					closeMessage.setMessage("The scripter is paused, close anyway?");
					
					event.doit = closeMessage.open() == SWT.YES;
				}else if(timerManager.isRunning()){
					MessageBox closeMessage = new MessageBox(getShell(), SWT.APPLICATION_MODAL | SWT.YES | SWT.NO);
					closeMessage.setText("ACTIVE TIMER!");
					closeMessage.setMessage("The scripter is still running, close anyway?");
					
					event.doit = closeMessage.open() == SWT.YES;
				}else{
					MessageBox closeMessage = new MessageBox(getShell(), SWT.APPLICATION_MODAL | SWT.YES | SWT.NO);
					closeMessage.setMessage("Closing");
					closeMessage.setMessage("Are you sure you want to exit EventScripter?");
					
					event.doit = closeMessage.open() == SWT.YES;
				}
				try {
					savePreferences();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		// LISTENERS LISTENERS LISTENERS
		txtSourceFolder.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(new File(txtSourceFolder.getText()).exists()){
					directoryManager.setSourceFolder(new File(txtSourceFolder.getText()));
					directoryManager.setHasSourceFolder(true);
				}
				playButtonsCheck();
				
			}
		});
		
		txtSourceFolder.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String currentText = txtSourceFolder.getText();
				if(!(new File(currentText).exists())){
					txtSourceFolder.setForeground(mainDisplay.getSystemColor(SWT.COLOR_RED));
					directoryManager.setSourceFolder(new File(""));
					directoryManager.setHasSourceFolder(false);
					lblTotalCount.setText("0");
					lblToGoCount.setText("0");
				}else{
					txtSourceFolder.setForeground(mainDisplay.getSystemColor(SWT.COLOR_BLACK));
					directoryManager.setSourceFolder(new File(currentText));
					directoryManager.setHasSourceFolder(true);
					lblTotalCount.setText(new Integer(directoryManager.getTotalFileCount()).toString());
					lblToGoCount.setText(new Integer(directoryManager.getTotalFileCount()).toString());
				}
				playButtonsCheck();
			}
		});
		
		txtMonitoredFolder.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(new File(txtMonitoredFolder.getText()).exists()){
					directoryManager.setDestinationFolder(new File(txtMonitoredFolder.getText()));
					directoryManager.setHasDestinationFolder(true);
				}
				playButtonsCheck();
			}
		});
		txtMonitoredFolder.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String currentText = txtMonitoredFolder.getText();
				if(!(new File(currentText).exists())){
					txtMonitoredFolder.setForeground(mainDisplay.getSystemColor(SWT.COLOR_RED));
					directoryManager.setDestinationFolder(new File(""));
					directoryManager.setHasDestinationFolder(false);
				}else{
					txtMonitoredFolder.setForeground(mainDisplay.getSystemColor(SWT.COLOR_BLACK));
					directoryManager.setDestinationFolder(new File(currentText));
					directoryManager.setHasDestinationFolder(true);
				}
				playButtonsCheck();
			}
		});
		
		sourceBrowseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog DD = new DirectoryDialog((Shell) mainScripterShell);
				DD.setFilterPath("c:\\");
				String resultFolder = DD.open();
				if(resultFolder != null){
					txtSourceFolder.setText(resultFolder);
					
					if(directoryManager.setSourceFolder(new File(resultFolder))){
						Integer count = directoryManager.getTotalFileCount();
						lblTotalCount.setText(count.toString());
						lblToGoCount.setText(count.toString());
						directoryManager.setHasSourceFolder(true);
					}
				}
				
				
				playButtonsCheck();
			}
		});
		
		monitoredBrowseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog DD = new DirectoryDialog((Shell)mainScripterShell);
				DD.setFilterPath("c:\\");
				String resultFolder = DD.open();
				
				if(resultFolder != null){
					txtMonitoredFolder.setText(resultFolder);
					directoryManager.setDestinationFolder(new File(resultFolder));
					directoryManager.setHasDestinationFolder(true);
				}
				
				playButtonsCheck();
			}
		});
		
		// TMC
		timeRadioButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(timerManager.getEventSendPreference().equals(EventSendPreference.MANUAL)){
					timerManager.resetTimer();
				}
				timerManager.setEventSendPreference(EventSendPreference.TIMER);
				lblTimeLeft.setVisible(true);
				
				if(timeRadioButton.getSelection()){
					intervalSpinner_1.setEnabled(true);
					unitCombo_1.setEnabled(true);
					innerTimerComposite.setVisible(true);
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
				timerManager.setEventSendPreference(EventSendPreference.MANUAL);
				lblTimeLeft.setVisible(false);
				if(timerManager.isRunning()){
					timerManager.resetTimer();
				}
				
				if(manualRadioButton.getSelection()){
					
					intervalSpinner_1.setEnabled(false);
					intervalSpinner_2.setEnabled(false);
					unitCombo_1.setEnabled(false);
					unitCombo_2.setEnabled(false);
					innerTimerComposite.setVisible(false);
					
					pauseButton.setVisible(false);
					timerManager.pauseTimer();
				}
			}
		});
		
		intervalSpinner_1.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				timerManager.setLowerUnit(intervalSpinner_1.getSelection());
				if(intervalSpinner_1.getSelection() > intervalSpinner_2.getSelection() && (timerManager.getLowerUnitType().equals(timerManager.getUpperUnitType()))){
					intervalSpinner_2.setSelection(intervalSpinner_1.getSelection());
					timerManager.setUpperUnit(intervalSpinner_1.getSelection());
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		intervalSpinner_2.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				timerManager.setUpperUnit(intervalSpinner_2.getSelection());
				if(intervalSpinner_1.getSelection() > intervalSpinner_2.getSelection() && (timerManager.getLowerUnitType().equals(timerManager.getUpperUnitType()))){
					intervalSpinner_1.setSelection(intervalSpinner_2.getSelection());
					timerManager.setLowerUnit(intervalSpinner_2.getSelection());
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		unitCombo_1.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(unitCombo_1.getText().equalsIgnoreCase("seconds")){
					timerManager.setLowerUnitType(IntervalUnitType.SECONDS);
				}else{
					timerManager.setLowerUnitType(IntervalUnitType.MINUTES);
				}
				
				if(unitCombo_1.getText().equalsIgnoreCase("Minutes")){
					unitCombo_2.setText("Minutes");
					timerManager.setUpperUnitType(IntervalUnitType.MINUTES);
					if(intervalSpinner_1.getSelection() > intervalSpinner_2.getSelection()){
						intervalSpinner_2.setSelection(intervalSpinner_1.getSelection());
						timerManager.setUpperUnit(intervalSpinner_1.getSelection());
					}
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		unitCombo_2.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(unitCombo_2.getText().equalsIgnoreCase("seconds")){
					timerManager.setUpperUnitType(IntervalUnitType.SECONDS);
				}else{
					timerManager.setUpperUnitType(IntervalUnitType.MINUTES);
				}
				
				if(unitCombo_1.getText().equalsIgnoreCase("minutes") && unitCombo_2.getText().equalsIgnoreCase("seconds")){
					unitCombo_1.setText("Seconds");
					timerManager.setLowerUnitType(IntervalUnitType.SECONDS);
					if(intervalSpinner_1.getSelection() > intervalSpinner_2.getSelection()){
						intervalSpinner_1.setSelection(intervalSpinner_2.getSelection());
						timerManager.setLowerUnit(intervalSpinner_2.getSelection());
					}
				}
					

			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		betweenSelectionRadio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				intervalSpinner_2.setEnabled(true);
				unitCombo_2.setEnabled(true);
				timerManager.setIntervalType(IntervalType.BETWEEN);
			}
		});
		
		everySelectionRadio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				intervalSpinner_2.setEnabled(false);
				unitCombo_2.setEnabled(false);
				timerManager.setIntervalType(IntervalType.EVERY);
			}
		});
		
		
		playButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				resetButton.setEnabled(true);
				
				if(timerManager.getEventSendPreference() == EventSendPreference.TIMER){
					disableTimerConfiguration();
					if(timerManager.isRunning()){
						playButton.setEnabled(false);
						pauseButton.setEnabled(true);
						
						timerManager.startTimer();
						System.out.println("Timer resumed.");
					}else{
						playButton.setEnabled(false);
						pauseButton.setEnabled(true);
						
						timerManager.startTimer();
						System.out.println("Timer started.");
					}
					
				}else{
					
					directoryManager.copyFile();
				}
			}
		});
		
		pauseButton.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				enableTimerConfiguration();
				timerManager.pauseTimer();
				
				pauseButton.setEnabled(false);
				playButton.setEnabled(true);
				
				System.out.println("Timer paused.");
			}
			
		});
		
		resetButton.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				timerManager.resetTimer();
				directoryManager.resetIndex();
				playButton.setEnabled(true);
				resetButton.setEnabled(false);
				pauseButton.setEnabled(false);
				enableTimerConfiguration();
				lblTimeLeft.setText("0:00");
				lblSentCount.setText("0");
				lblToGoCount.setText(lblTotalCount.getText());
				
				System.out.println("Timer reset.");
			}
		});
		timerManager.addListener(new TimerListener() {
			
			@Override
			public void timerChanged() {
				Display.getDefault().syncExec(new Runnable() {
					@Override
					public void run() {
						lblTimeLeft.setText("0:00");
						lblSentCount.setText("0");
						lblToGoCount.setText(lblTotalCount.getText());
					}
				});
			}

			@Override
			public void sendEvent() {
				directoryManager.copyFile();
			}

			@Override
			public void timeUpdate(int time) {
				Integer seconds = new Integer(time % 60);
				Integer minutes = new Integer(time / 60);

				Display.getDefault().syncExec(new Runnable() {
					@Override
					public void run() {
						if(seconds < 10){
							lblTimeLeft.setText(minutes.toString() + ":0" + seconds.toString());
						}else{
							lblTimeLeft.setText(minutes.toString() + ":" + seconds.toString());
						}
					}
				});
			}
		});
		
		directoryManager.addListener(new DirectoryManagerListener() {
			
			@Override
			public void sourceFolderChanged() {
				if(timerManager.isRunning()){
					timerManager.resetTimer();
				}
			}
			
			@Override
			public void fileCopied() {
				Display.getDefault().syncExec(new Runnable() {
					@Override
					public void run() {	
						directoryManager.incrementCurrentIndex();
						Integer count = directoryManager.getCurrentIndex();
						lblSentCount.setText(count.toString());
						count = directoryManager.getTotalFileCount() - directoryManager.getCurrentIndex();
						lblToGoCount.setText(count.toString());
					}
				});
			}

			@Override
			public void noMoreFilesEvent() {
				Display.getDefault().syncExec(new Runnable() {
					@Override
					public void run() {
						timerManager.resetTimer();
						if(timerManager.getEventSendPreference().equals(EventSendPreference.TIMER))
							enableTimerConfiguration();
						playButtonsCheck();
						
						System.out.println("All files copied!");
					}
				});
			}
		});
		
	}
	
	public void enableTimerConfiguration() {
		innerTimerComposite.setVisible(true);
		timeRadioButton.setEnabled(true);
		manualRadioButton.setEnabled(true);
	}
	public void disableTimerConfiguration() {
		innerTimerComposite.setVisible(false);
		timeRadioButton.setEnabled(false);
		manualRadioButton.setEnabled(false);
	}
	public void playButtonsCheck(){
		if(directoryManager.foldersConfigured()){
			playButton.setEnabled(true);
			pauseButton.setEnabled(false);
			resetButton.setEnabled(false);
		}else{
			playButton.setEnabled(false);
			pauseButton.setEnabled(false);
			resetButton.setEnabled(false);
		}
	}
	
	public void savePreferences() throws IOException{
		File file = new File("config.properties");
		FileWriter writer = new FileWriter(file);
		
		Properties properties = new Properties();
		properties.setProperty("setVal", "true");
		
		//GUI properties
		properties.setProperty("innerTimerComposite-visible", new Boolean(innerTimerComposite.getVisible()).toString());
		properties.setProperty("timeRadioButton", new Boolean(timeRadioButton.getSelection()).toString());
		properties.setProperty("manualRadioButton", new Boolean(manualRadioButton.getSelection()).toString());
		properties.setProperty("intervalSpinner_1", new Integer(intervalSpinner_1.getSelection()).toString());
		properties.setProperty("intervalSpinner_2", new Integer(intervalSpinner_2.getSelection()).toString());
		properties.setProperty("intervalSpinner_2-enabled", new Boolean(intervalSpinner_2.getEnabled()).toString());
		properties.setProperty("unitCombo_1", unitCombo_1.getText());
		properties.setProperty("unitCombo_2", unitCombo_2.getText());
		properties.setProperty("unitCombo_2-enabled", new Boolean(unitCombo_2.getEnabled()).toString());
		properties.setProperty("everySelectionRadio", new Boolean(everySelectionRadio.getSelection()).toString());
		properties.setProperty("betweenSelectionRadio", new Boolean(betweenSelectionRadio.getSelection()).toString());
		properties.setProperty("everySelectionRadio", new Boolean(everySelectionRadio.getSelection()).toString());
		
		//Timer Manager
		properties.setProperty("lowerUnitType", timerManager.getLowerUnitType().toString());
		properties.setProperty("upperUnitType", timerManager.getUpperUnitType().toString());
		properties.setProperty("intervalType", timerManager.getIntervalType().toString());

		//Directory Manager
		properties.setProperty("sourceFolder", directoryManager.getSourceFolder().getPath());
		properties.setProperty("destinationFolder", directoryManager.getDestinationFolder().getAbsolutePath());
		
		properties.store(writer, "Last exit settings");
		writer.close();
	}
	
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
