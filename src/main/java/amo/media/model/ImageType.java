package amo.media.model;

public enum ImageType {
    
    JPEG("jpg", "jpeg");
    
    private final String extension;
    
    private ImageType(final String... extension) {
        this.extension = extension[0];
    }
    
    public String getExtension() {
        return this.extension;
    }
    
    public ImageType fromFilename(String filename) {
        throw new IllegalStateException("Not implemented, yet...");
    }
    
}
