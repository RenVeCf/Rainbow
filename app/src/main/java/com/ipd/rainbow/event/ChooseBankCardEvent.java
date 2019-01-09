package com.ipd.rainbow.event;

import com.ipd.rainbow.bean.BankCardBean;

public class ChooseBankCardEvent {
    public BankCardBean bankInfo;

    public ChooseBankCardEvent(BankCardBean bankInfo) {
        this.bankInfo = bankInfo;
    }
}
