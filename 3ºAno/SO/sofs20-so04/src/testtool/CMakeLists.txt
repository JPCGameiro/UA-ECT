set(CMAKE_EXE_LINKER_FLAGS  "${CMAKE_EXE_LINKER_FLAGS} -L${PROJECT_ROOT_DIR}/lib/bin")
set(CMAKE_EXE_LINKER_FLAGS  "${CMAKE_EXE_LINKER_FLAGS} -L${PROJECT_ROOT_DIR}/lib/grp")
set(CMAKE_EXE_LINKER_FLAGS  "${CMAKE_EXE_LINKER_FLAGS} -Wl,--start-group")

add_executable(testtool
        testtool.cpp
        testtool_msgs.cpp
        testtool_core.cpp
        testtool_freeinodes.cpp
        testtool_freedatablocks.cpp
        testtool_fileblocks.cpp
        testtool_direntries.cpp
        testtool_inodeattrs.cpp
)

target_link_libraries(testtool
       direntries 
       grp_direntries
       bin_direntries
       fileblocks
       grp_fileblocks
       bin_fileblocks
       freeinodes
       grp_freeinodes
       bin_freeinodes
       freedatablocks
       grp_freedatablocks
       bin_freedatablocks
       daal bin_daal
       devtools
       core
)
