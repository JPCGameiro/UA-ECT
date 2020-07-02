// NMEC: 93097
// NOME: João Gameiro
//
// João Manuel Rodrigues, AlgC, May 2020
// Joaquim Madeira, AlgC, May 2020
//
// TESTING the TAD MinHeap implementation
//

// This program merges several sorted files into a sorted output.

//// PROCURE ... E COMPLETE ////

//ARGS file10.txt file20.txt
//ARGS file10.txt file11.txt file12.txt
//ARGS file10.txt file11.txt file12.txt file20.txt file99.txt

#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "MinHeap.h"
#include "FileReader.h"

// THE ALGORITHM:
//
// Read one line from each file and insert it into a heap.  Then:
//
// 1. Extract one line from the heap and output it.
// 2. Get next line from the same file and insert into the heap.
// 3. Repeat from 1.
//
// Each heap item must store the current line but also the corresponding file.
// That is why the items are stored as FileReader objects.

// The comparator for FileReaders (compares the last lines read from each)
int comparator(const void* p1, const void* p2) {
   FileReader* f1 = (FileReader*)p1;
   FileReader* f2 = (FileReader*)p2;
   return strcmp(f1->buffer, f2->buffer);
}

// The printer for FileReader: print the last line (and its file serial number)
void printer(void* p) {
   FileReader* fr = (FileReader*)p;
   printf("%s ", fr->buffer);
}


int main(int argc, char* argv[]) {
   
   // Number of files in arguments:
   int numFiles = argc-1;
   
   // Create heap with capacity for one line buffer per file:
   MinHeap* H = MinHeapCreate(numFiles, comparator, printer);
   if (H == NULL) abort();
   
   for (int i = 0; i < numFiles; i++) {
      // Open a FileReader for each argument:
      FileReader* fr = FileReaderOpen(argv[i+1]);
      if (FileReaderError(fr)) { perror(fr->name); exit(1); }
      // Insert this file reader object into the Heap:
      MinHeapInsert(H, fr);
   }

   while (numFiles > 0) {
      FileReader* fr;
      
      // 1a. Get the first file reader from the Heap and remove it:
      fr = (FileReader *)MinHeapGetMin(H);
      MinHeapRemoveMin(H);
      
      
      // 1b. Output the current line from that reader:
      char* line = FileReaderBuffer(fr);
      fputs(line, stdout);             // output the line
      free(line);                      // and free the buffer
      
      // 2a. Advance the reader to the next line:
      int ok = FileReaderNextLine(fr);
      
      if (ok) {         // On success:
         // 2b: Reinsert file reader into the Heap:
         MinHeapInsert(H, fr);
      } else {          // On EOF or Error:
         // 2c: Close the file reader:
         FileReaderClose(fr);
         numFiles--;    // Decrease number of open files
         if (FileReaderError(fr)) { perror(fr->name); exit(2); }
      }
   }
   
   MinHeapDestroy(&H);
}
