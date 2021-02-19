/**
 * \file
 * \brief Binary version of the \b sofs20 system calls
 *
 * The set of system calls are divided into two subsets:
 * \li one, contains the set of system calls for group work;
 * \li the other, constains the remaining required system calls
 *
 *  \author Artur Carneiro Pereira 2007-2009, 2016-2020
 *  \author Miguel Oliveira e Silva 2009, 2017
 *  \author António Rui Borges - 2010--2015
 *
 */

#ifndef __SOFS20_BIN_SYSCALLS__
#define __SOFS20_BIN_SYSCALLS__

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
    int binLink(const char *path, const char *newPath);

    int binUnlink(const char *path);

    int binRename(const char *path, const char *newPath);

    int binMknod(const char *path, mode_t mode);

    int binRead(const char *path, void *buff, uint32_t count, int32_t pos);

    int binWrite(const char *path, void *buff, uint32_t count, int32_t pos);

    int binTruncate(const char *path, off_t length);

    int binMkdir(const char *path, mode_t mode);

    int binRmdir(const char *path);

    int binReaddir(const char *path, void *buff, int32_t pos);

    int binSymlink(const char *effPath, const char *path);

    int binReadlink(const char *path, char *buff, size_t size);

    /* ******************************************************************* */

    int binOpenFileSystem(const char *devname);

    int binCloseFileSystem(void);

    int binStatFS(const char *path, struct statvfs *st);

    int binStat(const char *path, struct stat *st);

    int binAccess(const char *path, int opRequested);

    int binChmod(const char *path, mode_t mode);

    int binChown(const char *path, uid_t owner, gid_t group);

    int binUtime(const char *path, const struct utimbuf *times);

    int binUtimens(const char *path, const struct timespec tv[2]);

    int binOpen(const char *path, int flags);

    int binClose(const char *path);

    int binFsync(const char *path);

    int binOpendir(const char *path);

    int binClosedir(const char *path);
};

#endif /* __SOFS20_BIN_SYSCALLS__ */
