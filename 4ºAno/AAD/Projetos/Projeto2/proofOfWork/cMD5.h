//
// Tomás Oliveira e Silva,  November 2018
//
// ACA 2018/2019
//
// custom MD5 message digest
//

#ifndef _MD5_H_
#define _MD5_H_

//
// macros used in type-1 rounds
//

#define MD5_F(x,y,z)  ((x & y) | (~x & z))
#define MD5_11  7
#define MD5_12 12
#define MD5_13 17
#define MD5_14 22

//
// macros used in type-2 rounds
//

#define MD5_G(x,y,z)  ((x & z) | (y & ~z))
#define MD5_21  5
#define MD5_22  9
#define MD5_23 14
#define MD5_24 20

//
// macros used in type-3 rounds
//

#define MD5_H(x,y,z)  (x ^ y ^ z)
#define MD5_31  4
#define MD5_32 11
#define MD5_33 16
#define MD5_34 23

//
// macros used in type-4 rounds
//

#define MD5_I(x,y,z)  (y ^ (x | ~z))
#define MD5_41  6
#define MD5_42 10
#define MD5_43 15
#define MD5_44 21

//
// state mixing macros
//

#define MD5_ROTL(x,n)                ((x << n) | (x >> (32 - n)))
#define MD5_ROUND(F,a,b,c,d,x,s,ac)  a += F(b,c,d) + x + ac; if(s != 0) a = MD5_ROTL(a,s); a += b

//
// custom MD5 message digest
//
// the input (16 32-bit words) is m[0], ..., m[15]
// the output (4 32-bit words) is d[0], ..., d[3]
//

#define cMD5(m,d)                                                    \
  do                                                                 \
  {                                                                  \
    unsigned int n_rounds;                                           \
                                                                     \
    /*               */                                              \
    /* initial state */                                              \
    /*               */                                              \
    d[0] = 0x67452301u;                                              \
    d[1] = 0xEFCDAB89u;                                              \
    d[2] = 0x98BADCFEu;                                              \
    d[3] = 0x10325476u;                                              \
    /*               */                                              \
    /* do all rounds */                                              \
    /*               */                                              \
    n_rounds = 4u + (m[0] & 0x0F);                                   \
    for(;;)                                                          \
    {                                                                \
      /*              */                                             \
      /* type-1 round */                                             \
      /*              */                                             \
      if(n_rounds == 0u)                                             \
        break;                                                       \
      n_rounds--;                                                    \
      MD5_ROUND(MD5_F,d[0],d[1],d[2],d[3],m[ 0],MD5_11,0xD76AA478u); \
      MD5_ROUND(MD5_F,d[3],d[0],d[1],d[2],m[ 1],MD5_12,0xE8C7B756u); \
      MD5_ROUND(MD5_F,d[2],d[3],d[0],d[1],m[ 2],MD5_13,0x242070DBu); \
      MD5_ROUND(MD5_F,d[1],d[2],d[3],d[0],m[ 3],MD5_14,0xC1BDCEEEu); \
      MD5_ROUND(MD5_F,d[0],d[1],d[2],d[3],m[ 4],MD5_11,0xF57C0FAFu); \
      MD5_ROUND(MD5_F,d[3],d[0],d[1],d[2],m[ 5],MD5_12,0x4787C62Au); \
      MD5_ROUND(MD5_F,d[2],d[3],d[0],d[1],m[ 6],MD5_13,0xA8304613u); \
      MD5_ROUND(MD5_F,d[1],d[2],d[3],d[0],m[ 7],MD5_14,0xFD469501u); \
      MD5_ROUND(MD5_F,d[0],d[1],d[2],d[3],m[ 8],MD5_11,0x698098D8u); \
      MD5_ROUND(MD5_F,d[3],d[0],d[1],d[2],m[ 9],MD5_12,0x8B44F7AFu); \
      MD5_ROUND(MD5_F,d[2],d[3],d[0],d[1],m[10],MD5_13,0xFFFF5BB1u); \
      MD5_ROUND(MD5_F,d[1],d[2],d[3],d[0],m[11],MD5_14,0x895CD7BEu); \
      MD5_ROUND(MD5_F,d[0],d[1],d[2],d[3],m[12],MD5_11,0x6B901122u); \
      MD5_ROUND(MD5_F,d[3],d[0],d[1],d[2],m[13],MD5_12,0xFD987193u); \
      MD5_ROUND(MD5_F,d[2],d[3],d[0],d[1],m[14],MD5_13,0xA679438Eu); \
      MD5_ROUND(MD5_F,d[1],d[2],d[3],d[0],m[15],MD5_14,0x49B40821u); \
      /*              */                                             \
      /* type-2 round */                                             \
      /*              */                                             \
      if(n_rounds == 0u)                                             \
        break;                                                       \
      n_rounds--;                                                    \
      MD5_ROUND(MD5_G,d[0],d[1],d[2],d[3],m[ 1],MD5_21,0xF61E2562u); \
      MD5_ROUND(MD5_G,d[3],d[0],d[1],d[2],m[ 6],MD5_22,0xC040B340u); \
      MD5_ROUND(MD5_G,d[2],d[3],d[0],d[1],m[11],MD5_23,0x265E5A51u); \
      MD5_ROUND(MD5_G,d[1],d[2],d[3],d[0],m[ 0],MD5_24,0xE9B6C7AAu); \
      MD5_ROUND(MD5_G,d[0],d[1],d[2],d[3],m[ 5],MD5_21,0xD62F105Du); \
      MD5_ROUND(MD5_G,d[3],d[0],d[1],d[2],m[10],MD5_22,0x02441453u); \
      MD5_ROUND(MD5_G,d[2],d[3],d[0],d[1],m[15],MD5_23,0xD8A1E681u); \
      MD5_ROUND(MD5_G,d[1],d[2],d[3],d[0],m[ 4],MD5_24,0xE7D3FBC8u); \
      MD5_ROUND(MD5_G,d[0],d[1],d[2],d[3],m[ 9],MD5_21,0x21E1CDE6u); \
      MD5_ROUND(MD5_G,d[3],d[0],d[1],d[2],m[14],MD5_22,0xC33707D6u); \
      MD5_ROUND(MD5_G,d[2],d[3],d[0],d[1],m[ 3],MD5_23,0xF4D50D87u); \
      MD5_ROUND(MD5_G,d[1],d[2],d[3],d[0],m[ 8],MD5_24,0x455A14EDu); \
      MD5_ROUND(MD5_G,d[0],d[1],d[2],d[3],m[13],MD5_21,0xA9E3E905u); \
      MD5_ROUND(MD5_G,d[3],d[0],d[1],d[2],m[ 2],MD5_22,0xFCEFA3F8u); \
      MD5_ROUND(MD5_G,d[2],d[3],d[0],d[1],m[ 7],MD5_23,0x676F02D9u); \
      MD5_ROUND(MD5_G,d[1],d[2],d[3],d[0],m[12],MD5_24,0x8D2A4C8Au); \
      /*              */                                             \
      /* type-3 round */                                             \
      /*              */                                             \
      if(n_rounds == 0u)                                             \
        break;                                                       \
      n_rounds--;                                                    \
      MD5_ROUND(MD5_H,d[0],d[1],d[2],d[3],m[ 5],MD5_31,0xFFFA3942u); \
      MD5_ROUND(MD5_H,d[3],d[0],d[1],d[2],m[ 8],MD5_32,0x8771F681u); \
      MD5_ROUND(MD5_H,d[2],d[3],d[0],d[1],m[11],MD5_33,0x6D9D6122u); \
      MD5_ROUND(MD5_H,d[1],d[2],d[3],d[0],m[14],MD5_34,0xFDE5380Cu); \
      MD5_ROUND(MD5_H,d[0],d[1],d[2],d[3],m[ 1],MD5_31,0xA4BEEA44u); \
      MD5_ROUND(MD5_H,d[3],d[0],d[1],d[2],m[ 4],MD5_32,0x4BDECFA9u); \
      MD5_ROUND(MD5_H,d[2],d[3],d[0],d[1],m[ 7],MD5_33,0xF6BB4B60u); \
      MD5_ROUND(MD5_H,d[1],d[2],d[3],d[0],m[10],MD5_34,0xBEBFBC70u); \
      MD5_ROUND(MD5_H,d[0],d[1],d[2],d[3],m[13],MD5_31,0x289B7EC6u); \
      MD5_ROUND(MD5_H,d[3],d[0],d[1],d[2],m[ 0],MD5_32,0xEAA127FAu); \
      MD5_ROUND(MD5_H,d[2],d[3],d[0],d[1],m[ 3],MD5_33,0xD4EF3085u); \
      MD5_ROUND(MD5_H,d[1],d[2],d[3],d[0],m[ 6],MD5_34,0x04881D05u); \
      MD5_ROUND(MD5_H,d[0],d[1],d[2],d[3],m[ 9],MD5_31,0xD9D4D039u); \
      MD5_ROUND(MD5_H,d[3],d[0],d[1],d[2],m[12],MD5_32,0xE6DB99E5u); \
      MD5_ROUND(MD5_H,d[2],d[3],d[0],d[1],m[15],MD5_33,0x1FA27CF8u); \
      MD5_ROUND(MD5_H,d[1],d[2],d[3],d[0],m[ 2],MD5_34,0xC4AC5665u); \
      /*              */                                             \
      /* type-4 round */                                             \
      /*              */                                             \
      if(n_rounds == 0u)                                             \
        break;                                                       \
      n_rounds--;                                                    \
      MD5_ROUND(MD5_I,d[0],d[1],d[2],d[3],m[ 0],MD5_41,0xF4292244u); \
      MD5_ROUND(MD5_I,d[3],d[0],d[1],d[2],m[ 7],MD5_42,0x432AFF97u); \
      MD5_ROUND(MD5_I,d[2],d[3],d[0],d[1],m[14],MD5_43,0xAB9423A7u); \
      MD5_ROUND(MD5_I,d[1],d[2],d[3],d[0],m[ 5],MD5_44,0xFC93A039u); \
      MD5_ROUND(MD5_I,d[0],d[1],d[2],d[3],m[12],MD5_41,0x655B59C3u); \
      MD5_ROUND(MD5_I,d[3],d[0],d[1],d[2],m[ 3],MD5_42,0x8F0CCC92u); \
      MD5_ROUND(MD5_I,d[2],d[3],d[0],d[1],m[10],MD5_43,0xFFEFF47Du); \
      MD5_ROUND(MD5_I,d[1],d[2],d[3],d[0],m[ 1],MD5_44,0x85845DD1u); \
      MD5_ROUND(MD5_I,d[0],d[1],d[2],d[3],m[ 8],MD5_41,0x6FA87E4Fu); \
      MD5_ROUND(MD5_I,d[3],d[0],d[1],d[2],m[15],MD5_42,0xFE2CE6E0u); \
      MD5_ROUND(MD5_I,d[2],d[3],d[0],d[1],m[ 6],MD5_43,0xA3014314u); \
      MD5_ROUND(MD5_I,d[1],d[2],d[3],d[0],m[13],MD5_44,0x4E0811A1u); \
      MD5_ROUND(MD5_I,d[0],d[1],d[2],d[3],m[ 4],MD5_41,0xF7537E82u); \
      MD5_ROUND(MD5_I,d[3],d[0],d[1],d[2],m[11],MD5_42,0xBD3AF235u); \
      MD5_ROUND(MD5_I,d[2],d[3],d[0],d[1],m[ 2],MD5_43,0x2AD7D2BBu); \
      MD5_ROUND(MD5_I,d[1],d[2],d[3],d[0],m[ 9],MD5_44,0xEB86D391u); \
    }                                                                \
    /*        */                                                     \
    /* finish */                                                     \
    /*        */                                                     \
    d[0] += 0x67452301u;                                             \
    d[1] += 0xEFCDAB89u;                                             \
    d[2] += 0x98BADCFEu;                                             \
    d[3] += 0x10325476u;                                             \
  }                                                                  \
  while(0)

#endif
