package com.ipd.rainbow.event;

public class VideoResultEvent {
    public String videoCover;
    public String videoPath;

    public VideoResultEvent(String videoCover, String videoPath) {
        this.videoCover = videoCover;
        this.videoPath = videoPath;
    }
}
