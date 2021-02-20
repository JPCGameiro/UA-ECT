add_library(syscalls STATIC
    rmdir.cpp
    mkdir.cpp
    mknod.cpp
    link.cpp
    read.cpp
    readdir.cpp
    readlink.cpp
    rename.cpp
    symlink.cpp
    truncate.cpp
    unlink.cpp
    write.cpp
    syscalls_others.cpp
)

