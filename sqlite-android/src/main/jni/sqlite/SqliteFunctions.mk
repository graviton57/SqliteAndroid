LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_CFLAGS += -DHAVE_CONFIG_H

ifeq ($(TARGET_ARCH), arm)
	LOCAL_CFLAGS += -DPACKED="__attribute__ ((packed))"
else
	LOCAL_CFLAGS += -DPACKED=""
endif

LOCAL_SRC_FILES += extension-functions.c

LOCAL_MODULE:= sqlitefunctions
LOCAL_LDLIBS += -ldl -llog

include $(BUILD_SHARED_LIBRARY)

