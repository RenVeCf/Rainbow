package com.ipd.taxiu.event;

import com.ipd.taxiu.bean.ExchangeBean;

public class ChooseCouponEvent {
    public ExchangeBean couponInfo;

    public ChooseCouponEvent(ExchangeBean couponInfo) {
        this.couponInfo = couponInfo;
    }
}
