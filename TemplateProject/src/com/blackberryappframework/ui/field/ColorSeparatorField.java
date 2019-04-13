package com.blackberryappframework.ui.field;

import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.component.SeparatorField;

public class ColorSeparatorField extends SeparatorField{

	private int color;
	private int line_width;
	
	public ColorSeparatorField(int color, int line_width)
	{
		this.color = color;
		this.line_width= line_width;
	}
	
	protected void layout(int width, int height)
	{
		setExtent(width, this.line_width);
	}
	
	protected void paint(Graphics g)
	{
		g.setBackgroundColor(color);						   
	    g.clear();									
	    super.paint(g);
	}
}
