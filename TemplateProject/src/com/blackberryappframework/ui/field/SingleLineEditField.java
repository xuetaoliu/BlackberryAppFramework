//http://supportforums.blackberry.com/t5/Java-Development/Help-resizing-width-of-EditField-on-Storm-screen-tilt/td-p/460614
//http://supportforums.blackberry.com/t5/Java-Development/Stop-edit-field-from-wrapping-text/m-p/255289#M41759
package com.blackberryappframework.ui.field;

import com.blackberryappframework.ui.Interface.CMFieldCommon;
import com.blackberryappframework.ui.Interface.CMFieldInterface;
import com.blackberryappframework.utility.CMBBApplication;

import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FocusChangeListener;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.XYRect;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.ui.decor.Border;
import net.rim.device.api.ui.decor.BorderFactory;


public class SingleLineEditField extends HorizontalFieldManager implements CMFieldInterface, FocusChangeListener {
	private final int DEFAULT_ROUND_RECT = 10;
	
    private ExtendedEditField _editField;
    private HorizontalFieldManager textMgr;

    private int fixed_width = -1; 
    private int fixed_height = -1;
    
    public SingleLineEditField() {
    	this("", "", "Input data");
    }
    
    public SingleLineEditField(String label, String text, String hintMsg) {
    	this(label, text, hintMsg, EditField.FIELD_LEFT | EditField.FIELD_VCENTER | EditField.NO_COMPLEX_INPUT);
    }
    
    public SingleLineEditField(String label, String text, String hintMsg, long style) {
    	super(NO_HORIZONTAL_SCROLL);

    	if (label != null) {
	    	LabelField labelField = new LabelField(label+" ");
	        this.add(labelField);
    	}

    	textMgr = new HorizontalFieldManager(HORIZONTAL_SCROLL);
        this.add(textMgr);
        
        _editField = new ExtendedEditField(text, hintMsg, EditField.DEFAULT_MAXCHARS, style | EditField.NO_NEWLINE | EditField.FOCUSABLE | EditField.EDITABLE);
        _editField.setFocusListener(this);
        _editField.setNonSpellCheckable(true);
        textMgr.add(_editField);
        
        this.setFieldWidth( this.getFont().getAdvance('A') * EditField.DEFAULT_MAXCHARS );
        this.setFieldHeight( _editField.getPreferredHeight() + CMFieldCommon.DEFAULT_VERTICAL_PADDING); //this.getFont().getHeight() );
        
//        setBorderColor(CMFieldCommon.DEFAULT_COLOR_BORDER, -1);
            
//        this.textMgr.setBackground(BackgroundFactory.createSolidBackground(Color.RED));
    }
    
    protected void paint(Graphics graphics) {
        super.paint(graphics);
    }
    
    protected void paintBackground(Graphics graphics)
    {
    	graphics.setColor(Color.BLUE);
    	
    	graphics.drawRect(0, 0, getExtent().width, getExtent().height);
        graphics.setColor(Color.WHITE);
        
        XYRect rect = new XYRect();
        this.getFocusRect(rect);
        
        graphics.fillRect(0, 0, getExtent().width, getExtent().height);
        graphics.setColor(Color.BLACK);
        
        super.paintBackground(graphics);
    }

    public String getText() {
        return _editField.getText();
    }
    public void setText(String text) {
    	if (text != null)
    		_editField.setText(text);
    }
    
    private int fieldWidth;
    private int fieldHeight;
    public int getPreferredWidth() {return fieldWidth;}
    public int getPreferredHeight() {return fieldHeight;}
    
    public void setFieldWidth(int width) {fieldWidth = width;}
    public void setFieldHeight(int height) {fieldHeight = height;}
	public int getFieldWidth() { return fieldWidth; }
	public int getFieldHeight() { return fieldHeight; }

    private int layoutStyle;
	public void setLayoutStyle(int style) { layoutStyle = style;	}
	public int getLayoutStyle() {  return layoutStyle; }

	public void setFieldPadding(int horizontalPadding, int verticalPadding) { }

	public void setBackgroundColor(int color, int focusColor) {
		if (color != -1)
			_editField.setBackground(BackgroundFactory.createSolidBackground(color));
	}

	public void setTextColor(int color, int focusColor) {  }

	public void setFocusable(boolean focusable) { }
	
	public void setBorderColor(int color, int foucsColor) {
		//borderColor = color;
		if (color != -1) {
			XYEdges padding = new XYEdges( DEFAULT_ROUND_RECT, DEFAULT_ROUND_RECT, DEFAULT_ROUND_RECT, DEFAULT_ROUND_RECT );
			int lineStyle = Border.STYLE_SOLID;
			Border roundedBorder = BorderFactory.createRoundedBorder(padding, color, lineStyle);
			textMgr.setBorder(roundedBorder);
		}
	}

    public boolean keyChar(char character, int status, int time) {
    	return super.keyChar(character, status, time);
    }
    public boolean navigationMovement(int dx, int dy, int status, int time){
    	return super.navigationMovement(dx, dy, status, time);
    }

	public void focusChanged(Field field, int arg1) {
		if (field.isFocus()) {
			CMBBApplication.showVirtualKeyBoard(getScreen());
		} else {
			CMBBApplication.hideVirtualKeyBoard(getScreen());
		}
	}


}
