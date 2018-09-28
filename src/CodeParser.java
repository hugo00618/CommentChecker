import java.io.BufferedReader;
import java.io.IOException;

public class CodeParser {

    static class Result {
        int numLine;
        int numCommentLine;
        int numSingleLineComment;
        int numBlockLineComment;
        int numBlockComment;
        int numTodo;

        public Result(int numLine, int numCommentLine, int numSingleLineComment,
                      int numBlockLineComment, int numBlockComment, int numTodo) {
            this.numLine = numLine;
            this.numCommentLine = numCommentLine;
            this.numSingleLineComment = numSingleLineComment;
            this.numBlockLineComment = numBlockLineComment;
            this.numBlockComment = numBlockComment;
            this.numTodo = numTodo;
        }
    }

    public static Result parse(BufferedReader br, Lexer sc) throws IOException {
        int numLine = 0;
        int numCommentLine = 0;
        int numSingleLineComment = 0;
        int numBlockLineComment = 0;
        int numBlockComment = 0;
        int numTodo = 0;

        boolean withinBlockComment = false; // indicates if we are currently in a block comment

        String line = null;
        String remain = null; // remaining part of last line
        while (remain != null || (line = br.readLine()) != null) {
            line = line.trim(); // remove leading and trailing spaces
            numLine++;

            // match comment within line
            // if withinBlockComment, then the entire line is comment
            String matchedComment = withinBlockComment ? line : sc.commentPart(line);
            if (matchedComment != null) {
                numCommentLine++;

                // distinguish between single line comment and block comment
                if (sc.isSingleLineComment(matchedComment)) { // single line
                    numSingleLineComment++;
                } else { // block comment
                    if (sc.isContainStartOfBlockComment(matchedComment)) {
                        withinBlockComment = true;
                        numBlockComment++;
                    }

                    // line within a block comment is a block line comment
                    if (withinBlockComment) {
                        numBlockLineComment++;
                    }

                    if (sc.isContainEndOfBlockComment(matchedComment)) {
                        withinBlockComment = false;
                    }
                }

                // check if it contains todo
                if (sc.isTODO(matchedComment)) {
                    numTodo++;
                }
            }
        }

        return new Result(numLine, numCommentLine, numSingleLineComment,
                numBlockLineComment, numBlockComment, numTodo);
    }

    public static Result newParse(BufferedReader br, Lexer lexer) throws IOException {
        Result res = new Result(0,0,0,
                0,0,0);
        String line;
        while ((line = br.readLine()) != null) {
            lexer.scan(line, res);
        }

        return res;
    }

}
