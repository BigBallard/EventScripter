package com.champtc.champ.eventscripter.ui;

import org.eclipse.swt.widgets.Composite;

import com.champtc.champ.eventscripter.ScriptController;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.wb.swt.SWTResourceManager;

public class TimerManipulationComposite extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public TimerManipulationComposite(Composite parent, int style, ScriptController SC) {
		super(parent, SWT.BORDER);
		
		Composite innerComposite = new Composite(this, SWT.NONE);
		innerComposite.setBounds(10, 29, 195, 78);
		
		Button timeRadioButton = new Button(this, SWT.RADIO);
		timeRadioButton.setSelection(true);
		timeRadioButton.setBounds(10, 8, 90, 20);
		timeRadioButton.setText("Timed");
		
		Button manualRadioButton = new Button(this, SWT.RADIO);
		manualRadioButton.setBounds(106, 10, 90, 16);
		manualRadioButton.setText("Manual");
		
		
		Spinner intervalSpinner_1 = new Spinner(innerComposite, SWT.BORDER);
		intervalSpinner_1.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		intervalSpinner_1.setSelection(20);
		intervalSpinner_1.setBounds(0, 21, 47, 22);
		intervalSpinner_1.setValues(20, 0, 999, 0, 1, 10);
		
		Spinner intervalSpinner_2 = new Spinner(innerComposite, SWT.BORDER);
		intervalSpinner_2.setEnabled(false);
		intervalSpinner_2.setSelection(20);
		intervalSpinner_2.setBounds(0, 56, 47, 22);
		intervalSpinner_2.setValues(20, 0, 999, 0, 1, 10);
		
		String choices[] = {"Seconds","Minutes"};
		Combo unitCombo_1 = new Combo(innerComposite, SWT.NONE);
		unitCombo_1.setBounds(63, 21, 91, 23);
		unitCombo_1.setItems(choices);
		unitCombo_1.setText("Seconds");
		
		Combo unitCombo_2 = new Combo(innerComposite, SWT.NONE);
		unitCombo_2.setEnabled(false);
		unitCombo_2.setBounds(63, 56, 91, 23);
		unitCombo_2.setItems(choices);
		unitCombo_2.setText("Seconds");
		
		Button betweenSelectionRadio = new Button(innerComposite, SWT.RADIO);
		betweenSelectionRadio.setText("between");
		betweenSelectionRadio.setBounds(130, -1, 90, 16);
		
		Button everySelectionRadio = new Button(innerComposite, SWT.RADIO);
		everySelectionRadio.setSelection(true);
		everySelectionRadio.setBounds(75, -1, 49, 16);
		everySelectionRadio.setText("every");
		
		Label lblCopyText = new Label(innerComposite, SWT.NONE);
		lblCopyText.setBounds(0, 0, 69, 15);
		lblCopyText.setText("Copy next file");
		
		Label lblAnd = new Label(innerComposite, SWT.NONE);
		lblAnd.setBounds(160, 29, 20, 15);
		lblAnd.setText("and");
		
		
		timeRadioButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SC.timMan.setEventSendPreferences("timed");
				if(timeRadioButton.getSelection()){
					intervalSpinner_1.setEnabled(true);
					unitCombo_1.setEnabled(true);
					innerComposite.setEnabled(true);
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
				SC.timMan.setEventSendPreferences("manual");
				if(manualRadioButton.getSelection()){
					
					intervalSpinner_1.setEnabled(false);
					intervalSpinner_2.setEnabled(false);
					unitCombo_1.setEnabled(false);
					unitCombo_2.setEnabled(false);
					innerComposite.setEnabled(false);
				}
			}
		});
		
		intervalSpinner_1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				SC.timMan.setLowerTimerBound(intervalSpinner_1.getSelection());
			}
		});
		
		intervalSpinner_2.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				SC.timMan.setUpperTimerBound(intervalSpinner_2.getSelection());
			}
		});
		
		unitCombo_1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				SC.timMan.setLowerBoundUnits(unitCombo_1.getText().toLowerCase());
			}
		});
		
		unitCombo_2.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				SC.timMan.setUpperBoundUnits(unitCombo_2.getText().toLowerCase());
			}
		});
		
		betweenSelectionRadio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				intervalSpinner_2.setEnabled(true);
				unitCombo_2.setEnabled(true);
				SC.timMan.setIntervalType("between");
			}
		});
		
		everySelectionRadio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				intervalSpinner_2.setEnabled(false);
				unitCombo_2.setEnabled(false);
				SC.timMan.setIntervalType("every");
			}
		});
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
