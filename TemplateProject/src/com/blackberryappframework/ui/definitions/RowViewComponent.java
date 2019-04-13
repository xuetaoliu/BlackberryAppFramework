package com.blackberryappframework.ui.definitions;

/**
 * The class implements the component included in RowViewButtonField. 
 */
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.XYRect;

public class RowViewComponent {

	public static final int ROW_VIEW_LEFT = 1;
	public static final int ROW_VIEW_RIGHT = 2;
	public static final int ROW_VIEW_CENTER = 4;
	public static final int ROW_VIEW_TOP  = 8;
	public static final int ROW_VIEW_BOTTOM = 16;
	public static final int ROW_VIEW_USEALLWIDTH = 32;
	public static final int ROW_VIEW_CONTENT_LEFT = 64;
	public static final int ROW_VIEW_CONTENT_RIGHT = 128;
	public static final int ROW_VIEW_CONTENT_CENTER = 256;
	
	public static final int ROW_COMPONENT_BITMAP = 1;
	public static final int ROW_COMPONENT_TEXT   = 2;
	public static final int ROW_COMPONENT_MIXCONTENT = 3;
	
	private int componentType;
	private Object content; // This object is either a Bitmap instance or a TextContent instance.
	private int layoutStyle;
	
	private int posX ;
	private int posY ;
	private int availableWidth;
	private int availableHeight;
	
	private int contentWidth;
	
	public RowViewComponent(Object content)
	{
		this(content, ROW_VIEW_LEFT); 
	}
	
	public RowViewComponent(Object content, int layoutStyle)
	{
		this.content = content; 
		this.layoutStyle = layoutStyle;
		
		if (content instanceof Bitmap) {
			componentType = ROW_COMPONENT_BITMAP;
//		} else if (content instanceof TextContent) {
//			componentType = ROW_COMPONENT_TEXT;
		} else if (content instanceof MixedContent) {
			componentType = ROW_COMPONENT_MIXCONTENT;
		}

		posX = 0;
		posY = 0;

		availableWidth = this.getComponentWidth();
		availableHeight = this.getComponentHeight();
		
		contentWidth = this.getComponentWidth();
	}
	
	public void wrapText(RowViewElement element, int maxLines) {
		element.wrapText(availableWidth, maxLines);
		availableHeight = this.getComponentHeight();
	}
	
	public Object getContent() { return this.content;}
	public boolean linearUpable() { return this.content instanceof MixedContent; }
	
	public int getStyle() { return layoutStyle; }
	public boolean isCenterComponent() { return (layoutStyle & ROW_VIEW_USEALLWIDTH) != 0 ; }
	
	public int getPosX() { return posX;}
	public int getPosY() { return posY;}
	public void setPosX(int x) { posX = x;}
	public void setPosY(int y) { posY = y;}
	
	public void setPreferredWidth(int width) { this.availableWidth = width;}
	public int getPreferredWidth() { return this.availableWidth; }

	public void setPreferredHeight(int height) { this.availableHeight = height;}
	public int getPreferredHeight() { return this.availableHeight; }
	public void recalculatePreferredHeight() { this.availableHeight = this.getComponentHeight(); }
	
	public int getContentWidth() { return this.contentWidth; }
	
	public void drawComponent(Graphics graphics, int posX, int posY, boolean wrapText, boolean focus) {
		int drawPosX = posX + getPosX();
		int drawPosY = posY + getPosY();
		
		switch (this.componentType) {
		case ROW_COMPONENT_BITMAP:
			Bitmap bitmap = (Bitmap)this.content;
			XYRect rect = new XYRect(drawPosX, drawPosY, getPreferredWidth(), bitmap.getHeight());
			graphics.drawBitmap(rect, bitmap, 0, 0);
			break;
			
//		case ROW_COMPONENT_TEXT:
//			TextContent text = (TextContent)this.content;
//			text.draw(graphics, getPreferredWidth(), drawPosX, drawPosY, wrapText);
//			break;
		
		case RowViewComponent.ROW_COMPONENT_MIXCONTENT:
			MixedContent mixedContent = (MixedContent)this.content;
			mixedContent.draw(graphics, getPreferredWidth(), drawPosX, drawPosY, focus);
			break;
		}
	}
	
	private int getComponentWidth()
	{
		int width = 0;
		switch (componentType) {
		case RowViewComponent.ROW_COMPONENT_BITMAP:
			width = ((Bitmap)content).getWidth();
			break;
//		case RowViewComponent.ROW_COMPONENT_TEXT:
//			width = ((TextContent)content).calculateWidth();
//			break;
		case RowViewComponent.ROW_COMPONENT_MIXCONTENT:
			width = ((MixedContent)content).calculateWidth();
			break;
		default:
			width = availableWidth;
		}

		if (width != 0) width += 5;
		
		return width;
	}
	
	private int getComponentHeight() 
	{
		int height = 0;
		switch (componentType) {
		case RowViewComponent.ROW_COMPONENT_BITMAP:
			height = ((Bitmap)content).getHeight();
			break;
//		case RowViewComponent.ROW_COMPONENT_TEXT:
//			height = ((TextContent)content).calculateHeight(getPreferredWidth(), false);
//			break;
		case RowViewComponent.ROW_COMPONENT_MIXCONTENT:
			height = ((MixedContent)content).calculateHeight();
			break;
		default:
			break;
		}
		
		return height;
	}
	
}
