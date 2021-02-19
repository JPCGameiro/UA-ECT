/**
 * \file
 * \brief Access to disk blocks at raw level
 *
 *  \author Artur Pereira - 2007-2009, 2016-2020
 *  \author Miguel Oliveira e Silva - 2009, 2017
 *  \author António Rui Borges - 2010-2015
 *
 *  \remarks In case an error occurs, every function throws an error code (an int)
 */

#ifndef __SOFS20_RAWDISK__
#define __SOFS20_RAWDISK__

#include <inttypes.h>
#include <stdlib.h>

namespace sofs20
{

    /**
     * \defgroup rawdisk rawdisk
     * \brief Access to disk blocks at raw level
     *  \remarks In case an error occurs, every function throws an error code (an int)
     * @{ 
     */

    /* ***************************************** */

    /**
     * \brief Open the storage device.
     *
     * \details
     *   A communication channel is established with the storage device.
     *   It is supposed that no communication channel was previously established.
     *   The storage file must exist and have a size multiple of the block size.
     *
     * \param [in] devname absolute path to the Linux file that simulates the storage device
     * \param [out] np if not null,
     *     pointer to a location where the number of blocks of the device is to be stored
     *
     * \note Only called, directly or indirectly, by main programs
     */
    void soOpenRawDisk(const char *devname, uint32_t * np = NULL);

    /* ***************************************** */

    /**
     * \brief Close the storage device.
     *
     * \details
     *   The communication channel previously established with the storage device is closed.
     *
     * \note Only called, directly or indirectly, by main programs
     */
    void soCloseRawDisk(void);

    /* ***************************************** */

    /**
     * \anchor rrb
     *
     * \brief Read a block of data from the storage device.
     *
     * \param [in] n physical number of the disk block to be read from
     * \param [out] buf pointer to the buffer where the data must be read into
     */
    void soReadRawBlock(uint32_t n, void *buf);

    /* ***************************************** */

    /**
     * \anchor wrb
     *
     * \brief Write a block of data from the storage device.
     *
     * \param [in] n physical number of the disk block to be written into
     * \param [in] buf pointer to the buffer containing the data to be written from
     */
    void soWriteRawBlock(uint32_t n, void *buf);

/* ***************************************** */

/** @} closing group rawdisk */

};

#endif /* __SOFS20_RAWDISK__ */
