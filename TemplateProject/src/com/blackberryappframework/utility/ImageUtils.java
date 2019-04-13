package com.blackberryappframework.Utility;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;

import net.rim.device.api.io.IOUtilities;
import net.rim.device.api.system.Bitmap;

public class ImageUtils {
	
	public static Bitmap getImageFromPhoneFile(String fileName) {
		Bitmap image = null;
		if (fileName == null || fileName.trim().length() == 0)
			return image;
		
		InputStream inputStream=null;
	    FileConnection fileConnection=null;     
	    try {
	    	String fullPathName ;
	    	if (fileName.indexOf("file://") >= 0) {
	    		fullPathName = fileName;
	    	} else {
	    		fullPathName = "file://" + fileName;
	    	}
	        fileConnection=(FileConnection) Connector.open(fullPathName);
	        if(fileConnection.exists())
	        {
	            inputStream=fileConnection.openInputStream();           
	            byte[] data=new byte[(int)fileConnection.fileSize()];           
	            data=IOUtilities.streamToBytes(inputStream);
	            inputStream.close();
	            fileConnection.close();
	            image = Bitmap.createBitmapFromBytes(data,0,data.length,1);
	        }
	    } catch (Exception e)  {
	        try  {
	            if(inputStream!=null) {
	                inputStream.close();                
	            }
	            if(fileConnection!=null) {
	                fileConnection.close();
	            }
	        }  catch (Exception exp)  { }
	    }
	    
	    return image;
	}
	
	public static String saveImageOnPhone(Bitmap image, String path, String fileName, boolean deleteIfExist, boolean pngFormat) {
		String fullFileName = null;
		
		if (image != null) {
			byte[] imageData = ImageUtils.getImageBytes(image, pngFormat);
			fullFileName = FileUtils.saveDataOnPhone(imageData, path, fileName, deleteIfExist);
		}
		
		return fullFileName;
		
	}
	
	public static byte[] getImageBytes(Bitmap bitmap, boolean pngFormat) {
		byte[] imageBytes = null;
		if (pngFormat) {
			try {
				PNGEncoder encoder = new PNGEncoder(bitmap, true);
				imageBytes = encoder.encode(true);
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
		
		return imageBytes;
	}
	
	public static byte[] getImageBytes(String fileName) {
		byte[] data = null;
		if (fileName == null || fileName.trim().length() == 0)
			return data;
		
		InputStream inputStream=null;
	    FileConnection fileConnection=null;     
	    try {
	    	String fullPathName ;
	    	if (fileName.indexOf("file://") >= 0) {
	    		fullPathName = fileName;
	    	} else {
	    		fullPathName = "file://" + fileName;
	    	}
	        fileConnection=(FileConnection) Connector.open(fullPathName);
	        if(fileConnection.exists())
	        {
	            inputStream=fileConnection.openInputStream();           
	            data = new byte[(int)fileConnection.fileSize()];           
	            data = IOUtilities.streamToBytes(inputStream);
	            inputStream.close();
	            fileConnection.close();
	        }
	    } catch (Exception e)  {
	        try  {
	            if(inputStream!=null) {
	                inputStream.close();                
	            }
	            if(fileConnection!=null) {
	                fileConnection.close();
	            }
	        }  catch (Exception exp)  { }
	    }
	    
	    return data;
	}

}
