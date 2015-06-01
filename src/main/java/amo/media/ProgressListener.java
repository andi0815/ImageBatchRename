package amo.media;

import java.io.File;

public interface ProgressListener {
    
    void start();
    
    void notifySuccess(File oldFile, File newFile);
    
    void notifyError(File file, Exception e);

    void end();
}
