/**
 *  \file
 *  \brief Definition of the directory entry data type.
 *
 *  \author Artur Pereira - 2008-2009, 2016-2020
 *  \author Miguel Oliveira e Silva - 2009, 2017
 *  \author António Rui Borges - 2010-2015
 */

#ifndef __SOFS20_DIRENTRY__
#define __SOFS20_DIRENTRY__

#include "constants.h"

#include <inttypes.h>

namespace sofs20
{

    /** 
     * \brief Definition of the directory entry data type. 
     * \ingroup direntry
     * \details
     *   A direntry entry is a fixed-size data type composed of 
     *   a name and an inode number.
     *   It allows to implement the typical hierarchical view of a disk content.
     */
    struct SODirentry
    {

        /** \brief the associated inode number */
        uint16_t in;

        /** \brief a name of the file (NULL-terminated string) */
        char name[SOFS20_FILENAME_LEN + 1];
    };

};

#endif /* __SOFS20_DIRENTRY__ */
