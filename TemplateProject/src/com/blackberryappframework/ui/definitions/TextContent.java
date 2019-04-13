package com.blackberryappframework.UI.Definition;

/**
 * This class implements the multiple-line text included in RowViewButtonField.
 */
import java.util.Vector;

import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.FontFamily;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Ui;


public class TextContent {

	public static final int ROW_GAP=2;
	private Vector textVector;
	private Vector fontVector;
	private Vector colorVector;
	private int size;
	private Font defaultFont;
	
	/**
	 * All the three vectors must have the same length!
	 * @param textLines 
	 * @param font
	 * @param color
	 * @throws VectorLengthNotEqualException 
	 */
	public TextContent(Vector textLines, Vector font, Vector color)
	{
		this.textVector=textLines;
		this.fontVector=font;
		this.colorVector=color;
		
		size=textVector.size();
		
		try 
		{
			FontFamily alphaSansFamily = FontFamily.forName("BBAlpha Serif");
			defaultFont = alphaSansFamily.getFont(Font.PLAIN, 8, Ui.UNITS_pt);
		}
		catch (ClassNotFoundException e) 
		{	}
		
	}
	
	/**
	 * Draw the multiple-line text. If the length exceeds the available width, use "..." at the end.
	 * @param graphics
	 * @param width ------ the available width. 
	 * @param posX 
	 */
	public void draw(Graphics graphics, int width, int posX, int posY, boolean focused)
	{
		Font font;
		String text;
		Integer color;
		
		for(int i=0; i < size; i++)
		{
			font=(Font) fontVector.elementAt(i);
			text= (String) textVector.elementAt(i);
			color= (Integer) colorVector.elementAt(i);
			
			int colorV = focused ? Color.WHITE : color.intValue();
			graphics.setFont(font);
			graphics.setColor(colorV);
			
			if (text != null)
			{
				graphics.drawText(text, posX, posY, DrawStyle.ELLIPSIS, width);
				posY += font.getHeight() + ROW_GAP;
			}
				
		}
	}
	
	
	public int calculateHeight()
	{
		int h=0;
		for(int i=0; i<size; i++)
		{
			h += ((Font)(fontVector.elementAt(i))).getHeight();
		}
		h += (size+1)*ROW_GAP;
		return h;
	}
	
	/**
	 * Compute the width of this multiple-line text.
	 * @return The width of the longest line. 
	 */
	public int calculateWidth()
	{
		int w=0;
		int tempWidth;
		for(int i=0; i<size; i++)
		{
			tempWidth=((Font)fontVector.elementAt(i)).getAdvance(((String)textVector.elementAt(i)));
		    if(w < tempWidth)
		    	w=tempWidth;
		}
		return w;
	}
	
	public Vector getTextVector()
	{
		return this.textVector;
	}
	
	public void setTextVector(Vector textV)
	{
		this.textVector=textV;
		size=textVector.size();
	}
	
	public Vector getFontVector()
	{
		return this.fontVector;
	}
	
	public void setFontVector(Vector fontV)
	{
		this.fontVector = fontV;
	}
	
	public Vector getColorVector()
	{
		return this.colorVector;
	}
	
	public void setColorVector(Vector colorV)
	{
		this.colorVector = colorV;
	}
		
}
