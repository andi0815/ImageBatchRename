package amo.media;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.Priority;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 09.05.2015
 */
public class BatchRename {

    /** Logger Object for this Class */
    private static final Logger           LOGGER              = Logger.getLogger(BatchRename.class);
    private static final PatternLayout    FILE_LOG_PATTERN    = new PatternLayout("[%5p|%d] %m - %F:%L%n");
    private static final PatternLayout    CONSOLE_LOG_PATTERN = new PatternLayout("%m%n");
    private static final SimpleDateFormat DATE_FORMAT         = new SimpleDateFormat("YYYYMMdd-HH'h'mm");
    
    @SuppressWarnings("deprecation")
    public static void main(String[] args) {
        
        Integer maxFiles = null;
        boolean doCreationDateFallback = false;

        ConsoleAppender consoleAppender = new ConsoleAppender(CONSOLE_LOG_PATTERN, "System.out");
        consoleAppender.setThreshold(Priority.INFO);
        BasicConfigurator.configure(consoleAppender);
        // logfile appender
        try {
            FileAppender fileAppender = new FileAppender(FILE_LOG_PATTERN, "batchrename_" + DATE_FORMAT.format(new Date()) + ".log", true);
            fileAppender.setThreshold(Level.DEBUG);
            Logger.getRootLogger().addAppender(fileAppender);
        }
        catch (IOException e) {
            LOGGER.error("Couldn't setup file appender.", e);
        }

        // create Options object
        Options options = new Options();
        options.addOption("v", false, "verbose mode");
        options.addOption("c", false, "use file creation date, if date could not be extracted from metadata");
        options.addOption("max", "maxFiles", true, "Maximum number of files to process");

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("v")) {
                consoleAppender.setThreshold(Priority.DEBUG);
            }
            if (cmd.hasOption("c")) {
                doCreationDateFallback = true;
            }
            if (cmd.hasOption("max")) {
                maxFiles = Integer.valueOf(cmd.getOptionValue("max"));
            }
            String[] cliArgs = cmd.getArgs();
            if (cliArgs == null || cliArgs.length == 0) {
                printUsage(options, 1);
            }
            File folderToProcess = new File(cliArgs[0]);
            if (!folderToProcess.exists() || !folderToProcess.isDirectory()) {
                LOGGER.error("Folder does not exist or is not a folder: '" + folderToProcess + "'");
                printUsage(options, 1);
            }
            printConfig(folderToProcess, maxFiles, doCreationDateFallback);
            
            Statistics statistics = new Statistics();
            BatchProcessor.startBatchRun(folderToProcess, maxFiles, statistics, doCreationDateFallback);
            statistics.printStatistics();
        }
        catch (ParseException e) {
            LOGGER.error("Could not parse input.", e);
            printUsage(options, 1);
        }
        LOGGER.info("DONE.");
    }
    
    private static void printConfig(File folderToProcess, Integer maxFiles, boolean doCreationDateFallback) {
        LOGGER.info("Starting with folder: \t\t\t" + folderToProcess);
        if (doCreationDateFallback) {
            LOGGER.info("Using file creation date, if creation date could not be found in metadata");
        }
        if (maxFiles != null) {
            LOGGER.info("Maximum number of files to process: \t" + maxFiles);
        }
        // only to be seen in verbose mode...
        LOGGER.debug("Verbose mode enabled");
    }

    /**
     * Print tool usage to console
     *
     * @param options
     *            the options for this tool
     */
    private static void printUsage(Options options, int exitCode) {
        // automatically generate the help statement
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("BatchRename <folder>", options);
        System.exit(exitCode);
    }
}
