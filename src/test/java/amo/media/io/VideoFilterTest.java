package amo.media.io;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class VideoFilterTest {
    
    @Test
    public void testVideoMov() throws Exception {
        assertTrue(new VideoFilter().accept(null, "it.mov"));
        assertTrue(new VideoFilter().accept(null, "bla.blablabl.mov"));
        assertTrue(new VideoFilter().accept(null, "bla.blablabl.MOV"));
        assertTrue(new VideoFilter().accept(null, "bla.blablabl.moV"));
        assertTrue(new VideoFilter().accept(null, "bla.blablabl.MoV"));
        
        assertFalse(new VideoFilter().accept(null, ".mov"));
        assertFalse(new VideoFilter().accept(null, "bla.blablabl.mov."));
        assertFalse(new VideoFilter().accept(null, "bla.blablabl.mov.lll"));
        assertFalse(new VideoFilter().accept(null, "bla.blablablmov"));
    }
    
    @Test
    public void testVideoMp4() throws Exception {
        assertTrue(new VideoFilter().accept(null, "it.mp4"));
        assertTrue(new VideoFilter().accept(null, "bla.blablabl.mp4"));
        assertTrue(new VideoFilter().accept(null, "bla.blablabl.mP4"));
        assertTrue(new VideoFilter().accept(null, "bla.blablabl.Mp4"));
        
        assertFalse(new VideoFilter().accept(null, ".mp4"));
        assertFalse(new VideoFilter().accept(null, "bla.blablabl.mp4."));
        assertFalse(new VideoFilter().accept(null, "bla.blablabl.mp4.lll"));
        assertFalse(new VideoFilter().accept(null, "bla.blablablmp4"));
    }
    
    @Test
    public void testEmptyName() throws Exception {
        assertFalse(new VideoFilter().accept(null, ""));
    }
    
    @Test
    public void testNullName() throws Exception {
        assertFalse(new VideoFilter().accept(null, ""));
    }
}
