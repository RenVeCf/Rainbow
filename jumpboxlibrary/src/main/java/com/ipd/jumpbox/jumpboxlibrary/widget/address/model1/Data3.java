package com.ipd.jumpbox.jumpboxlibrary.widget.address.model1;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/3.
 */
public class Data3 implements Serializable {
    public String id;
    public String cndqmc;

    public Data3() {
    }

    public Data3(String cndqmc, String id) {
        this.cndqmc = cndqmc;
        this.id = id;
    }
}
