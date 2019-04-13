/**
 * @author      Xuetao (Sheldon) Liu
 * @version     2011.0503                                 
 * @history
 *              Date        Author  Comments
 *              2011/05/17  X.L     move generateMenuItem() to CMFieldController
 *              2011/05/03  X.L.    created
 *              
 * @todo
 *    1) to finish the actionListner             
 **/

package com.blackberryappframework.UI.Field;

import java.util.Vector;

import com.blackberryappframework.UI.Interface.ActionListener;


import net.rim.device.api.ui.MenuItem;

public class CustomerMenuItem extends MenuItem {
	
	protected int menuId;
	protected String name;
	protected int actionId;
	protected Vector labels;
	protected boolean sysmenu;
	protected ActionListener listener;
	
	protected int curLabelID = 0;

	public CustomerMenuItem(String text, int menuID, int ordinal, int priority){
		super(new String(text), ordinal, priority);
		
		menuId = menuID;
		labels = new Vector();
		actionId = -1;
		sysmenu = false;
	}
	
	public int getActionId(){
		return actionId;
	}
	public int getMenuId() { return this.menuId;}
	public void setName(String name) {this.name = name;}
	public void setActionID(int actionId) {this.actionId = actionId;}
	public void addLabel(String label) { this.labels.addElement(label);}
	public void setLabelID(int id) { 
		this.curLabelID = id;
		if (id < labels.size()) {
			this.setText(new String((String) labels.elementAt(id)));
		}
	}
	public int getLabelID() { return this.curLabelID;}

	public void setSysmenu(boolean sysmenu) {  this.sysmenu = sysmenu; }
	public boolean isSysmenu() { return sysmenu; }
	
	private Object data;
	public Object data() { return data; }
	public void data(Object data) { this.data = data; }
	
	public void setListener(ActionListener listener) { this.listener = listener; }
 
	public void run() {
		
		if (labels.size() > 1 ) {
			curLabelID = (curLabelID + 1 ) % labels.size();
			this.setText(new String((String) labels.elementAt(curLabelID)));
		}
		
		if (listener != null) 
			listener.run(this, actionId);
	}
}
