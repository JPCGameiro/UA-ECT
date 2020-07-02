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

TARGETS = MinHeapTest FileMerge \
	file10.txt file11.txt file12.txt file20.txt file99.txt

all: $(TARGETS)

MinHeap.o: MinHeap.cpp MinHeap.h

MinHeapTest.o: MinHeapTest.cpp MinHeap.h

MinHeapTest: MinHeapTest.o MinHeap.o

FileReader.o: FileReader.cpp FileReader.h

FileMerge.o: FileMerge.cpp

FileMerge: FileMerge.o MinHeap.o FileReader.o

file%.txt:
	@# Generate a sample file with % sorted lines taken from word list
	cat /etc/dictionaries-common/words | \
	grep -v "'" | tr '[:upper:]' '[:lower:]' | \
	shuf -n $* - | \
	LC_ALL=C sort -o $@

clean:
	rm -f *.o
	rm -f $(TARGETS)


