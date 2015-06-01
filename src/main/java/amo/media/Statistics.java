/**
 *
 */
package amo.media;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Collection statistics data of a single batch run.
 *
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 26.05.2015
 */
public class Statistics implements ProgressListener {

    /** Logger Object for this Class */
    private static final Logger LOGGER           = Logger.getLogger(Statistics.class);
    
    private List<ErrorRecord>   errors           = new ArrayList<ErrorRecord>();
    private Integer             numFiles;
    private int                 progessCounter;
    Iterator<Integer>           precentagesToLog = Arrays.asList(new Integer[] { 10, 20, 30, 40, 50, 60, 70, 80, 90 }).iterator();
    int                         nextPercentage   = this.precentagesToLog.next();
    
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
    public void notifyError(File file, Exception e) {
        this.getErrors().add(new ErrorRecord(file, e));
        System.out.print("x");
        this.progessCounter++;
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
        String numFilesString = this.progessCounter != this.numFiles ?
                String.valueOf(this.progessCounter + " of " + this.numFiles) :
                String.valueOf(this.progessCounter);

                if (this.errors.isEmpty()) {
            LOGGER.info("Processed " + numFilesString + " successfully!");
        }
        else {
            LOGGER.info("Processed " + numFilesString + " of which " + this.errors.size() + " were in error:");
            for (ErrorRecord record : this.errors) {
                LOGGER.warn("\t" + record);
            }
        }

    }

    @Override
    public void start() {
        System.out.print("Progress: 0% ");
        this.progessCounter = 0;
    }
    
    @Override
    public void notifySuccess(File oldFile, File newFile) {
        this.progessCounter++;
        if (this.progessCounter > this.nextPercentage) {
            System.out.print(" " + (int) (this.progessCounter * 100 / (float) this.numFiles) + "% ");
            if (this.precentagesToLog.hasNext()) {
                this.nextPercentage = this.precentagesToLog.next();
            }
            else {
                this.nextPercentage = Integer.MAX_VALUE;
            }
        }
        else {
            System.out.print(".");
        }
    }
    
    @Override
    public void end() {
        System.out.print(" 100%");
    }
    
}
