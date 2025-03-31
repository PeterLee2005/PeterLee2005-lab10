import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordCounter {
    public static int processText (StringBuffer text, String stopword) throws Exception {
        int wordCount = 0;
        boolean foundStopword = (stopword == null);

        Pattern wordPattern = Pattern.compile ("[a-zA-Z0-9']+");
        Matcher matcher = wordPattern.matcher(text);

        while (matcher.find()) {
            String word = matcher.group();
            wordCount++;
            if (!foundStopword && word.equals(stopword)) {
                foundStopword = true;
                if (wordCount >= 5) {
                    break;
                }
            }
        }

        if (wordCount < 5) {
            throw new TooSmallText(wordCount);
        }

        if (!foundStopword && stopword != null) {
            throw new InvalidStopwordException(stopword);
        }

        return wordCount;
    }

    public static StringBuffer processFile (String filename) throws Exception {
        File file = new File(filename);
        Scanner scanner = null;

        while (scanner == null) {
            try {
                scanner = new Scanner(file);
            }
            catch (FileNotFoundException e) {
                System.out.println ("Invalid filename. Enter a valid filename:");
                Scanner input = new Scanner(System.in);
                filename = input.nextLine();
                file = new File (filename);
            }
            catch (Exception e) {
                System.out.println (e.toString());
            }
        }

        String content = "";
        while (scanner.hasNextLine()) {
            content += scanner.nextLine() + " ";
        }
        scanner.close();

        if (content.trim().isEmpty()) {
            throw new EmptyFileException(filename);
        }
        String result = content.substring(0, content.length()-1);
        return new StringBuffer(result);
    }

    public static void main (String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringBuffer text = new StringBuffer();
        String stopword = null;

        int option = 0;
        while (option != 1 && option != 2) {
            System.out.println ("Choose: file (1) | text (2)");
            if (scanner.hasNextInt()) {
                option = scanner.nextInt();
                scanner.nextLine();
            }
            else {
                scanner.nextLine();
            }
        }

        if (args.length >= 2) {
            stopword = args[1];
        }

        try {
            if (option == 1) {
                if (args.length >= 1) {
                    text = processFile(args[0]);
                }
                else {
                    System.out.println ("Enter filename:");
                    String filename = scanner.nextLine();
                    text = processFile(filename);
                }
            }

            else {
                System.out.println ("Enter text:");
                String input = scanner.nextLine();
                text = new StringBuffer(input);
            }

            try {
                int count = processText(text, stopword);
                System.out.println ("Found " + count + " words.");
            }
            catch (InvalidStopwordException e) {
                System.out.println(e.toString());
                System.out.println ("Enter a stopword:");
                stopword = scanner.nextLine();

                try {
                    int count = processText(text, stopword);
                    System.out.println("Found " + count + " words.");
                }
                catch (InvalidStopwordException f) {
                    System.out.println (f.toString());
                }
                catch (TooSmallText f) {
                    System.out.println (f.toString());
                }
                catch (Exception f) {
                    System.out.println (f.toString());
                }
            }
            catch (TooSmallText e) {
                System.out.println (e.toString());
            }
            catch (Exception e) {
                System.out.println (e.toString());
            }
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}