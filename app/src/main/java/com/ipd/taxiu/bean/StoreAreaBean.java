package com.ipd.taxiu.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class StoreAreaBean implements Parcelable {

    /**
     * SHOP_AREA_ID : 1
     * AREA_NAME : 小型犬专区
     * CATEGORY : 1
     * LOGO : /upload/shop/1.png
     * SORT : 1
     * CREATETIME : 2018-08-24 16:10:05
     * STATUS : 1
     */

    public int SHOP_AREA_ID;
    public String AREA_NAME;
    public int CATEGORY;
    public String LOGO;
    public int SORT;
    public String CREATETIME;
    public int STATUS;

    protected StoreAreaBean(Parcel in) {
        SHOP_AREA_ID = in.readInt();
        AREA_NAME = in.readString();
        CATEGORY = in.readInt();
        LOGO = in.readString();
        SORT = in.readInt();
        CREATETIME = in.readString();
        STATUS = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(SHOP_AREA_ID);
        dest.writeString(AREA_NAME);
        dest.writeInt(CATEGORY);
        dest.writeString(LOGO);
        dest.writeInt(SORT);
        dest.writeString(CREATETIME);
        dest.writeInt(STATUS);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<StoreAreaBean> CREATOR = new Creator<StoreAreaBean>() {
        @Override
        public StoreAreaBean createFromParcel(Parcel in) {
            return new StoreAreaBean(in);
        }

        @Override
        public StoreAreaBean[] newArray(int size) {
            return new StoreAreaBean[size];
        }
    };
}
