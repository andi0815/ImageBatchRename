/**
 *
 */
package amo.media;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Collection statistics data of a single batch run.
 *
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 26.05.2015
 */
public class Statistics {

    /** Logger Object for this Class */
    private static final Logger LOGGER = Logger.getLogger(Statistics.class);
    
    private List<ErrorRecord>   errors = new ArrayList<ErrorRecord>();
    private Integer             numFiles;
    
    public Statistics() {
    }

    /**
     * Creates a new instance
     *
     * @param file
     *            the file in error
     * @param e
     *            the associated Exception
     */
    public void logError(File file, Exception e) {
        this.getErrors().add(new ErrorRecord(file, e));
    }

    public List<ErrorRecord> getErrors() {
        return this.errors;
    }

    public Integer getNumFiles() {
        return this.numFiles;
    }

    public void setNumFiles(Integer numFiles) {
        this.numFiles = numFiles;
    }
    
    /**
     * Prints a final summary to logger
     */
    public void printStatistics() {
        if (this.errors.isEmpty()) {
            LOGGER.info("Processed " + this.numFiles + " successfully!");
        }
        else {
            LOGGER.info("Processed " + this.numFiles + " of which " + this.errors.size() + " were in error:");
            for (ErrorRecord record : this.errors) {
                LOGGER.warn("\t" + record);
            }
        }

    }

}
