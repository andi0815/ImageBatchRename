/**
 * 
 */
package amo.media.model;

import java.io.File;
import java.util.Date;

/**
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date Feb 19, 2015
 */
public class Image {
    
    /**
     * Idee: * Parse direkt in TikaParser --> File filter anhand content-types *
     * Image ist nur Wrapper für Metadaten
     */
    
    private Date      creationDate;
    
    private String    filename;
    
    private ImageType extension;
    
    private File      file;
    
    public static Image fromFile(File file) {
        Image image = new Image();
        image.file = file;
        image.filename = file.getName(); // remove extension?
        
        return image;
    }
    
    @Override
    public String toString() {
        return String.format("{Image[%s]: date=%s extension=%s}", this.filename, this.creationDate != null ? this.creationDate.toString() : "N.A.",
                this.extension);
    }
}
