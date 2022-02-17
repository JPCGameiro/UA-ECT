#include <time.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include "common.h"
#include <cuda_runtime.h>

static double RandomReal(double low, double high); //generate double between -0,5 and 0.5
__global__ static void prod_matrices_gpu(double *a, double *b, double  *c, int N);
static void prod_matrices_cpu(double *h_a, double *h_b, double *h_result, int N);
static double get_delta_time(void); //medir tempos
static void generateMatrix(double *m, int size);

int main(int argc, char **argv) {

    // set up device
    int dev = 0;

    cudaDeviceProp deviceProp;
    CHECK (cudaGetDeviceProperties (&deviceProp, dev));
    printf("Using Device %d: %s\n", dev, deviceProp.name);
    CHECK (cudaSetDevice (dev)); // a gpu que vou utilizar


    int n = 1024; // degree of a square matrix

    // host space
    int nElem = n * n; // number of elements of a square matrix
    int nBytes = nElem * sizeof(double); // matrix storage space in bytes
    double *h_a, *h_b, *h_c, *h_cc; // h_c result of multiplication of matrix a to b in CPU
                                    // h_cc result of multiplication of matrix a to b in GPU
    h_a = (double *)malloc(nBytes);
    h_b = (double *)malloc(nBytes);
    h_c = (double *)malloc(nBytes);
    h_cc = (double *)malloc(nBytes);

    //generate matrix for a and b
    (void) get_delta_time ();
    generateMatrix(h_a,nElem);
    generateMatrix(h_b,nElem);
    printf("Matrix a and b generated on %.3e seconds\n", get_delta_time());

    //reserve memory for gpu
    double *d_a, *d_b, *d_c;
    CHECK(cudaMalloc((void **)&d_a, nBytes));
    CHECK(cudaMalloc((void **)&d_b, nBytes));
    CHECK(cudaMalloc((void **)&d_c, nBytes));

    (void) get_delta_time ();
    //copy matrix to gpu
    CHECK(cudaMemcpy(d_a, h_a, nBytes, cudaMemcpyHostToDevice));
    CHECK(cudaMemcpy(d_b, h_b, nBytes, cudaMemcpyHostToDevice));
    printf ("The transfer of %d bytes from the host to the device took %.3e seconds\n",
            2  * nBytes, get_delta_time ());

    // run the computational kernel
    // as an example, nElem threads are launched where each thread deals with one multiplication point
    unsigned int gridDimX,gridDimY,gridDimZ,blockDimX,blockDimY,blockDimZ;

    blockDimX = 1;                                             // optimize! // 1 thread
    blockDimY = 1;                                             // optimize!
    blockDimZ = 1;                                             // do not change! // sempre 1
    gridDimX = nElem;                                          // optimize!
    gridDimY = 1;                                              // optimize!
    gridDimZ = 1;                                              // do not change! // sempre 1

    dim3 grid (gridDimX, gridDimY, gridDimZ);
    dim3 block (blockDimX, blockDimY, blockDimZ);

    if ((gridDimX * gridDimY * gridDimZ * blockDimX * blockDimY * blockDimZ) != nElem)
    { printf ("Wrong configuration!\n");
        return 1;
    }

    (void) get_delta_time ();
    prod_matrices_gpu <<<grid, block>>> (d_a, d_b, d_c, n);
    CHECK (cudaDeviceSynchronize ());                            // wait for kernel to finish - aguarda que o gpu acabe de executar
    CHECK (cudaGetLastError ());                                 // check for kernel errors // por sempre
    printf("The CUDA kernel <<<(%d,%d,%d), (%d,%d,%d)>>> took %.3e seconds to run\n",
           gridDimX, gridDimY, gridDimZ, blockDimX, blockDimY, blockDimZ, get_delta_time ());

    // copy kernel result back to host side
    CHECK (cudaMemcpy (h_cc, d_c, nBytes, cudaMemcpyDeviceToHost));
    printf ("The transfer of %d bytes from the device to the host took %.3e seconds\n",
            nBytes, get_delta_time ());

    // free device global memory
    CHECK (cudaFree (d_a)); //gpu
    CHECK (cudaFree (d_b));
    CHECK (cudaFree (d_c)); //gpu

    // reset device
    CHECK (cudaDeviceReset ());

    (void) get_delta_time ();
    prod_matrices_cpu(h_a, h_b, h_c, n);
    printf("The cpu kernel took %.3e seconds to run (single core)\n",get_delta_time ());

    // compare
    for(int i = 0; i < nElem; i++)
      if (((fabs (h_c[i]) <= 1.0e-6) && fabs (h_c[i] - h_cc[i]) > 1.0e-6) || (((fabs (h_c[i]) > 1.0e-6) && fabs ((h_c[i] - h_cc[i]) / h_c[i]) > 1.0e-6)))
         { printf ("Mismatch in element (%d, %d): %.6e(GPU) - %.6e(CPU)\n", i / n, i % n, h_cc[i], h_c[i]);
           exit(1);
         }
    printf ("All is well!\n");

    // free host memory
    free (h_a); //cpu
    free (h_b);
    free (h_c);
    free (h_cc);

    return 0;

}

static double RandomReal(double low, double high){
    double d;

    d = (double) rand() / ((double) RAND_MAX + 1);
    return (low + d * (high - low));
}

static void generateMatrix(double *m, int size){
    for (int j = 0; j < size; j++) {
        m[j] = (double) RandomReal(-0.5, 0.5);
    }
}


__global__ static void prod_matrices_gpu(double *a, double *b, double  *c, int N) {

    int x = threadIdx.x + blockDim.x * blockIdx.x;
    int y = threadIdx.y + blockDim.y * blockIdx.y;
    int idx = gridDim.x * blockDim.x * y + x;
    int row = idx / N;
    int col = idx % N;
    double sum = 0.0;
    if ((row < N) && (col < N)) {
        for (int i = 0; i < N; i++) {
            sum += a[row * N + i] * b[i * N + col];
        }
        c[row * N + col] = sum;
    }
}


static void prod_matrices_cpu(double *h_a, double *h_b, double *h_result, int N) {
    for (int i = 0; i < N ;++i){
        for (int j = 0; j <N; ++j){
            double sum = 0.0;
            for (int h = 0; h < N; ++h){
                sum += h_a[i * N + h] * h_b[h * N + j];
            }
            h_result[i * N + j] = sum;
        }
    }
}


static double get_delta_time(void){
    static struct timespec t0,t1;

    t0 = t1;
    if(clock_gettime(CLOCK_MONOTONIC,&t1) != 0)
    {
        perror("clock_gettime");
        exit(1);
    }
    return (double)(t1.tv_sec - t0.tv_sec) + 1.0e-9 * (double)(t1.tv_nsec - t0.tv_nsec);
}
