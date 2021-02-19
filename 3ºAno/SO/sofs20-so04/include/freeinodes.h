/**
 * \file
 * \brief Functions to manage the list of free inodes 
 * 
 *  \author Artur Pereira 2008-2009, 2016-2020
 *  \author Miguel Oliveira e Silva 2009, 2017
 *  \author António Rui Borges - 2010-2015
 *
 */

#ifndef __SOFS20_FREE_INODES__
#define __SOFS20_FREE_INODES__

#include "bin_freeinodes.h"
#include "grp_freeinodes.h"

#include <inttypes.h>

namespace sofs20
{

    /* *************************************************** */

    /**
     * \anchor ai
     *
     *  \brief Allocate a free inode.
     *  \ingroup a_ilayer
     *
     *  \details
     *  An inode is retrieved from the list (bitmap) of free inodes 
     *      and is properly initialized.
     *
     *  \internal
     *  - The \b ibitmap field of the superblock must be circularly searched and updated:
     *    - starting at the bit position following the one pointed to by the \b iidx superblock field,
     *    - looking for the first bit at zero.
     *    - putting it at one and returning its index, which corresponds to the inode number.
     *  - Fields \c mode, \c owner, and \c group of the allocated inode must be initialized
     *    (see unix system calls \c getuid and \c getgid).
     *  - The inode meta data fields in the superblock must be updated.
     *
     *  \sa https://www.gnu.org/software/libc/manual/html_node/Testing-File-Type.html
     *  \sa https://www.gnu.org/software/libc/manual/html_node/Permission-Bits.html
     *
     *  \pre \b type must represent either a file (\c S_IFREG), 
     *      a directory (\c S_IFDIR), or a symbolic link (\c S_IFLNK);
     *  \pre \b permissions must represent a valid permission pattern (a octal value in the
     *      range 0000 to 0777);
     *
     *  \throw ENOSPC if there are no free inodes
     *  \throw EINVAL if type or permissions are invalid
     *
     *  \param [in] mode bitwise-or of type and permissions
     *
     *  \return the reference (number) of the inode allocated
     *
     *  \note
     *  When calling a function of any layer, the version with prefix \b so should be used,
     *    not the one with prefix \b grp or \b bin.
     */
    uint16_t soAllocInode(uint32_t mode);

    /* *************************************************** */

    /**
     * \anchor fi
     *
     *  \brief Free the referenced inode.
     *  \ingroup a_ilayer
     *
     * \details
     *  The inode is cleaned, marked as free, and inserted into the list of free inodes.
     *
     *  \internal
     *  - The corresponding bit of the \c ibitmap field is put at zero.
     *  - All inode fields are put at zero, except the references, that
     *    are put at \c BlockNullReference.
     *  - The inode meta data fields in the superblock must be updated.
     *
     *  \pre \c in must represent a valid inode number
     *
     *  \throw EINVAL if inode number is not valid.
     *
     *  \param [in] in number (reference) of the inode to be freed
     *
     *  \note
     *  When calling a function of any layer, the version with prefix \b so should be used,
     *    not the one with prefix \b grp or \b bin.
     *
     */
    void soFreeInode(uint16_t in);

    /* *************************************************** */

};

#endif /* __SOFS20_FREE_INODES__ */
