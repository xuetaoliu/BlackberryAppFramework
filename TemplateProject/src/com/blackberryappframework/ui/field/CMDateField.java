package com.blackberryappframework.UI.Field;

import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.component.DateField;

public class CMDateField extends DateField{
	public CMDateField(String label, long date, long style) {
		super(label, date, style);
		
		textColor = Color.BLACK;
	}

	private boolean focusable = true;
	public void setFocusable(boolean focusable) { this.focusable = focusable; }
	public boolean isFocusable() { return this.focusable; }
	
	public void drawFocus(Graphics graphics, boolean on) {
		super.drawFocus(graphics, on);
	}
	public void paint(Graphics graphics) {
		graphics.setColor(textColor);
		super.paint(graphics);
	}

	private int textColor;
	public void setTextColor(int color) { textColor = color; }
}
