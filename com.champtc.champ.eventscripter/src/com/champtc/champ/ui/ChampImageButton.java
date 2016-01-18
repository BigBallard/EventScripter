package com.champtc.champ.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;

public class ChampImageButton extends Composite {
  private Label lblImage;

  public ChampImageButton(final Composite parent, final Image image) {
    super(parent, SWT.NONE);
    setBackgroundMode(SWT.INHERIT_DEFAULT);
    GridLayout gridLayout = new GridLayout(1, false);
    gridLayout.horizontalSpacing = 0;
    gridLayout.verticalSpacing = 0;
    gridLayout.marginWidth = 0;
    gridLayout.marginHeight = 0;
    setLayout(gridLayout);

    this.lblImage = new Label(this, SWT.NONE);
    this.lblImage.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
    this.lblImage.setImage(image);

    Rectangle bounds = image.getBounds();
    setSize(new Point(bounds.width, bounds.height));

    this.lblImage.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseUp(final MouseEvent e) {
        Event event = new Event();
        event.button = e.button;
        ChampImageButton.this.notifyListeners(SWT.MouseUp, event);
      }
    });
  }

  @Override
  public void setToolTipText(final String tooltip) {
    this.lblImage.setToolTipText(tooltip);
  }

  @Override
  public void setEnabled(final boolean isEnabled) {
    this.lblImage.setEnabled(isEnabled);
    super.setEnabled(isEnabled);
  }
}
