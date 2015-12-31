package com.champtc.champ.eventscripter.ui;

import org.eclipse.swt.widgets.Composite;

import com.champtc.champ.eventscripter.ScriptController;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public class FileStatisticsComposite extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public FileStatisticsComposite(Composite parent, int style, ScriptController SC) {
		super(parent, SWT.BORDER);
		
		Label lblSourceFiles = new Label(this, SWT.NONE);
		lblSourceFiles.setForeground(SWTResourceManager.getColor(0, 0, 0));
		lblSourceFiles.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblSourceFiles.setBounds(10, 10, 85, 15);
		lblSourceFiles.setText("Source Files");
		
		Label lblTotal = new Label(this, SWT.NONE);
		lblTotal.setAlignment(SWT.RIGHT);
		lblTotal.setBounds(10, 31, 40, 15);
		lblTotal.setText("Total:");
		
		Label lblSent = new Label(this, SWT.NONE);
		lblSent.setAlignment(SWT.RIGHT);
		lblSent.setBounds(10, 52, 40, 15);
		lblSent.setText("Sent:");
		
		Label lblToGo = new Label(this, SWT.NONE);
		lblToGo.setAlignment(SWT.RIGHT);
		lblToGo.setBounds(10, 73, 40, 15);
		lblToGo.setText("To Go:");
		
		Label lblTotalCount = new Label(this, SWT.NONE);
		lblTotalCount.setAlignment(SWT.RIGHT);
		lblTotalCount.setBounds(61, 31, 55, 15);
		lblTotalCount.setText("0");
		
		Label lblSentCount = new Label(this, SWT.NONE);
		lblSentCount.setAlignment(SWT.RIGHT);
		lblSentCount.setBounds(61, 52, 55, 15);
		lblSentCount.setText("0");
		
		Label lblToGoCount = new Label(this, SWT.NONE);
		lblToGoCount.setAlignment(SWT.RIGHT);
		lblToGoCount.setBounds(61, 73, 55, 15);
		lblToGoCount.setText("0");
		
		Label lblTimeToNext = new Label(this, SWT.NONE);
		lblTimeToNext.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblTimeToNext.setBounds(165, 10, 106, 15);
		lblTimeToNext.setText("Time to next file");
		
		Label lblTimeLeft = new Label(this, SWT.NONE);
		lblTimeLeft.setAlignment(SWT.RIGHT);
		lblTimeLeft.setBounds(165, 31, 40, 15);
		lblTimeLeft.setText("0 :");
		
		Label lblTimeLeftUnits = new Label(this, SWT.NONE);
		lblTimeLeftUnits.setBounds(216, 31, 55, 15);
		lblTimeLeftUnits.setText("Seconds");
		
		Composite playerComposite = new Composite(this, SWT.NONE);
		playerComposite.setBounds(169, 52, 102, 30);
		
		Button playButton = new Button(playerComposite, SWT.NONE);
		playButton.setImage(SWTResourceManager.getImage(FileStatisticsComposite.class, "/icons/play-arrow.png"));
		playButton.setBounds(72, 0, 30, 30);
		
		Button pauseButton = new Button(playerComposite, SWT.NONE);
		pauseButton.setImage(SWTResourceManager.getImage(FileStatisticsComposite.class, "/icons/pause.png"));
		pauseButton.setBounds(36, 0, 30, 30);
		
		Button backButton = new Button(playerComposite, SWT.NONE);
		backButton.setImage(SWTResourceManager.getImage(FileStatisticsComposite.class, "/icons/back.png"));
		backButton.setBounds(0, 0, 30, 30);
		
	

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
