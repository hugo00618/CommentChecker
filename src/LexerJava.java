public class LexerJava extends Lexer {

    @Override
    public void scan(String line, CodeParser.Result res) {
        line = line.trim();

        boolean isComment = false;
        boolean isSingleLineComment = false;
        boolean isBlockLineComment = false;
        boolean isStartOfBlockComment = false;
        int numToDo = 0;

        if (interLineState == InterLineState.blockComment) {
            intraLineState = IntraLineState.blockComment;
        } else {
            intraLineState = IntraLineState.start;
        }

        while (!line.isEmpty()) {
            switch (intraLineState) {
                case start:
                case code:
                    if (line.startsWith("//")) {
                        intraLineState = IntraLineState.inlineComment;
                        isComment = true;
                        isSingleLineComment = true;
                        line = line.substring(2);
                    } else if (line.startsWith("/*")) {
                        intraLineState = IntraLineState.blockComment;
                        isComment = true;
                        isBlockLineComment = true;
                        isStartOfBlockComment = true;
                        line = line.substring(2);
                    } else if (line.startsWith("*/")) {
                        intraLineState = IntraLineState.code;
                        isComment = true;
                        isBlockLineComment = true;
                        line = line.substring(2);
                    } else if (line.startsWith("\"")) {
                        intraLineState = IntraLineState.quote;
                    } else {
                        intraLineState = IntraLineState.code;
                        line = line.substring(1);
                    }
                    break;
                case quote:
                    if (line.startsWith("\"")) {
                        intraLineState = IntraLineState.code;
                    }
                    line = line.substring(1);
                    break;
                case inlineComment:
                    isComment = true;
                    isSingleLineComment = true;
                    int todoIdx = line.toUpperCase().indexOf("TODO");
                    if (todoIdx != -1) {
                        numToDo++;
                        line = line.substring(todoIdx + 4);
                    } else {
                        line = "";
                    }
                    break;
                case blockComment:
                    isComment = true;
                    isBlockLineComment = true;
                    int endOfBlockIdx = line.indexOf("*/");
                    todoIdx = line.toUpperCase().indexOf("TODO");
                    if (endOfBlockIdx != -1) {
                        // if there is a to do within block comment
                        if (todoIdx != -1 && todoIdx < endOfBlockIdx) {
                            numToDo++;
                            line = line.substring(todoIdx + 4);
                        } else {
                            intraLineState = IntraLineState.code;
                            line = line.substring(endOfBlockIdx);
                        }
                    } else {
                        if (todoIdx != -1) {
                            numToDo++;
                            line = line.substring(todoIdx + 4);
                        } else {
                            line = "";
                        }
                    }
            }
            line = line.trim();
        }

        if (intraLineState == IntraLineState.blockComment) {
            interLineState = InterLineState.blockComment;
        } else {
            interLineState = InterLineState.code;
        }

        res.numLine++;
        if (isComment) res.numCommentLine++;
        if (isSingleLineComment) res.numSingleLineComment++;
        if (isBlockLineComment) res.numBlockLineComment++;
        if (isStartOfBlockComment) res.numBlockComment++;
        res.numTodo += numToDo;
    }
}
