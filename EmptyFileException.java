import java.io.IOException;

public class EmptyFileException extends IOException {
    public EmptyFileException (String filename) {
        super("EmptyFileException: " + filename + " was empty");
    }
}
