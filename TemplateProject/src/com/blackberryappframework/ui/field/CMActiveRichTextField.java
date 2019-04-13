package com.blackberryappframework.ui.field;

import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.component.ActiveRichTextField;

public class CMActiveRichTextField extends ActiveRichTextField {
	
//	private final int DEFAULT_MARGIN = 15;
	
//	private int margin = DEFAULT_MARGIN;
	private int fontColor = Color.BLACK;
		
	public CMActiveRichTextField(String text) {
		this(text, CMActiveRichTextField.USE_ALL_WIDTH);
	}
	public CMActiveRichTextField(String text, long style) {
		super(text, style);
		//setPosition(margin, 0);
		setMargin(4, 6, 4, 6); //top, right. bottom, left
	}
	
	public void setTextColor(int color)
	{
		this.fontColor = color;
	}
	
	protected void paint(Graphics g) {
		g.setColor(fontColor);
		super.paint(g);
	}
	
	
}
