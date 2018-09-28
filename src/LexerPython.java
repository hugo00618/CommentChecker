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
                    if (line.startsWith("#")) { // whole line comment
                        // if last line is a block comment or also a whole line comment,
                        // then we are in a block comment
                        isComment = true;
                        if (interLineState == InterLineState.wholeLineComment ||
                                interLineState == InterLineState.blockComment) {
                            isBlockLineComment = true;
                            intraLineState = IntraLineState.blockComment;
                        } else {
                            isSingleLineComment = true;
                            intraLineState = IntraLineState.wholeLineComment;
                        }
                        line = line.substring(1);
                    } else {
                        intraLineState = IntraLineState.code;
                        line = line.substring(1);
                    }
                    break;
                case code:
                    if (line.startsWith("#")) {
                        isComment = true;
                        isSingleLineComment = true;
                        intraLineState = IntraLineState.inlineComment;
                        line = line.substring(1);
                    } else if (line.startsWith("\'")) {
                        intraLineState = IntraLineState.singleQuote;
                        line = line.substring(1);
                    } else if (line.startsWith("\"")) {
                        intraLineState = IntraLineState.doubleQuote;
                        line = line.substring(1);
                    } else {
                        intraLineState = IntraLineState.code;
                        line = line.substring(1);
                    }
                    break;
                case singleQuote:
                    // ignore single quotes as char content
                    if (line.startsWith("\\\'")) {
                        line = line.substring(2);
                    } else if (line.startsWith("\'")) {
                        intraLineState = IntraLineState.code;
                        line = line.substring(1);
                    } else {
                        line = line.substring(1);
                    }
                    break;
                case doubleQuote:
                    // ignore double quotes as string content
                    if (line.startsWith("\\\"")) {
                        line = line.substring(2);
                    } else if (line.startsWith("\"")) {
                        intraLineState = IntraLineState.code;
                        line = line.substring(1);
                    } else {
                        line = line.substring(1);
                    }
                    break;
                case inlineComment:
                case wholeLineComment:
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

        // if last line is a whole line comment and current line is block comment,
        // then we have found a new block comment. Needs to update comment type stats
        if (interLineState == InterLineState.wholeLineComment &&
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
        } else if (intraLineState == IntraLineState.wholeLineComment) {
            interLineState = InterLineState.wholeLineComment;
        } else {
            interLineState = InterLineState.code;
        }
    }

}
