# To compile all programs, run:
#   make
#
# Note that source files have .cpp extensions,
# for compatibility with codecheck, but they are actually pure C.
# 
# JMR 2020

#CC = gcc
#CXX = $(CC)

# Options for the C compiler. (-x c forces C language even for .ccp files)
CFLAGS += -x c -g -Wall
CPPFLAGS = $(CFLAGS)

TARGETS = SortedListTest1 SortedListTest2 TAD3Test

all: $(TARGETS)

Time.o: Time.cpp Time.h

Date.o: Date.cpp Date.h

DateTime.o: DateTime.cpp DateTime.h

TimeInterval.o: TimeInterval.cpp TimeInterval.h DateTime.h Date.h Time.h

SortedList.o: SortedList.cpp SortedList.h

SortedListTest1.o: SortedListTest1.cpp SortedList.h

SortedListTest1: SortedListTest1.o SortedList.o

SortedListTest2.o: SortedListTest2.cpp Date.h SortedList.h

SortedListTest2: SortedListTest2.o SortedList.o Date.o

SchedulingSequence.o: SchedulingSequence.cpp SchedulingSequence.h \
            TimeInterval.h DateTime.h Date.h Time.h SortedList.h

TAD3Test.o: TAD3Test.cpp TimeInterval.h DateTime.h Date.h Time.h \
            SchedulingSequence.h

TAD3Test: TAD3Test.o TimeInterval.o DateTime.o Date.o Time.o \
            SchedulingSequence.o SortedList.o

clean:
	rm -f *.o
	rm -f $(TARGETS)


