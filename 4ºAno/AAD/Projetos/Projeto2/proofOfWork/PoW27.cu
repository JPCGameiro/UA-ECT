//
// Tomás Oliveira e Silva
//

#ifndef _use_cuda_
# define _use_cuda_  1
#endif

#include <time.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#if _use_cuda_ != 0
#include <cuda.h>
#endif

#include "cMD5.h"

__global__ static void cMD5_cuda(unsigned int * __restrict__ tokens);

//
// measure the amount of time used so far
//

static double real_time(void)
{
  struct timespec t;

  if(clock_gettime(CLOCK_REALTIME,&t) != 0) // to measure CPU time only, use CLOCK_PROCESS_CPUTIME_ID instead of CLOCK_REALTIME
    return -1.0; // clock_gettime() failed so we return an illegal value
  return (double)t.tv_sec + 1.0e-9 * (double)t.tv_nsec;
}

//
// predefined token data (there are exactly 32 tokens)
//

static struct
{
  unsigned int token[14];      // a "random" token
  unsigned int first_solution; // the smallest good value of n_0
}
token_data[32] =
{
  { // token  0 ( 6 rounds)
    {
      0x51D30262u,0x719173EDu,0xEF43D22Du,0x8CC5710Fu,0x527CDDE0u,0xA83834DCu,0x6BA6EC2Cu,
      0xCF11E082u,0x305F69C0u,0xB9AB6DD3u,0x9D378435u,0xD114633Au,0xB5C4FCD9u,0x330476C4u
    },
    0x0029840Bu
  },
  { // token  1 (18 rounds)
    {
      0x75B416CEu,0xBCDACD17u,0x0A859CB7u,0x103AB7EEu,0xAD20DD57u,0x2975EB52u,0x1DE343C2u,
      0x0255E117u,0x61E31F91u,0xDB8D8CEBu,0x67389489u,0x230D1CC4u,0x871E296Fu,0x5C5DEF9Bu
    },
    0x0225A1C1u
  },
  { // token  2 ( 9 rounds)
    {
      0x431442A5u,0xB16AFB4Bu,0x91C925D3u,0x0A36A2C4u,0xB781E9FAu,0x0FEB4DF7u,0x26DBE996u,
      0xB5E8B359u,0x7C61C01Eu,0x282E47B4u,0x5ECB595Fu,0xD6C19F7Cu,0x9D1D9218u,0xD6DD369Eu
    },
    0x0050B4EDu
  },
  { // token  3 ( 7 rounds)
    {
      0x9D100173u,0x8459D49Au,0xE1A2CE0Au,0xF2975D7Cu,0x3EB006CDu,0xA87E40DCu,0x4E5072FEu,
      0xBAB92A63u,0x4AA2BE6Eu,0xF01A4C59u,0xCD31B86Cu,0x4D0524E5u,0xBC5C99F3u,0x799FDBB4u
    },
    0x006BC0B5u
  },
  { // token  4 (12 rounds)
    {
      0xF2AEEC38u,0x0B867BBFu,0x61DD9C8Du,0xAECA6AB4u,0x1F482A3Du,0x712554D1u,0x6C1400FDu,
      0x0394F952u,0x5FD8D7AFu,0x2ABE16D4u,0x642434A0u,0x2BBEF62Cu,0x477D3D29u,0xA0DB9963u
    },
    0x01A18987u
  },
  { // token  5 (14 rounds)
    {
      0x02F1267Au,0xE016A53Du,0x15273346u,0x86AF0971u,0x29247109u,0xC6E671ACu,0x0EF7597Fu,
      0xC5C3A269u,0x8ECF9F9Bu,0x2BBCA6D5u,0xEA441965u,0x6445BECBu,0x9A295769u,0xC51619AFu
    },
    0x008A8E42u
  },
  { // token  6 ( 4 rounds)
    {
      0x30005430u,0xF9D9CE78u,0x25B559C3u,0xECFD12ECu,0x442D7FA8u,0x7477E24Du,0xFB372D72u,
      0x6F0ECF64u,0x16923498u,0x46F69361u,0xAE3D4A43u,0x5467F10Au,0x7443B816u,0xE3C1A9BEu
    },
    0x00775541u
  },
  { // token  7 (14 rounds)
    {
      0x59161F4Au,0xC1977008u,0x105A72D0u,0x8604ADD4u,0x820128EFu,0xF0DDC9D8u,0x1979CBF7u,
      0x8749F2D6u,0x76F43CE4u,0xB82B3D20u,0x39742379u,0x4193DB00u,0x7FF50286u,0x436F3526u
    },
    0x00CDAF11u
  },
  { // token  8 ( 9 rounds)
    {
      0x52004935u,0x5E410AD7u,0xC8249A9Au,0x7FF392B8u,0x932FA520u,0x1DB97617u,0x4237085Au,
      0xD5E681F8u,0xF6691AC3u,0x76630E45u,0xDE46329Du,0x1CCEBF38u,0xEAB24EF4u,0xF162D49Au
    },
    0x00CEB5F0u
  },
  { // token  9 (13 rounds)
    {
      0x22895989u,0x29103D9Fu,0x21A9705Eu,0x665C6211u,0xCE0EA9A0u,0x9E0E32F0u,0x03793061u,
      0x7A73D769u,0x8B2810D0u,0x4622C474u,0xA0354880u,0x6515B7DDu,0xF94FBB8Fu,0x039ECD96u
    },
    0x01BDAD8Bu
  },
  { // token 10 (16 rounds)
    {
      0x7938E1ECu,0x72551990u,0x79600970u,0x98803492u,0x3EEDC68Eu,0x60A4E994u,0x2E0B6563u,
      0xE2C9C4E4u,0x48BA5BE9u,0xCF8AE52Eu,0x0EF05BFCu,0x7AC8902Au,0x4455B2E1u,0xB760358Au
    },
    0x00178BBEu
  },
  { // token 11 (18 rounds)
    {
      0x3187021Eu,0x0F7BAF84u,0x8E8AF950u,0x4F94D05Cu,0xA533A4B3u,0x7DEE17C7u,0x66996110u,
      0xFAFCD6E8u,0x25EC12F3u,0xAC9F39FFu,0x6F394440u,0x3509BF57u,0x6C8BDF39u,0xD4504ADBu
    },
    0x02B17931u
  },
  { // token 12 ( 5 rounds)
    {
      0x10E7F141u,0xAAF4667Du,0x0067C520u,0xF076712Au,0xFFAB5679u,0x0D867EF2u,0x972E3F22u,
      0x7048B2EFu,0xAC3F7196u,0x4A9D5709u,0xB3D02358u,0xD383945Eu,0x273FCC2Bu,0x4DDF51B9u
    },
    0x0021316Au
  },
  { // token 13 (10 rounds)
    {
      0xA71F3356u,0x87433652u,0x439C65B3u,0x89189369u,0xCC068549u,0x9122810Fu,0x0F56EC90u,
      0x0E432AB6u,0xCE04750Cu,0x059A3A44u,0x44208C8Bu,0xE2A92AD7u,0x1C432F82u,0x6E64C438u
    },
    0x022FE046u
  },
  { // token 14 ( 7 rounds)
    {
      0x59C43543u,0xB61F79DDu,0x702F9114u,0xD8D08BF5u,0x51F8A45Fu,0x169AE6EEu,0x6A21AB1Fu,
      0x10F6DCB2u,0x14954A4Au,0x3DD7C5DBu,0x730DACE2u,0x270F00CDu,0xC2D37A7Du,0xBC9AE087u
    },
    0x0027A3ADu
  },
  { // token 15 (10 rounds)
    {
      0xBB35F546u,0x13C3732Eu,0x3C9F3549u,0x155FF657u,0x6B0FC581u,0x888A59AFu,0x0D1A48F8u,
      0x0384615Bu,0xD92EA445u,0x03983264u,0x7AE8B60Bu,0xFC72CD05u,0x7610FD36u,0x39CB181Au
    },
    0x01CF56A9u
  },
  { // token 16 (15 rounds)
    {
      0x3D6ACA4Bu,0x04767B17u,0x81A2234Cu,0x967F7875u,0xD71E46E2u,0x103C16A0u,0x9C5EB117u,
      0x8D5EA1A0u,0x9D393CA1u,0x63FCA6A0u,0xC0B4D6BDu,0x3667788Au,0x2FA35E89u,0x81C58688u
    },
    0x021A63E6u
  },
  { // token 17 (13 rounds)
    {
      0x1EA04D99u,0x5A261FC8u,0xB9B71390u,0xD4EB98A5u,0x73D3D2EFu,0x66B92CC0u,0x48058553u,
      0xA124CC10u,0x83313A68u,0x0DAEEFD5u,0xFF1B2EA6u,0xE90DCE30u,0x1F690EB0u,0x4E016955u
    },
    0x005CCA9Du
  },
  { // token 18 (11 rounds)
    {
      0x37006027u,0x80844129u,0x137985F7u,0x391916F5u,0x41D8839Cu,0x2611A2C4u,0x327EE70Au,
      0x55F7CD39u,0xDB5EF7F8u,0xABCC426Du,0x8B2F0B4Bu,0xFBC1AA3Cu,0xE2D6FB33u,0xAF2AEFE3u
    },
    0x01E303F6u
  },
  { // token 19 (19 rounds)
    {
      0x7809D33Fu,0x17271419u,0xB6DCDE51u,0x1CC909BCu,0x9296673Fu,0xC845EB6Bu,0xA48A80CFu,
      0x54EB77D7u,0x0A001F98u,0xE441F75Eu,0xB891FDE2u,0x5282C91Bu,0x39565641u,0x7C2DA684u
    },
    0x033DFBD7u
  },
  { // token 20 (17 rounds)
    {
      0xD4844EFDu,0x0DC48CE8u,0xD0C3A383u,0x31183CE3u,0x52E9AB95u,0x9BD4F062u,0x9443932Au,
      0x5411A3D9u,0x350FACADu,0xF66F0202u,0x13D63C8Au,0xD8058C40u,0xE6738249u,0x7B3B6DD5u
    },
    0x0263F363u
  },
  { // token 21 ( 6 rounds)
    {
      0xD930A392u,0x704C01BFu,0x521177D8u,0x0C0BFF39u,0x195F5D05u,0xA309FE46u,0xAA2B244Au,
      0xC1A1F646u,0x53871D74u,0xACB93F77u,0x61F02DF5u,0xAB622F1Au,0xF275DB98u,0x66DA2EE1u
    },
    0x0012E2CFu
  },
  { // token 22 ( 8 rounds)
    {
      0xA1FAAB44u,0xE6DAF81Eu,0x87E375C5u,0x5549A6C3u,0xC1F68953u,0x076AFB3Bu,0x27FDCF74u,
      0x90A962B0u,0xF23CD457u,0x7410B702u,0x489979B8u,0x54AA2D20u,0x1902E890u,0x0D4339A9u
    },
    0x00EA11B7u
  },
  { // token 23 ( 4 rounds)
    {
      0x5FE92C40u,0xDBFA42D1u,0xCD63F596u,0xF13D9E41u,0xE813C4BAu,0x6804F010u,0x77874E11u,
      0x8D5D4B14u,0x1901166Eu,0xF0B2A187u,0x95B4B5F2u,0x518949F5u,0x064697E2u,0x7FDAEEE8u
    },
    0x00FF775Fu
  },
  { // token 24 (11 rounds)
    {
      0x54AB9E37u,0xB6AD18DAu,0xB7618A2Du,0x22FEC66Bu,0x00F27F4Cu,0x9D799CBBu,0x857B4006u,
      0x5B249D09u,0xDB4F5392u,0xCF4EA65Fu,0xEA0F93B1u,0xAB387CD2u,0xA879BAB6u,0x4E23BB44u
    },
    0x009AC1F0u
  },
  { // token 25 (17 rounds)
    {
      0xBB70850Du,0xB79A4564u,0x8AD244D5u,0x0F6E8A3Cu,0xB3F36795u,0xFCDDDDB7u,0x6D5C57F3u,
      0x94145839u,0x43C172DDu,0x2B36AD5Fu,0xD7F38692u,0x16C21D94u,0x06966302u,0x6432195Cu
    },
    0x001BD28Du
  },
  { // token 26 (12 rounds)
    {
      0xC2C187C8u,0xD91C8AE7u,0x32A68471u,0xD4361EB8u,0x21DA2DBFu,0x8998C3BDu,0xDBB579EFu,
      0xA44EEE43u,0x785A48C5u,0x0CB4C581u,0x6D0D5ACEu,0xEF78C737u,0xA906D6EBu,0xBBCA5627u
    },
    0x02A29680u
  },
  { // token 27 (15 rounds)
    {
      0xCA13EE1Bu,0xA9D1FB01u,0x2D167038u,0xF9706098u,0x10C80054u,0xC2490677u,0x0A8DD11Bu,
      0xC1F59CB7u,0xB61B4B75u,0x13E42D7Cu,0x3DB18691u,0xE58FDF5Du,0x29A6008Du,0xB559C943u
    },
    0x00AE462Au
  },
  { // token 28 (16 rounds)
    {
      0x9CCB519Cu,0xCABC4875u,0xA73B87DCu,0xB883829Eu,0xFEC68714u,0xB0CA89EBu,0xA8DF8F78u,
      0x136EC645u,0x24688467u,0x35CE2531u,0x8A455729u,0x8AB50655u,0x55A62435u,0x257D5498u
    },
    0x00D02030u
  },
  { // token 29 ( 8 rounds)
    {
      0xAEC0F6B4u,0x03983F15u,0x74C9810Au,0x87EC7EDDu,0x9BA90EACu,0x386F1D3Bu,0x4A544285u,
      0xFFF27A4Cu,0x995AD833u,0x31AE0E73u,0x1E884983u,0x34B0974Eu,0xDAB19664u,0x27DEE285u
    },
    0x00E8492Bu
  },
  { // token 30 ( 5 rounds)
    {
      0xEE532451u,0xCCF0CA5Cu,0x3283EB8Du,0x5FD06EBAu,0x16C0B2DBu,0x77FE5D39u,0x768BC72Au,
      0x8047569Au,0x42D0E875u,0x840D86ADu,0x391A65FCu,0x7AF820F7u,0x3B3235E4u,0x699DEE35u
    },
    0x01255328u
  },
  { // token 31 (19 rounds)
    {
      0x40807F8Fu,0x05216775u,0xF7125AE7u,0x566A3499u,0x8C2AEC9Cu,0x9A5AD1B0u,0x8535B001u,
      0xF75514CFu,0xF81C41D5u,0x0D6A059Eu,0xE698EBC7u,0xF9FF71C7u,0x0A058C12u,0xF4DBFF1Cu
    },
    0x01F7931Cu
  }
};

//
// CPU-only functions
//
// the custom MD5 function (returning only d[0] mod 2^24) and the PoW function (with n_1 always set to 0)
//

static unsigned int cMD5_cpu (unsigned int t[14], unsigned int n[2])
{
  unsigned int m[16],d[4];

  m[ 0] = t[ 0];
  m[ 1] = t[ 1];
  m[ 2] = t[ 2];
  m[ 3] = t[ 3];
  m[ 4] = t[ 4];
  m[ 5] = t[ 5];
  m[ 6] = t[ 6];
  m[ 7] = t[ 7];
  m[ 8] = t[ 8];
  m[ 9] = t[ 9];
  m[10] = t[10];
  m[11] = t[11];
  m[12] = t[12];
  m[13] = t[13];
  m[14] = n[ 0];
  m[15] = n[ 1];
  cMD5 (m, d);
  return d[0] & 0x00FFFFFFu;
}

static unsigned int PoW_cpu (unsigned int t[14])
{
  unsigned int n[2];

  n[1] = 0u; // always 0
  for (n[0] = 0u; n[0] < (1u << 27); n[0]++)
    if (cMD5_cpu (t, n) == 0u)
       return n[0];
  fprintf(stderr," no n_0 found up to 2^27\n");
  exit(1);
}
#include <sys/time.h>

//
// (macro) call a CUDA driver API function and terminate the program if it reports an error
// it can, and should, be used to test the return value of calls
//

#if _use_cuda_ != 0

#define CHECK(call)                                                            \
{                                                                              \
    const cudaError_t error = call;                                            \
    if (error != cudaSuccess)                                                  \
    {                                                                          \
        fprintf(stderr, "Error: %s:%d, ", __FILE__, __LINE__);                 \
        fprintf(stderr, "code: %d, reason: %s\n", error,                       \
                cudaGetErrorString(error));                                    \
        exit(1);                                                               \
    }                                                                          \
}

#endif

//
// main program
//

int main (int argc,char **argv)
{
  //
  // token data check (CPU only)
  //

  if ((argc == 2) && (strcmp (argv[1],"-cpu") == 0))
  {
    unsigned int i,n0;
    double t0,t1,t2;

    t0 = t1 = real_time ();
    for (i = 0u; i < 32u; i++)
    {
      fprintf (stderr, "token %2u [%2u rounds]: ", i, 4u + (token_data[i].token[0] & 0x0F));
      n0 = PoW_cpu (&token_data[i].token[0]);
      if(n0 != token_data[i].first_solution)
      {
        fprintf(stderr, " %u is different from %u\n", n0, token_data[i].first_solution);
        exit(1);
      }
      t2 = real_time ();
      fprintf (stderr, "%8u%s,", n0, (n0 >= (1u << 24)) ? " (>= 2^24)" : "          ");
      fprintf (stderr, " done in %6.3fs", t2 - t1);
      fprintf (stderr, " [%6.3fns per round]\n", 1.0e9 * (t2 - t1) / (double) (1u + n0) / (double)(4u + (token_data[i].token[0] & 0x0F)));
      t1 = t2;
    }
    printf ("All is well (all work done in %.3fs)\n", t1 - t0);
    exit (0);
  }

#if _use_cuda_ != 0
  //
  // proof-of-work computation (CPU and CUDA device)
  //

  if((argc == 2) && (strcmp(argv[1],"-cuda") == 0))
  {
    // set up device

    int dev = 0;

    cudaDeviceProp deviceProp;
    CHECK (cudaGetDeviceProperties (&deviceProp, dev));
    printf("Using Device %d: %s\n", dev, deviceProp.name);
    CHECK (cudaSetDevice (dev));

    //
    // create memory areas in host and device memory where the token data and proof-or-work data will be placed
    //

    size_t data_size;
    unsigned int *host_tokens;
    unsigned int *device_tokens;

    data_size = (size_t)32u * (size_t)16 * sizeof(unsigned int); // 32 tokens, each with 16 32-bit words
    host_tokens = (unsigned int *) malloc (data_size);
    CHECK (cudaMalloc ((void **) &device_tokens, data_size));

    //
    // thread identification
    //
    // thread x coordinate ............ threadIdx.x + blockDim.x * blockIdx.x
    // thread y coordinate ............ threadIdx.y + blockDim.y * blockIdx.y
    // thread idx (work identifier) ... y + blockDim.y * gridDim.y * x
    // thread token number ............ idx mod 32
    // thread n_0 ..................... floor (idx / 32u)
    //

    unsigned int n_threads_log2 = 5u + 24u; // 32 tokens, 2^24 values of n_0 per token
    static struct
    {
      unsigned int grid_dim_x_log2;  // valid values: 0 to 29
      unsigned int grid_dim_y_log2;  // valid values: 0 to 15
      unsigned int block_dim_x_log2; // valid values: 0 to 10 \ the sum of the two cannot
      unsigned int block_dim_y_log2; // valid values: 0 to 10 /    be larger than 10
    }
    grids[] =
    {
      { 16u, 8u, 8u, 0u }
    };

    //
    // for each launch grid ...
    //

    printf("            GRID      BLOCK\n");
    printf("----------------  ---------\n");
    printf("         X     Y     X    Y      Time\n");
    printf("---------- -----  ---- ----  --------\n");
    fflush(stdout);

    int g;

    for (g = 0; g < (int)(sizeof(grids) / sizeof(grids[0])); g++)
    {
      //
      // get the launch grid dimensions
      //

      unsigned int grid_dim_x,grid_dim_y,block_dim_x,block_dim_y;

      if ((grids[g].grid_dim_x_log2 + grids[g].grid_dim_y_log2 + grids[g].block_dim_x_log2 + grids[g].block_dim_y_log2) != n_threads_log2)
      {
        fprintf(stderr,"grid #%u is bad\n",g);
        exit(1);
      }
      grid_dim_x = 1u << grids[g].grid_dim_x_log2;
      grid_dim_y = 1u << grids[g].grid_dim_y_log2;
      block_dim_x = 1u << grids[g].block_dim_x_log2;
      block_dim_y = 1u << grids[g].block_dim_y_log2;

      //
      // initialize the host data
      //
      int i, j;

      for (i = 0; i < 32; i++)
      {
        for (j = 0; j < 14; j++)
          host_tokens[16 * i + j] = token_data[i].token[j];
        host_tokens[16 * i + 14] = 0xFFFFFFFFu; // not found mark
        host_tokens[16 * i + 15] = 0xFFFFFFFFu; // not found mark
      }

      //
      // copy the host data to device memory
      //

      CHECK (cudaMemcpy (device_tokens, host_tokens, data_size, cudaMemcpyHostToDevice));

      //
      // run the kernel (set its arguments first)
      //

      dim3 grid (grid_dim_x, grid_dim_y, 1);
      dim3 block (block_dim_x, block_dim_y, 1);

      double t0 = real_time();
      cMD5_cuda <<<grid, block>>> (device_tokens);
      CHECK (cudaDeviceSynchronize ());                            // wait for kernel to finish
      CHECK (cudaGetLastError ());                                 // check for kernel errors
      double t1 = real_time();
      printf ("%10u %5u  %4u %4u  %8.5f\n", grid_dim_x, grid_dim_y, block_dim_x, block_dim_y, t1 - t0);
      fflush(stdout);

      //
      // copy the buffer form device memory to CPU memory
      //

      CHECK (cudaMemcpy (host_tokens, device_tokens, data_size, cudaMemcpyDeviceToHost));

      //
      // check data
      //

      for (i = 0; i < 32; i++)
      {
        if ((host_tokens[16 * i + 15] == 0xFFFFFFFFu) && (token_data[i].first_solution < (1u << 24)))
        {
          fprintf (stderr, "PoW(%u token) not finished! (unexpected)\n", i);
          exit (1);
        }
        if (host_tokens[16 * i + 15] != 0xFFFFFFFFu)
           if ((host_tokens[16 * i + 15] != 0u) || (cMD5_cpu (&host_tokens[16 * i], &host_tokens[16 * i + 14]) != 0u))
           {
             fprintf (stderr, "bad PoW(%u token)\n", i);
             exit (1);
           }
      }
    }
    printf ("---------- -----  ---- ----  --------\n");
    fflush(stdout);

    // free device global memory

    CHECK (cudaFree (device_tokens));

    // free host memory

    free (host_tokens);

    // reset device

    CHECK (cudaDeviceReset ());

    return 0;
  }
#endif

  return 0;
}

//
// cMD5_cuda kernel (each thread computes one custom MD5 function for one specific value of n for a specific token)
//

__global__ static void cMD5_cuda(unsigned int * __restrict__ tokens)
{
  unsigned int idx,m[16],d[4];

  //
  // get a unique work identifier (idx = token_number + 32 * n_0)
  //
  {
    unsigned int x = (unsigned int)threadIdx.x + (unsigned int)blockDim.x * (unsigned int)blockIdx.x;
    unsigned int y = (unsigned int)threadIdx.y + (unsigned int)blockDim.y * (unsigned int)blockIdx.y;
    idx = y + (unsigned int)blockDim.y * (unsigned int)gridDim.y * x;
  }
  //
  // adjust the token_data pointer
  //
  tokens += 16u * (idx % 32u); // each token uses 16 32-bit words (14 for the token, 2 for an eventual return value)
  
  if(tokens[15] == 0u)
    return;
  
  idx /= 32u;                  // now idx is just n_0

  //
  // read the token data
  //
  m[ 0] = tokens[ 0];
  m[ 1] = tokens[ 1];
  m[ 2] = tokens[ 2];
  m[ 3] = tokens[ 3];
  m[ 4] = tokens[ 4];
  m[ 5] = tokens[ 5];
  m[ 6] = tokens[ 6];
  m[ 7] = tokens[ 7];
  m[ 8] = tokens[ 8];
  m[ 9] = tokens[ 9];
  m[10] = tokens[10];
  m[11] = tokens[11];
  m[12] = tokens[12];
  m[13] = tokens[13];
  //
  // put n_1 and n_0 in the m[] array
  //
  m[14] = idx; // n_0
  m[15] = 0u;  // in this implementation n_1 is always 0
  //
  // compute the custom MD5 function
  //
  cMD5(m,d);
  //
  // check result and exit
  //
  if((d[0] & 0x00FFFFFFu) == 0u)
  {                   // got it! since we are interested in ANY solution, if two or more threads find
    tokens[14] = idx; //         good values for n_0, it does not mater which one writes its solution
    tokens[15] = 0u;  //         first, so here we record a solution in the simplest possible way; as
  }                   //         m[15] is always 0, race conditions are irrelevant here
}
