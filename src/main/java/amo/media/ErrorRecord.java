package amo.media;

import java.io.File;

/**
 * Error record of a failed move operation.
 *
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 26.05.2015
 */
public class ErrorRecord {
    
    private final File      file;
    
    private final Exception cause;
    
    public ErrorRecord(File file, Exception e) {
        this.file = file;
        this.cause = e;
    }
    
    public File getFile() {
        return this.file;
    }
    
    public Exception getCause() {
        return this.cause;
    }

    @Override
    public String toString() {
        return "ErrorRecord[" + this.file + "]: \t" + (this.cause != null ? this.cause.getMessage() : null);
    }
    
}
