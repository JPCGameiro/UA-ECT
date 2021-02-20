add_library(core STATIC 
    exception.cpp
    rawdisk.cpp
)

add_executable(sizes sizes.cpp)
target_link_libraries(sizes core)
