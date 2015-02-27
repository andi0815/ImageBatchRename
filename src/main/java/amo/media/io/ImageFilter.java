package amo.media.io;

import java.io.File;
import java.io.FilenameFilter;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class ImageFilter implements FilenameFilter {
    
    /** Logger Object for this Class */
    private static final Logger LOGGER = Logger.getLogger(ImageFilter.class.getName());
    
    private static final Pattern IMAGE_PATTERN = Pattern.compile(".+\\.(jpg|jpeg)", Pattern.CASE_INSENSITIVE);
    
    @Override
    public boolean accept(File directory, String filename) {
        if (filename == null) {
            LOGGER.severe("Name should never be null! Dir is: " + directory);
            return false;
        }
        return IMAGE_PATTERN.matcher(filename).matches();
    }
    
}
