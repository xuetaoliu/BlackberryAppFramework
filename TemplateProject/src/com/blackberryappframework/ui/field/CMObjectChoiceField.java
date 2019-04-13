package com.blackberryappframework.UI.Field;

import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.component.ObjectChoiceField;

public class CMObjectChoiceField extends ObjectChoiceField{
	private boolean focusable = true;
	public void setFocusable(boolean focusable) { this.focusable = focusable; }
	public boolean isFocusable() { return this.focusable; }

	public CMObjectChoiceField(String label, Object[] choices) {
		super(label, choices);
		textColor = Color.BLACK;
	}

	public void paint(Graphics graphics) {
		graphics.setColor(textColor);
		super.paint(graphics);
	}
	
	private int textColor;
	public void setTextColor(int color) { textColor = color;}
}
