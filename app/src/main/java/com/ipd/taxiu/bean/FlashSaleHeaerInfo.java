package com.ipd.taxiu.bean;

import java.util.List;

public class FlashSaleHeaerInfo {
    public FlashSaleProductBean todayProduct;
    public List<FlashSaleTimeBean> timeList;

    public FlashSaleHeaerInfo(FlashSaleProductBean todayProduct, List<FlashSaleTimeBean> timeList) {
        this.todayProduct = todayProduct;
        this.timeList = timeList;
    }

    public FlashSaleHeaerInfo() {
    }
}
