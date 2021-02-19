/**
 * \file 
 * \brief Functions to manage directory entries
 *
 * \author Artur Pereira 2008-2009, 2016-2020
 * \author Miguel Oliveira e Silva 2009, 2017
 * \author António Rui Borges - 2012-2015
 *
 * \remarks In case an error occurs, every function throws an SOException
 */

#ifndef __SOFS20_DIRENTRIES__
#define __SOFS20_DIRENTRIES__

#include "bin_direntries.h"
#include "grp_direntries.h"

#include <inttypes.h>

namespace sofs20
{

    /* ************************************************** */

    /**
     * \anchor tp
     *
     * \brief Get the inode associated to a given path
     * \ingroup d_ilayer
     *
     * \details
     *   The directory hierarchy of the file system is traversed to find
     *   an entry whose name is the rightmost component of the given path
     *
     * \sa \ref gde "soGetDirentry"
     *
     * \param [in] path the path to be traversed
     * 
     * \pre \c path is an absolute path
     * \pre \c path does not contain symbolic links
     *
     * \throw ENOTDIR if one of the \c path components, with the exception of the rightmost one,
     *   is not a directory
     * \throw EACCES if the process that calls the operation does not have traverse
     *   (x) permission on all the components of the path, with
     *   exception of the rightmost one
     *
     * \return the corresponding inode number
     *
     * \note
     *   When calling a function of any layer, the version with prefix \b so should be used,
     *   not the one with prefix \b grp or \b bin.
     *
     */
    uint16_t soTraversePath(char *path);

    /* ************************************************** */

    /**
     * \anchor gde
     *
     * \brief Get the inode associated to a given name
     * \ingroup d_ilayer
     *
     * \details
     *   The directory contents, seen as an array of directory entries,
     *   is searched for an entry whose name is the given one.
     *
     * \pre \c pih is a valid inode handler of a directory
     * \pre \c name is a valid <em>base name</em> (it doesn't contains '/')
     *
     * \param [in] pih inode handler of the parent directory
     * \param [in] name the name of the entry to be searched for
     *
     * \return the corresponding inode number or InodeNullReference, if \c name doesn't exist
     *
     * \note
     *   When calling a function of any layer, the version with prefix \b so should be used,
     *   not the one with prefix \b grp or \b bin.
     *
     */
    uint16_t soGetDirentry(int pih, const char *name);

    /* ************************************************** */

    /**
     * \anchor ade
     *
     * \brief Add a new entry to the parent directory.
     * \ingroup d_ilayer
     *
     * \details
     *   A direntry is added connecting the parent inode to the child inode.
     *
     * \pre \c pih is a valid inode handler of a directory where the user has write access
     * \pre \c cin is a valid inode number
     * \pre \c name is valid and doesn't exist yet
     *
     * \param [in] pih inode handler of the parent inode
     * \param [in] name the name of the entry to be created
     * \param [in] cin inode number of the entry to be created
     * 
     * \note The \c lnkcnt of the child inode is not to be changed by this function.
     * \note
     *   When calling a function of any layer, the version with prefix \b so should be used,
     *   not the one with prefix \b grp or \b bin.
     *
     */
    void soAddDirentry(int pih, const char *name, uint16_t cin);

    /* ************************************************** */

    /**
     * \anchor dde
     *
     * \brief Delete an entry from a parent directory.
     * \ingroup d_ilayer
     *
     * \details
     *   The direntry associated from the given name is deleted.
     *   - If the entry is not the last one, the last one must be moved to the empty slot.
     *   - If the last data block becomes empty, it must be freed.
     *
     * \pre \c pih is a valid inode handler of a directory where the user has write access
     * \pre \c name is valid and exists
     *
     * \param [in] pih inode handler of the parent inode
     * \param [in] name name of the entry
     *
     * \return the inode number of the deleted entry
     *
     * \note The \c lnkcnt of the child inode is not to be changed by this function.
     * \note
     *   When calling a function of any layer, the version with prefix \b so should be used,
     *   not the one with prefix \b grp or \b bin.
     *
     */
    uint16_t soDeleteDirentry(int pih, const char *name);

    /* ************************************************** */

    /**
     * \anchor rde
     *
     * \brief Rename an entry of a directory.
     * \ingroup d_ilayer
     *
     * \details
     *   A direntry associated from the given directory is renamed.
     *   - The renaming can not be done deleting the entry and adding a new one.
     *
     * \pre \c pih is a valid inode handler of a directory where the user has write access
     * \pre \c name is valid and exists
     * \pre \c newName is valid and doesn't exist yet
     *
     * \param [in] pih inode handler of the parent inode
     * \param [in] name current name of the entry
     * \param [in] newName new name for the entry
     *
     * \note
     *   When calling a function of any layer, the version with prefix \b so should be used,
     *   not the one with prefix \b grp or \b bin.
     *
     */
    void soRenameDirentry(int pih, const char *name, const char *newName);

    /* ************************************************** */

    /**
     * \anchor cde
     *
     * \brief Check directory emptiness
     * \ingroup d_ilayer
     *
     * \details
     *   The directory is traversed to verified if the only existing entries are "." and "..".
     *
     * \pre \c pih is a valid inode handler of a directory
     *
     * \param [in] pih handler of the inode to be checked
     * 
     * \note
     *   When calling a function of any layer, the version with prefix \b so should be used,
     *   not the one with prefix \b grp or \b bin.
     *
     */
    bool soCheckDirEmpty(int pih);

    /* ************************************************** */

};

#endif /* __SOFS20_DIRENTRIES__ */
