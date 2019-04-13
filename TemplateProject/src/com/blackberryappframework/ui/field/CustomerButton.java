/**
 * @author      Xuetao (Sheldon) Liu
 * @version     2011.0426                                 
 * @history
 *              Date        Author  Comments
 *              2011/0/27   X.L.    add keyDown event process
 *              2011/05/20  X.L.    fixed a bug in wrap()
 *              2011/03/01  X.L.    created
 **/

package com.blackberryappframework.ui.field;

import java.util.Vector;

import com.blackberryappframework.ui.Interface.CMFieldCommon;
import com.blackberryappframework.ui.Interface.CMFieldInterface;
import com.blackberryappframework.utility.GeneralUtils;
import com.blackberryappframework.utility.StringUtils;

import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Keypad;

public class CustomerButton extends Field implements CMFieldInterface{

	protected String label;
    
    protected int fieldWidth;
	protected int fieldHeight;

	//the layout style in parent manager
	protected int layout;
    
	protected int backgroundColor;
	protected int backgroundColor_focus;
	protected int textColor;
	protected int textColor_focus;
    
    public CustomerButton(String labelTxt, long style) {
    	super(style | Field.FOCUSABLE | Field.FIELD_VCENTER ) ;

    	this.label = labelTxt;
    	this.setMultiLine(1);
    	
    	Font font = this.getFont();  
    	this.setFieldWidth(font.getAdvance(label));
    	this.setFieldHeight(font.getHeight());
    	
    	this.setLayoutStyle(DrawStyle.LEFT);

    	this.setTextColor(DEFAULT_COLOR_TEXT, DEFAULT_COLOR_TEXT_FOCUS);
    	this.setBackgroundColor(DEFAULT_COLOR_BACKGROUND, DEFAULT_COLOR_BACKGROUND_FOCUS);
    	this.setBorderColor(DEFAULT_COLOR_BORDER, DEFAULT_COLOR_BORDER_FOCUS);
    	this.setFieldPadding(DEFAULT_HORIZONTAL_PADDING, DEFAULT_VERTICAL_PADDING);
    }

	public String getLabel() { return this.label;}
	
    public void setFieldWidth(int width) { this.fieldWidth = width;	}
	public void setFieldHeight(int height) { this.fieldHeight = height;	}
	public int getFieldWidth() {  return this.fieldWidth; }
	public int getFieldHeight() { return this.fieldHeight; }

	public void setPreferredWidth(int width) {
		this.fieldWidth = width - this.horizontalPadding * 2;
	}
	public void setPreferredHeight(int height) {
		this.fieldHeight = height = this.verticalPadding * 2;
	}

	public void setLayoutStyle(int style) { this.layout = style; }
	public int getLayoutStyle() { return this.layout; }

	protected int horizontalPadding;
	protected int verticalPadding;
	public void setFieldPadding(int horizontalPadding, int verticalPadding) {
		this.horizontalPadding = horizontalPadding;
		this.verticalPadding = verticalPadding;
		
//    	this.setPadding(verticalPadding, horizontalPadding, 
//    			verticalPadding, horizontalPadding);
	}

	public void setBackgroundColor(int color, int focusColor) {
		this.backgroundColor = color;
		this.backgroundColor_focus = focusColor;
	}

	public void setTextColor(int color, int focusColor) {
		this.textColor = color;
		this.textColor_focus = focusColor;
	}

	protected int borderColor;
	protected int borderColor_f;
	public void setBorderColor(int color, int focusColor) {
		this.borderColor = color;
		this.borderColor_f = focusColor;
	}

	protected boolean focusable = true;
	public void setFocusable(boolean focusable) {
		this.focusable = focusable;
	}
	
    protected int multiLines;
	public void setMultiLine(int maxLines) { 
		this.multiLines = maxLines;
		
		int height = this.getFont().getHeight();
		height = multiLines == 1 ? height : (height + CMFieldCommon.DEFAULT_ROW_SPACE ) * this.multiLines;
		this.setFieldHeight(height);
	}
	
	public boolean isFocusable() { return this.focusable; }
	
    public int getPreferredWidth() {
    	return this.fieldWidth + this.horizontalPadding * 2;
    }
    public int getPreferredHeight() {
    	return this.fieldHeight + this.verticalPadding * 2;
    }
    
    public void setFont(Font font) {
    	super.setFont(font);
    	
    	if (multiLines == 1) {
    		this.setFieldWidth(font.getAdvance(label));
        	this.setFieldHeight(font.getHeight());
    	} else {
			int height = this.getFont().getHeight();
			height = multiLines == 1 ? height : (height + CMFieldCommon.DEFAULT_ROW_SPACE ) * this.multiLines;
			this.setFieldHeight(height);
    	}
    }

    protected void layout(int width, int height) {
    	setExtent(getPreferredWidth(), getPreferredHeight());
	}

    protected void drawFocus(Graphics graphics, boolean on) {

    	if (on) {
    		if(this.backgroundColor_focus != -1) {
    			GeneralUtils.drawGradientFilledRoundedRect(graphics, 
    													   getPreferredWidth(), getPreferredHeight(), 
    													   backgroundColor_focus, backgroundColor_focus, true, true);
    		} 
//    		else {
//    			GeneralUtils.drawGradientFilledRoundedRect(graphics, 
//    					   getPreferredWidth(), getPreferredHeight(), 
//    					   backgroundColor_focus, backgroundColor_focus, true, true);
//    		}
    		
    		if (this.borderColor_f != -1) {
    			graphics.setColor(borderColor_f);
    			graphics.drawRoundRect(0, 0, this.getPreferredWidth(), this.getPreferredHeight(), CMFieldCommon.DEFAULT_ROUND_RECT,CMFieldCommon.DEFAULT_ROUND_RECT );
    		}

	    	paintText(graphics);
    	}
    }
    
	protected void paint(Graphics graphics) {
		if(this.backgroundColor != -1) {
			GeneralUtils.drawGradientFilledRoundedRect(graphics, 
													   getPreferredWidth(), getPreferredHeight(), 
													   backgroundColor, backgroundColor, true, false);
		} 
//		else {
//			GeneralUtils.drawGradientFilledRoundedRect(graphics, 
//					   getPreferredWidth(), getPreferredHeight(), 
//					   backgroundColor, backgroundColor, true, false);
//		}

		if (this.borderColor != -1) {
			graphics.setColor(borderColor);
			graphics.drawRoundRect(0, 0, this.getPreferredWidth(), this.getPreferredHeight(), CMFieldCommon.DEFAULT_ROUND_RECT,CMFieldCommon.DEFAULT_ROUND_RECT );
		}
		
    	paintText(graphics);	
    }
	
	protected boolean navigationClick(int status, int time) {
    	fieldChangeNotify(1);
    	return true;
    }  
    
	protected boolean keyDown(int keycode, int time) {
		if (keycode == Keypad.KEY_ENTER) {
			fieldChangeNotify(1);
			return true;
		}
		
		return false;
	}

	private int getTextColor() {
		int color = this.textColor;
		if (this.focusable) {
			if (this.isFocus()) 
				color = this.textColor_focus;
		} else
			color = CMFieldCommon.DEFAULT_COLOR_TEXT_UNFOCUSABLE;
		
		return color;
	}
    private void paintText(Graphics graphics) {
    	if (multiLines == 1) {
    		Font font = this.getFont();
    		int posX = ( this.getPreferredWidth() - font.getAdvance(this.label) ) /2;
    		int posY = ( this.getPreferredHeight() - font.getHeight()) / 2;
        	graphics.setColor(getTextColor());
    		graphics.drawText(this.label, posX, posY, Graphics.HCENTER | DrawStyle.ELLIPSIS);
    	} else {
    		wrapAndPaintText(graphics);
    	}
    }
    
	private void wrapAndPaintText(Graphics graphics) {
    	graphics.setColor(getTextColor());
	
		Font font = this.getFont();
		int textSize = font.getAdvance(this.label);
		int textLines = textSize / this.fieldWidth;
		if ( font.getAdvance(this.label) % this.fieldWidth != 0)
			textLines += 1;
		textLines = textLines == 0 ? 1 : textLines;
		
		Vector lines = StringUtils.wrapText(this.label, fieldWidth, false, font);
	
		int startPosY = CMFieldCommon.DEFAULT_PADDING;
		if (lines.size() < multiLines) {
			//if wrapped lines are less than the expected line number, 
			//	 the text should start from the middle of the field
			startPosY = (this.getPreferredHeight() - lines.size() * (CMFieldCommon.DEFAULT_ROW_SPACE + font.getHeight()))/2;
		}
		for (int line = 0; line < lines.size(); line++) {
	    	if (line == multiLines) break;

	    	String lineString = (String)lines.elementAt(line);
	
			int posX = (this.getPreferredWidth() - font.getAdvance(lineString) ) /2;
	    	int posY = startPosY + (font.getHeight() + CMFieldCommon.DEFAULT_ROW_SPACE )* line;
	    	
			graphics.drawText(lineString, posX, posY, Graphics.HCENTER);
		}
	}
	
}
