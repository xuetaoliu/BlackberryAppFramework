package com.blackberryappframework.ui.field;

import com.blackberryappframework.ui.Interface.CMFieldCommon;
import com.blackberryappframework.ui.Interface.CMFieldInterface;

import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.component.CheckboxField;

public class CustomerCheckboxField extends CheckboxField implements CMFieldInterface{
	public static final int RIGHT = 0;
	public static final int LEFT = 1;
	
	private final int COLOR_RECT = 0x00646464;
	private final int COLOR_RECT_FOCUS = Color.LIGHTGRAY;
	private final int COLOR_CHECKMARK = 0x00207CFE;
	private final int COLOR_CHECKMARK_FOCUS = Color.WHITE;

	private String label;
	private int layout;
	
	public CustomerCheckboxField() { this("", true, RIGHT);}
	public CustomerCheckboxField(String label, boolean checked, int layout) {
		this(label, checked, USE_ALL_WIDTH, layout);
	}
	public CustomerCheckboxField(String label, boolean checked, long style, int layout) {
		super (label, checked, style);
	
		this.label = label;
		this.layout = layout;
		
		if (layout == LEFT) 
			this.setLabel("");
		
		this.setTextColor(CMFieldCommon.DEFAULT_COLOR_TEXT, CMFieldCommon.DEFAULT_COLOR_TEXT_FOCUS);
		this.setRectColor(COLOR_RECT, COLOR_RECT_FOCUS);
		this.setCheckmarkColor(COLOR_CHECKMARK, COLOR_CHECKMARK_FOCUS);
		
		this.setFieldPadding(CMFieldCommon.DEFAULT_HORIZONTAL_PADDING/2, CMFieldCommon.DEFAULT_VERTICAL_PADDING/2);
		
	}
	
	private int horizontalPadding;
	private int verticalPadding;
	public void setFieldPadding(int horizontalPadding, int verticalPadding) {
		this.horizontalPadding = horizontalPadding;
		this.verticalPadding = verticalPadding;
//		this.setPadding(verticalPadding, horizontalPadding, verticalPadding, verticalPadding);
		this.setPadding(0,0,0,0);
	}
	
	public void setFieldWidth(int width) {  }
	public int getFieldWidth() { return this.getPreferredWidth() - this.horizontalPadding * 2; }
	
	public void setFieldHeight(int height) {  }
	public int getFieldHeight() { return this.getPreferredHeight() - this.verticalPadding * 2; }
	public void setPreferredWidth(int width) { }
	public void setPreferredHeight(int height) { }	
	
	public void setBackgroundColor(int color, int focusColor) { }
	
	private int textColor;
	private int textColor_f;
	public void setTextColor(int color, int focusColor) { 
		this.textColor = color;
		this.textColor_f = focusColor;
	}
	
	public void setBorderColor(int color, int focusColor) { }
	
	private int layoutStyle = DrawStyle.LEFT;
	public void setLayoutStyle(int style) { layoutStyle = style; }
	public int getLayoutStyle() {
		return layoutStyle;
	}
	
	private boolean focusable = true;
	public void setFocusable(boolean focusable) { this.focusable = focusable; }
	
	private int rectColor;
	private int rectColor_f;
	public void setRectColor(int color, int color_f){
		rectColor=color;
		rectColor_f = color_f;
	}
	
	private int checkmarkColor;
	private int checkmarkColor_f;
	public void setCheckmarkColor(int color, int color_f){
		checkmarkColor = color;
		checkmarkColor_f = color_f;
	}
	
	private int rectangularSize = 15;
	public void setRectSize(int size){
		rectangularSize=size;
	}

	public int getPreferredWidth() {
		return super.getPreferredWidth() + this.rectangularSize;
	}
	
	public boolean isFocusable() { return this.focusable; }
	
	public void paint(Graphics graphics) {
		drawContent(graphics);
	}
	
	protected void drawFocus(Graphics graphics, boolean on) {
//		graphics.setColor(CMFieldCommon.DEFAULT_COLOR_BACKGROUND_FOCUS);
//		graphics.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		super.drawFocus(graphics, on);

		drawContent(graphics);
	}
	
	private void drawContent(Graphics graphics) {
		int posX ;
		int posY ;
		if (layout == LEFT) {
			posX = horizontalPadding;
			posY = ( this.getPreferredHeight() - this.getFont().getHeight() ) / 2;
			drawText(graphics, posX, posY);
			
			posX = this.getWidth() - rectangularSize - horizontalPadding;
			posY = ( this.getPreferredHeight()- rectangularSize -verticalPadding ) / 2;
			drawRect(graphics, posX, posY);
		} else {
			posX = horizontalPadding;
			posY = ( this.getPreferredHeight()- rectangularSize ) / 2;
			drawRect(graphics, posX, posY);

			posX = horizontalPadding + rectangularSize + horizontalPadding;
			posY = ( this.getPreferredHeight() - this.getFont().getHeight() ) / 2;
			drawText(graphics, posX, posY);
		}
		
	}

	private void drawText(Graphics graphics, int posX, int posY) {
		int color = this.textColor;
		if (this.isFocus())
			color = this.textColor_f;
		
		graphics.setColor(color);
		graphics.drawText(label, posX, posY);
	}
	
	private void drawRect(Graphics graphics, int posX, int posY) {
		int color = rectColor;
		if (this.isFocus())
			color = rectColor_f;
		if (color == -1)
			color = COLOR_RECT;
		graphics.setColor( color );
		graphics.drawRoundRect(posX, posY, rectangularSize, rectangularSize, 5, 5);

		//draw check mark
		if (this.getChecked()) {
			color = checkmarkColor;
			if (this.isFocus())
				color = checkmarkColor_f;
			if (color == -1)
				color = COLOR_CHECKMARK;
			graphics.setColor(color);

//			graphics.setDrawingStyle(Graphics.DRAWSTYLE_ANTIALIASED , true);
			
			graphics.drawLine(posX+2, posY + rectangularSize * 1/2, posX + rectangularSize * 1/3, posY + rectangularSize - 4);
			graphics.drawLine(posX + rectangularSize * 1/3, posY + rectangularSize - 4, posX + rectangularSize - 2, posY + 2);
			graphics.drawLine(posX+2, posY + rectangularSize * 1/2+1, posX + rectangularSize * 1/3, posY + rectangularSize - 4+1);
			graphics.drawLine(posX + rectangularSize * 1/3, posY + rectangularSize - 4+1, posX + rectangularSize - 2, posY + 2+1);
		}
	}

	
}
