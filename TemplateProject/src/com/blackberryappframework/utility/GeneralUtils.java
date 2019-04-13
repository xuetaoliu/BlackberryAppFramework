/**
 * @author      Xuetao (Sheldon) Liu
 * @version     2011.0319
 * 
 * @history    Date        author         reason
 *             2011/09/15  Xuetao Liu     Added loadBitmapFromFile() function, which will pick up the file from the respective resolution folder
 *             2011/04/22  Xuetao Liu     Added StringToInt() and StringToBoolean()
 *             2011/03/19  Xuetao Liu     created                 
 **/


package com.blackberryappframework.utility;

import java.io.InputStream;

import com.blackberryappframework.CMAppConfiguration.CMAppSetting;
import com.blackberryappframework.ui.Interface.CMFieldCommon;

import net.rim.device.api.io.IOUtilities;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Graphics;

public class GeneralUtils {
	public static long HOUR_IN_MILLIS = 3600000L;
	public static long DAY_IN_MILLIS = 86400000L;
	public static long WEEK_IN_MILLIS = 604800000L;
	
	public static String getMediaType(String fileName) {
		String mediaType ="";
		if (fileName.equalsIgnoreCase("3gpp")) {
			mediaType = "video";
		}
		return mediaType + "/" + fileName.toLowerCase();
	}
	
	public static Bitmap loadBitmapFromFile(String bitmapFileName, int screenType) {
		if (bitmapFileName == null)
			return null;
		
		String imageFolder = CMAppSetting.DEFAULT_IMAGE_FOLDER;
		switch (screenType) {
		case BlackberryFeature.BLACKBERRY_320x240: 
			imageFolder = imageFolder+CMAppSetting.DEFAULT_320x240_SCREEN_FOLDER;
			break;
		case BlackberryFeature.BLACKBERRY_360x480: 
			imageFolder = imageFolder+CMAppSetting.DEFAULT_360x480_SCREEN_FOLDER;
			break;
		case BlackberryFeature.BLACKBERRY_480x320: 
			imageFolder = imageFolder+CMAppSetting.DEFAULT_480x320_SCREEN_FOLDER;
			break;			
		case BlackberryFeature.BLACKBERRY_480x360: 
			imageFolder = imageFolder+CMAppSetting.DEFAULT_480x360_SCREEN_FOLDER;
			break;			
		case BlackberryFeature.BLACKBERRY_480x640: 
			imageFolder = imageFolder+CMAppSetting.DEFAULT_480x640_SCREEN_FOLDER;
			break;
		case BlackberryFeature.BLACKBERRY_480x800:
			imageFolder = imageFolder+CMAppSetting.DEFAULT_480x800_SCREEN_FOLDER;
			break;
		case BlackberryFeature.BLACKBERRY_640x480: 
			imageFolder = imageFolder+CMAppSetting.DEFAULT_640x480_SCREEN_FOLDER;
			break;
		default:
			imageFolder = imageFolder+CMAppSetting.DEFAULT_SCREEN_FOLDER;
			break;
		}
		
		String fileName = imageFolder + bitmapFileName;
		Bitmap bitmap = loadBitmapFromFile(fileName);

		if (bitmap == null) {
			//try to load image from default folder 
			fileName = CMAppSetting.DEFAULT_IMAGE_FOLDER +CMAppSetting.DEFAULT_SCREEN_FOLDER + bitmapFileName;
			bitmap = loadBitmapFromFile(fileName);
		}
		
		return bitmap;
	}
	
	private static Bitmap loadBitmapFromFile(String fileName) {

		Bitmap bitmap = null;
		try {
			InputStream stream = GeneralUtils.class.getResourceAsStream(fileName);
			if (stream.available() > 0) {
				byte[] bytes = IOUtilities.streamToBytes(stream);
				Bitmap.PNGInfo png = new Bitmap.PNGInfo();
				png.getPNGInfo(bytes, 0, bytes.length);
				bitmap = Bitmap.createBitmapFromPNG(bytes, 0, bytes.length);
			}
		} catch (Exception e) {}
		
		return bitmap;
	}

	public static String getVideoFileName(String videoFileName, int screenType) {
		if (videoFileName == null)
			return null;
		
		String videoFolder = CMAppSetting.DEFAULT_VIDEO_FOLDER;
		switch (screenType) {
		case BlackberryFeature.BLACKBERRY_320x240: 
			videoFolder = videoFolder+CMAppSetting.DEFAULT_320x240_SCREEN_FOLDER;
			break;
		case BlackberryFeature.BLACKBERRY_360x480: 
			videoFolder = videoFolder+CMAppSetting.DEFAULT_360x480_SCREEN_FOLDER;
			break;
		case BlackberryFeature.BLACKBERRY_480x320: 
			videoFolder = videoFolder+CMAppSetting.DEFAULT_480x320_SCREEN_FOLDER;
			break;			
		case BlackberryFeature.BLACKBERRY_480x360: 
			videoFolder = videoFolder+CMAppSetting.DEFAULT_480x360_SCREEN_FOLDER;
			break;			
		case BlackberryFeature.BLACKBERRY_480x640: 
			videoFolder = videoFolder+CMAppSetting.DEFAULT_480x640_SCREEN_FOLDER;
			break;
		case BlackberryFeature.BLACKBERRY_480x800:
			videoFolder = videoFolder+CMAppSetting.DEFAULT_480x800_SCREEN_FOLDER;
			break;
		case BlackberryFeature.BLACKBERRY_640x480: 
			videoFolder = videoFolder+CMAppSetting.DEFAULT_640x480_SCREEN_FOLDER;
			break;
		default:
			videoFolder = videoFolder+CMAppSetting.DEFAULT_SCREEN_FOLDER;
			break;
		}
		
		return videoFolder + videoFileName;
	}
	
	public static Bitmap resizeImage(Bitmap original_image, int newWidth, int newHeight)
	{
	    Bitmap scaledBitmap = new Bitmap(newWidth, newHeight);
		original_image.scaleInto(scaledBitmap, Bitmap.FILTER_LANCZOS);
	    return scaledBitmap;
	}
	
	public static Bitmap makeImageTransparent(Bitmap I)
	{
		if(I != null) {
			int height=I.getHeight();
			int width=I.getWidth();
			
			int[] pixels_1D=new int[width*height];
			
			I.getARGB(pixels_1D, 0, width, 0, 0, width, height);
		    
			for(int i=0; i<height*width; i++)
			{
				pixels_1D[i]=(pixels_1D[i]&0x00ffffff)+(0<<24);
			}
			
			I.setARGB(pixels_1D, 0, width, 0, 0, width, height);
		}
		
		return I;
	}
	
	public static void drawGradientFilledRoundedRect(Graphics graphics, int width, int height, int startColor, int endColor, boolean defaultColor, boolean focus) {
//    	if (startColor == endColor || endColor == -1) {
			graphics.setColor(startColor);
	    	graphics.fillRoundRect(0, 0, width, height, CMFieldCommon.DEFAULT_ROUND_RECT, CMFieldCommon.DEFAULT_ROUND_RECT);
//    	} else 
		
//TODO: for 7.0 only
//    	{
//    		int linearStartColor = startColor;
//    		int linearEndColor = endColor; 
//    		if (defaultColor) {
//    			if (focus) {
//    	    		linearStartColor = 0x098CEE;
//    	    		linearEndColor = 0x1766DB; 
//    			} else {
//    	    		linearStartColor = 0xF9F9F9;
//    	    		linearEndColor = 0xD0D2D4; 
//    			}
//    		}
//    		
//			graphics.drawGradientFilledRoundedRect(0, 0, width, height / 2, 
//					linearStartColor, linearEndColor, 
//					false, Graphics.TOP_LEFT_ROUNDED_RECT_CORNER | Graphics.TOP_RIGHT_ROUNDED_RECT_CORNER, 
//					CMFieldCommon.DEFAULT_ROUND_RECT, CMFieldCommon.DEFAULT_ROUND_RECT);
//	    	
//	    	graphics.drawGradientFilledRoundedRect(0, height/2, width, height / 2, 
//	    			linearEndColor, linearEndColor, 
//					false, Graphics.BOTTOM_LEFT_ROUNDED_RECT_CORNER | Graphics.BOTTOM_RIGHT_ROUNDED_RECT_CORNER, 
//					CMFieldCommon.DEFAULT_ROUND_RECT, CMFieldCommon.DEFAULT_ROUND_RECT);
//    	}
	}
	
	public static void drawGradientFilledRect(Graphics graphics, int width, int height, int startColor, int endColor, boolean defaultColor, boolean focus) {
//    	if (startColor == endColor || endColor == -1) {
			graphics.setColor(startColor);
	    	graphics.fillRect(0, 0, width, height);
//    	} else 
		
//TODO: for 7.0 only
//    	{
//    		int linearStartColor = startColor;
//    		int linearEndColor = endColor; 
//    		if (defaultColor) {
//    			if (focus) {
//    	    		linearStartColor = 0x098CEE;
//    	    		linearEndColor = 0x1766DB; 
//    			} else {
//    	    		linearStartColor = 0xF9F9F9;
//    	    		linearEndColor = 0xD0D2D4; 
//    			}
//    		}
//    		
//			graphics.drawGradientFilledRect(0, 0, width, height / 2, 
//					linearStartColor, linearEndColor, 
//					false);
//	    	
//	    	graphics.drawGradientFilledRect(0, height/2, width, height / 2, 
//	    			linearEndColor, linearEndColor, 
//					false);
//    	}
	}
}
