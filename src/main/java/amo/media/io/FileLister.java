package amo.media.io;

import java.io.File;

public class FileLister {
    
    private File directory;
    
    public FileLister(File directory) {
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("File is not a directory: " + directory);
        }
        this.directory = directory;
    }
    
    /**
     * Returns all images found by {@link ImageFilter} in given directory (non-recursive)
     * 
     * @return the images in the current dir
     */
    public String[] getImages() {
        // System.out.println(" +++ LIST: " + Arrays.toString(this.directory.list()));
        return this.directory.list(new ImageFilter());
    }
    
    /**
     * Returns all videos found by {@link ImageFilter} in given directory (non-recursive)
     * 
     * @return the videos in the current dir
     */
    public String[] getVideos() {
        return this.directory.list(new VideoFilter());
    }
    
}
