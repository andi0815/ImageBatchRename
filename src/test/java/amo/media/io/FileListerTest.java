package amo.media.io;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author Andreas Monger (andreas.monger@gmail.com)
 * @date Feb 12, 2015
 * 
 */
public class FileListerTest {
    
    private static final String sep = System.getProperty("file.separator");
    
    private final String testdataDir = "src" + sep + "test" + sep + "resources" + sep + "testdata";
    
    @Test
    public void testFilterTestdata() throws Exception {
        File file = new File(this.testdataDir);
        assertTrue(file.isDirectory());
        FileLister fileLister = new FileLister(file);
        Assert.assertEquals(37, fileLister.getImages().length);
        Assert.assertEquals(2, fileLister.getVideos().length);
    }
}
