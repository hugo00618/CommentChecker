public class LexerPython extends Lexer {
    @Override
    public void scan(String line, CodeParser.Result res) {
//        return null;
    }

    @Override
    public String commentPart(String line) {
        String pattern = "(.*?)([^\"]?)((//|/[*]).*)";
        return null;
    }

    @Override
    public boolean isSingleLineComment(String comment) {
        return false;
    }

    @Override
    public boolean isBlockLineComment(String comment) {
        return false;
    }

    @Override
    public boolean isContainStartOfBlockComment(String comment) {
        return false;
    }

    @Override
    public boolean isContainEndOfBlockComment(String comment) {
        return false;
    }

    @Override
    public boolean isTODO(String comment) {
        return false;
    }
}
