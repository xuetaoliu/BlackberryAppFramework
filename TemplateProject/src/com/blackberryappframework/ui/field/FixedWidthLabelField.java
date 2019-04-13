/**
 * @author      Xuetao (Sheldon) Liu
 * @version     2011.0513                                 
 * @history
 *              Date        Author  Comments
 *              2011/05/17  X.L.    change constructor, if use_all_width is set,we should check it 
 *              2011/05/13  X.L.    added layout function                                        
 *              2011/04/26  X.L.    added new constructor                                        
 *              2011/03/01  X.L.    created
 **/

package com.blackberryappframework.ui.field;

import com.blackberryappframework.ui.Interface.CMFieldCommon;
import com.blackberryappframework.ui.Interface.CMFieldInterface;

import net.rim.device.api.system.Display;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.component.LabelField;

public class FixedWidthLabelField extends LabelField implements CMFieldInterface, CMFieldCommon{

	private int backgroundColor;
	private int backgroundColor_f;
	
	private int textColor;
	private int textColor_f;
	
	/* The text drawStyle. By default, it is HCenter and VCenter */
	private long textStyle;
	
	private int fieldWidth;
	private int fieldHeight;
	
	private boolean focusable = false;
	
	public FixedWidthLabelField(String text){
		this(text, FixedWidthLabelField.FIELD_LEFT, DrawStyle.HCENTER | DrawStyle.VCENTER);
	}

	public FixedWidthLabelField(String text, long fieldStyle, long textStyle, boolean focusable){
		this(text, fieldStyle, textStyle);
		
		this.setFocusable(focusable);
	}
	
	public FixedWidthLabelField(String text, long fieldStyle, long textStyle){
		super(text, fieldStyle);
		
		this.textStyle = textStyle;
		
		Font font = this.getFont();
		if (( fieldStyle | FixedWidthLabelField.USE_ALL_WIDTH) != 0 ) 
			this.setFieldWidth(Display.getWidth());
		else
			this.setFieldWidth(font.getAdvance(text));
		this.setFieldHeight(font.getHeight());
		
		this.setTextColor(DEFAULT_COLOR_TEXT, DEFAULT_COLOR_TEXT_FOCUS);
		this.setBackgroundColor(-1, -1);
	}
	
	public boolean isFocusable() { return this.focusable; }

	public void setFont(Font font) {
		super.setFont(font);
		
		if (this.fieldHeight < font.getHeight()) {
			setFieldHeight(font.getHeight());
		}
		if (this.fieldWidth < font.getAdvance(this.getText())) {
			setFieldWidth(font.getAdvance(this.getText()));
		}
	}
	
	public int getPreferredWidth() {
		return this.fieldWidth;
	}

	public int getPreferredHeight() {
		return this.fieldHeight;
	}

	protected void paint(Graphics graphics) {
        
       if (this.backgroundColor != -1) {
        	graphics.setColor(this.backgroundColor);
        	graphics.fillRect(0, 0, this.getPreferredWidth(), this.getPreferredHeight());
       }

        this.drawText(graphics, false);
    }
	
	protected void drawFocus(Graphics graphics, boolean on) 
	{
		if (this.backgroundColor_f != -1) {
			graphics.setColor(backgroundColor_f);
			graphics.fillRect(0, 0, this.getPreferredWidth(), this.getPreferredHeight());
		}
		
		this.drawText(graphics, true);
	}
	
	private void drawText(Graphics graphics, boolean focus) {
		int color = focus ? textColor_f : textColor;
		if (color == -1)
			return;
		
		String text = this.getText();

		Font font = this.getFont();
		int posX = 0;
		int posY = 0;
		
		int drawStyle = (int) (this.textStyle & 0x7); //0x07: 000111
		if (drawStyle == DrawStyle.LEFT) {
			; //posX = 0;
		} else if ( ( drawStyle == DrawStyle.HCENTER) || (drawStyle == 0)){
			posX = ( this.fieldWidth - font.getAdvance(text) ) / 2;
		} else if (drawStyle == DrawStyle.RIGHT) {
			posX = this.fieldWidth - font.getAdvance(text);
		} 
		
		drawStyle = (int) (this.textStyle & 0x38); //0x38: 111000
		if (drawStyle == DrawStyle.TOP) {
			;//posY = 0;
		}else if ( (drawStyle == DrawStyle.VCENTER) || (drawStyle == 0)){
			posY = ( this.fieldHeight - font.getHeight() ) / 2;
		} else if (drawStyle == DrawStyle.BOTTOM) {
			posY = this.fieldHeight - font.getHeight() ;
		}
		
		graphics.setColor(color);
		graphics.drawText(text, posX, posY, DrawStyle.ELLIPSIS, this.getPreferredWidth());
	}

	public void setFieldWidth(int width) { this.fieldWidth = width; }
	public int getFieldWidth() { return this.fieldWidth; }

	public void setFieldHeight(int height) { this.fieldHeight = height; }
	public int getFieldHeight() { return this.fieldHeight; }

	private int layoutStyle = DrawStyle.LEFT;
	public void setLayoutStyle(int style) { layoutStyle = style; }
	public int getLayoutStyle() { return layoutStyle; }

	public void setFieldPadding(int horizontalPadding, int verticalPadding) {
		this.setFieldWidth(this.fieldWidth + horizontalPadding * 2);
		this.setFieldHeight(this.fieldHeight + verticalPadding * 2);
	}

	public void setBackgroundColor(int color, int focusColor) {
		this.backgroundColor = color;
		this.backgroundColor_f = focusColor;
	}
	public void setTextColor(int color, int focusColor) {
		this.textColor = color;
		this.textColor_f = focusColor;
	}

	public void setBorderColor(int color, int focusColor) {
		;
	}

	public void setFocusable(boolean focusable) { this.focusable = focusable; }

}
