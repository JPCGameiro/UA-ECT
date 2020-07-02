//
// João Manuel Rodrigues, AlgC, May 2020
// Joaquim Madeira, AlgC, May 2020
//
// A FileReader is an object that keeps a buffer of the last read data
// from a file, together with the file pointer and file name.
//
#ifndef _FILEREADER_
#define _FILEREADER_

#include <stdio.h>
#include <errno.h>

typedef struct {
   FILE* file;    // the open file
   char* name;    // the file name
   char* buffer;  // the last buffer read from the file
   int error;     // the error code of the last operation 0=OK, otherwise errno
} FileReader;

FileReader* FileReaderOpen(char* fname) ;

void FileReaderClose(FileReader* fr) ;

int FileReaderNextLine(FileReader* fr) ;

int FileReaderError(FileReader* fr) ;

char* FileReaderBuffer(FileReader* fr) ;

#endif
