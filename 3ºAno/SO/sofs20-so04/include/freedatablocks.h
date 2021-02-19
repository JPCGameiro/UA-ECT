/**
 * \file
 * \brief Functions to manage the list of free inodes and the list of free data blocks
 * 
 *  \author Artur Pereira 2008-2009, 2016-2020
 *  \author Miguel Oliveira e Silva 2009, 2017
 *  \author António Rui Borges - 2010-2015
 *
 */

#ifndef __SOFS20_FREE_DATABLOCKS__
#define __SOFS20_FREE_DATABLOCKS__

#include "bin_freedatablocks.h"
#include "grp_freedatablocks.h"

#include <inttypes.h>

namespace sofs20
{

    /* *************************************************** */

    /**
     * \anchor adb
     *
     * \brief Allocate a free data block.
     * \ingroup b_ilayer
     *
     * \details 
     * A data block reference is retrieved from the retrieval cache.
     *
     * \internal
     * - If the retrieval cache is empty, the replenish function must be called first.
     * - The first reference in the retrieval cache must be retrieved and returned.
     * - The data block meta data fields in the superblock must be updated.
     *
     * \throw ENOSPC if there are no free data blocks
     *
     * \remarks
     * When calling a function of any layer, the version with prefix \b so should be used,
     *   not the one with prefix \b grp or \b bin.
     *
     * \return the number (reference) of the data block allocated
     */
    uint32_t soAllocDataBlock();

    /* *************************************************** */

    /**
     * \anchor fdb
     *
     * \brief Free the referenced data block.
     * \ingroup b_ilayer
     *
     * details
     * The reference of the freed data block is inserted into the
     * list of free data blocks, in the insertion cache:
     *
     * \internal
     * - If the insertion cache is full, the deplete function must be called first.
     * - The reference must be inserted at the first empty cell.
     * - The data block meta data fields in the superblock must be updated.
     *
     * \pre \c bn must represent a valid data block number
     *
     * \throw EINVAL if data block number is not valid.
     *
     * \param [in] bn the number (reference) of the data block to be freed
     *
     * \remarks
     * When calling a function of any layer, the version with prefix \b so should be used,
     *   not the one with prefix \b grp or \b bin.
     *
     */
    void soFreeDataBlock(uint32_t bn);

    /* *************************************************** */

    /**
     * \anchor rrc
     *
     * \brief Replenish the retrieval cache
     * \ingroup b_ilayer
     *
     * \details 
     *   References to free data blocks should be transferred from the 
     *   reference table, or from the insertion cache,
     *   to the retrieval cache:
     *
     * \internal
     * - Nothing is done if the cache is not empty;
     * - The insertion cache is only used if the reference table is empty.
     * - Only a single block of the reference table is processed, 
     *   even if it is not enough to fulfill the retrieval cache:
     *   - the block processed is the first one with references;
     *   - be aware of the circularity of the reference table.
     * - After transferring a reference from A to B, the value in A must become \c BlockNullReference.
     * - The data block meta data fields in the superblock must be updated.
     * .
     *  \remarks
     *  When calling a function of any layer, the version with prefix \b so should be used,
     *    not the one with prefix \b grp or \b bin.
     *
     */
    void soReplenishRetrievalCache();

    /* *************************************************** */

    /**
     * \anchor dic
     *
     * \brief Deplete the insertion cache
     * \ingroup b_ilayer
     *
     * \details
     *   References to free data blocks should be transferred from the insertion cache
     *   to the reference table.
     *
     * \internal
     * - Nothing is done if the cache is not full.
     * - Only a single block of the reference table is processed, 
     *   even if it has no room to empty the insertion cache:
     *   - the block processed is the last with references;
     *   - make sure the cache is in a valid state, if, after the operation, it is not empty;
     *   - be aware of the circularity of the reference table.
     * - after transferring a reference from A to B, the value in A becomes BlockNullReference.
     * - The data block meta data fields in the superblock must be updated.
     * .
     * \remarks
     *   When calling a function of any layer, the version with prefix \b so should be used,
     *   not the one with prefix \b grp or \b bin.
     *
     */
    void soDepleteInsertionCache();

    /* *************************************************** */

};

#endif /* __SOFS20_FREE_DATABLOCKS__ */
