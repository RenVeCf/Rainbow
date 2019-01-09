package com.ipd.rainbow.event;

/**
 * Created by jumpbox on 16/6/2.
 */
public class PayRequestEvent {
    public int payType;

    public PayRequestEvent(int payType) {
        this.payType = payType;
    }
}
