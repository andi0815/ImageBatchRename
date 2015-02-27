package amo.media.io;

import java.io.File;
import java.io.FilenameFilter;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class VideoFilter implements FilenameFilter {
    
    /** Logger Object for this Class */
    private static final Logger LOGGER = Logger.getLogger(VideoFilter.class.getName());
    
    private static final Pattern VIDEO_PATTERN = Pattern.compile(".+\\.(mp4|mov)", Pattern.CASE_INSENSITIVE);
    
    @Override
    public boolean accept(File directory, String filename) {
        if (filename == null) {
            LOGGER.severe("Name should never be null! Dir is: " + directory);
            return false;
        }
        return VIDEO_PATTERN.matcher(filename).matches();
    }
}
