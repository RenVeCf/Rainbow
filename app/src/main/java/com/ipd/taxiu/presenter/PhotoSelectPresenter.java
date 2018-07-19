package com.ipd.taxiu.presenter;


import com.ipd.taxiu.bean.LocalDirectoryBean;
import com.ipd.taxiu.bean.LocalPictureBean;
import com.ipd.taxiu.model.PhotoSelectModel;

import java.util.List;

/**
 * Created by jumpbox on 2017/8/7.
 */

public class PhotoSelectPresenter extends BasePresenter<PhotoSelectPresenter.IPhotoSelectView, PhotoSelectModel> {


    @Override
    public void initModel() {
        setMModel(new PhotoSelectModel());
    }


    public void loadPictureByDirectoryName(String directoryName) {
        List<LocalPictureBean> localPictureBeans = getMModel().loadPictureByDirectoryName(getMContext(), directoryName);
        if (getMView() != null) getMView().loadAllPictureSuccess(localPictureBeans);
    }

    public void loadAllDirectory() {
        List<LocalDirectoryBean> directoryList = getMModel().loadAllDirectory(getMContext());
        //添加全部选项
        String firstPicturePath = directoryList.isEmpty() ? "" : directoryList.get(0).firstPicturePath;
        directoryList.add(0, new LocalDirectoryBean("", "所有照片", firstPicturePath));
        if (getMView() != null) getMView().loadAllDirectorySuccess(directoryList);
    }


    public interface IPhotoSelectView {

        void loadAllPictureSuccess(List<LocalPictureBean> localPictureList);

        void loadAllDirectorySuccess(List<LocalDirectoryBean> directoryList);

    }
}
