# To compile all programs, run:
#   make
#
# Note that source files have .cpp extensions,
# for compatibility with codecheck, but they are actually pure C.
# 
# João Manuel Rodrigues, May 2020

#CC = gcc
#CXX = $(CC)

# Options for the C compiler. (-x c forces C language even for .ccp files)
CFLAGS += -x c -g -Wall
CPPFLAGS = $(CFLAGS)

TARGETS = BSTreeTest TAD4Test

all: $(TARGETS)

Time.o: Time.cpp Time.h

Date.o: Date.cpp Date.h

DateTime.o: DateTime.cpp DateTime.h

TimeInterval.o: TimeInterval.cpp TimeInterval.h DateTime.h Date.h Time.h

BSTree.o: BSTree.cpp BSTree.h

BSTreeTest.o: BSTreeTest.cpp BSTree.h

BSTreeTest: BSTreeTest.o BSTree.o

SchedulingSequence.o: SchedulingSequence.cpp SchedulingSequence.h \
            TimeInterval.h DateTime.h Date.h Time.h BSTree.h

TAD4Test.o: TAD4Test.cpp TimeInterval.h DateTime.h Date.h Time.h \
            SchedulingSequence.h

TAD4Test: TAD4Test.o TimeInterval.o DateTime.o Date.o Time.o \
            SchedulingSequence.o BSTree.o

clean:
	rm -f *.o
	rm -f $(TARGETS)


