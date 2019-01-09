package com.ipd.rainbow.event;

import com.ipd.rainbow.bean.AddressBean;

public class ChooseAddressEvent {
    public AddressBean addressInfo;

    public ChooseAddressEvent(AddressBean addressInfo) {
        this.addressInfo = addressInfo;
    }
}
