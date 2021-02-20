set(CMAKE_EXE_LINKER_FLAGS  "${CMAKE_EXE_LINKER_FLAGS} -L${PROJECT_ROOT_DIR}/lib/bin")
set(CMAKE_EXE_LINKER_FLAGS  "${CMAKE_EXE_LINKER_FLAGS} -L${PROJECT_ROOT_DIR}/lib/grp")

add_executable(mksofs 
        mksofs_main.cpp
        mksofs_CS.cpp
        mksofs_FSB.cpp
        mksofs_FIT.cpp
        mksofs_FRD.cpp
        mksofs_FRT.cpp
        mksofs_RFDB.cpp
)

target_link_libraries(mksofs 
        core 
        devtools
        bin_mksofs
        grp_mksofs
)

