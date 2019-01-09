package com.ipd.rainbow.event;

import com.ipd.rainbow.bean.ExchangeBean;

public class ChooseCouponEvent {
    public ExchangeBean couponInfo;

    public ChooseCouponEvent(ExchangeBean couponInfo) {
        this.couponInfo = couponInfo;
    }
}
