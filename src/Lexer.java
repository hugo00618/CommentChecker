public abstract class Lexer {

    // tracks the state within a line
    public enum IntraLineState {
        start, code, singleQuote, doubleQuote, inlineComment, blockComment;
    }

    // tracks the state between lines
    public enum InterLineState {
        start, code, inlineComment, blockComment;
    }

    IntraLineState intraLineState = IntraLineState.start;
    InterLineState interLineState = InterLineState.start;

    /**
     * scans the line and updates comment stats
     * @param line
     * @param res
     */
    public abstract void scan(String line, CodeParser.Result res);
}
