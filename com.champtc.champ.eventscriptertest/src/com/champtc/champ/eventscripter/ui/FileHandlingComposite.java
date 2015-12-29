package com.champtc.champ.eventscripter.ui;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.champtc.champ.eventscripter.ScriptController;

public class FileHandlingComposite extends Composite {

	 /**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public FileHandlingComposite(Composite parent, int style, ScriptController SC) {
		super(parent, SWT.BORDER);
		
		Text txtSourceFolder = new Text(this, SWT.BORDER);
		txtSourceFolder.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		txtSourceFolder.setBounds(10, 30, 400, 25);
		txtSourceFolder.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(new File(txtSourceFolder.getText()).exists())
					SC.setSourceFolder(txtSourceFolder.getText());
			}
		});
		txtSourceFolder.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String currentText = txtSourceFolder.getText();
				if(!(new File(currentText).exists())){
					txtSourceFolder.setForeground(parent.getShell().getDisplay().getSystemColor(SWT.COLOR_RED));
				}else{
					txtSourceFolder.setForeground(parent.getShell().getDisplay().getSystemColor(SWT.COLOR_BLACK));
				}
			}
		});
		
		Text txtMonitoredFolder = new Text(this, SWT.BORDER);
		txtMonitoredFolder.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				if(new File(txtMonitoredFolder.getText()).exists())
					SC.setDestinationFolder(txtMonitoredFolder.getText());
			}
		});
		txtMonitoredFolder.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String currentText = txtMonitoredFolder.getText();
				if(!(new File(currentText).exists())){
					txtMonitoredFolder.setForeground(parent.getShell().getDisplay().getSystemColor(SWT.COLOR_RED));
				}else{
					txtMonitoredFolder.setForeground(parent.getShell().getDisplay().getSystemColor(SWT.COLOR_BLACK));
					SC.setDestinationFolder(currentText);
				}
			}
		});
		txtMonitoredFolder.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		txtMonitoredFolder.setBounds(10, 80, 400, 25);
		
		Button sourceBrowseButton = new Button(this, SWT.NONE);
		sourceBrowseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog DD = new DirectoryDialog(parent.getShell());
				DD.setFilterPath("c:\\");
				String resultFolder = DD.open();
				txtSourceFolder.setText(resultFolder);
				SC.dirMan.setSourceFolder(new File(resultFolder));
			}
		});
		sourceBrowseButton.setBounds(420, 30, 85, 25);
		sourceBrowseButton.setText("Browse");
		
		Label sourceDirectoryLabel = new Label(this, SWT.NONE);
		sourceDirectoryLabel.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		sourceDirectoryLabel.setBounds(10, 10, 200, 17);
		sourceDirectoryLabel.setText("Numbered Source Files Directory");
		
		Button monitoredBrowseButton = new Button(this, SWT.NONE);
		monitoredBrowseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog DD = new DirectoryDialog(parent.getShell());
				DD.setFilterPath("c:\\");
				String resultFolder = DD.open();
				txtMonitoredFolder.setText(resultFolder);
				SC.dirMan.setDestinationFolder(new File(resultFolder));
			}
		});
		monitoredBrowseButton.setBounds(420, 80, 85, 25);
		monitoredBrowseButton.setText("Browse");
		setTabList(new Control[]{txtSourceFolder, txtMonitoredFolder, sourceBrowseButton, monitoredBrowseButton});
		
		Label monitoredDirectoryLabel = new Label(this, SWT.NONE);
		monitoredDirectoryLabel.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
		monitoredDirectoryLabel.setBounds(10, 61, 210, 17);
		monitoredDirectoryLabel.setText("DarkLight Monitor Folder");

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
