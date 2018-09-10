LOCAL_PATH := $(call my-dir)
LIB_NAME_PLUS := prebuilt
INCLUDE_PATH := include

include $(CLEAR_VARS)
LOCAL_MODULE := libavcodec
LOCAL_SRC_FILES := $(LIB_NAME_PLUS)/libavcodec.so
LOCAL_EXPORT_C_INCLUDES := $(INCLUDE_PATH)
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libavfilter
LOCAL_SRC_FILES := $(LIB_NAME_PLUS)/libavfilter.so
LOCAL_EXPORT_C_INCLUDES := $(INCLUDE_PATH)
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libavformat
LOCAL_SRC_FILES := $(LIB_NAME_PLUS)/libavformat.so
LOCAL_EXPORT_C_INCLUDES := $(INCLUDE_PATH)
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libavutil
LOCAL_SRC_FILES := $(LIB_NAME_PLUS)/libavutil.so
LOCAL_EXPORT_C_INCLUDES := $(INCLUDE_PATH)
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libswresample
LOCAL_SRC_FILES := $(LIB_NAME_PLUS)/libswresample.so
LOCAL_EXPORT_C_INCLUDES := $(INCLUDE_PATH)
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := libswscale
LOCAL_SRC_FILES := $(LIB_NAME_PLUS)/libswscale.so
LOCAL_EXPORT_C_INCLUDES := $(INCLUDE_PATH)
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := ffmpegapi
LOCAL_SRC_FILES := include/cmdutils.c include/ffmpeg.c include/ffmpeg_filter.c include/ffmpeg_opt.c include/ffmpeg_hw.c include/ffmpeg_api.c include/ffmpeg_thread.c

LOCAL_C_INCLUDES :=/Users/jumpbox/Desktop/ffmpeg-3.4.4
LOCAL_LDLIBS := -llog -lz -ldl
LOCAL_SHARED_LIBRARIES :=libavcodec libavfilter libavformat libavutil libswresample libswscale
include $(BUILD_SHARED_LIBRARY)
