package com.ipd.rainbow.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class BannerBean implements Parcelable {
    /**
     * BANNER_ID : 1
     * TYPE : 2
     * LOGO : /upload/image/20180819/banner.png
     * CATEGORY : 1
     * URL : http://www.baidu.com/
     * CONTENT :
     * SORT : 1
     * CREATETIME : 2018-08-22 14:16:18
     * STATUS : 1
     */

    public int BANNER_ID;
    public int TYPE;
    public String LOGO;
    public int CATEGORY;
    public String URL;
    public String CONTENT;
    public int SORT;
    public String CREATETIME;
    public int STATUS;
    public boolean isVideo = false;
    public String videoUrl;
    public int PRODUCT_ID;
    public int FORM_ID;


    public BannerBean() {
    }

    public BannerBean(String LOGO, boolean isVideo, String videoUrl) {
        this.LOGO = LOGO;
        this.isVideo = isVideo;
        this.videoUrl = videoUrl;
    }

    public BannerBean(String LOGO) {
        this.LOGO = LOGO;
    }

    protected BannerBean(Parcel in) {
        BANNER_ID = in.readInt();
        TYPE = in.readInt();
        LOGO = in.readString();
        CATEGORY = in.readInt();
        URL = in.readString();
        CONTENT = in.readString();
        SORT = in.readInt();
        CREATETIME = in.readString();
        STATUS = in.readInt();
        isVideo = in.readByte() != 0;
        videoUrl = in.readString();
        PRODUCT_ID = in.readInt();
        FORM_ID = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(BANNER_ID);
        dest.writeInt(TYPE);
        dest.writeString(LOGO);
        dest.writeInt(CATEGORY);
        dest.writeString(URL);
        dest.writeString(CONTENT);
        dest.writeInt(SORT);
        dest.writeString(CREATETIME);
        dest.writeInt(STATUS);
        dest.writeByte((byte) (isVideo ? 1 : 0));
        dest.writeString(videoUrl);
        dest.writeInt(PRODUCT_ID);
        dest.writeInt(FORM_ID);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BannerBean> CREATOR = new Creator<BannerBean>() {
        @Override
        public BannerBean createFromParcel(Parcel in) {
            return new BannerBean(in);
        }

        @Override
        public BannerBean[] newArray(int size) {
            return new BannerBean[size];
        }
    };
}
