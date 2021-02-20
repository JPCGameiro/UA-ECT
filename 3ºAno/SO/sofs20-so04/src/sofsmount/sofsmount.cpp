
/**
 *  \defgroup tools tools
 *  \brief The \b sofs20 suppoting tools.
 *
 *  \author Artur Pereira - 2005--2020
 *  \author Miguel Oliveira e Silva -  2009, 2017
 *  \author João Rodrigues - 2009
 *  \author António Rui Borges - 2010--2015
 */

/**
 *  \defgroup sofsmount sofsmount
 *  \ingroup tools
 *  \brief The \b sofs20 mounting program.
 * 
 *  \details 
 *      It provides a simple method to integrate the sofs20 file system into Linux.
 *
 */

#include <stdio.h>
#include <stdlib.h>
#include <inttypes.h>
#define __STDC_FORMAT_MACROS
#include <unistd.h>
#include <libgen.h>
#include <pthread.h>
#include <errno.h>
#include <string.h>
#include <fuse.h>
#include <fuse/fuse.h>

#include "core.h"
#include "devtools.h"
#include "syscalls.h"

using namespace sofs20;

/* ***************************************************** */

/*
 *  Access with mutual exclusion to some of the operations
 */
static pthread_mutex_t accessCR = PTHREAD_MUTEX_INITIALIZER;    /* locking flag */

/* ***************************************************** */

/* SOFS20 disk filename (should be the absolute path) */
static char *sofs_supp_file = NULL;


/* ***************************************************** */

/*
 *  \brief Mount the filesystem.
 *
 *  The return value will be passed in the private_data field of fuse_context to all 
 *  file operations and as a parameter to the destroy () method.
 *
 *  \remarks Introduced in version 2.3 and changed in version 2.6.
 *  \param fci pointer to fuse connection information
 *  \return pointer to the path of the disk file
 */
static void *sofs_mount(struct fuse_conn_info *fci)
{
    fprintf(stderr, "=============================================\n");
    soProbe(SOPROBE_GREEN, 11, "%s()\n", __FUNCTION__);

    int stat;
    if ((stat = soOpenFileSystem(sofs_supp_file)) != 0)
        return NULL;
    return sofs_supp_file;
}

/* ***************************************************** */

/*
 *  \brief Unmount the filesystem.
 *
 *  Called on filesystem exit.
 *
 *  \remarks Introduced in version 2.3.
 *
 *  \param path pointer to the path of the disk file
 */
static void sofs_unmount(void *path)
{
    fprintf(stderr, "=============================================\n");
    soProbe(SOPROBE_GREEN, 11, "%s(\"%s\")\n", __FUNCTION__, (char *)path);

    pthread_mutex_lock(&accessCR);
    soCloseFileSystem();
    pthread_mutex_unlock(&accessCR);
}

/* ***************************************************** */

/*
 *  \brief Get file status.
 *
 *  Similar to stat (man 2 stat).
 *
 *  The 'st_dev' and 'st_blksize' fields are ignored. 
 *  The 'st_ino' field is ignored except if the 'use_ino'
 *  mount option is given.
 *
 *  \param path pointer to path
 *  \param st pointer to stat structure
 *
 *  \return 0, on success, and a negative value, on error
 */
static int sofs_getattr(const char *path, struct stat *st)
{
    fprintf(stderr, "=============================================\n");
    soProbe(SOPROBE_GREEN, 11, "%s(\"%s\", %p)\n", __FUNCTION__, path, st);

    pthread_mutex_lock(&accessCR);
    int ret = soStat(path, st);
    pthread_mutex_unlock(&accessCR);
    return ret;
}

/* ***************************************************** */

/*
 *  Check file access permissions.
 *
 *  Equivalent to the system call access (man 2 access).
 *
 *  \remarks Introduced in version 2.5.
 *
 *  \param path pointer to path
 *  \param opRequested operation to be performed
 *
 *  \return 0, on success, and a negative value, on error
 */
static int sofs_access(const char *path, int opRequested)
{
    fprintf(stderr, "=============================================\n");
    soProbe(SOPROBE_GREEN, 11, "%s(\"%s\", %x)\n", __FUNCTION__, path, opRequested);

    pthread_mutex_lock(&accessCR);
    int ret = soAccess(path, opRequested);
    pthread_mutex_unlock(&accessCR);
    return ret;
}

/* ***************************************************** */

/*
 *  \brief Create a file node.
 *
 *  Similar to system call mknod (man 2 mknod).
 *
 *  This is called for creation of all non-directory, non-symlink nodes.
 *  If the filesystem defines a
 *  create () method, then for regular files that will be called instead.
 *
 *  \param path path to the file
 *  \param mode type and permissions to be set
 *  \param rdev raw device id
 *
 *  \return 0, on success, and a negative value, on error
 */
static int sofs_mknod(const char *path, mode_t mode, dev_t rdev)
{
    fprintf(stderr, "=============================================\n");
    soProbe(SOPROBE_GREEN, 11, "%s(\"%s\", %x, %x)\n", __FUNCTION__, path, 
                (uint32_t) mode, (uint32_t) rdev);

    pthread_mutex_lock(&accessCR);
    int ret = soMknod(path, mode);
    pthread_mutex_unlock(&accessCR);
    return ret;
}

/* ***************************************************** */

/*
 *  \brief Create a directory.
 *
 *  Similar to system call mkdir (man 2 mkdir).
 *
 *  \param path path to the file
 *  \param mode permissions to be set
 *
 *  \return 0, on success, and a negative value, on error
 */
static int sofs_mkdir(const char *path, mode_t mode)
{
    fprintf(stderr, "=============================================\n");
    soProbe(SOPROBE_GREEN, 11, "%s(\"%s\", %x)\n", __FUNCTION__, path, (uint32_t) mode);

    pthread_mutex_lock(&accessCR);
    int ret = soMkdir(path, mode);
    pthread_mutex_unlock(&accessCR);
    return ret;
}

/* ***************************************************** */

/*
 *  \brief Remove a regular file.
 *
 *  Similar to system call unlink (man 2 unlink).
 *
 *  \param path path to the file
 *
 *  \return 0, on success, and a negative value, on error
 */
static int sofs_unlink(const char *path)
{
    fprintf(stderr, "=============================================\n");
    soProbe(SOPROBE_GREEN, 11, "%s(\"%s\")\n", __FUNCTION__, path);

    pthread_mutex_lock(&accessCR);
    int ret = soUnlink(path);
    pthread_mutex_unlock(&accessCR);
    return ret;
}

/* ***************************************************** */

/*
 *  \brief Remove a directory.
 *
 *  Similar to system call rmdir (man 2 rmdir).
 *
 *  \param path path to the file
 *
 *  \return 0, on success, and a negative value, on error
 */
static int sofs_rmdir(const char *path)
{
    fprintf(stderr, "=============================================\n");
    soProbe(SOPROBE_GREEN, 11, "%s(\"%s\")\n", __FUNCTION__, path);

    pthread_mutex_lock(&accessCR);
    int ret = soRmdir(path);
    pthread_mutex_unlock(&accessCR);
    return ret;
}

/* ***************************************************** */

/*
 *  \brief Rename a file.
 *
 *  Similar to system call rename (man 2 rename).
 *
 *  \param path path to an existing file
 *  \param newPath new path to the same file in replacement of the old one
 *
 *  \return 0, on success, and a negative value, on error
 */
static int sofs_rename(const char *path, const char *newPath)
{
    fprintf(stderr, "=============================================\n");
    soProbe(SOPROBE_GREEN, 11, "%s(\"%s\", \"%s\")\n", __FUNCTION__, path, newPath);

    pthread_mutex_lock(&accessCR);
    int ret = soRename(path, newPath);
    pthread_mutex_unlock(&accessCR);
    return ret;
}

/* ***************************************************** */

/* \brief Create a hard link to a file.
 *
 *  Similar to system call link (man 2 link).
 *
 *  \param path path to an existing file
 *  \param newPath new path to the same file
 *
 *  \return 0, on success, and a negative value, on error
 */
static int sofs_link(const char *path, const char *newPath)
{
    fprintf(stderr, "=============================================\n");
    soProbe(SOPROBE_GREEN, 11, "%s(\"%s\", \"%s\")\n", __FUNCTION__, path, newPath);

    pthread_mutex_lock(&accessCR);
    int ret = soLink(path, newPath);
    pthread_mutex_unlock(&accessCR);
    return ret;
}

/* ***************************************************** */

/* \brief Change the permission bits of a file.
 *
 *  Similar to system call chmod (man 2 chmod).
 *
 *  \param path path to the file
 *  \param mode permissions to be set
 *
 *  \return 0, on success, and a negative value, on error
 */
static int sofs_chmod(const char *path, mode_t mode)
{
    fprintf(stderr, "=============================================\n");
    soProbe(SOPROBE_GREEN, 11, "%s(\"%s\", 0%o)\n", __FUNCTION__, path, (uint32_t) mode);

    pthread_mutex_lock(&accessCR);
    int ret = soChmod(path, mode);
    pthread_mutex_unlock(&accessCR);
    return ret;
}

/* ***************************************************** */

/* \brief Change the owner and group of a file.
 *
 *  Similar to system call chown (man 2 chown).
 *
 *  \param path path to the file
 *  \param owner file user id (-1, if user is not to be changed)
 *  \param group file group id (-1, if group is not to be changed)
 *
 *  \return 0, on success, and a negative value, on error
 */
static int sofs_chown(const char *path, uid_t owner, gid_t group)
{
    fprintf(stderr, "=============================================\n");
    soProbe(SOPROBE_GREEN, 11, "%s(\"%s\", %" PRIu32 ", %" PRIu32 ")\n", __FUNCTION__,
            path, (uint32_t) owner, (uint32_t) group);

    pthread_mutex_lock(&accessCR);
    int ret = soChown(path, owner, group);
    pthread_mutex_unlock(&accessCR);
    return ret;
}

/* ***************************************************** */

/* \brief Change the length of a file.
 *
 *  Similar to system call truncate (man 2 truncate).
 *
 *  \param path path to the file
 *  \param length new size for the regular size
 *
 *  \return 0, on success, and a negative value, on error
 */
static int sofs_truncate(const char *path, off_t length)
{
    fprintf(stderr, "=============================================\n");
    soProbe(SOPROBE_GREEN, 11, "%s(\"%s\", %u)\n", __FUNCTION__, path, (uint32_t) length);

    pthread_mutex_lock(&accessCR);
    int ret = soTruncate(path, length);
    pthread_mutex_unlock(&accessCR);
    return ret;
}

/* ***************************************************** */

/* \brief Change the access and/or modification times of a file.
 *
 *  Similar to system call utime (man 2 utime).
 *
 *  Change the access and modification times of a file with nanosecond resolution.
 *
 *  \param path path to the file
 *  \param times pointer to a structure where the last access and modification times are passed,
 *      if \c NULL, the last access and modification times are set to the current time
 *
 *  \return 0, on success, and a negative value, on error
 */
static int sofs_utime(const char *path, struct utimbuf *times)
{
    fprintf(stderr, "=============================================\n");
    soProbe(SOPROBE_GREEN, 11, "%s(\"%s\", %p)\n", __FUNCTION__, path, times);

    pthread_mutex_lock(&accessCR);
    int ret = soUtime(path, times);
    pthread_mutex_unlock(&accessCR);
    return ret;
}

/* ***************************************************** */

/*
 *  \brief Get file system statistics.
 *
 *  Equivalent to system call statvfs (man 2 statvfs).
 *
 *  The 'f_type' and 'f_fsid' fields are ignored.
 *
 *  \param path path to any file within the mounted file system
 *  \param st pointer to a statvfs structure
 *
 *  \return 0, on success, and a negative value, on error
 */
static int sofs_statfs(const char *path, struct statvfs *st)
{
    fprintf(stderr, "=============================================\n");
    soProbe(SOPROBE_GREEN, 11, "%s(\"%s\", %p)\n", __FUNCTION__, path, st);

    pthread_mutex_lock(&accessCR);
    int ret = soStatFS(path, st);
    pthread_mutex_unlock(&accessCR);
    return ret;
}

/* ***************************************************** */

/*
 *  \brief File open operation.
 *
 *  Equivalent to system call open (man 2 open).
 *
 *  No creation, or truncation flags (O_CREAT, O_EXCL, O_TRUNC) will be passed to open().
 *  Open should check if the
 *  operation is permitted for the given flags.
 *  Optionally open may also return an arbitrary filehandle in the
 *  fuse_file_info structure, which will be passed to all file operations.
 *
 *  \remarks Changed in version 2.2.
 *
 *  \param path path to the file
 *  \param fi pointer to fuse file information
 *
 *  \return 0, on success, and a negative value, on error
 */
static int sofs_open(const char *path, struct fuse_file_info *fi)
{
    fprintf(stderr, "=============================================\n");
    soProbe(SOPROBE_GREEN, 11, "%s(\"%s\", %p)\n", __FUNCTION__, path, fi);

    pthread_mutex_lock(&accessCR);
    int ret = soOpen(path, fi->flags);
    fi->fh = (uint64_t) 0;
    pthread_mutex_unlock(&accessCR);
    return ret;
}

/* ***************************************************** */

/*
 *  \brief Read data from an open file.
 *
 *  Equivalent to system call read (man 2 read).
 *
 *  Read should return exactly the number of bytes requested except on EOF or error,
 *  otherwise the rest of the data will be substituted with zeroes.
 *  An exception to this is when the 'direct_io' mount option is specified, in which
 *  case the return value of the read system call will reflect the return value of this operation.
 *
 *  \remarks Changed in version 2.2.
 *
 *  \param path path to the file
 *  \param buff pointer to the buffer where data to be read is to be stored
 *  \param count number of bytes to be read
 *  \param pos starting [byte] position in the file data continuum where data is to be read from
 *  \param fi pointer to fuse file information
 *
 *  \return number of bytes effectively read, on success, and a negative value, on error
 */
static int sofs_read(const char *path, char *buff, size_t count, off_t pos, struct fuse_file_info *fi)
{
    fprintf(stderr, "=============================================\n");
    soProbe(SOPROBE_GREEN, 11, "%s(\"%s\", %p, %" PRIu32 ", %" PRId32 ", %p)\n", __FUNCTION__, 
            path, buff, (uint32_t) count, (int32_t) pos, fi);

    pthread_mutex_lock(&accessCR);
    int n = soRead(path, buff, (uint32_t) count, (int32_t) pos);
    pthread_mutex_unlock(&accessCR);
    return n;
}

/* ***************************************************** */

/*
 *  \brief Write data to an open file.
 *
 *  Equivalent to system call write (man 2 write).
 *
 *  Write should return exactly the number of bytes requested except on error.
 *  An exception to this is when the
 *  'direct_io' mount option is specified (see read operation).
 *
 *  \remarks Changed in version 2.2.
 *
 *  \param path path to the file
 *  \param buff pointer to the buffer where data to be written is stored
 *  \param count number of bytes to be read
 *  \param pos starting [byte] position in the file data continuum where data is to be read from
 *  \param fi pointer to fuse file information
 *
 *  \return number of bytes effectively written, on success, and a negative value, on error
 */
static int sofs_write(const char *path, const char *buff, size_t count, off_t pos, struct fuse_file_info *fi)
{
    fprintf(stderr, "=============================================\n");
    soProbe(SOPROBE_GREEN, 11, "%s(\"%s\", %p, %" PRIu32 ", %" PRId32 ", %p)\n", __FUNCTION__, 
            path, buff, (uint32_t) count, (int32_t) pos, fi);

    pthread_mutex_lock(&accessCR);
    int n = soWrite(path, (void *)buff, (uint32_t) count, (int32_t) pos);
    pthread_mutex_unlock(&accessCR);
    return n;
}

/* ***************************************************** */

/*
 *  \brief Possibly flush cached data.
 *
 *  Equivalent to system call close (man 2 close).
 *
 *  BIG NOTE: This is not equivalent to fsync(). It's not a request to sync dirty data.
 *
 *  Flush is called on each close() of a file descriptor.
 *  So if a filesystem wants to return write errors in close() and the file has cached dirty data,
 *  this is a good place to write back data and return any errors.  Since many
 *  applications ignore close() errors this is not always useful.
 *
 *  NOTE: The flush() method may be called more than once for each  open().
 *  This happens if more than one file
 *  descriptor refers to an opened file due to dup(), dup2() or fork() calls.
 *  It is not possible to determine if a
 *  flush is final, so each flush should be treated equally.
 *  Multiple write-flush sequences are relatively rare, so
 *  this shouldn't be a problem.
 *
 *  Filesystems shouldn't assume that flush will always be called after some writes,
 *  or that if will be called at all.
 *
 *  \remarks Changed in version 2.2.
 *
 *  \param path path to the file
 *  \param fi pointer to fuse file information
 *
 *  \return 0, on success, and a negative value, on error
 */
static int sofs_flush(const char *path, struct fuse_file_info *fi)
{
    fprintf(stderr, "=============================================\n");
    soProbe(SOPROBE_GREEN, 11, "%s(\"%s\", %p)\n", __FUNCTION__, path, fi);

    //pthread_mutex_lock(&accessCR);
    //pthread_mutex_unlock(&accessCR);
    return 0;
}

/* ***************************************************** */

/*
 *  \brief Release an open file.
 *
 *  Release is called when there are no more references to an open file:
 *  all file descriptors are closed and all memory mappings are unmapped.
 *
 *  For every open() call there will be exactly one release() call with the same flags and
 *  file descriptor.
 *  It is possible to have a file opened more than once, 
 *  in which case only the last release will mean,
 *  that no more reads/writes will happen on the file.
 *  The return value of release is ignored.
 *
 *  \remarks Changed in version 2.2.
 *
 *  \param path path to the file
 *  \param fi pointer to fuse file information
 *
 *  \return 0, on success, and a negative value, on error
 */
static int sofs_release(const char *path, struct fuse_file_info *fi)
{
    fprintf(stderr, "=============================================\n");
    soProbe(SOPROBE_GREEN, 11, "%s(\"%s\", %p)\n", __FUNCTION__, path, fi);

    pthread_mutex_lock(&accessCR);
    int ret = soClose(path);
    pthread_mutex_unlock(&accessCR);
    return ret;
}

/* ***************************************************** */

/*
 *  \brief Synchronize file contents.
 *
 *  Equivalent to system calls fsync and fdatasync (man 2 fsync and fdatasync).
 *
 *  If the datasync parameter is non-zero, then only the user data should be flushed,
 *  not the meta data.
 *
 *  \remarks Changed in version 2.2.
 *
 *  \param path path to the file
 *  \param isdatasync flag signaling if only the user data should be flushed
 *  \param fi pointer to fuse file information
 *
 *  \return 0, on success, and a negative value, on error
 */
static int sofs_fsync(const char *path, int isdatasync, struct fuse_file_info *fi)
{
    fprintf(stderr, "=============================================\n");
    soProbe(SOPROBE_GREEN, 11, "%s(\"%s\", %d, %p)\n", __FUNCTION__, path, isdatasync, fi);

    pthread_mutex_lock(&accessCR);
    int ret = soFsync(path);
    pthread_mutex_unlock(&accessCR);
    return ret;
}

/* ***************************************************** */

/*
 *  \brief Open directory.
 *
 *  Equivalent to opendir (man 3 opendir).
 *
 *  This method should check if the open operation is permitted for this  directory
 *
 *  \remarks Introduced in version 2.3.
 *
 *  \param path path to the file
 *  \param fi pointer to fuse file information
 *
 *  \return 0, on success, and a negative value, on error
 */
static int sofs_opendir(const char *path, struct fuse_file_info *fi)
{
    fprintf(stderr, "=============================================\n");
    soProbe(SOPROBE_GREEN, 11, "%s(\"%s\", %p)\n", __FUNCTION__, path, fi);

    pthread_mutex_lock(&accessCR);
    int ret = soOpendir(path);
    fi->fh = (uint64_t) 0;
    pthread_mutex_unlock(&accessCR);
    return ret;
}

/* ***************************************************** */

/*
 *  \brief Read directory.
 *
 *  Equivalent to readdir (man 3 readdir).
 *
 *  This supersedes the old getdir() interface.  New applications should use this.
 *
 *  The filesystem may choose between two modes of operation:
 *
 *  1) The readdir implementation ignores the offset parameter,
 *      and passes zero to the filler function's offset.
 *      The filler function will not return '1' (unless an error happens),
 *      so the whole directory is read in a single
 *      readdir operation. This works just like the old getdir() method.
 *
 *  2) The readdir implementation keeps track of the offsets of the directory entries.
 *      It uses the offset parameter and always passes non-zero offset to the filler function.
 *      When the buffer is full (or an error happens) the filler function will return '1'.
 *
 *  \remarks Introduced in version 2.3.
 *
 *  \param path path to the file
 *  \param buf pointer to the buffer where data to be read is to be stored
 *  \param filler pointer to the filler function
 *  \param offset starting [byte] position in the file data continuum where data is to be read from
 *  \param fi pointer to fuse file information
 *
 *  \return 0, on success, and a negative value, on error
 */
static int sofs_readdir(const char *path, void *buf, fuse_fill_dir_t filler, off_t offset, struct fuse_file_info *fi)
{
    fprintf(stderr, "=============================================\n");
    soProbe(SOPROBE_GREEN, 11, "%s(\"%s\", %p, %p, %" PRId32 ", %p)\n", __FUNCTION__,
            path, buf, filler, (int32_t) offset, fi);

    pthread_mutex_lock(&accessCR);

    char name[SOFS20_FILENAME_LEN + 1];
    int stat = soReaddir(path, name, (int32_t) offset);
    if (stat > 0)
    {
        offset += stat;
        stat = filler(buf, name, NULL, offset);
    }

    pthread_mutex_unlock(&accessCR);
    return stat;
}

/* ***************************************************** */

/*
 *  \brief Release directory.
 *
 *  Equivalent to closedir (man 3 closedir).
 *
 *  \remarks Introduced in version 2.3.
 *
 *  \param path path to the file
 *  \param fi pointer to fuse file information
 *
 *  \return 0, on success, and a negative value, on error
 */
static int sofs_releasedir(const char *path, struct fuse_file_info *fi)
{
    fprintf(stderr, "=============================================\n");
    soProbe(SOPROBE_GREEN, 11, "%s(\"%s\", %p)\n", __FUNCTION__, path, fi);

    pthread_mutex_lock(&accessCR);
    int ret = soClosedir(path);
    pthread_mutex_unlock(&accessCR);
    return ret;
}

/* ***************************************************** */

/*
 *  \brief Synchronize directory contents.
 *
 *  If the datasync parameter is non-zero, then only the user data should be flushed,
 *  not the meta data.
 *
 *  \remarks Introduced in version 2.3.
 *
 *  \param path path to the file
 *  \param isdatasync flag signaling if only the user data should be flushed
 *  \param fi pointer to fuse file information
 *
 *  \return 0, on success, and a negative value, on error
 */
static int sofs_fsyncdir(const char *path, int isdatasync, struct fuse_file_info *fi)
{
    fprintf(stderr, "=============================================\n");
    soProbe(SOPROBE_GREEN, 11, "%s(\"%s\", %d, %p)\n", __FUNCTION__, path, isdatasync, fi);

    pthread_mutex_lock(&accessCR);
    int ret = soFsync(path);
    pthread_mutex_unlock(&accessCR);
    return ret;
}

/* ***************************************************** */

/*
 *  \brief Create a symbolic link.
 *
 *  Similar to system call symlink (man 2 symlink).
 *
 *  \param effPath path to be stored in the symbolic link file
 *  \param path path to the symbolic link
 *
 *  \return 0, on success, and a negative value, on error
 */
static int sofs_symlink(const char *effPath, const char *path)
{
    fprintf(stderr, "=============================================\n");
    soProbe(SOPROBE_GREEN, 11, "%s(\"%s\", \"%s\")\n", __FUNCTION__, effPath, path);

    pthread_mutex_lock(&accessCR);
    int ret = soSymlink(effPath, path);
    pthread_mutex_unlock(&accessCR);
    return ret;
}

/* ***************************************************** */

/*
 *  \brief Read the target of a symbolic link.
 *
 *  Similar to system call readlink (man 2 readlink).
 *
 *  The buffer should be filled with a null terminated string.
 *  The buffer size argument includes the space
 *  for the terminating null character. If the linkname is too long to fit in the buffer,
 *  it should be truncated. The return value should be 0 for success.
 *
 *  \param path path to the symbolic link
 *  \param buf pointer to the buffer where data to be read is to be stored
 *  \param size buffer size in bytes
 *
 *  \return 0, on success, and a negative value, on error
 */
static int sofs_readlink(const char *path, char *buf, size_t size)
{
    fprintf(stderr, "=============================================\n");
    soProbe(SOPROBE_GREEN, 11, "%s(\"%s\", %p, %" PRIu32 ")\n", __FUNCTION__, path, buf, (uint32_t) size);

    pthread_mutex_lock(&accessCR);
    /*int ret = */ soReadlink(path, buf, size);
    pthread_mutex_unlock(&accessCR);
    return 0;
}

/* ***************************************************** */

/* ***************************************************** */

/*
 *  \brief Set extended attributes.
 *
 *  Equivalent to setxattr (man 2 setxattr).
 *
 *  \remarks UNIMPLEMENTED.
 */

static int sofs_setxattr(const char *path, const char *name, const char *value, size_t size, int flags)
{
    fprintf(stderr, "=============================================\n");
    soProbe(SOPROBE_GREEN, 11, "%s(\"%s\", \"%s\", %p, %" PRIu32 ", %d)\n", __FUNCTION__,
            path, name, value, (uint32_t) size, flags);

    return -ENOSYS;
}

/*
 *  \brief Get extended attributes.
 *
 *  Equivalent to getxattr (man 2 getxattr).
 *
 *  \remarks UNIMPLEMENTED.
 */

/* ***************************************************** */

static int sofs_getxattr(const char *path, const char *name, char *value, size_t size)
{
    fprintf(stderr, "=============================================\n");
    soProbe(SOPROBE_GREEN, 11, "%s(\"%s\", \"%s\", %p, %" PRIu32 ")\n", __FUNCTION__,
            path, name, value, (uint32_t) size);

    return -ENOSYS;
}

/*
 *  \brief List extended attributes.
 *
 *  Equivalent to listxattr (man 2 listxattr).
 *
 *  \remarks UNIMPLEMENTED.
 */

static int sofs_listxattr(const char *path, char *list, size_t size)
{
    fprintf(stderr, "=============================================\n");
    soProbe(SOPROBE_GREEN, 11, "%s(\"%s\", \"%s\", %" PRIu32 ")\n", __FUNCTION__, path, list, (uint32_t) size);

    return -ENOSYS;
}

/* ***************************************************** */

/*
 *  \brief Remove extended attributes.
 *
 *  Equivalent to removexattr (man 2 removexattr).
 *
 *  \remarks UNIMPLEMENTED.
 */

int sofs_removexattr(const char *path, const char *name)
{
    fprintf(stderr, "=============================================\n");
    soProbe(SOPROBE_GREEN, 11, "%s(\"%s\", \"%s\")\n", __FUNCTION__, path, name);

    return -ENOSYS;
}

/* ***************************************************** */

/*
 *  \brief Get directory contents.
 *
 *  \remarks Deprecated, use readdir() instead.
 */

static int sofs_getdir(const char *path, fuse_dirh_t handle, fuse_dirfil_t filler)
{
    fprintf(stderr, "=============================================\n");
    soProbe(SOPROBE_GREEN, 11, "%s(\"%s\", ...)\n", __FUNCTION__, path);

    return -ENOSYS;
}

/* ***************************************************** */

/*
 *  Set of FUSE operations (required by the FUSE filesystem)
 */
const struct fuse_operations sofs20_fuse_operations = {
    getattr:sofs_getattr,
    readlink:sofs_readlink,
    getdir:sofs_getdir,           /* deprecated */
    mknod:sofs_mknod,
    mkdir:sofs_mkdir,
    unlink:sofs_unlink,
    rmdir:sofs_rmdir,
    symlink:sofs_symlink,
    rename:sofs_rename,
    link:sofs_link,
    chmod:sofs_chmod,
    chown:sofs_chown,
    truncate:sofs_truncate,
    utime:sofs_utime,             /* deprecated */
    open:sofs_open,
    read:sofs_read,
    write:sofs_write,
    statfs:sofs_statfs,
    flush:sofs_flush,
    release:sofs_release,
    fsync:sofs_fsync,
    setxattr:sofs_setxattr,
    getxattr:sofs_getxattr,
    listxattr:sofs_listxattr,
    removexattr:sofs_removexattr,
    opendir:sofs_opendir,
    readdir:sofs_readdir,
    releasedir:sofs_releasedir,
    fsyncdir:sofs_fsyncdir,
    init:sofs_mount,
    destroy:sofs_unmount,
    access:sofs_access,
    create:NULL,
    ftruncate:NULL,
    fgetattr:NULL,
    lock:NULL,
    utimens:NULL,
    bmap:NULL,
    flag_nullpath_ok:0,
    flag_nopath:0,
    flag_utime_omit_ok:0,
    flag_reserved:0,
    ioctl:NULL,
    poll:NULL
};

/* The main function */

/* ***************************************************** */

/*
 * print help message
 */
static void printUsage(char *cmd_name)
{
    printf("Sinopsis: %s [OPTIONS] supp-file mount-point\n"
           "  OPTIONS:\n"
           "  -d          --- set debugging mode (default: no debugging)\n"
           "  -p num-num  --- set probe ID range (default: 0-0)\n"
           "  -A num-num  --- add range of IDs to probe configuration\n"
           "  -R num-num  --- remove range of IDs from probe configuration\n"
           "  -L file     --- log file (default: stdout)\n"
           "  -b          --- set bin configuration to 600-699\n"
           "  -w          --- set bin configuration to 0-0 (default)\n"
           "  -a num-num  --- add range of IDs to bin configuration\n"
           "  -r num-num  --- remove range of IDs from bin configuration\n"
           "  -h          --- print this help\n", cmd_name);
}

/* ***************************************************** */

int main(int argc, char *argv[])
{
    bool debug_mode = false;    /* debugging mode? */
    FILE *probeStream = NULL;   /* probe stream */

    /* process command line options */
    int opt;
    while ((opt = getopt(argc, argv, "P:p:A:R:bwa:r:dh")) != -1)
    {
        switch (opt)
        {
            case 'P':          /* probe file */
            {
                if ((probeStream = fopen(optarg, "w")) == NULL)
                {
                    fprintf(stderr, "%s: Can't open probe file \"%s\".\n", basename(argv[0]), optarg);
                    printUsage(basename(basename(argv[0])));
                    return EXIT_FAILURE;
                }
                soProbeStream(probeStream);
                break;
            }
            case 'p':          /* set ID range to probing system */
            {
                uint32_t lower, upper;
                uint32_t cnt = 0;
                if ( (sscanf(optarg, "%d%*[,-]%d %n", &lower, &upper, &cnt) != 2) 
                        or (cnt != strlen(optarg)) )
                {
                    fprintf(stderr, "%s: Bad argument to 'p' option.\n", basename(argv[0]));
                    printUsage(basename(argv[0]));
                    return EXIT_FAILURE;
                }
                soProbeAddIDs(lower, upper);
                if (probeStream == NULL)
                {
                    probeStream = stdout;
                    soProbeStream(stdout);
                }
                break;
            }
            case 'A':          /* add IDs to probe conf */
            {
                uint32_t lower, upper;
                uint32_t cnt = 0;
                if ( (sscanf(optarg, "%d%*[,-]%d %n", &lower, &upper, &cnt) != 2) 
                        or (cnt != strlen(optarg)) )
                {
                    fprintf(stderr, "%s: Bad argument to 'A' option.\n", basename(argv[0]));
                    printUsage(basename(argv[0]));
                    return EXIT_FAILURE;
                }
                soProbeAddIDs(lower, upper);
                if (probeStream == NULL)
                {
                    probeStream = stdout;
                    soProbeStream(stdout);
                }
                break;
            }
            case 'R':          /* remove IDs from probe conf */
            {
                uint32_t lower, upper;
                uint32_t cnt = 0;
                if ( (sscanf(optarg, "%d-%d %n", &lower, &upper, &cnt) != 2) 
                        or (cnt != strlen(optarg)) )
                {
                    fprintf(stderr, "%s: Bad argument to 'R' option.\n", basename(argv[0]));
                    printUsage(basename(argv[0]));
                    return EXIT_FAILURE;
                }
                soProbeRemoveIDs(lower, upper);
                if (probeStream == NULL)
                {
                    probeStream = stdout;
                    soProbeStream(stdout);
                }
                break;
            }
            case 'b':          /* set binary mode: all functios binary */
            {
                soBinSetIDs(200, 799);;
                break;
            }
            case 'w':          /* set group mode: nobinary functios */
            {
                soBinSetIDs(0, 0);;
                break;
            }
            case 'a':          /* add IDs to bynary conf */
            {
                uint32_t lower, upper;
                uint32_t cnt = 0;
                if ( (sscanf(optarg, "%d%*[,-]%d %n", &lower, &upper, &cnt) != 2) 
                        or (cnt != strlen(optarg)) )
                {
                    fprintf(stderr, "%s: Bad argument to 'a' option.\n", basename(argv[0]));
                    printUsage(basename(argv[0]));
                    return EXIT_FAILURE;
                }
                soBinAddIDs(lower, upper);
                break;
            }
            case 'r':          /* remove IDs from bynary conf */
            {
                uint32_t lower, upper;
                uint32_t cnt = 0;
                if ( (sscanf(optarg, "%d-%d %n", &lower, &upper, &cnt) != 2) 
                        or (cnt != strlen(optarg)) )
                {
                    fprintf(stderr, "%s: Bad argument to 'r' option.\n", basename(argv[0]));
                    printUsage(basename(argv[0]));
                    return EXIT_FAILURE;
                }
                soBinRemoveIDs(lower, upper);
                break;
            }
            case 'd':          /* debugging mode */
            {
                debug_mode = true;
                break;
            }
            case 'h':          /* help mode */
            {
                printUsage(basename(argv[0]));
                return EXIT_SUCCESS;
            }
            default:
            {
                fprintf(stderr, "%s: Wrong option.\n", basename(argv[0]));
                printUsage(basename(argv[0]));
                return EXIT_FAILURE;
            }
        }
    }

    /* check existence of mandatory argument: storage device name */
    if ((argc - optind) != 2)
    {
        fprintf(stderr, "%s: Wrong number of mandatory arguments.\n", basename(argv[0]));
        printUsage(basename(argv[0]));
        return EXIT_FAILURE;
    }

    /* set the absolute path for the storage device name */
    if ((sofs_supp_file = realpath(argv[optind], NULL)) == NULL)
    {
        fprintf(stderr, "%s: Setting the absolute path - %s.\n", basename(argv[0]), strerror(errno));
        return EXIT_FAILURE;
    }

    /* build argv and argc for fuse_main */
    char s1[] = "-d";
    char s2[] = "-o";
    char s3[] = "nonempty";
    char s4[] = "fsname=sofs20";
    char s5[] = "subtype=ext-like";
    char *fargv[] = {
        argv[0],
        argv[optind + 1],
        s2, s3, s2, s4, s2, s5, s1,
        NULL
    };
    int fargc = debug_mode ? 9 : 8;
    return fuse_main(fargc, fargv, &sofs20_fuse_operations, NULL);
}
