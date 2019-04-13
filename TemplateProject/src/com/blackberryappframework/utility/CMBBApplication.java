/**
 * @author      Xuetao (Sheldon) Liu
 * @version     2011.0512
 * @comments    This class includes the static methods to invoke BB applications, which include
 *                  --  BBBrowser
 *                  --  BB Map -- getMapDirection()
 *                  --  Compose email
 *                  
 *                  
 * @history
 *              Date        Author  Comments
 *              2011/10/03  X.L.    add Phone feature
 *              2011/08/24  X.L.    added orientation lock/unlock function
 *              2011/08/24  X.L.    post email
 *              2011/08/11  X.L.    compose email 
 *              2011/--/--  X.L.    created
 **/

package com.blackberryappframework.utility;



import javax.microedition.location.AddressInfo;
import javax.microedition.location.Landmark;

import net.rim.blackberry.api.browser.Browser;
import net.rim.blackberry.api.browser.BrowserSession;
import net.rim.blackberry.api.invoke.Invoke;
import net.rim.blackberry.api.invoke.MapsArguments;
import net.rim.blackberry.api.invoke.MessageArguments;
import net.rim.blackberry.api.mail.Header;
import net.rim.blackberry.api.mail.Message;
import net.rim.blackberry.api.mail.MessagingException;
import net.rim.blackberry.api.phone.Phone;
import net.rim.device.api.io.transport.TransportInfo;
import net.rim.device.api.lbs.Locator;
import net.rim.device.api.lbs.LocatorException;
import net.rim.device.api.system.ApplicationDescriptor;
import net.rim.device.api.system.DeviceInfo;
import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Screen;
import net.rim.device.api.ui.Ui;
import net.rim.device.api.ui.VirtualKeyboard;

public class CMBBApplication {
	
    public static int getDeviceId() {
    	return DeviceInfo.getDeviceId();
    }
    
    public static String getDeviceName() {
    	return DeviceInfo.getDeviceName();
    }
    
    public static String getPlatformVersion() {
    	return DeviceInfo.getPlatformVersion();
    }
    
    public static String getOSVersion() {
    	return DeviceInfo.getSoftwareVersion();
    }
    
    public static String getApplicationVersion() {
    	return ApplicationDescriptor.currentApplicationDescriptor().getVersion();
    }
    
    public static String getApplicationName() {
    	return ApplicationDescriptor.currentApplicationDescriptor().getName();
    }
    
	public static void lockOrientation(boolean lock, boolean north) {
		//int direction = Display.DIRECTION_PORTRAIT;
		int direction = Display.DIRECTION_NORTH;
		if (! lock) {
			//direction |= Display.DIRECTION_LANDSCAPE;
			direction |= Display.DIRECTION_WEST | Display.DIRECTION_EAST;
		} else if (!north) {
			direction = Display.DIRECTION_WEST;
		}

		Ui.getUiEngineInstance().setAcceptableDirections(direction );
	}
	
	public static void hideVirtualKeyBoard(Screen scrn) {
		if (scrn == null) return;
		
		VirtualKeyboard keyboard = scrn.getVirtualKeyboard();
        if( keyboard != null ) {
            keyboard.setVisibility(VirtualKeyboard.HIDE);
        }
	}
	
	public static void showVirtualKeyBoard(Screen scrn) {
		if (scrn == null) return;
		
		VirtualKeyboard keyboard = scrn.getVirtualKeyboard();
        if( keyboard != null ) {
            keyboard.setVisibility(VirtualKeyboard.SHOW);
        }
	}
	
	public static boolean isConnected()
	{
		int[] preferredTransportTypes={TransportInfo.TRANSPORT_TCP_WIFI, TransportInfo.TRANSPORT_WAP2, TransportInfo.TRANSPORT_TCP_CELLULAR, TransportInfo.TRANSPORT_MDS};
		for(int i=0; i<preferredTransportTypes.length; i++)
		{
			if(TransportInfo.hasSufficientCoverage(preferredTransportTypes[i]))
				return true;
		}
		return false;
	}
	
	public static void invokeBBBrowser(String url) {
		BrowserSession bSession = Browser.getDefaultSession();
		bSession.displayPage(url);
	}
	
	public static void composeEmail(String to, String subject, String body) {
		Message msg = new Message();
		if (to != null) 
			msg.addHeader(Header.TO, to);
		if (subject != null)
			msg.addHeader(Header.SUBJECT, subject);
		if (body != null)
			try {
				msg.setContent(body);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		Invoke.invokeApplication(Invoke.APP_TYPE_MESSAGES, new MessageArguments( msg ));
	}
	
	public static String performPhoneCall(String phoneNumber) {
    	
    	String errorMsg = null;
    	if (phoneNumber == null || phoneNumber.length() == 0)
    		errorMsg = "Invalid phone number! ";
    	else {
	    	try {
	    		if (Phone.isLineAvailable(Phone.getActiveLineId())) {
	    			Phone.initiateCall(Phone.getActiveLineId(), phoneNumber);
	    		} else {
	    			errorMsg = "No available phone line!";
	    		}
	    	} catch (Exception e) {
	    		errorMsg = "Failed to perform phone call!";
	    	} 
    	}
    	
    	return errorMsg;
    }
	
	public static void getMapDirection(final double srcLon, final double srcLat, final String srcLoc,
			final double tgtLon, final double tgtLat, final String tgtLoc) 
	{
		
		Runnable thread = new Runnable()
		{
			public void run()
			{
				String sourceAddress = srcLoc;
				
				try
				{
					AddressInfo addrInfo = null;
					Landmark[] results = Locator.reverseGeocode((int)(srcLat * 100000), (int)(srcLon * 100000), Locator.ADDRESS );
					
					if ( results != null && results.length > 0 )
					{
						addrInfo = results[0].getAddressInfo();
						sourceAddress = addrInfo.getField(AddressInfo.STREET) + ", " + addrInfo.getField(AddressInfo.CITY);
					}
				}
				catch ( LocatorException lex )
				{
				}
				
				String document =  "<lbs> <getRoute>" 
								 + "<location x='"+(int)(srcLon*100000) +"' y='"+(int)(srcLat*100000)+"' label='" + sourceAddress +  "'/>"// description='Kitchener, Ontario, Canada' 
								 + "<location x='"+(int)(tgtLon*100000) +"' y='"+(int)(tgtLat*100000)+"' label='" + tgtLoc +  "'/>"       // description='Ottawa, Ontario, Canada' 
								 + "</getRoute></lbs>";
				
				Invoke.invokeApplication( Invoke.APP_TYPE_MAPS, 
										  new MapsArguments( MapsArguments.ARG_LOCATION_DOCUMENT, document));
			}
		};
		
		Thread getDirection = new Thread(thread);
		getDirection.setPriority(Thread.MIN_PRIORITY);
		getDirection.start();
	}
}
