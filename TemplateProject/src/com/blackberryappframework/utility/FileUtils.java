package com.blackberryappframework.Utility;

import java.io.IOException;
import java.io.OutputStream;

import javax.microedition.content.*;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;

public class FileUtils {
	public static boolean openFileWithBBApp(String fileName, String projectPackage) {
		boolean result = true;
        try {
    		//Create the invocation request.
            Invocation invocation = new Invocation(fileName);
            invocation.setAction(ContentHandler.ACTION_OPEN);
            invocation.setResponseRequired(true); 

            //Use the registry to perform the invocation.
            Registry registry = Registry.getRegistry(projectPackage);
			registry.invoke(invocation);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (ContentHandlerException e) {
			if (e.getErrorCode() == ContentHandlerException.NO_REGISTERED_HANDLER)
				result = false;
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        return result;
	}
	
	public static boolean fileExist(String fileName) {
    	String fullPathName ;
    	if (fileName.indexOf("file://") >= 0) {
    		fullPathName = fileName;
    	} else {
    		fullPathName = "file://" + fileName;
    	}
    	
    	boolean exist = false;
		try {
			FileConnection fileConnection = (FileConnection) Connector.open(fullPathName);
			exist = fileConnection.exists();
	        fileConnection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        return exist;
	}
	
	public static String createFolderOnPhone(String folderName, boolean memoryCard) {
		
		String fullPath = "";
		try {
			String fullFilePath = System.getProperty("fileconn.dir.photos");
			int index = fullFilePath.indexOf("pictures");
			fullFilePath = fullFilePath.substring(0, index);
			fullPath = fullFilePath + folderName;
			// the final slash in the folder path is required
			FileConnection fc = (FileConnection)Connector.open(fullPath);
			// If no exception is thrown, the URI is valid but the folder may not exist.
			if (!fc.exists()) {
				fc.mkdir();  // create the folder if it doesn't exist
			}
			fc.close();
			
		} catch (IOException ioe)  {
		}
		
		return fullPath;
	}
	
	public static void deleteFileOnPhone(String fileName) {
		if (fileName != null) {
			FileConnection fileConnection = null;
			try {
				fileConnection = (FileConnection) Connector.open(fileName);
				if(fileConnection.exists()) {
					fileConnection.delete();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String saveDataOnPhone(byte[] data, String path, String fileName, boolean deleteIfExist) {
		String fullFileName = null;
		
		if (data != null) {
			FileConnection fileConnection = null;
			OutputStream outputStream = null;
			try {
				fullFileName = path + fileName;
				fileConnection = (FileConnection) Connector.open(fullFileName);
				if(fileConnection.exists()) {
					if (deleteIfExist)
						fileConnection.delete();
				}
				
				fileConnection.create();
				outputStream = fileConnection.openOutputStream();
				outputStream.write(data, 0, data.length);
				
			} catch (IOException e) {
				fullFileName = null;
				e.printStackTrace();
			} finally {
				if (outputStream != null) {
					try {
						outputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				if (fileConnection != null) {
					try {
						fileConnection.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		return fullFileName;
	}
}
