//
// Tomás Oliveira e Silva, November 2017
//

#include <time.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "common.h"
#include <cuda_runtime.h>

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// program configuration
//

#ifndef SECTOR_SIZE
# define SECTOR_SIZE  512
#endif
#ifndef N_SECTORS
# define N_SECTORS    (1 << 21)  // can go as high as (1 << 21)
#endif

static void modify_sector_cpu_kernel (unsigned int *sector_data, unsigned int sector_number, unsigned int n_sectors,
                                      unsigned int sector_size);
__global__ static void modify_sector_cuda_kernel (unsigned int * __restrict__ sector_data, unsigned int * __restrict__ sector_number,
                                                  unsigned int n_sectors, unsigned int sector_size);
static double get_delta_time(void);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Main program
//

int main (int argc, char **argv)
{
  printf("%s Starting...\n", argv[0]);
  if (sizeof (unsigned int) != (size_t) 4)
     return 1; // fail with prejudice if an integer does not have 4 bytes

  // set up device
  int dev = 0;
  int i;

  cudaDeviceProp deviceProp;
  CHECK (cudaGetDeviceProperties (&deviceProp, dev));
  printf("Using Device %d: %s\n", dev, deviceProp.name);
  CHECK (cudaSetDevice (dev));

  // create memory areas in host and device memory where the disk sectors data and sector numbers will be stored
  size_t sector_data_size;
  size_t sector_number_size;
  unsigned int *host_sector_data, *host_sector_number;
  unsigned int *device_sector_data, *device_sector_number;

  sector_data_size = (size_t) N_SECTORS * (size_t) SECTOR_SIZE;
  sector_number_size = (size_t) N_SECTORS * sizeof (unsigned int);
  if ((sector_data_size + sector_number_size) > (size_t) 1.3e9)
     { fprintf (stderr,"The GTX 480 cannot handle more than 1.5GiB of memory!\n");
       exit (1);
     }
  printf ("Total sector data size: %lu\n", sector_data_size);
  printf ("Total sector numbers data size: %lu\n", sector_number_size);

  host_sector_data = (unsigned int *) malloc (sector_data_size);
  host_sector_number = (unsigned int *) malloc (sector_number_size);
  CHECK (cudaMalloc ((void **) &device_sector_data, sector_data_size));
  CHECK (cudaMalloc ((void **) &device_sector_number, sector_number_size));

  // initialize the host data
  (void) get_delta_time ();
  srand(0xCCE2021);
  for (i = 0; i < (int) (sector_data_size / (int) sizeof(unsigned int)); i++)
    host_sector_data[i] = 108584447u * (unsigned int) i; // "pseudo-random" data (faster than using the rand() function)
  for(i = 0; i < (int) (sector_number_size / (int)sizeof(unsigned int)); i++)
    host_sector_number[i] = (rand () & 0xFFFF) | ((rand () & 0xFFFF) << 16);
  printf ("The initialization of host data took %.3e seconds\n",get_delta_time ());

  // copy the host data to the device memory
  (void) get_delta_time ();
  CHECK (cudaMemcpy (device_sector_data, host_sector_data, sector_data_size, cudaMemcpyHostToDevice));
  CHECK (cudaMemcpy (device_sector_number, host_sector_number, sector_number_size, cudaMemcpyHostToDevice));
  printf ("The transfer of %ld bytes from the host to the device took %.3e seconds\n",
          (long) sector_data_size + (long) sector_number_size, get_delta_time ());

  // run the computational kernel
  // as an example, N_SECTORS threads are launched where each thread deals with one sector
  unsigned int gridDimX,gridDimY,gridDimZ,blockDimX,blockDimY,blockDimZ;
  int n_sectors, sector_size;

  n_sectors = N_SECTORS;
  sector_size = SECTOR_SIZE;
  blockDimX = 1 << 0;                                             // optimize!
  blockDimY = 1 << 0;                                             // optimize!
  blockDimZ = 1 << 0;                                             // do not change!
  gridDimX = 1 << 21;                                             // optimize!
  gridDimY = 1 << 0;                                              // optimize!
  gridDimZ = 1 << 0;                                              // do not change!

  dim3 grid (gridDimX, gridDimY, gridDimZ);
  dim3 block (blockDimX, blockDimY, blockDimZ);

  if ((gridDimX * gridDimY * gridDimZ * blockDimX * blockDimY * blockDimZ) != n_sectors)
     { printf ("Wrong configuration!\n");
       return 1;
     }
  (void) get_delta_time ();
  modify_sector_cuda_kernel <<<grid, block>>> (device_sector_data, device_sector_number, n_sectors, sector_size);
  CHECK (cudaDeviceSynchronize ());                            // wait for kernel to finish
  CHECK (cudaGetLastError ());                                 // check for kernel errors
  printf("The CUDA kernel <<<(%d,%d,%d), (%d,%d,%d)>>> took %.3e seconds to run\n",
         gridDimX, gridDimY, gridDimZ, blockDimX, blockDimY, blockDimZ, get_delta_time ());

  // copy kernel result back to host side
  unsigned int *modified_device_sector_data;

  modified_device_sector_data = (unsigned int *) malloc (sector_data_size);
  CHECK (cudaMemcpy (modified_device_sector_data, device_sector_data, sector_data_size, cudaMemcpyDeviceToHost));
  printf ("The transfer of %ld bytes from the device to the host took %.3e seconds\n",
          (long) sector_data_size, get_delta_time ());

  // compute the modified sector data on the CPU
  (void) get_delta_time ();
  for (i = 0; i < N_SECTORS; i++)
    modify_sector_cpu_kernel (&host_sector_data[i*SECTOR_SIZE/(sizeof (unsigned int))], host_sector_number[i], n_sectors, sector_size);
  printf("The cpu kernel took %.3e seconds to run (single core)\n",get_delta_time ());

  // compare
  for(i = 0; i < (int) sector_data_size / (int) sizeof (unsigned int); i++)
    if (host_sector_data[i] != modified_device_sector_data[i])
       { int sector_words = sector_size / (int) sizeof (unsigned int);

         printf ("Mismatch in sector %d, word %d\n", i / sector_words, i % sector_words);
         exit(1);
       }
  printf ("All is well!\n");

  // free device global memory
  CHECK (cudaFree (device_sector_data));
  CHECK (cudaFree (device_sector_number));

  // free host memory
  free (host_sector_data);
  free (host_sector_number);
  free (modified_device_sector_data);

  // reset device
  CHECK (cudaDeviceReset ());

  return 0;
}

static void modify_sector_cpu_kernel (unsigned int *sector_data, unsigned int sector_number, unsigned int n_sectors,
                                      unsigned int sector_size)
{
  unsigned int x, i, a, c, n_words;

  // convert the sector size into number of 4-byte words (it is assumed that sizeof(unsigned int) = 4)
  n_words = sector_size / 4u;

  // initialize the linear congruencial pseudo-random number generator
  // (section 3.2.1.2 of The Art of Computer Programming presents the theory behind the restrictions on a and c)
  i = sector_number;                          // get the sector number
  a = 0xCCE00001u ^ ((i & 0x0F0F0F0Fu) << 2); // a must be a multiple of 4 plus 1
  c = 0x00CCE001u ^ ((i & 0xF0F0F0F0u) >> 3); // c must be odd
  x = 0xCCE02021u;                            // initial state

 // modify the sector data
  for (i = 0u; i < n_words; i++)
  { x = a * x + c;                            // update the pseudo-random generator state
    sector_data[i] ^= x;                      // modify the sector data
  }
}

__global__ static void modify_sector_cuda_kernel (unsigned int * __restrict__ sector_data, unsigned int * __restrict__ sector_number,
                                           unsigned int n_sectors, unsigned int sector_size)
{
  unsigned int x, y, idx, i, a, c, n_words;

  // compute the thread number
  x = (unsigned int) threadIdx.x + (unsigned int) blockDim.x * (unsigned int) blockIdx.x;
  y = (unsigned int) threadIdx.y + (unsigned int) blockDim.y * (unsigned int) blockIdx.y;
  idx = (unsigned int) blockDim.x * (unsigned int) gridDim.x * y + x;
  if (idx >= n_sectors)
     return;                                             // safety precaution

  // convert the sector size into number of 4-byte words (it is assumed that sizeof(unsigned int) = 4)
  // and define boundaries
  n_words = sector_size / 4u;

  // adjust pointers
  sector_data += n_words * idx;
  sector_number += idx;

  // initialize the linear congruencial pseudo-random number generator
  // (section 3.2.1.2 of The Art of Computer Programming presents the theory behind the restrictions on a and c)
  i = sector_number[0];                                  // get the sector number
  a = 0xCCE00001u ^ ((i & 0x0F0F0F0Fu) << 2);            // a must be a multiple of 4 plus 1
  c = 0x00CCE001u ^ ((i & 0xF0F0F0F0u) >> 3);            // c must be odd
  x = 0xCCE02021u;                                       // initial state

  // modify the sector data

  for (i = 0u; i < n_words; i++)
  { x = a * x + c;                                       // update the pseudo-random generator state
    sector_data[i] ^= x;
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
