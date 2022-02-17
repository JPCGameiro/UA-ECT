//
// Tomás Oliveira e Silva, November 2017
//

#include <time.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// program configuration
//

#ifndef SECTOR_SIZE
# define SECTOR_SIZE  512
#endif
#ifndef N_SECTORS
# define N_SECTORS    (1 << 21)
#endif

static void modify_sector_cpu_kernel (unsigned int *sector_data, unsigned int sector_number,
                                      unsigned int sector_size, unsigned int stride);
static double get_delta_time(void);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Main program
//

int main (int argc, char **argv)
{
  printf("%s Starting...\n", argv[0]);
  if (sizeof (unsigned int) != (size_t) 4)
     return 1; // fail with prejudice if an integer does not have 4 bytes

  // create memory areas in host memory where the disk sectors data and sector numbers will be stored
  unsigned int sector_data_size;
  unsigned int sector_number_size;
  unsigned int *host_sector_data, *host_sector_number;

  sector_data_size = (unsigned int) N_SECTORS * (unsigned int) SECTOR_SIZE;
  sector_number_size = (unsigned int) N_SECTORS * sizeof (unsigned int);
  printf ("Total sector data size: %u\n", sector_data_size);
  printf ("Total sector numbers data size: %u\n", sector_number_size);

  host_sector_data = (unsigned int *) malloc (sector_data_size);
  host_sector_number = (unsigned int *) malloc (sector_number_size);

  // initialize the host data
  int i;

  (void) get_delta_time ();
  srand(0xACA2019);
  for (i = 0; i < (int) (sector_data_size / (int) sizeof(unsigned int)); i++)
    host_sector_data[i] = 108584447u * (unsigned int) i;       // "pseudo-random" data (faster than using the rand() function)
  for(i = 0; i < (int) (sector_number_size / (int)sizeof(unsigned int)); i++)
    host_sector_number[i] = (rand () & 0xFFFF) | ((rand () & 0xFFFF) << 16);
  printf ("The initialization of host data took %.3e seconds\n",get_delta_time ());

  // compute the modified sector data on the CPU
  (void) get_delta_time ();
  for (i = 0; i < N_SECTORS; i++)
    modify_sector_cpu_kernel (&host_sector_data[i], host_sector_number[i], SECTOR_SIZE, N_SECTORS);
  printf("The cpu kernel took %.3e seconds to run (single core)\n",get_delta_time ());

  // free host memory
  free (host_sector_data);
  free (host_sector_number);

  return 0;
}

static void modify_sector_cpu_kernel (unsigned int *sector_data, unsigned int sector_number,
                                      unsigned int sector_size, unsigned int stride)
{
  unsigned int x, i, a, c, n_words;

  // convert the sector size into number of 4-byte words (it is assumed that sizeof(unsigned int) = 4)
  n_words = sector_size / 4u;

  // initialize the linear congruence pseudo-random number generator
  // (section 3.2.1.2 of The Art of Computer Programming presents the theory behind the restrictions on a and c)
  i = sector_number;                          // get the sector number
  a = 0xACA00001u ^ ((i & 0x0F0F0F0Fu) << 2); // a must be a multiple of 4 plus 1
  c = 0x00ACA001u ^ ((i & 0xF0F0F0F0u) >> 3); // c must be odd
  x = 0xACA02019u;                            // initial state

 // modify the sector data
  for (i = 0u; i < n_words; i++)
  { x = a * x + c;                            // update the pseudo-random generator state
    sector_data[stride * i] ^= x;                      // modify the sector data
  }
}

static double get_delta_time(void)
{
  static struct timespec t0,t1;

  t0 = t1;
  if(clock_gettime(CLOCK_MONOTONIC,&t1) != 0)
  {
    perror("clock_gettime");
    exit(1);
  }
  return (double)(t1.tv_sec - t0.tv_sec) + 1.0e-9 * (double)(t1.tv_nsec - t0.tv_nsec);
}
