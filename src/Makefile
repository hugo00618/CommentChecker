JFLAGS = 
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
        CodeParser.java \
        CommentChecker.java \
        Lexer.java \
        LexerJava.java \
        LexerPython.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class