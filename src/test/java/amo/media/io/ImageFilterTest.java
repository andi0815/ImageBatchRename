package amo.media.io;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ImageFilterTest {
    
    @Test
    public void testImageJpeg() throws Exception {
        assertTrue(new ImageFilter().accept(null, "bla.jpg"));
        assertTrue(new ImageFilter().accept(null, "bla.jpeg"));
        assertTrue(new ImageFilter().accept(null, "bla.blablabl.jpeg"));
        assertTrue(new ImageFilter().accept(null, "bla.blablabl.JPEG"));
        assertTrue(new ImageFilter().accept(null, "bla.blablabl.JPG"));
        assertTrue(new ImageFilter().accept(null, "bla.blablabl.jPeG"));
        
        assertFalse(new ImageFilter().accept(null, "bla.blablabl.jPeG."));
        assertFalse(new ImageFilter().accept(null, "bla.blablabl.jPeG.lll"));
        assertFalse(new ImageFilter().accept(null, "bla.blablabljPeG"));
    }
    
    @Test
    public void testEmptyName() throws Exception {
        assertFalse(new ImageFilter().accept(null, ""));
    }
    
    @Test
    public void testNullName() throws Exception {
        assertFalse(new ImageFilter().accept(null, null));
    }
}
