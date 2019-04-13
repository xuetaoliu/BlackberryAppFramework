package com.blackberryappframework.utility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import javax.microedition.io.HttpConnection;

import com.blackberryappframework.ui.screen.general.BrowserScreen;

import net.rim.blackberry.api.browser.URLEncodedPostData;
import net.rim.device.api.io.transport.ConnectionDescriptor;
import net.rim.device.api.io.transport.ConnectionFactory;
import net.rim.device.api.io.transport.TransportInfo;
import net.rim.device.api.ui.UiApplication;

public class ConnectionUtils {

	public static final String NETWORK_UNAVALIABLE_MESSAGE="Network unavaliable";
	
	
	
	public static InputStream openHTTPConnection(String urlStr) {
		InputStream is = null;
		HttpConnection http = null;

		try {
			ConnectionFactory connectionFactory = new ConnectionFactory();
			ConnectionDescriptor connectionDesc = connectionFactory.getConnection(urlStr);

			http = (HttpConnection) connectionDesc.getConnection();
			http.setRequestMethod(HttpConnection.GET);
			is = http.openInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return is;
	}

	public static HttpConnection getHttpConnection(String url)
	{
		int[] preferredTransportTypes={TransportInfo.TRANSPORT_TCP_WIFI, TransportInfo.TRANSPORT_WAP2, TransportInfo.TRANSPORT_TCP_CELLULAR, TransportInfo.TRANSPORT_MDS};
		
		ConnectionFactory factory=new ConnectionFactory();
		
		factory.setPreferredTransportTypes(preferredTransportTypes);
		ConnectionDescriptor conDescriptor=factory.getConnection(url);
		if(conDescriptor!=null){
//			int transportUsed=conDescriptor.getTransportDescriptor().getTransportType();
			HttpConnection connection = (HttpConnection)conDescriptor.getConnection();
			
			return connection ;
		}
		return null;
	}
	
	public static byte[] retrieveHttpConnectionData(HttpConnection connection) throws IOException {
		byte[] data = null;
			
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream responseData;

		responseData = connection.openInputStream();
		byte[] buffer = new byte[10000];
		int bytesRead = responseData.read(buffer);
		while(bytesRead > 0) 
		{
			baos.write(buffer, 0, bytesRead);			
			bytesRead = responseData.read(buffer);
		}
		baos.close();
				
		data = baos.toByteArray();
			
		return data;
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
	
	public static byte[] fetchData(String url)
	{
		byte[] data=null;
		try {
			HttpConnection connection = ConnectionUtils.getHttpConnection(url);
			connection.setRequestMethod("GET");
			int responseCode = connection.getResponseCode();
			if (responseCode != HttpConnection.HTTP_OK) 
			{
			   return data;
			}
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			InputStream responseData = connection.openInputStream();
			byte[] buffer = new byte[10000];
			int bytesRead = responseData.read(buffer);
			while(bytesRead > 0) 
			{
			    baos.write(buffer, 0, bytesRead);			
			    bytesRead = responseData.read(buffer);
			}
			baos.close();
			connection.close();
			
			data=baos.toByteArray();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
	

	/**
	 * Json format is like:
	 *   "fieldname":"fieldValue",
	 *   "fieldname":fieldValue,
	 *   "fieldname":"fieldValue"
	 * or
	 *   [{
	 *      "fieldname":"fieldValue",  
	 *      "loc" : {
	 *           "lat" : 51.514274,
	 *           "lon" : -0.141878
	 *       },
	 *      "fieldname":"fieldValue"
	 *    },{
	 *      "fieldname":"fieldValue",  
	 *      "loc" : {
	 *           "lat" : 51.514274,
	 *           "lon" : -0.141878
	 *       },	 
	 *      "fieldname":"fieldValue"
	 *    },{
	 *      ...
	 *    }]
	 * */
	public static Vector getJsonObject(String content) {
		Vector objects = null;
		
		if (content != null && content.trim().length() != 0) {
			objects = new Vector();
			
			content = content.trim();
			String obj;
			if (content.charAt(0) == '[') {
				if (content.charAt(content.length() - 1) == ']') {
					int startPos = content.indexOf('{');
					int endPos;
					if (startPos != -1) {
						
						int nextStartSign = startPos;
						while (startPos != -1) {
							endPos = content.indexOf('}', startPos + 1);
							nextStartSign = content.indexOf('{', startPos + 1);
							//this is the embedded object definition
							while (nextStartSign != -1 && nextStartSign < endPos) {
								endPos = content.indexOf('}', endPos + 1);
								nextStartSign = content.indexOf('{', nextStartSign + 1);
							}
							
							if (endPos != -1) {
								obj = content.substring(startPos + 1, endPos);
								objects.addElement(obj.trim());
							} else {
								//incorrect json format
								break;
							}
							
							startPos = content.indexOf('{', endPos);
						}
					} else {
						//incorrect json format
					}
				} else {
					//incorrect json format
				}
			} else { //only one object
				obj = content;
				objects.addElement(obj);
			}
		}
		
		return objects;
	}
	
	/**
	 *      "fieldname":"fieldValue",  
	 *      "loc" : {
	 *           "lat" : 51.514274,
	 *           "lon" : -0.141878
	 *       },
	 *      "fieldname":["fieldValue", "fieldValue"]
     * */
	public static Vector getJsonFieldValue(String content, String fieldName) {
		Vector values = null;
		
		int pos = content.indexOf('"' + fieldName + '"');
		if (pos >= 0) {
			int valueStart = content.indexOf(":", pos);
			int valueEnd = valueStart+1;
			if (valueStart > 0) {
				values = new Vector();
				
				for (int i = valueStart+1; i < content.length(); i++ ) {
					if (content.charAt(i) == ' ') continue;
					
					if (content.charAt(i) == '[') {
						valueEnd = content.indexOf("]", i)+1;
						break;
					}
					
					if (content.charAt(i) == '"') {
						valueEnd = content.indexOf('"', i + 1) + 1;
					} else {
						valueEnd = content.indexOf(",", valueStart);
					}
					
					if (valueEnd == -1)
						valueEnd = content.length();
					
					break;
				}
				
				String value = content.substring(valueStart+1, valueEnd).trim();
				int startPos = value.indexOf('[');
				if (startPos == -1) {
					if (value.charAt(value.length() - 1) == '}')
						value = value.substring(0, value.length()-1);
					value = removeStringMark(value);
					values.addElement(value);
				} else {
					int endPos = value.indexOf(',', startPos+1);
					while (endPos != -1) {
						String str = value.substring(startPos+1, endPos);
						str = removeStringMark(str);
						values.addElement(str);
						
						startPos = endPos;
						endPos = value.indexOf(',', startPos+1);
					}
					
					//add the last item, which doesn't end with ',' and end with ']'
					value = value.substring(startPos+1, value.length()-1);
					if (value.charAt(value.length() - 1) == '}')
						value = value.substring(0, value.length()-1);
					value = removeStringMark(value);
					values.addElement(value);
				}
				
			}
		}
/*		
		if (CMAppDefinitions.debug) {
			System.out.println("=== field: value===");
			System.out.print(fieldName + " : " );
				if (values != null)
				for (int i = 0; i < values.size(); i++)
					System.out.print((String)values.elementAt(i) + " :: ");
			else
				System.out.println(" NO THIS FIELD !!!!!");
			System.out.println();
		}
*/		
		return values;
	}
	
	public static void shareOnFacebook(String comments, String encodedUrl)
	{
		shareOnFacebook(comments, encodedUrl, null, null, null);
	}
	public static void shareOnFacebook(String comments, String encodedUrl, String loadingText, String spinnerFile, String failureMsg) {
		String facebookSharingUrl = "http://www.facebook.com/sharer.php?src=bm&v=4&i=1316798316&u=%@&t=%@";
		facebookSharingUrl = StringUtils.replace(facebookSharingUrl, "%@", encodedUrl, false);
		facebookSharingUrl = StringUtils.replace(facebookSharingUrl, "%@", comments, false);
		//String facebookSharingUrl = "http://www.facebook.com/sharer.php?src=bm&v=4&i=1316798316&u=http://www.blackberry.com&t=blackberry";

		BrowserScreen sharingScreen = new BrowserScreen(facebookSharingUrl, 
														loadingText, spinnerFile, failureMsg);
		UiApplication.getUiApplication().pushScreen(sharingScreen);
	}
	
	public static void shareOnTwitter(String comments, String encodedUrl)
	{
		shareOnTwitter(comments, encodedUrl, null, null, null);
	}
	public static void shareOnTwitter(String comments, String encodedUrl, String loadingText, String spinnerFile, String failureMsg) {
		String twitterSharingUrl =  "https://twitter.com/intent/tweet?text=%@";
		//twitterSharingUrl = GeneralUtils.replace(twitterSharingUrl, "%@", comments, false);
		twitterSharingUrl = StringUtils.replace(twitterSharingUrl, "%@", comments + encodedUrl, false);
		
		BrowserScreen sharingScreen = new BrowserScreen(twitterSharingUrl, 
				loadingText, spinnerFile, failureMsg);
		UiApplication.getUiApplication().pushScreen(sharingScreen);
	}

	
	public static String encodeUrl(String hsURL) {
        URLEncodedPostData urlEncoder = new URLEncodedPostData("UTF-8", false);
        urlEncoder.setData(hsURL);
        hsURL = urlEncoder.toString();
        return hsURL;
	}

	
	private static String removeStringMark(String str) {
		String result = null;
		if (str != null) {
			int startPos = str.indexOf('"');
			if (startPos != -1) {
				int endPos = str.indexOf('"', startPos + 1);
				if (endPos != -1)
					result = str.substring(startPos+1, endPos);
			} else 
				result = str;
		}
		
		return result;
	}
}
