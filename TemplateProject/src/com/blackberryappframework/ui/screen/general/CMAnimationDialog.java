package com.blackberryappframework.ui.screen.general;


import com.blackberryappframework.ui.field.AnimatedGIFField;

import net.rim.device.api.system.GIFEncodedImage;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Keypad;
import net.rim.device.api.ui.Screen;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.VerticalFieldManager;

public class CMAnimationDialog extends Screen  
{
	private HorizontalFieldManager HFM;
	private LabelField label;
	private AnimatedGIFField _ourAnimation;
	
	public CMAnimationDialog(String message, String agifFileName) {
		this(message, agifFileName, 30*1000);
	}
	public CMAnimationDialog(String message, String agifFileName, long timeout) {
		super(new VerticalFieldManager(), Screen.DEFAULT_CLOSE);

		HFM=new HorizontalFieldManager();
		add(HFM);
		
		GIFEncodedImage ourAnimation = (GIFEncodedImage) GIFEncodedImage.getEncodedImageResource(agifFileName);
        _ourAnimation = new AnimatedGIFField(ourAnimation, Field.FIELD_VCENTER);
        HFM.add(_ourAnimation);
        
        label=new LabelField(message, LabelField.FIELD_VCENTER) {

			protected void paint(Graphics graphics) {
				graphics.setColor(Color.BLACK);
				super.paint(graphics);
			}
        	
        };
        HFM.add(label);
		
        TimerThread timer = new TimerThread(timeout);
        timer.start();
	}

	protected void sublayout(int width, int height) {
		int preferredWidth = _ourAnimation.getPreferredWidth() + label.getPreferredWidth() + 20;
		if (preferredWidth > width - 120)
			preferredWidth = width - 120;
		
		layoutDelegate(preferredWidth, height - 80);
		setPositionDelegate(10, 10);
		
		preferredWidth += 20;
		setExtent(preferredWidth, Math.min(height - 60, getDelegate().getHeight() + 20));
		setPosition((width - preferredWidth)/2, (height - getHeight())/2);
	}

	protected void paintBackground(Graphics graphics) {
		graphics.setColor(Color.LIGHTGRAY);
		graphics.fillRect(0, 0, getWidth(), getHeight());
		graphics.setColor(Color.BLACK);
		graphics.drawRect(0, 0, getWidth(), getHeight());
	}
	
	// Disable the Escape key.
	protected boolean keyChar(char character, int status, int time) {
		if (character == Keypad.KEY_ESCAPE) {
			return true;
		}
		return super.keyChar(character, status, time);
	}
	
	private class TimerThread extends Thread
	{
		private long timeout;
		public TimerThread(long timeout)
		{ 
			this.timeout = timeout;
		}
		
		public void run()
		{
			try {
				sleep(timeout);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			UiApplication app = UiApplication.getUiApplication();
			synchronized(UiApplication.getEventLock())
			{
				if(app.getActiveScreen() == CMAnimationDialog.this)
				{
					app.popScreen(app.getActiveScreen());
				}
			}			
		}
	}
	
}
