import java.io.IOException;

public class EmptyFileException extends IOException {
    public EmptyFileException (String filename) {
        super(filename + " was empty");
    }
}
