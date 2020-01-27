SET(CMAKE_SYSTEM_NAME Linux)
SET(TOOLCHAIN_PREFIX aarch64-linux-gnu)
if(NOT SKIP_COMPILER_VERSION)
	SET(COMPILER_VERSION 5)
	if(NOT COMPILER_VERSION MATCHES "-.*")
		SET(COMPILER_VERSION "-${COMPILER_VERSION}")
	endif()
endif()
SET(CMAKE_C_COMPILER ${TOOLCHAIN_PREFIX}-gcc${COMPILER_VERSION})
SET(CMAKE_CXX_COMPILER ${TOOLCHAIN_PREFIX}-g++${COMPILER_VERSION})
SET(CMAKE_STRIP ${TOOLCHAIN_PREFIX}-strip CACHE FILEPATH "" FORCE)
SET(CMAKE_FIND_ROOT_PATH /usr/${TOOLCHAIN_PREFIX}/)
set(CMAKE_FIND_ROOT_PATH_MODE_PROGRAM NEVER)
set(CMAKE_FIND_ROOT_PATH_MODE_LIBRARY BOTH)
set(CMAKE_FIND_ROOT_PATH_MODE_INCLUDE BOTH)
