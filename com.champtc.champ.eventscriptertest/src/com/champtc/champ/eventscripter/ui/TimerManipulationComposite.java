package com.champtc.champ.eventscripter.ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;

public class TimerManipulationComposite extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public TimerManipulationComposite(Composite parent, int style) {
		super(parent, style);
		
		Button btnRadioButton = new Button(this, SWT.RADIO);
		btnRadioButton.setBounds(10, 10, 90, 16);
		btnRadioButton.setText("Timed");
		
		Button btnRadioButton_1 = new Button(this, SWT.RADIO);
		btnRadioButton_1.setBounds(106, 10, 90, 16);
		btnRadioButton_1.setText("Radio Button");

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
