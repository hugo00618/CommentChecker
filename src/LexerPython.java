public class LexerPython extends Lexer {

    @Override
    public void scan(String line, CodeParser.Result res) {
        line = line.trim();

        boolean isComment = false;
        boolean isSingleLineComment = false;
        boolean isBlockLineComment = false;
        int numToDo = 0;

        intraLineState = IntraLineState.start;

        while (!line.isEmpty()) {
            switch (intraLineState) {
                case start:
                case code:
                    if (line.startsWith("#")) {
                        isComment = true;

                        // if last line is also a comment, then we are in a block comment
                        if (interLineState == InterLineState.inlineComment ||
                        interLineState == InterLineState.blockComment) {
                            isBlockLineComment = true;
                            intraLineState = IntraLineState.blockComment;
                        } else {
                            isSingleLineComment = true;
                            intraLineState = IntraLineState.inlineComment;
                        }

                        line = line.substring(1);
                    } else if (line.startsWith("\"")) {
                        intraLineState = IntraLineState.quote;
                        line = line.substring(1);
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

                    // find "to do"
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

                    // find "to do"
                    todoIdx = line.toUpperCase().indexOf("TODO");
                    if (todoIdx != -1) {
                        numToDo++;
                        line = line.substring(todoIdx + 4);
                    } else {
                        line = "";
                    }
                    break;
            }
            line = line.trim();
        }

        // if last line is an inline comment and current line is block comment,
        // then we have found a new block comment. Needs to update comment type stats
        if (interLineState == InterLineState.inlineComment &&
        intraLineState == IntraLineState.blockComment) {
            res.numSingleLineComment--;
            res.numBlockLineComment++;
            res.numBlockComment++;
        }

        res.numLine++;
        if (isComment) res.numCommentLine++;
        if (isSingleLineComment) res.numSingleLineComment++;
        if (isBlockLineComment) res.numBlockLineComment++;
        res.numTodo += numToDo;

        if (intraLineState == IntraLineState.blockComment) {
            interLineState = InterLineState.blockComment;
        } else if (intraLineState == IntraLineState.inlineComment) {
            interLineState = InterLineState.inlineComment;
        } else {
            interLineState = InterLineState.code;
        }
    }

}
