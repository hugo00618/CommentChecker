# Comment Checker

---

## Compile and Run

~~~
$ make
$ java CommentChecker { path to input file }
~~~

---

## Assumptions
* For C-style languages, two block comments in the same line are counted as one **line within block comments** and two **block line comments**.
	* For example, `int i = 0; /* block comment 1 */ int j = 0; /* block comment 2 */`
		* adds 1 to the **total # of comment lines**
		* adds 1 to the **total # of comment lines within block comments**
		* adds 2 to the **total # of block line comments**
* Similar rule applies when we have block comments and an inline comment in the same line.
	* For example, `int i = 0; /* block comment */ int j = 0; // inline comment`
		* adds 1 to the **total # of comment lines**
		* adds 1 to the **total # of single line comments**
		* adds 1 to the **total # of comment lines within block comments**
		* adds 1 to the **total # of block line comments**

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