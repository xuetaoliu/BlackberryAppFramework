package com.blackberryappframework.ui.progressbar;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Keypad;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.GaugeField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.PopupScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;

public class ProgressScreen extends PopupScreen implements FieldChangeListener, ObserverInterface {
	private final String CANCEL_COOKIE = "cancel";
	
	private LabelField messageField;
	private GaugeField gaugeField;
	private ButtonField cancelButton;
	private LabelField statusField;
	
	private ProgressThread waitingThread;
	private int returnCode = ObserverInterface.CANCELLED;
	
	private boolean interruptable;
	
	public ProgressScreen(String title, String message, boolean interruptable) {
		super(new VerticalFieldManager());
		
		this.add(new LabelField(title, LabelField.FIELD_HCENTER));
        this.add(new SeparatorField());
        messageField = new LabelField(message, LabelField.USE_ALL_WIDTH);
        this.add(messageField);
        
        gaugeField = new GaugeField(null, 1, 100, 1, GaugeField.NO_TEXT);
        this.add(gaugeField);
        this.add(new SeparatorField());
        
        this.interruptable = interruptable;
        if (interruptable) {
        	cancelButton = new ButtonField("Cancel", ButtonField.FIELD_HCENTER | ButtonField.CONSUME_CLICK);
        	cancelButton.setCookie(CANCEL_COOKIE);
        	cancelButton.setChangeListener( this );
            this.add(cancelButton);
        }

        statusField = new LabelField("Starting");
        this.add(statusField);
	}
	
	public int show(ProgressThread waitingThread) {
		this.waitingThread = waitingThread;
		this.waitingThread.start();
		
        UiApplication.getUiApplication().pushModalScreen(this);
        return returnCode;
	}

	// Disable the Escape key.
	protected boolean keyChar(char character, int status, int time) {
		if (character == Keypad.KEY_ESCAPE) {
			if (this.interruptable) 
				cancelThread();
			else
				return true;
		}
		
		return super.keyChar(character, status, time);
	}
	
	public void fieldChanged(Field field, int context) {
		String cookie = (String) field.getCookie();
		if (cookie.equalsIgnoreCase(CANCEL_COOKIE)) {
			cancelThread();
		}
	}
	
	private void cancelThread() {
		if ( waitingThread != null ) {
            if ( waitingThread.isAlive() ) {
            	waitingThread.stop();
                // This will send us a 'failure' notification
            }
        } else {
            // Something has gone really wrong?!
            throw new RuntimeException("Oppsss.... sth is wrong with the waiting thread");
        }
	}

	//// inherited from the Observer interface
    /**
     * This method is called by the Background Thread
     * So we need to gain access to the Event Thread to update our Ui.
     */
	public void processMessageUpdate(final String message, final boolean resetGauge) {
		synchronized (UiApplication.getEventLock())  {
//        UiApplication.getUiApplication().invokeLater(new Runnable() {
//            public void run () {
                messageField.setText(message);
                if (resetGauge)
                	gaugeField.reset(null, 1, 100, 1);
                
                ProgressScreen.this.invalidate();
//            }
//        });
		}
	}
	
    /**
     * This method is called by the Background Thread
     * So we need to gain access to the Event Thread to update our Ui.
     */
	public void processStatusUpdate(final int progress, final String statusString) {
		synchronized (UiApplication.getEventLock())  {
//        UiApplication.getUiApplication().invokeLater(new Runnable() {
//            public void run () {
                statusField.setText(statusString);
                if ( progress > 0 ) {
                    gaugeField.setValue(progress);
                }
                ProgressScreen.this.invalidate();
//            }
//        });
		}
    }

    /**
     * This method is called by the Background Thread
     * So we need to gain access to the Event Thread to update our Ui.
     */
    public void processResponse(final String responseMsg) {
        returnCode = ObserverInterface.OK;
		synchronized (UiApplication.getEventLock())  {
//        UiApplication.getUiApplication().invokeLater(new Runnable() {
//            public void run () {
//                Dialog.alert("Response:\n" + new String(responseBytes));
                UiApplication.getUiApplication().popScreen(ProgressScreen.this);
//            }
//        });
		}
    }
            
    /**
     * This method is called by the Background Thread
     * So we need to gain access to the Event Thread to update our Ui.
     */
    public void processError(int errorCode, final String errorMessage) {
        returnCode = errorCode;
		synchronized (UiApplication.getEventLock())  {
//        UiApplication.getUiApplication().invokeLater(new Runnable() {
//            public void run () {
                Dialog.alert("Error:!\n" + errorMessage);
                UiApplication.getUiApplication().popScreen(ProgressScreen.this);
//            }
//        });
		}
    }
}
