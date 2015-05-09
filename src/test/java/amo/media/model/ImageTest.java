package amo.media.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import junit.framework.Assert;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ImageTest {

    private static final String sep         = System.getProperty("file.separator");
    private final String        testdataDir = "src" + sep + "test" + sep + "resources" + sep + "testdata";
    private final File          testImage   = new File(this.testdataDir, "20140727_145200.jpeg");

    @Ignore
    @Test
    public void testFromFile() throws Exception {
        Image image = Image.fromFile(this.testImage);
        System.out.println(" +++ IMAGE: " + image);
        // Desktop.getDesktop().open(this.testImage);
        // Assert.assertEquals(expected, image);
    }

    private Tika        tika;
    private InputStream stream;

    @Before
    public void setUp() throws FileNotFoundException {
        this.tika = new Tika();
        this.stream = new FileInputStream(this.testImage);
    }

    @Ignore
    @Test
    public void testImageMetadataCameraModel() throws IOException,
    SAXException, TikaException {
        Metadata metadata = new Metadata();
        ContentHandler handler = new DefaultHandler();
        // Parser parser = new JpegParser();
        AutoDetectParser parser = new AutoDetectParser();
        ParseContext context = new ParseContext();
        System.out.println(" ### SUPPORTED TYPES: " + parser.getSupportedTypes(context));
        String mimeType = this.tika.detect(this.stream); // needs to be
        // different input
        // stream ...
        System.out.println(" ### Recognized Type: " + mimeType);
        metadata.set(Metadata.CONTENT_TYPE, mimeType);
        parser.parse(new FileInputStream(this.testImage), handler, metadata, context);
        for (String name : metadata.names()) {
            System.out.println(" META[" + name + "]: " + metadata.get(name));
        }

        // assertTrue("The expected Model is not correct", metadata.get("Model")
        // .equals("Canon EOS 350D DIGITAL"));
        Assert.assertEquals("2014-07-27T14:52:00", metadata.get("Creation-Date"));
    }

    @Test
    public void testImageFolder() throws IOException, SAXException, TikaException {
        ContentHandler handler = new DefaultHandler();
        // Parser parser = new JpegParser();
        AutoDetectParser parser = new AutoDetectParser();
        File[] allTestFiles = new File(this.testdataDir).listFiles();
        int fileCount = 0;
        for (File file : allTestFiles) {
            FileInputStream inStream = new FileInputStream(file);
            String mimeType = this.tika.detect(inStream);
            Metadata metadata = new Metadata();
            metadata.set(Metadata.CONTENT_TYPE, mimeType);
            parser.parse(new FileInputStream(file), handler, metadata, new ParseContext());
            System.out.println("[" + fileCount++ + "]\t" + file.getName() + "\t " + mimeType + " \tdate: " + metadata.get("date")
                    + "\t Creation-Date: " + metadata.get("Creation-Date"));
            
            // System.out.println(" ### Recognized Type: " + mimeType);
            // for (String name : metadata.names()) {
            // System.out.println(" META[" + name + "]: " + metadata.get(name));
            // }
            // System.in.read(new byte[1]);
        }
        // assertTrue("The expected Model is not correct", metadata.get("Model")
        // .equals("Canon EOS 350D DIGITAL"));
        // Assert.assertEquals("2014-07-27T14:52:00",
        // metadata.get("Creation-Date"));
    }

    @After
    public void close() throws IOException {
        if (this.stream != null) {
            this.stream.close();
        }
    }

    // - See more at:
    // http://blog.jeroenreijn.com/2010/04/metadata-extraction-with-apache-tika.html#sthash.8KMgrrMi.dpuf
}
