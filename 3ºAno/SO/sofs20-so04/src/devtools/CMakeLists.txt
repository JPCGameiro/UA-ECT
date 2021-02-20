add_library(devtools STATIC 
    probing.cpp
    binselection.cpp
    blockviews.cpp
)

add_executable(showblock showblock.cpp)
target_link_libraries(showblock devtools core)
