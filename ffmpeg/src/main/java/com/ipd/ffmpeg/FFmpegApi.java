package com.ipd.ffmpeg;

public class FFmpegApi {
    static {
        System.loadLibrary("avcodec");
        System.loadLibrary("avformat");
        System.loadLibrary("avfilter");
        System.loadLibrary("avutil");
        System.loadLibrary("ffmpegapi");
        System.loadLibrary("swscale");
        System.loadLibrary("swresample");
    }

    public static native int exec(String[] cmd);

    private static OnExecListener listener;


    public static void onExecuted(int ret) {
        if (listener != null) {
            listener.onExecuted(ret);
        }
    }

    /**
     * 执行ffmoeg命令
     *
     * @param cmds
     * @param listener
     */
    public static void exec(String[] cmds, OnExecListener listener) {
        FFmpegApi.listener = listener;
        exec(cmds);
    }

    public static void removeListener(){
        listener = null;
    }

    /**
     * 执行完成/错误 时的回调接口
     */
    public interface OnExecListener {
        void onExecuted(int ret);
    }
}
