/**
 * @author      Xuetao (Sheldon) Liu
 * @version     2011.0426   
 * @comments
 *              This button supports multiple labels                              
 * @history
 *              Date        Author  Comments
 *              2011/05/27  X.L.    add keyDown event process
 **/

package com.blackberryappframework.ui.field;

import java.util.Vector;

import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Keypad;

public class MultiLabelButton extends CustomerButton {

	public MultiLabelButton(String label) {
		super(label, FOCUSABLE);
		
		labels = new Vector();
		this.addLabel(label);
		curLabel = 0;
	}
	
	public void addLabel(String label) {
		if (label != null && label.trim().length() > 0) {
			labels.addElement(label);

			Font font = this.getFont();
			if (font.getAdvance(label) > this.getFieldWidth()) {
				this.setFieldWidth(font.getAdvance(label));
			}
		}
	}
    
    public void setLabel(String labelTxt) {
    	this.label = labelTxt;
    	this.invalidate();
    }
    
    public void setLabelIdx(int idx) {
    	if (curLabel != idx) {
    		curLabel = (idx) % labels.size();
			setLabel((String)labels.elementAt(curLabel));
    	}
    }

    protected boolean navigationClick(int status, int time) {
		switchLabel();
    	
    	return super.navigationClick(status, time);
    }  
    
	public boolean keyDown(int keycode, int time) {
		if (keycode == Keypad.KEY_ENTER)
			switchLabel();
    	
    	return super.keyDown(keycode, time);
    }  

	private Vector labels;
	private int curLabel;

	private void switchLabel() {
		if (labels.size() > 1) {
			curLabel = (curLabel + 1) % labels.size();
			setLabel((String)labels.elementAt(curLabel));
		}
	}
}
