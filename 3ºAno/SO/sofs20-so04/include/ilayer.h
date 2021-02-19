/**
 *  \file
 *  \brief aggregation of ilayer header files
 *
 *  \author Artur Pereira - 2016-2020
 */

#ifndef __SOFS20_ILAYER__
#define __SOFS20_ILAYER__

/** 
 * \anchor ilayer
 * \defgroup ilayer ilayer
 *  \brief ILayer functions
 *  \details
 *    The \c ILayer represents the set of functions to be
 *    implemented by the students.
 *    It includes 4 sublayers:
 *    - \c freeinodes, to deal with the list of free inodes
 *    - \c freedatablocks, to deal with the list of free data blocks
 *    - \c fileblocks, to deal with the data blocks of a file (inode)
 *    - \c direntries, to deal with directories and directory entries
 */

/** 
 * \defgroup a_ilayer ILayer/freeinodes
 * \ingroup ilayer
 * \brief Functions to manage the list of free inodes 
 * \remarks In case an error occurs, every function throws an \c SOException.
 *
 * \defgroup b_ilayer ILayer/freedatablocks
 * \ingroup ilayer
 * \brief Functions to manage the list of free data blocks.
 * \remarks In case an error occurs, every function throws an \c SOException.
 *
 * \defgroup c_ilayer ILayer/fileblocks
 * \ingroup ilayer
 * \brief Functions to manage file blocks
 * \remarks In case an error occurs, every function throws an \c SOException.
 *
 * \defgroup d_ilayer ILayer/direntries
 * \ingroup ilayer
 * \brief Functions to manage directory entries
 * \remarks In case an error occurs, every function throws an \c SOException.
 */

#include "freeinodes.h"
#include "freedatablocks.h"
#include "fileblocks.h"
#include "direntries.h"

#endif /* __SOFS20_ILAYER__ */
