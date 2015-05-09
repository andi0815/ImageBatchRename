/**
 *
 */
package amo.media;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 09.05.2015
 */
public class BatchProcessor {
    
    /** Logger Object for this Class */
    private static final Logger     LOGGER        = Logger.getLogger(BatchProcessor.class);
    
    // beware this is not thread-safe!
    private static SimpleDateFormat importFormat  = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private static SimpleDateFormat newFileprefix = new SimpleDateFormat("yyyyMMdd_HH'h'mm");
    private static SimpleDateFormat newFoldername = new SimpleDateFormat("yyyyMM' - Handybilder'");
    
    public static void startBatchRun(File folderToProcess) {

        File[] allTestFiles = folderToProcess.listFiles();
        
        Tika tika = new Tika();
        ContentHandler handler = new DefaultHandler();
        AutoDetectParser parser = new AutoDetectParser();
        int fileCount = 0;
        for (File file : allTestFiles) {
            FileInputStream inStream;
            try {
                inStream = new FileInputStream(file);
                String mimeType = tika.detect(inStream);
                Metadata metadata = new Metadata();
                metadata.set(Metadata.CONTENT_TYPE, mimeType);
                parser.parse(new FileInputStream(file), handler, metadata, new ParseContext());
                processFile(fileCount, file, mimeType, metadata);
                fileCount++;
            }
            catch (IOException | SAXException | TikaException | ParseException e) {
                LOGGER.warn("Failed to process file '" + file + "'. Cause: " + e.getMessage());
            }
        }
    }

    private static void processFile(int fileCount, File file, String mimeType, Metadata metadata) throws ParseException, IOException {
        String creationDate = metadata.get("Creation-Date");
        Date parsedDate = importFormat.parse(creationDate);
        LOGGER.info(" #" + fileCount + ": \t" + file.getName() + "\t " + mimeType + "\t Creation-Date: " + creationDate + "\t IS: "
                + parsedDate);
        // Create folder for new files
        File newFolder = new File(file.getParent() + File.separator + newFoldername.format(parsedDate));
        if (!newFolder.exists()) {
            if (!newFolder.mkdir()) {
                LOGGER.warn("Failed to create subdirectory '" + newFolder + "' for file: '" + file.getName() + "'. skipping...");
            }
        }
        
        // move file
        File newFile = new File(newFolder, newFileprefix.format(parsedDate) + "_" + file.getName());
        FileUtils.moveFile(file, newFile);
        // if (file.renameTo(newFile)) {
        LOGGER.info(" #" + fileCount + ": \t" + file.getName() + "\t (" + creationDate + ")\t renamed to: '" + newFile + "'");
        // }
        // else {
        // LOGGER.warn("Couldn't rename file '" + file.getAbsolutePath() +
        // " to: '" + newFile + "'");
        // }

    }
}