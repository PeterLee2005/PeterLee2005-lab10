public class TooSmallText extends Exception {
    public TooSmallText (int count) {
        super("TooSmallText: Only found " + count + " words.");
    }
}
