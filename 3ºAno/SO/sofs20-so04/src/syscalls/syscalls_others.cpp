/*
 *  \author Artur Pereira - 2016-2020
 */

#include "bin_syscalls.h"

namespace sofs20
{
    int soOpenFileSystem(const char *devname)
    {
        return binOpenFileSystem(devname);
    }

    /* ********************************************************* */

    int soCloseFileSystem(void)
    {
        return binCloseFileSystem();
    }

    /* ********************************************************* */

    int soStatFS(const char *path, struct statvfs *st)
    {
        return binStatFS(path, st);
    }

    /* ********************************************************* */

    int soStat(const char *path, struct stat *st)
    {
        return binStat(path, st);
    }

    /* ********************************************************* */

    int soAccess(const char *path, int opRequested)
    {
        return binAccess(path, opRequested);
    }

    /* ********************************************************* */

    int soChmod(const char *path, mode_t mode)
    {
        return binChmod(path, mode);
    }

    /* ********************************************************* */

    int soChown(const char *path, uid_t owner, gid_t group)
    {
        return binChown(path, owner, group);
    }

    /* ********************************************************* */

    int soUtime(const char *path, const struct utimbuf *times)
    {
        return binUtime(path, times);
    }

    /* ********************************************************* */

    int soUtimens(const char *path, const struct timespec tv[2])
    {
        return binUtimens(path, tv);
    }

    /* ********************************************************* */

    int soOpen(const char *path, int flags)
    {
        return binOpen(path, flags);
    }

    /* ********************************************************* */

    int soClose(const char *path)
    {
        return binClose(path);
    }

    /* ********************************************************* */

    int soFsync(const char *path)
    {
        return binFsync(path);
    }

    /* ********************************************************* */

    int soOpendir(const char *path)
    {
        return binOpendir(path);
    }

    int soClosedir(const char *path)
    {
        return binClosedir(path);
    }

};
