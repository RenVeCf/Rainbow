package com.ipd.taxiu.bean;

/**
 * Created by jumpbox on 2017/8/7.
 */

public class LocalDirectoryBean {
    public String directoryName;
    public String showName;
    public String firstPicturePath;

    public LocalDirectoryBean(String directoryName, String showName, String firstPicturePath) {
        this.directoryName = directoryName;
        this.showName = showName;
        this.firstPicturePath = firstPicturePath;
    }
}
