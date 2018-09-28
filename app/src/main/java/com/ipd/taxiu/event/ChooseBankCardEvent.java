package com.ipd.taxiu.event;

import com.ipd.taxiu.bean.BankCardBean;

public class ChooseBankCardEvent {
    public BankCardBean bankInfo;

    public ChooseBankCardEvent(BankCardBean bankInfo) {
        this.bankInfo = bankInfo;
    }
}
