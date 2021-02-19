/**
 * \file
 * \brief The \b sofs20 system calls
 *
 * The set of system calls are divided into two subsets:
 * \li one, contains the set of system calls for evaluation;
 * \li the other, constains the remaining required system calls
 *
 *  \author Artur Carneiro Pereira 2007-2009, 2016-2020
 *  \author Miguel Oliveira e Silva 2009, 2017
 *  \author António Rui Borges - 2010--2015
 *
 */

#ifndef __SOFS20_SYSCALLS__
#define __SOFS20_SYSCALLS__

#include <unistd.h>
#include <fcntl.h>
#include <inttypes.h>
#include <sys/types.h>
#include <sys/statvfs.h>
#include <sys/stat.h>
#include <time.h>
#include <utime.h>
#include <libgen.h>

namespace sofs20
{

    /**
     * \defgroup syscalls syscalls
     * \brief \c sofs20 system calls
     * @{ 
     */

    /* ******************************************************************* */

    /**
     * \defgroup main_syscalls main syscalls
     * \brief Corpus of system calls for evaluation    
     * @{ 
     * */

    /* ******************************************************************* */

    /**
     *  \brief Create a regular file with size 0.
     *
     *  It tries to emulate <em>mknod</em> system call, for regular files.
     *
     *  To get more information, execute in a terminal the command <b><tt>man 2 mknod</tt></b>
     *
     *  \param path path to the file
     *  \param mode permissions to be set
     *
     *  \remarks
     *  - \c mode must be a bitwise combination of S_IRUSR, S_IWUSR, S_IXUSR, S_IRGRP, S_IWGRP, 
     *          S_IXGRP, S_IROTH, S_IWOTH, S_IXOTH
     *  - \c dirname(path) must exist, be a directory, and write permission on it is required
     *  - \c basename(path) must not exist within \c dirname(path)
     *  
     *  - <b>In case of success</b>
     *    - An inode is allocated for the new regular file
     *    - A \c direntry named \c basename(path) is added to \c dirname(path)
     *    - \c lnkcnt of the inode associated to \c basename(path) is updated in order
     *          to include the new incoming arc
     *
     *  \return 0 on success; 
     *      -errno in case of error, 
     *      being errno the system error that better represents the cause of failure
     */
    int soMknod(const char *path, mode_t mode);

    /* ******************************************************************* */

    /**
     *  \brief Make a new link to a file.
     *
     *  It tries to emulate <em>link</em> system call.
     *
     *  To get more information, execute in a terminal the command <b><tt>man 2 link</tt></b>
     *
     *  \param path path to an existing file
     *  \param newPath new path to the same file
     *
     *  \remarks
     *  - \c path must represent an existing regular file or symbolic link
     *  - \c dirname(newPath) must exist, be a directory, and write permission on it is required
     *  - \c basename(newPath) must not exist within \c dirname(newPath)
     *  
     *  - <b>In case of success</b>
     *    - A \c direntry named \c basename(newPath) is added to \c dirname(newPath)
     *    - Field \c lnkcnt of the inode associated to \c basename(path) is updated in order
     *          to include with the new incoming arc
     *
     *  \return 0 on success; 
     *      -errno in case of error, 
     *      being errno the system error that better represents the cause of failure
     */
    int soLink(const char *path, const char *newPath);

    /* ******************************************************************* */

    /**
     *  \brief Delete a link to a file from a directory and possibly the file it refers to from the file system.
     *
     *  It tries to emulate <em>unlink</em> system call.
     *
     *  To get more information, execute in a terminal the command <b><tt>man 2 unlink</tt></b>
     *
     *  \param path path to the file to be deleted
     *
     *  \remarks
     *  - \c dirname(path) must exist, be a directory, and write permission on it is required
     *  - \c basename(path) must exist within \c dirname(path) 
     *          and must be a regular file or symbolic link
     *  
     *  - <b>In case of success</b>
     *    - The \c direntry named \c basename(path) is deleted from \c dirname(path)
     *    - \c lnkcnt of the inode associated to \c path is updated in order to exclude 
     *          the deleted incoming arc
     *    - If after that \c lnkcnt become zero:
     *      - All data blocks associated to that inode are freed
     *      - The inode itself is freed
     *
     *  \return 0 on success; 
     *      -errno in case of error,
     *      being errno the system error that better represents the cause of failure
     */
    int soUnlink(const char *path);

    /* ******************************************************************* */

    /**
     *  \brief Create a directory.
     *
     *  It tries to emulate <em>mkdir</em> system call.
     *
     *  To get more information, execute in a terminal the command <b><tt>man 2 mkdir</tt></b>
     *
     *  \param path path to the file
     *  \param mode permissions to be set
     *          
     *  \remarks
     *  - \c mode must be a bitwise combination of S_IRUSR, S_IWUSR, S_IXUSR, S_IRGRP, S_IWGRP, 
     *          S_IXGRP, S_IROTH, S_IWOTH, S_IXOTH
     *  - \c dirname(path) must exist, be a directory, and write permission on it is required
     *  - \c base(path) must not exist within \c dirname(path)
     *  
     *  - <b>In case of success</b>
     *    - An inode is allocated for the new (child) directory
     *    - The \c direntries named \c . and \c .. are added to the child directory and,
     *          as a consequence, a data block is added, \c blkcnt is set to 1,
     *          and size is set to \c BlockSize
     *    - A \c direntry named \c basename(path) is added to \c dirname(path)
     *    - \c lnkcnt of parent and child directories are updated in order to include
     *          the new incoming arcs
     *
     *  \return 0 on success; 
     *      -errno in case of error,
     *      being errno the system error that better represents the cause of failure
     */
    int soMkdir(const char *path, mode_t mode);

    /* ******************************************************************* */

    /**
     *  \brief Remove an existing directory.
     *
     *  It tries to emulate <em>rmdir</em> system call.
     *
     *  To get more information, execute in a terminal the command <b><tt>man 2 rmdir</tt></b>
     *
     *  \param path path to the directory to be deleted
     *
     *  \remarks
     *  - \c dirname(path) must exist, be a directory, and write permission on it is required
     *  - \c basename(path) must exist within \c dirname(path), be a directory and be empty
     *  
     *  - <b>In case of success</b>
     *    - All data blocks associated to the deleted (child) directory are freed
     *    - The inode of the child directory is freed
     *    - The \c direntry named \c basename(path) is deleted from \c dirname(path)
     *    - \c lnkcnt of the inode associated to \c dirname(path) is updated in order to exclude 
     *          the deleted incoming arc
     *
     *  \return 0 on success; 
     *      -errno in case of error,
     *      being errno the system error that better represents the cause of failure
     */
    int soRmdir(const char *path);

    /* ******************************************************************* */

    /**
     *  \brief Read data from an open regular file.
     *
     *  It tries to emulate <em>read</em> system call.
     *
     *  To get more information, execute in a terminal the command <b><tt>man 2 read</tt></b>
     *
     *  \param path path to the file
     *  \param buff pointer to the buffer where data to be read is to be stored
     *  \param count number of bytes to be read
     *  \param pos starting [byte] position in the file data continuum where data is to be read from
     *
     *  \remarks
     *  - The user must have read permission to the inode associated with \c path
     *
     *  - <b>In case of success</b>
     *    - In terms of the disk internal structure, only \c atime is updated
     *
     *  \return the number of bytes read, on success; 
     *      -errno in case of error,
     *      being errno the system error that better represents the cause of failure
     */
    int soRead(const char *path, void *buff, uint32_t count, int32_t pos);

    /* ******************************************************************* */

    /**
     *  \brief Write data into an open regular file.
     *
     *  It tries to emulate <em>write</em> system call.
     *
     *  To get more information, execute in a terminal the command <b><tt>man 2 write</tt></b>
     *
     *  \param path path to the file
     *  \param buff pointer to the buffer where data to be written is stored
     *  \param count number of bytes to be written
     *  \param pos starting [byte] position in the file data continuum where data is to be written into
     *
     *  \remarks
     *  - The user must have write permission to the inode associated with \c path
     *
     *  - <b>In case of success</b>
     *    - Some data blocks of the associated inode can be updated
     *    - Some new data blocks can be associated to the inode, in which case \c blkcnt 
     *          is updated
     *    - Field \c size of the inode can be updated 
     *
     *  \return the number of bytes written, on success; 
     *      -errno in case of error,
     *      being errno the system error that better represents the cause of failure
     */
    int soWrite(const char *path, void *buff, uint32_t count, int32_t pos);

    /* ******************************************************************* */

    /**
     *  \brief Change the name or the location of a file in the directory hierarchy of the file system.
     *
     *  It tries to emulate <em>rename</em> system call.
     *
     *  To get more information, execute in a terminal the command <b><tt>man 2 rename</tt></b>
     *
     *  \param path path to an existing file
     *  \param newPath new path to the same file in replacement of the old one
     *
     *  \return 0 on success; 
     *      -errno in case of error,
     *      being errno the system error that better represents the cause of failure
     */
    int soRename(const char *path, const char *newPath);

    /* ******************************************************************* */

    /**
     *  \brief Truncate a regular file to a specified length.
     *
     *  It tries to emulate <em>truncate</em> system call.
     *
     *  To get more information, execute in a terminal the command <b><tt>man 2 truncate</tt></b>
     *
     *  \param path path to the file
     *  \param length new size for the regular size
     *
     *  \return 0 on success; 
     *      -errno in case of error,
     *      being errno the system error that better represents the cause of failure
     */
    int soTruncate(const char *path, off_t length);

    /* ******************************************************************* */

    /**
     *  \brief Read a directory entry from a directory.
     *
     *  It tries to emulate <em>getdents</em> system call, 
     *      but it reads a single directory entry at a time.<br/>
     *  Only the field <em>name</em> is read.
     *
     *  \remark The returned value is the number of bytes read from the directory in order 
     *      to get the next in use directory entry.<br/>
     *      The point is that the system (through FUSE) 
     *      uses the returned value to update file position.
     *
     *  To get more information, execute in a terminal the command <b><tt>man readdir</tt></b>
     *
     *  \param path path to the file
     *  \param buff pointer to the buffer where data to be read is to be stored
     *  \param pos starting [byte] position in the file data continuum where data is to be read from
     *
     *  \return 0 on success; 
     *      -errno in case of error, 
     *      being errno the system error that better represents the cause of failure
     */
    int soReaddir(const char *path, void *buff, int32_t pos);

    /* ******************************************************************* */

    /**
     *  \brief Creates a symbolic link which contains the given path.
     *
     *  It tries to emulate <em>symlink</em> system call.
     *
     *  \remark The permissions set for the symbolic link should have read (r), write (w) and execution (x) permissions for
     *          both <em>user</em>, <em>group</em> and <em>other</em>.
     *
     *  To get more information, execute in a terminal the command <b><tt>man 2 symlink</tt></b>
     *
     *  \param effPath path to be stored in the symbolic link file
     *  \param path path to the symbolic link to be created
     *
     *  \return 0 on success; 
     *      -errno in case of error,
     *      being errno the system error that better represents the cause of failure
     */
    int soSymlink(const char *effPath, const char *path);

    /* ******************************************************************* */

    /**
     *  \brief Read the value of a symbolic link.
     *
     *  It tries to emulate <em>readlink</em> system call.
     *
     *  To get more information, execute in a terminal the command <b><tt>man 2 readlink</tt></b>
     *
     *  \param path path to the symbolic link
     *  \param buff pointer to the buffer where data to be read is to be stored
     *  \param size buffer size in bytes
     *
     *  \return 0 on success; 
     *      -errno in case of error,
     *      being errno the system error that better represents the cause of failure
     */
    int soReadlink(const char *path, char *buff, size_t size);

    /* ******************************************************************* */

    /** @} close group main_syscalls */

    /* ******************************************************************* */

    /** 
     * \defgroup other_syscalls other syscalls
     * \brief Other system calls
     * @{ 
     */
    /* ******************************************************************* */

    /**
     *  \brief Open the sofs20 file system.
     *
     * The rawdisk is open and, if it does not fail,
     * the three dealers are open.
     * This function is called by the mount operation.
     *
     *  \param devname absolute path to the Linux file that simulates the storage device
     *
     *  \return 0 on success; 
     *      -errno in case of error,
     *      being errno the system error that better represents the cause of failure
     */
    int soOpenFileSystem(const char *devname);

    /* ******************************************************************* */

    /**
     *  \brief Close the sofs20 file system.
     *
     * The three dealers are closed and then the raw disk is closed.
     * This function is called by the unmount operation.
     *
     *  \return 0 on success; 
     *      -errno in case of error,
     *      being errno the system error that better represents the cause of failure
     */
    int soCloseFileSystem(void);

    /* ******************************************************************* */

    /**
     *  \brief Get file system statistics.
     *
     *  It tries to emulate <em>statvfs</em> system call.
     *
     *  Information about a mounted file system is returned.
     *  It checks whether the calling process can access the file specified by the path.
     *
     *  To get more information, execute in a terminal the command <b><tt>man 2 statvfs</tt></b>
     *
     *  \param path path to any file within the mounted file system
     *  \param st pointer to a statvfs structure
     *
     *  \return 0 on success; 
     *      -errno in case of error, 
     *      being errno the system error that better represents the cause of failure
     */
    int soStatFS(const char *path, struct statvfs *st);

    /* ******************************************************************* */

    /**
     *  \brief Get file status.
     *
     *  It tries to emulate <em>stat</em> system call.
     *
     *  Information about a specific file is returned.
     *  It checks whether the calling process can access the file specified by the path.
     *
     *  To get more information, execute in a terminal the command <b><tt>man 2 stat</tt></b>
     *
     *  \param path path to the file
     *  \param st pointer to a stat structure
     *
     *  \return 0 on success; 
     *      -errno in case of error,
     *      being errno the system error that better represents the cause of failure
     */
    int soStat(const char *path, struct stat *st);

    /* ******************************************************************* */

    /**
     *  \brief Check real user's permissions for a file.
     *
     *  It tries to emulate <em>access</em> system call.
     *
     *  It checks whether the calling process can access the file specified by the path
     *  for the requested operation.
     *
     *  To get more information, execute in a terminal the command <b><tt>man 2 access</tt></b>
     *
     *  \param path path to the file
     *  \param opRequested operation to be performed:
     *                    F_OK (check if file exists)
     *                    a bitwise combination of R_OK, W_OK, and X_OK
     *
     *  \return 0 on success; 
     *      -errno in case of error,
     *      being errno the system error that better represents the cause of failure
     */
    int soAccess(const char *path, int opRequested);

    /* ******************************************************************* */

    /**
     *  \brief Change permissions of a file.
     *
     *  It tries to emulate <em>chmod</em> system call.
     *
     *  It changes the permissions of a file specified by the path.
     *
     *  \remark If the file is a symbolic link, its contents shall always be used to reach the destination file, so the
     *          permissions of a symbolic link can never be changed (they are set to rwx for <em>user</em>, <em>group</em>
     *          and <em>other</em> when the link is created and remain unchanged thereafter).
     *
     *  To get more information, execute in a terminal the command <b><tt>man 2 chmod</tt></b>
     *
     *  \param path path to the file
     *  \param mode permissions to be set:
     *      a bitwise combination of S_IRUSR, S_IWUSR, S_IXUSR, S_IRGRP, S_IWGRP,
     *      S_IXGRP, S_IROTH, S_IWOTH, S_IXOTH
     *
     *  \return 0 on success; 
     *      -errno in case of error, 
     *      being errno the system error that better represents the cause of failure
     */
    int soChmod(const char *path, mode_t mode);

    /* ******************************************************************* */

    /**
     *  \brief Change the ownership of a file.
     *
     *  It tries to emulate <em>chown</em> system call.
     *
     *  It changes the ownership of the file specified by the path.
     *
     *  Only <em>root</em> may change the owner of a file. 
     *  The file's owner may change the group if the specified group is
     *  one of the owner's supplementary groups.
     *
     *  To get more information, execute in a terminal the command <b><tt>man 2 chown</tt></b>
     *
     *  \param path path to the file
     *  \param owner file user id (-1, if user is not to be changed)
     *  \param group file group id (-1, if group is not to be changed)
     *
     *  \return 0 on success; 
     *      -errno in case of error,
     *      being errno the system error that better represents the cause of failure
     */
    int soChown(const char *path, uid_t owner, gid_t group);

    /* ******************************************************************* */

    /**
     *  \brief Change the last access and modification times of a file.
     *
     *  It tries to emulate <em>utime</em> system call.
     *
     *  To get more information, execute in a terminal the command <b><tt>man 2 utime</tt></b>
     *
     *  \param path path to the file
     *  \param times pointer to a structure where the last access and modification times are passed, if \c NULL, the last
     *               access and modification times are set to the current time
     *
     *  \return 0 on success; 
     *      -errno in case of error,
     *      being errno the system error that better represents the cause of failure
     */
    int soUtime(const char *path, const struct utimbuf *times);

    /* ******************************************************************* */

    /**
     *  \brief Change the last access and modification times of a file with nanosecond resolution.
     *
     *  It tries to emulate <em>utimensat</em> system call.
     *
     *  To get more information, execute in a terminal the command <b><tt>man 2 utimensat</tt></b>
     *
     *  \param path path to the file
     *  \param tv structure array where the last access, element of index 0, and modification, element of index 1, times
     *            are passed, if \c NULL, the last access and modification times are set to the current time
     *            if the <tt>tv_nsec</tt> field of one of the <tt>timespec</tt> structures has the special
     *            value \c UTIME_NOW, then the corresponding file timestamp is set to the current time
     *            if the <tt>tv_nsec</tt> field of one of the <tt>timespec</tt> structures has the special
     *            value \c UTIME_OMIT, then the corresponding file timestamp is left unchanged
     *
     *  \return 0 on success; 
     *      -errno in case of error,
     *      being errno the system error that better represents the cause of failure
     */
    int soUtimens(const char *path, const struct timespec tv[2]);

    /* ******************************************************************* */

    /**
     *  \brief Open a regular file.
     *
     *  It tries to emulate <em>open</em> system call.
     *
     *  In the current implementation it only checks if the calling process
     *  can access the file for the openning operation.
     *
     *  To get more information, execute in a terminal the command <b><tt>man 2 open</tt></b>
     *
     *  \param path path to the file
     *  \param flags access modes to be used:
     *                    O_RDONLY, O_WRONLY, O_RDWR
     *
     *  \return 0 on success; 
     *      -errno in case of error,
     *      being errno the system error that better represents the cause of failure
     */
    int soOpen(const char *path, int flags);

    /* ******************************************************************* */

    /**
     *  \brief Close a regular file.
     *
     *  It tries to emulate <em>close</em> system call.
     *
     *  To get more information, execute in a terminal the command <b><tt>man 2 close</tt></b>
     *
     *  \param path path to the file
     *
     *  \return 0 on success; 
     *      -errno in case of error,
     *      being errno the system error that better represents the cause of failure
     */
    int soClose(const char *path);

    /* ******************************************************************* */

    /**
     *  \brief Synchronize a file's in-core state with storage device.
     *
     *  It tries to emulate <em>fsync</em> system call.
     *
     *  To get more information, execute in a terminal the command <b><tt>man 2 fsync</tt></b>
     *
     *  \param path path to the file
     *
     *  \return 0 on success; 
     *      -errno in case of error, being errno the system error that better represents the cause of failure
     *
     *  \return 0 on success; 
     *      -errno in case of error, 
     *      being errno the system error that better represents the cause of failure
     */
    int soFsync(const char *path);

    /* ******************************************************************* */

    /**
     *  \brief Open a directory for reading.
     *
     *  It tries to emulate <em>opendir</em> system call.
     *  In the current implementation it only checks if the calling process
     *  can access the file for the openning operation.
     *
     *
     *  To get more information, execute in a terminal the command <b><tt>man opendir</tt></b>
     *
     *  \param path path to the file
     *
     *  \return 0 on success; 
     *      -errno in case of error,
     *      being errno the system error that better represents the cause of failure
     */
    int soOpendir(const char *path);

    /* ******************************************************************* */

    /**
     *  \brief Close a directory.
     *
     *  It tries to emulate <em>closedir</em> system call.
     *
     *  To get more information, execute in a terminal the command <b><tt>man closedir</tt></b>
     *
     *  \param path path to the file
     *
     *  \return 0 on success; 
     *      -errno in case of error, 
     *      being errno the system error that better represents the cause of failure
     */
    int soClosedir(const char *path);

    /* ******************************************************************* */

    /** @} close group other_syscalls */
    /* ******************************************************************* */

    /* ******************************************************************* */

    /** @} close group syscalls */
    /* ******************************************************************* */

};

#endif /* __SOFS20_SYSCALLS__ */
