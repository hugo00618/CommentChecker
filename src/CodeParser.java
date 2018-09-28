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

    public static Result parse(BufferedReader br, Lexer lexer) throws IOException {
        Result res = new Result(0,0,0,
                0,0,0);
        String line;
        while ((line = br.readLine()) != null) {
            lexer.scan(line, res);
        }

        return res;
    }

}
