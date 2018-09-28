# Comment Checker

---

## Compile and Run

	$ make
	$ java CommentChecker { path to input file }

---

## Introduction
* A program that takes in a source code file and prints out its comment statistics.
* Supported languages: Java, JavaScript, Python, C, C++, C#.
* Main idea:
	* A simple finite state machine is implemented to perform basic lexcical analysis on the input file.
	* Both intra-line and inter-line states are tracked and maintained, and their state transformations are observed to determine the output.

---

## Testing
* Test runtime: 
	* javac 1.8.0_131
	* java version "1.8.0_131"
* Tested with files written in Java, JavaScript, Python, C, C++ and C#.