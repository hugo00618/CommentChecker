public abstract class Lexer {

    public enum IntraLineState {
        start, code, quote, inlineComment, blockComment;
    }

    public enum InterLineState {
        start, code, blockComment;
    }

    IntraLineState intraLineState = IntraLineState.start;
    InterLineState interLineState = InterLineState.start;

    /**
     * scan each line
     * @param line
     * @param res
     */
    public abstract void scan(String line, CodeParser.Result res);
}
