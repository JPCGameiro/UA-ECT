/**
 *  \file 
 *  \brief Functions to manage file blocks
 *
 *  \author Artur Pereira - 2008-2009, 2016-2020
 *  \author Miguel Oliveira e Silva - 2009, 2017
 *  \author António Rui Borges - 2010-2015
 *
 *  \remarks In case an error occurs, every function throws an SOException
 */

#ifndef __SOFS20_FILEBLOCKS__
#define __SOFS20_FILEBLOCKS__

#include "bin_fileblocks.h"
#include "grp_fileblocks.h"

#include <inttypes.h>

namespace sofs20
{
    /* *************************************************** */

    /**
     * \anchor gfb
     *
     * \brief Get the data block number corresponding to the given file block
     * \ingroup c_ilayer
     *
     * \details
     * The file block number (fbn) is the number of a block from the file (inode) point of view,
     * 0 representing the first block, 1 the second, and so forth.\n
     * The data block number (dbn) corresponding to fbn is given by d[fbn].
     * Note, however, that array d[.] is only partially stored in the inode.
     *
     * \pre \c ih is a valid handler of an inode in-use
     * \pre \c fbn is a valid file block number
     *
     * \throws EINVAL if \c fbn is not valid
     *
     * \param [in] ih inode handler
     * \param [in] fbn file block number
     * \return the number of the corresponding data block
     *
     * \note \c BlockNullReference is a valid return value.
     * \note When calling a sofs20 function of any layer,
     *   the version with prefix \b so should be used,
     *   not the one with prefix \b grp or \b bin.
     *
     */
    uint32_t soGetFileBlock(int ih, uint32_t fbn);

    /* *************************************************** */

    /**
     * \anchor afb
     *
     * \brief Associate a data block to the given file block position
     * \ingroup c_ilayer
     *
     * \details
     * The file block number (fbn) is the number of a block from the file (inode) point of view,
     * 0 representing the first block, 1 the second, and so forth.\n
     * The data block number (dbn) corresponding to fbn is given by d[fbn].
     * Note, however, that array d[.] is only partially stored in the inode.
     *
     * \sa \ref adb "soAllocDataBlock"
     *
     * \pre \c ih is a valid handler of an inode in-use
     * \pre <tt>soGetFileBlock(ih, fbn) = \c BlockNullReference</tt>
     *
     * \throws EINVAL if \c fbn is not valid
     *
     * \param [in] ih inode handler
     * \param [in] fbn file block number
     *
     * \return the number of the data block now associated to the given file block position
     *
     * \note Depending on the situation, 1, 2, or 3 data blocks are allocated.
     * \note When calling a sofs20 function of any layer,
     *   the version with prefix \b so should be used,
     *   not the one with prefix \b grp or \b bin.
     */
    uint32_t soAllocFileBlock(int ih, uint32_t fbn);

    /* *************************************************** */

    /**
     * \anchor ffb
     *
     * \brief Free all file blocks from the given position on 
     * \ingroup c_ilayer
     * \details
     * The file block number (fbn) is the number of a block from the file (inode) point of view,
     * 0 representing the first block, 1 the second, and so forth.\n
     * The data block number (dbn) corresponding to fbn is given by d[fbn].
     * Note, however, that array d[.] is only partially stored in the inode.
     *
     * \pre \c ih is a valid handler of an inode in-use
     *
     * \param [in] ih inode handler
     * \param [in] ffbn first file block number
     *
     * \note A file can have \e holes, meaning references equal to \c BlockNullReference
     *   can appear between others that aren't.
     * \note Data blocks used to store references that become empty are also freed.
     * \note When calling a sofs20 function of any layer,
     *   the version with prefix \b so should be used,
     *   not the one with prefix \b grp or \b bin.
     */
    void soFreeFileBlocks(int ih, uint32_t ffbn);

    /* *************************************************** */

    /**
     * \anchor rfb
     *
     * \brief Read a file block.
     * \ingroup c_ilayer
     *
     * \details
     * Data is read from a specific file block of an in-use inode
     *
     * If the referred file block has not been allocated yet,
     *   the returned data will consist of a byte stream filled with the
     *   null character (ascii code 0);
     *
     * \sa \ref gfb "soGetFileBlock"
     *
     * \pre \c ih is a valid handler of an inode in-use
     * \pre \c buf is a valid pointer to a memory portion,
     *   at least \c BlockSize bytes long,
     *   and where the user has write permissions.
     *
     * \param [in] ih inode handler
     * \param [in] fbn file block number
     * \param [in] buf pointer to the buffer where data must be read into
     *
     * \note When calling a sofs20 function of any layer,
     *   the version with prefix \b so should be used,
     *   not the one with prefix \b grp or \b bin.
     */
    void soReadFileBlock(int ih, uint32_t fbn, void *buf);

    /* *************************************************** */

    /**
     * \anchor wfb
     *
     * \brief Write a file block.
     * \ingroup c_ilayer
     *
     * \details
     * Data is written into a specific file block of an in-use inode
     *
     * If the referred block has not been allocated yet,
     *   it should be allocated now so that the data can be stored as its contents;
     *
     * \sa \ref gfb "soGetFileBlock", \ref afb "soAllocFileBlock"
     *
     * \pre \c ih is a valid handler of an inode in-use
     *
     * \param [in] ih inode handler
     * \param [in] fbn file block number
     * \param [in] buf pointer to the buffer containing data to be written
     *
     * \note When calling a sofs20 function of any layer,
     *   the version with prefix \b so should be used,
     *   not the one with prefix \b grp or \b bin.
     */
    void soWriteFileBlock(int ih, uint32_t fbn, void *buf);

    /* *************************************************** */

};

#endif /* __SOFS20_FILEBLOCKS__ */
