if ( CMAKE_COMPILER_IS_GNUCC )
    set(CMAKE_CXX_FLAGS  "${CMAKE_CXX_FLAGS} -DFUSE_USE_VERSION=26")
endif()

set(CMAKE_EXE_LINKER_FLAGS  "${CMAKE_EXE_LINKER_FLAGS} -L${PROJECT_ROOT_DIR}/lib/bin")
set(CMAKE_EXE_LINKER_FLAGS  "${CMAKE_EXE_LINKER_FLAGS} -L${PROJECT_ROOT_DIR}/lib/grp")
set(CMAKE_EXE_LINKER_FLAGS  "${CMAKE_EXE_LINKER_FLAGS} -Wl,--start-group")

add_executable(sofsmount
        sofsmount.cpp
)

target_link_libraries(sofsmount
        syscalls
        bin_syscalls
        direntries
        bin_direntries
        grp_direntries
        fileblocks
        bin_fileblocks
        grp_fileblocks
        freeinodes
        bin_freeinodes
        grp_freeinodes
        freedatablocks
        bin_freedatablocks
        grp_freedatablocks
        daal
        bin_daal
        devtools
        core
        fuse
)
