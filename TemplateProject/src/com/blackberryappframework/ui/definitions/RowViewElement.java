package com.blackberryappframework.ui.definitions;

import java.util.Vector;

import com.blackberryappframework.ui.Interface.CMFieldCommon;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Ui;

public class RowViewElement {
	public final static int ELEMENT_TEXT = 0;
	public final static int ELEMENT_BITMAP = 1;
	public final static int ELEMENT_BITMAP_ARRAY = 2;
	public final static int ELEMENT_BITMAP_WITH_TEXT = 3;
	public final static int ELEMENT_BITMAP_WITH_TEXT_ARRAY = 4;
	
	private static final int ROW_GAP = 2;
	
	public RowViewElement(Object content) {
		this(content, CMFieldCommon.DEFAULT_FONT, CMFieldCommon.DEFAULT_COLOR_TEXT, DrawStyle.LEFT);
	}
	
	public RowViewElement(Object content, Font font, int color) {
		this(content, font, color, DrawStyle.LEFT);
	}
	
	public RowViewElement(Object content, Font font, int color, int alignment) {
		this.content = content;
		this.color = color;
		this.font = font;
		this.alignment = alignment;
		
		if (content instanceof String)
			type = ELEMENT_TEXT;
		else if (content instanceof Bitmap)
			type = ELEMENT_BITMAP;
		else if (content instanceof Bitmap[])
			type = ELEMENT_BITMAP_ARRAY;
		else if (content instanceof BitmapWithText)
			type = ELEMENT_BITMAP_WITH_TEXT;
		else if (content instanceof BitmapWithText[])
			type = ELEMENT_BITMAP_WITH_TEXT_ARRAY;
		
		this.displayWidth = this.calculateWidth();
		this.displayHeight = this.calculateHeight();
		this.contentHeight = displayHeight;
	}
	
	private Object content;
	public Object getContent() { return this.content; }
	
	private int color;
	public int getColor() { return this.color; }

	private Font font;
	public Font getFont() { return this.font; }

	private int alignment;
	public int getAlignment() { return this.alignment; }

	private int type;
	public int getType() { return type; }
	
	public int getWidth() { return this.displayWidth; }
	public int getHeight() { return this.displayHeight; }
	public void setHeight(int height) { this.displayHeight = height; }
	
	private int displayHeight;
	private int contentHeight;
	private int calculateHeight() {
		int height = 0;
		
		switch (type) {
		case RowViewElement.ELEMENT_TEXT:
			if (wrapText) {
				height += font.getHeight(Ui.UNITS_px) * wrappedLines.size();
				height += (wrappedLines.size()-1) * ROW_GAP;
			} else {
				height += font.getHeight(Ui.UNITS_px);
			}
			break;
		case RowViewElement.ELEMENT_BITMAP:
			Bitmap image = (Bitmap) content;
			height = image.getHeight();
			break;
			
		case RowViewElement.ELEMENT_BITMAP_ARRAY:
			Bitmap[] images = (Bitmap[]) content;
			height += images[0].getHeight();
			break;
		case RowViewElement.ELEMENT_BITMAP_WITH_TEXT_ARRAY:
			BitmapWithText[] bitmapWithText = (BitmapWithText[]) content;
			height += bitmapWithText[0].getHeight();
			break;
		
		}
		
		return height;
	}
	
	private int displayWidth;
	private int calculateWidth() {
		int width = 0;
		
		switch (type) {
		case RowViewElement.ELEMENT_TEXT:
			width = font.getAdvance((String)content);
			break;

		case RowViewElement.ELEMENT_BITMAP:
			Bitmap image = (Bitmap) content;
			width = image.getWidth();
			break;
			
		case RowViewElement.ELEMENT_BITMAP_ARRAY:
			Bitmap[] images = (Bitmap[]) content;
			for(int index=0; index<images.length; index++)
			{
				width += images[index].getWidth() + 5;
			}
			break;
			
		case RowViewElement.ELEMENT_BITMAP_WITH_TEXT_ARRAY:
			BitmapWithText[] bitmapWithText = (BitmapWithText[]) content;
			for(int index=0; index<bitmapWithText.length; index++)
			{
				width += bitmapWithText[index].getWidth() + 5;
			}
			break;
		
		}
		
		return width;
	}
	
	private boolean wrapText = false;
	private Vector wrappedLines;
	public void wrapText(int maxWidth, int maxLines) {
		if (this.type == RowViewElement.ELEMENT_TEXT) {
			this.wrapText = true;
			
			wrappedLines = GeneralUtils.wrapText((String)content, maxWidth, false, font, maxLines);
		}
		
		this.displayWidth = maxWidth;
		this.displayHeight = this.calculateHeight();
	}
	
	/**
	 * Draw the multiple-line content. If the text length exceeds the available width, use "..." at the end.
	 * @param graphics
	 * @param width ------ the available width. 
	 * @param posX 
	 */
	public void draw(Graphics graphics, int width, int posX, int posY, boolean focus) {
		int drawPosY = posY + (this.displayHeight - this.contentHeight) / 2;
		switch (type) {
		case RowViewElement.ELEMENT_BITMAP:
			Bitmap image = (Bitmap) this.content;
			graphics.drawBitmap(posX, drawPosY, image.getWidth(), image.getHeight(), image, 0, 0);
			break;
			
		case RowViewElement.ELEMENT_TEXT:
			String text = (String) this.content;
			
			graphics.setFont(font);
			if (focus)
				graphics.setColor(CMFieldCommon.DEFAULT_COLOR_TEXT_FOCUS);
			else
				graphics.setColor(color);

			if (text != null) {
				if (this.wrapText) {
					int linePosY = drawPosY;
					for (int i = 0; i < this.wrappedLines.size(); i++) {
						text = (String) this.wrappedLines.elementAt(i);
						graphics.drawText(text, posX, linePosY, DrawStyle.ELLIPSIS, width);
						
						linePosY = linePosY + this.font.getHeight(Ui.UNITS_px) + RowViewElement.ROW_GAP;
					}
				} else {
					graphics.drawText(text, posX, drawPosY, DrawStyle.ELLIPSIS, width);
				}
			}
			break;
			
		case RowViewElement.ELEMENT_BITMAP_ARRAY:
			Bitmap[] images = (Bitmap[]) this.content;
			for(int j=0; j<images.length; j++)
			{
				graphics.drawBitmap(posX + j*(images[0].getWidth() + 5) , drawPosY, images[j].getWidth(), images[j].getHeight(), images[j], 0, 0);
			}
			break;
			
		case RowViewElement.ELEMENT_BITMAP_WITH_TEXT_ARRAY:
			BitmapWithText[] bitmapWithText = (BitmapWithText[]) this.content;
			int x = posX;
			for(int j=0; j<bitmapWithText.length; j++)
			{
				if(j > 0)
					x += bitmapWithText[j-1].getWidth() + 15;
				bitmapWithText[j].draw(graphics, x, drawPosY, focus);
			}
			break;
		}
	}

	
}
