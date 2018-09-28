import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CommentChecker {

    public static void main(String[] args) {

        // check input.js arguments
        if (args.length != 1) {
            System.err.println("Usage: java CommentChecker [ filename ]");
            return;
        }

        String filename = args[0];

        // open file
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(args[0]));
        } catch (FileNotFoundException e) {
            System.err.println("File not found for file name: " + filename);
            return;
        }

        // ignore file names start with . and file names that don't have extension
        if (filename.startsWith(".") || !filename.contains(".")) {
            System.out.println("Ignored file: " + filename);
            return;
        }

        // check extension and construct corresponding parser object
        Lexer sc = null;
        String extension = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
        switch (extension) {
            case "c":
            case "cc":
            case "cpp":
            case "cs":
            case "h":
            case "hpp":
            case "java":
            case "js":
                sc = new LexerJava();
                break;
            case "py":
                sc = new LexerPython();
            default:
                break;
        }

        // do parsing if applicable
        if (sc != null) {
            try {
                CodeParser.Result res = CodeParser.newParse(br, sc);

                System.out.println("Total # of lines: " + res.numLine);
                System.out.println("Total # of comment lines: " + res.numCommentLine);
                System.out.println("Total # of single line comments: " + res.numSingleLineComment);
                System.out.println("Total # of comment lines within block comments: " + res.numBlockLineComment);
                System.out.println("Total # of block line comments: " + res.numBlockComment);
                System.out.println("Total # of TODO’s: " + res.numTodo);
            } catch (IOException e) {

            }
        } else {
            System.out.println("File not supported: " + filename);
            return;
        }

    }
}
