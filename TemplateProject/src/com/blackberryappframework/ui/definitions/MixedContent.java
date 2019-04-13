/**
 * This class implements the multiple-line content included in RowViewButtonField. Each line can be a String, several images(Bitmap[]) or 
 * several images with texts(one image one text, one image one text......)
 */

package com.blackberryappframework.ui.definitions;

import java.util.Vector;

import net.rim.device.api.ui.Graphics;


public class MixedContent {

	private static final int ROW_GAP = 5;
	private Vector contentVector;       // the content Vector can have two kinds of elements: Bitmap[] and String.
	
	/**
	 * @param the content to be drawed. It should be in the RowViewElement type
	 */
	public MixedContent(Vector contentVector) {
		updateContent(contentVector);
	}
	
	public Vector getContentData() { return this.contentVector; }
	
	/**
	 * Draw the multiple-line content. If the text length exceeds the available width, use "..." at the end.
	 * @param graphics
	 * @param width ------ the available width. 
	 * @param posX 
	 */
	public void draw(Graphics graphics, int width, int posX, int posY, boolean focus)
	{
		for(int i=0; i < this.contentVector.size(); i++)
		{
			RowViewElement element = (RowViewElement)contentVector.elementAt(i);
			element.draw(graphics, width, posX, posY, focus);
			posY += element.getHeight() + ROW_GAP;
		}
	}
	
	public int calculateHeight()
	{
		int h=0;
		for(int i=0; i<this.contentVector.size(); i++)
		{
			RowViewElement element = (RowViewElement)contentVector.elementAt(i);
			h += element.getHeight();
		}
		
		h += (this.contentVector.size()-1)*ROW_GAP;
		return h;
	}
	
	/**
	 * Compute the width of this multiple-line content.
	 * @return The width of the longest line. 
	 */
	public int calculateWidth()
	{
		int w=0;
		int tempWidth=0;
		for(int i=0; i<this.contentVector.size(); i++)
		{
			RowViewElement element = (RowViewElement)contentVector.elementAt(i);
			tempWidth = element.getWidth();
		    if(w < tempWidth)
		    	w=tempWidth;
		}
		return w;
	}
	
	public void updateContent(Vector contentV) {
		this.contentVector = contentV;
	}

}
