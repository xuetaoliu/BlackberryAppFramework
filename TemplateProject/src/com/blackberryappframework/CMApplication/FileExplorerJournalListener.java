package com.blackberryappframework.CMApplication;

import com.blackberryappframework.Utility.CallBack;

import net.rim.device.api.io.file.FileSystemJournal;
import net.rim.device.api.io.file.FileSystemJournalEntry;
import net.rim.device.api.io.file.FileSystemJournalListener;
import net.rim.device.api.system.Application;

public class FileExplorerJournalListener implements FileSystemJournalListener {
	private long _lastUSN;
	
	private String fileLocation = null;
	public String getFileLocation() { return this.fileLocation;}
	
	private CallBack cb;
	public FileExplorerJournalListener(CallBack cb) {
		this.cb = cb;
	}
	
    public void fileJournalChanged() {
        long nextUSN = FileSystemJournal.getNextUSN();
        String msg = null;
        for (long lookUSN = nextUSN - 1; lookUSN >= _lastUSN && msg == null; --lookUSN) {
            FileSystemJournalEntry entry = FileSystemJournal.getEntry(lookUSN);
            if (entry == null) {
                break; 
            }
            
            String path = entry.getPath();
            if (path != null) {
                if (path.endsWith("png") || path.endsWith("jpg") || path.endsWith("bmp") || path.endsWith("gif") ){
                    switch (entry.getEvent()) {
                        case FileSystemJournalEntry.FILE_ADDED:
                            //either a picture was taken or a picture was added to the BlackBerry device 
                        	msg = path;
                            break;
                        case FileSystemJournalEntry.FILE_DELETED:
                            //a picture was removed from the BlackBerry device;
                            break;
                    }
                }
            }
        }
        
        if (msg != null) {
        	fileLocation = msg;
        	Application.getApplication().removeFileSystemJournalListener(this);
        	if (this.cb != null)
        		this.cb.finished(fileLocation);
        }
    }
}
