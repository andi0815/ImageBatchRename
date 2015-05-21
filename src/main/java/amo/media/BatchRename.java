package amo.media;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.Priority;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date 09.05.2015
 */
public class BatchRename {

    /** Logger Object for this Class */
    private static final Logger LOGGER = Logger.getLogger(BatchRename.class);
    
    @SuppressWarnings("deprecation")
    public static void main(String[] args) {
        
        // Set up a log configuration that logs on the console.
        ConsoleAppender consoleAppender = new ConsoleAppender(new PatternLayout("[%5p|%d] %m - %F:%L%n"), "System.out");
        consoleAppender.setThreshold(Priority.INFO);
        BasicConfigurator.configure(consoleAppender);

        // create Options object
        Options options = new Options();
        options.addOption("v", false, "verbose mode");

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("v")) {
                consoleAppender.setThreshold(Priority.DEBUG);
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
            printConfig(folderToProcess);

            BatchProcessor.startBatchRun(folderToProcess);
        }
        catch (ParseException e) {
            LOGGER.error("Could not parse input.", e);
            printUsage(options, 1);
        }
    }
    
    private static void printConfig(File folderToProcess) {
        LOGGER.info("Starting with folder: " + folderToProcess);
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
