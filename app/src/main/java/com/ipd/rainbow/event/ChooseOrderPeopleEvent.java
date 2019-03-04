package com.ipd.rainbow.event;

import com.ipd.rainbow.bean.OrderPeopleBean;

public class ChooseOrderPeopleEvent {
    public OrderPeopleBean addressInfo;

    public ChooseOrderPeopleEvent(OrderPeopleBean addressInfo) {
        this.addressInfo = addressInfo;
    }
}
