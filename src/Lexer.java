public abstract class Lexer {

    public enum IntraLineState {
        start, code, quote, inlineComment, blockComment;
    }

    public enum InterLineState {
        start, code, blockComment;
    }

    IntraLineState intraLineState = IntraLineState.start;
    InterLineState interLineState = InterLineState.start;

    public abstract void scan(String line, CodeParser.Result res);

    /**
     *
     * @param line
     * @return the comment part if line contains comment or null if not found
     */
    public abstract String commentPart(String line);

    /**
     *
     * @param comment a string that starts with "//", "/*" or "*"
     * @return checks if line is a single line comment (starts with "//")
     */
    public abstract boolean isSingleLineComment(String comment);

    /**
     * this method assumes that line is within a block comment,
     * that is, the start of block comment is already seen before or within line
     * @param comment
     * @return checks if line is a block comment
     */
    public abstract boolean isBlockLineComment(String comment);

    /**
     *
     * @param comment
     * @return checks if line contains the start of block comment
     */
    public abstract boolean isContainStartOfBlockComment(String comment);

    /**
     *
     * @param comment
     * @return index of end of block comment (* /)
     */
    public abstract boolean isContainEndOfBlockComment(String comment);

    /**
     *
     * @param comment
     * @return checks if line contains a TODO
     */
    public abstract boolean isTODO(String comment);
}
