package com.blackberryappframework.ui.definitions;

import com.blackberryappframework.ui.Interface.CMFieldCommon;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;

public class BitmapWithText {
	
	private Bitmap image;
	private String label;
	private Font font;
	private int color;
	
	private final int GAP = 2;
	
	public BitmapWithText(Bitmap image, String label, Font font, int color)
	{
		this.image = image;
		this.label = label;
		this.font = font;
		this.color = color;
	}
	
	public void draw(Graphics graphics, int posX, int posY, boolean focus)
	{
		graphics.drawBitmap(posX, posY + (getHeight() - image.getHeight())/2, image.getWidth(), image.getHeight(), image, 0, 0);
		
		graphics.setFont(font);
		if (focus)
			graphics.setColor(CMFieldCommon.DEFAULT_COLOR_TEXT_FOCUS);
		else
			graphics.setColor(color);
		
		graphics.drawText(label, posX + image.getWidth() + GAP,  posY + (getHeight() - font.getHeight())/2);		
	}

	public int getHeight()
	{
		return Math.max(image.getHeight(), font.getHeight());
	}
	
	public int getWidth()
	{
		return image.getWidth() +  font.getAdvance(label) + GAP;
	}
	
}
