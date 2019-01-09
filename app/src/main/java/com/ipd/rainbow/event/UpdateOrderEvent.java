package com.ipd.rainbow.event;

public class UpdateOrderEvent {
    public int[] refreshPos;

    public UpdateOrderEvent(int[] refreshPos) {
        this.refreshPos = refreshPos;
    }
}
