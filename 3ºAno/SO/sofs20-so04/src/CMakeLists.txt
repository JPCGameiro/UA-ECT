cmake_minimum_required(VERSION 2.8.9)

project(sofs20)

if ( CMAKE_COMPILER_IS_GNUCC )
    set(CMAKE_CXX_FLAGS  "${CMAKE_CXX_FLAGS} -Wall -D_FILE_OFFSET_BITS=64 -ggdb")
endif()

execute_process ( COMMAND dirname ${CMAKE_SOURCE_DIR} 
            OUTPUT_VARIABLE PROJECT_ROOT_DIR
            OUTPUT_STRIP_TRAILING_WHITESPACE
        )

set(CMAKE_ARCHIVE_OUTPUT_DIRECTORY ${PROJECT_ROOT_DIR}/lib)

set(CMAKE_RUNTIME_OUTPUT_DIRECTORY ${PROJECT_ROOT_DIR}/bin)

include_directories(${PROJECT_ROOT_DIR}/include)
include_directories(${PROJECT_ROOT_DIR}/include/bin)
include_directories(${PROJECT_ROOT_DIR}/include/grp)

add_subdirectory(core)
add_subdirectory(devtools)

add_subdirectory(grp_src/grp_mksofs)
add_subdirectory(mksofs)

add_subdirectory(daal)

add_subdirectory(grp_src/grp_freeinodes)
add_subdirectory(freeinodes)

add_subdirectory(grp_src/grp_freedatablocks)
add_subdirectory(freedatablocks)

add_subdirectory(grp_src/grp_fileblocks)
add_subdirectory(fileblocks)

add_subdirectory(grp_src/grp_direntries)
add_subdirectory(direntries)

add_subdirectory(syscalls)

add_subdirectory(testtool)
add_subdirectory(sofsmount)

