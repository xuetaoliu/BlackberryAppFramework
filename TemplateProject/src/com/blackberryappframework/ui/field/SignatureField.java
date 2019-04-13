package com.blackberryappframework.UI.Field;

import java.util.Vector;

import com.blackberryappframework.axon.configuration.AppSetting;
import com.blackberryappframework.axon.configuration.ResourceLoader;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.Screen;
import net.rim.device.api.ui.TouchEvent;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.XYPoint;
import net.rim.device.api.ui.decor.BorderFactory;

public class SignatureField extends Field {
	
	private static final int DEFAULT_PADDING = 12;
	private static final int DEFAULT_HEIGHT = 200;

	private boolean signed = false;
	
	private int fieldWidth;
	private int fieldHeight;
    private XYPoint absolutePos = null;
	
	private Bitmap roundBorder;
	private Bitmap roundBorderFocus;

	public SignatureField() {
		
		setFieldWidth(Display.getWidth()-20);
		setFieldHeight(DEFAULT_HEIGHT);
		
		this.roundBorder = AppSetting.getAppSetting().loadBitmapFromFile(ResourceLoader.getString(ResourceLoader.ID_IMG_ROUNDED_BORDER));
		this.roundBorderFocus = AppSetting.getAppSetting().loadBitmapFromFile(ResourceLoader.getString(ResourceLoader.ID_IMG_ROUNDED_BORDER_F));

	    setMargin(DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING);
	}

	public void setFieldWidth(int width) { 
		this.fieldWidth = width - DEFAULT_PADDING * 2; 
	}
	public void setFieldHeight(int height) { 
		this.fieldHeight = height - DEFAULT_PADDING * 2; 
	}
	
	public void clearImage() {
		this.drawPaths.removeAllElements();
		this.signed = false;
		
		this.invalidate();
	}
	
	public Bitmap getSignature() {
		Bitmap screenshot = null;
		if (this.signed) {
			screenshot = new Bitmap(this.getWidth()-DEFAULT_PADDING*2, this.getHeight()-DEFAULT_PADDING*2);
			Display.screenshot(screenshot, 
					           absolutePos.x+DEFAULT_PADDING, 
					           absolutePos.y+DEFAULT_PADDING, 
					           this.getWidth()-DEFAULT_PADDING*2, 
					           this.getHeight()-DEFAULT_PADDING*2);
		}
		
		return screenshot;
	}
	
    public boolean isFocusable() { return true;}
	public int getPreferredWidth() { 
		return this.fieldWidth; 
	}
	public int getPreferredHeight() { 
		return this.fieldHeight; 
	}
	
    protected void layout(int width, int height) {
        setExtent(this.getPreferredWidth(), this.getPreferredHeight());
    }
    
    protected void onDisplay() {
    	super.onDisplay();

    	//get the absolute X and Y in screen
    	if (absolutePos == null) 
    		absolutePos = getAbsolutePostionInScreen();
    }
    public void drawFocus(Graphics graphics, boolean on) {
    	;
    }
    
    public void onFocus(int direction) {
    	setBorder(BorderFactory.createBitmapBorder(
	                new XYEdges(DEFAULT_PADDING,DEFAULT_PADDING,DEFAULT_PADDING,DEFAULT_PADDING), 
	                roundBorderFocus) );
    }
    
    public void onUnfocus() {
    	setBorder(BorderFactory.createBitmapBorder(
	                new XYEdges(DEFAULT_PADDING,DEFAULT_PADDING,DEFAULT_PADDING,DEFAULT_PADDING), 
	                roundBorder ));
    }


    
    public void paint(Graphics graphics) {
        graphics.setColor(Color.BLACK);
        int[][] coordinates;
        //draw the existing paths
        for (int i=0; i < drawPaths.size(); i++) {
        	coordinates = (int[][]) drawPaths.elementAt(i);

        	this.drawThickerLine(graphics, coordinates);
        }
        
        //draw the path while moving
//        if (touchMove) {
//        	coordinates = getValidPoints(preMoveX, preMoveY);
//        	graphics.drawOutlinedPath(coordinates[0], coordinates[1], null, null, false);
//        }
        
    }
    
    private void drawThickerLine(Graphics graphics, int[][] centerCoordinates) {
    	graphics.drawOutlinedPath(centerCoordinates[0], centerCoordinates[1], null, null, false);
    	
    	int[][] adjustCoords = new int[2][centerCoordinates[0].length];
    	for (int i = 0; i < centerCoordinates[0].length; i++) {
    		adjustCoords[0][i] = centerCoordinates[0][i] + 1;
    		adjustCoords[1][i] = centerCoordinates[1][i] + 1;
    	}
    	graphics.drawOutlinedPath(adjustCoords[0], adjustCoords[1], null, null, false);

    	for (int i = 0; i < centerCoordinates[0].length; i++) {
    		adjustCoords[0][i] = centerCoordinates[0][i] - 1;
    		adjustCoords[1][i] = centerCoordinates[1][i] - 1;
    	}
    	graphics.drawOutlinedPath(adjustCoords[0], adjustCoords[1], null, null, false);
    }

    //the existing paths. We need to redraw them
	private Vector drawPaths = new Vector();

	private boolean touchMove = false;
	private int[] preMoveX = null;
	private int[] preMoveY = null;
	
	private int[] moveX = new int[10000];
	private int[] moveY = new int[10000];
	private int[] moveTime = new int[10000];

	/**
	 * Under some scenario (a long touch move), the touch event might discard some points.
	 * Therefore, for each touch move message, we will compare the current points (actually only the start point) 
	 * with the one we get last time. If they are the same, we will keep the current points array.
	 * Otherwise, we will "think" the points we collected before are a path and the current ones is a new path.
	 * */
	protected boolean touchEvent(TouchEvent message) {
    	boolean consumed = false;
        switch( message.getEvent() ) {
        case TouchEvent.MOVE:
        	this.signed = true;
        	message.getMovePoints(1, moveX, moveY, moveTime);
        	checkMovePath(false);
        	
        	touchMove = true;
        	consumed = true;
        	break;
        case TouchEvent.UP:
            if (touchMove) {
            	message.getMovePoints(1, moveX, moveY, moveTime);
            	
            	checkMovePath(true);
                touchMove = false;
            }

            this.invalidate();
            consumed = true;
        	break;
        }

        if (!consumed)
        	consumed = super.touchEvent(message);
        return consumed;
    }
    
	/**
	 * Under some scenario (a long touch move), the touch event might discard some points.
	 * Therefore, for each touch move message, we will compare the current points (actually only the start point) 
	 * with the one we get last time. If they are the same, we will keep the current points array.
	 * Otherwise, we will "think" the points we collected before are a path and the current ones is a new path.
	 * */
	private void checkMovePath(boolean endMove) {
    	if (preMoveX != null) {
    		if (preMoveX[0] == moveX[0] && preMoveY[0] == moveY[0] && moveX[0]!=0 && moveY[0] != 0) {
    			;//it is still the same path
    		} else { 
    			//a new path
    			int[][] coordinates = this.getValidPoints(preMoveX, preMoveY);
            	drawPaths.addElement(coordinates);
    		}
    	}
    	
    	preMoveX = moveX;
    	preMoveY = moveY;
    	moveX = new int[10000];
    	moveY = new int[10000];
    	
    	if (endMove) {
			int[][] coordinates = this.getValidPoints(preMoveX, preMoveY);
			if (coordinates[0].length > 1)
				drawPaths.addElement(coordinates);

        	preMoveX = null;
        	preMoveY = null;
    	}
	}
	
    /**
     * When fetching the points from the touch event, there will be a lot of (0, 0) points.
     * These points are invalid and should be discarded
     * */
    private int[][] getValidPoints(int[] pointX, int[] pointY) {
    	int length = 0;
    	for (int i = 0; i < pointX.length; i++) {
    		if (pointX[i] == 0 && pointY[i] == 0) {
    			length = i;
    			break;
    		}
    	}
    	
    	int[] validPointX = new int[length];
    	int[] validPointY = new int[length];
    	for (int i = 0; i < length; i++) {
    		validPointX[i] = pointX[i] - absolutePos.x;
    		validPointY[i] = pointY[i] - absolutePos.y;
    		
    		pointX[i] = 0;
    		pointY[i] = 0;
    	}
    	
    	int[][] coordinates = new int[2][];
    	coordinates[0] = validPointX;
    	coordinates[1] = validPointY;
    	
    	return coordinates;
    }
    
    private XYPoint getAbsolutePostionInScreen() {
    	int posX = this.getLeft();
    	int posY = this.getTop();
    	
    	Screen screen = this.getScreen();
    	Manager mainManager = screen.getDelegate();
    	Manager tempManager = this.getManager();
    	
    	while (tempManager != mainManager) {
    		posX += tempManager.getLeft();
    		posY += tempManager.getTop();
    		
    		tempManager = tempManager.getManager();
    	}
    	
    	return new XYPoint(posX, posY);
    }
}
