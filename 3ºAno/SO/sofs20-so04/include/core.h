/**
 *  \file
 *  \brief aggregation of core header files
 *
 *  \author Artur Pereira - 2016-2020
 */

#ifndef __SOFS20_CORE__
#define __SOFS20_CORE__

/** \defgroup core core
 *  \brief Core constants and core data types
 */

/** 
 * \defgroup superblock superblock
 * \ingroup core
 * \brief The \c SOSuperblock data type
 *
 * \defgroup inode inode
 * \ingroup core
 * \brief The \c SOInode data type
 *
 * \defgroup direntry direntry
 * \ingroup core
 * \brief The \c SODirentry data type
 *
 * \defgroup exception exception
 * \ingroup core
 * \brief \c The sofs20 exception data type
 */

#include "constants.h"
#include "exception.h"
#include "superblock.h"
#include "inode.h"
#include "direntry.h"

#endif /* __SOFS20_CORE__ */
