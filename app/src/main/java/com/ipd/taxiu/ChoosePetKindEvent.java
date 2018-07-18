package com.ipd.taxiu;

import com.ipd.taxiu.bean.PetInfoBean;

public class ChoosePetKindEvent {
    public int type;
    public PetInfoBean petKind;

    public ChoosePetKindEvent(int type, PetInfoBean petKind) {
        this.type = type;
        this.petKind = petKind;
    }
}
