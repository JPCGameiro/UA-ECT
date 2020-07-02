//
// João Manuel Rodrigues, AlgC, May 2020
// Joaquim Madeira, AlgC, May 2020
//
// A FileReader is an object that keeps a buffer of the last read data
// from a file, together with the file pointer and file name.
//

#include <stdlib.h>
#include "FileReader.h"

FileReader* FileReaderOpen(char* fname) {
   FileReader* fr = (FileReader*)malloc(sizeof(*fr));
   if (fr == NULL) abort();
   fr->name = fname;
   fr->buffer = (char*)calloc(1, sizeof(char));   // alloc 1 empty string
   if (fr->buffer == NULL) abort();
   fr->error = 0;
   fr->file = fopen(fname, "r");
   if (fr->file == NULL) {
      fr->error = errno;
      return NULL;
   }
   return fr;
}

void FileReaderClose(FileReader* fr) {
   if (fclose(fr->file) != 0)
      fr->error = errno;
   free(fr);
}

// Advance file reader to the next line, which is stored in a new buffer.
// Return 1 on success, 0 on EOF or Error.
// NOTE: the client is responsible for freeing the allocated buffer.
int FileReaderNextLine(FileReader* fr) {
   char** pstr = &(fr->buffer);   // pointer to the buffer field
   size_t len = 0;
   // Read a line from file and store in allocated memory (see man getline)
   ssize_t read = getline(pstr, &len, fr->file);
   if (read < (ssize_t)0) {
      fr->error = errno;
      return 0;
   }
   return 1;
}

// Report the error number (errno) from last failed FileReader operation.
// 0 means No Error.
int FileReaderError(FileReader* fr) {
   return fr->error;
}

// Return the pointer to the read buffer.
char* FileReaderBuffer(FileReader* fr) {
   return fr->buffer;
}
