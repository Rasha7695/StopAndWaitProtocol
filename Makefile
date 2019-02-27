JFLAGS = -g
JC = javac
SRC = ./src/Main
OUT = ./out
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
        Sender.java \
        Receiver.java
all: classes clean
classes: $(CLASSES:.java=.class)
.PHONY: clean
clean:
	$(RM) *.class
