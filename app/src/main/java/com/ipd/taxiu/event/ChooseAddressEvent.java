package com.ipd.taxiu.event;

import com.ipd.taxiu.bean.AddressBean;

public class ChooseAddressEvent {
    public AddressBean addressInfo;

    public ChooseAddressEvent(AddressBean addressInfo) {
        this.addressInfo = addressInfo;
    }
}
