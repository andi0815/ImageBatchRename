/**
 *
 */
package amo.media;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.Priority;
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
 * @date 12.06.2015
 */
public class FileAnalysis {
    
    /** Logger Object for this Class */
    private static final Logger           logger              = Logger.getLogger(FileAnalysis.class);
    private static final PatternLayout    CONSOLE_LOG_PATTERN = new PatternLayout("%m%n");
    private static final SimpleDateFormat DATE_FORMAT         = new SimpleDateFormat("YYYYMMdd-HH'h'mm");

    public static void main(String[] args) {

        ConsoleAppender consoleAppender = new ConsoleAppender(CONSOLE_LOG_PATTERN, "System.out");
        consoleAppender.setThreshold(Priority.INFO);
        BasicConfigurator.configure(consoleAppender);

        if (args.length < 1) {
            printUsage(1);
        }
        File file = new File(args[0]);
        if (!file.exists() || !file.isFile()) {
            logger.warn("File '" + file + "' does not exist or is not a file!");
            printUsage(2);
        }

        Tika tika = new Tika();
        ContentHandler handler = new DefaultHandler();
        AutoDetectParser parser = new AutoDetectParser();
        try {
            String mimeType = null;
            Metadata metadata = new Metadata();
            try (
                    FileInputStream inStream = new FileInputStream(file);
                    FileInputStream inStream2 = new FileInputStream(file);) {

                mimeType = tika.detect(inStream);
                metadata.set(Metadata.CONTENT_TYPE, mimeType);
                parser.parse(inStream2, handler, metadata, new ParseContext());

                logger.info("Metadata of file: " + file);
                printMetadata(metadata);
            }

        }
        catch (IOException | SAXException | TikaException | RuntimeException e) {
            logger.error("Failed to process file '" + file + "'. Cause: " + e.getMessage());
        }
    }

    private static void printMetadata(Metadata metadata) {
        for (String name : metadata.names()) {
            logger.info(String.format("\t[%-25s]:\t %s", name, metadata.get(name)));
        }
    }

    /**
     * Print tool usage to console
     *
     * @param options
     *            the options for this tool
     */
    private static void printUsage(int exitCode) {
        logger.info("Usage: FileAnalysis <file>");
        System.exit(exitCode);
    }

}
