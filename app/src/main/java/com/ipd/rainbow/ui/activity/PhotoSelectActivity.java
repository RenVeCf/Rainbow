package com.ipd.rainbow.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ipd.jumpbox.jumpboxlibrary.utils.CommonUtils;
import com.ipd.jumpbox.jumpboxlibrary.utils.DensityUtil;
import com.ipd.rainbow.R;
import com.ipd.rainbow.adapter.LocalPictureAdapter;
import com.ipd.rainbow.adapter.PictureDirectoryAdapter;
import com.ipd.rainbow.bean.LocalDirectoryBean;
import com.ipd.rainbow.bean.LocalPictureBean;
import com.ipd.rainbow.bean.PictureBean;
import com.ipd.rainbow.platform.global.GlobalApplication;
import com.ipd.rainbow.presenter.PhotoSelectPresenter;
import com.ipd.rainbow.ui.BaseUIActivity;
import com.ipd.rainbow.utils.BaseAdapter;
import com.ipd.rainbow.utils.PictureChooseUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jumpbox on 2017/8/4.
 */

public class PhotoSelectActivity extends BaseUIActivity implements PhotoSelectPresenter.IPhotoSelectView, LocalPictureAdapter.OnItemSelectChangeListener, LocalPictureAdapter.OnTakePhotoListener, View.OnClickListener {

    private int mMaxSize;
    public static final int REQUEST_CODE = 955;

    public static void launch(Activity activity, int maxSize) {
        Intent intent = new Intent(activity, PhotoSelectActivity.class);
        intent.putExtra("maxSize", maxSize);
        activity.startActivityForResult(intent, REQUEST_CODE);
    }

    public static void launch(Activity activity, int maxSize, int requestCode) {
        Intent intent = new Intent(activity, PhotoSelectActivity.class);
        intent.putExtra("maxSize", maxSize);
        activity.startActivityForResult(intent, requestCode);
    }

    RecyclerView recycler_view;
    TextView tv_selected;
    TextView tv_preview;
    TextView tv_directory;


    @Override
    protected int getToolbarLayout() {
        return R.layout.select_picture_toolbar;
    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        return "选择图片";
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_photo_select;
    }

    private PhotoSelectPresenter mPresenter;
    private String mCurDirectoryName = "";

    @Override
    protected void onViewAttach() {
        mPresenter = new PhotoSelectPresenter();
        mPresenter.attachView(this, this);
    }

    @Override
    protected void onViewDetach() {
        mPresenter.detachView();
        mPresenter = null;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        initToolbar();
        recycler_view = findViewById(R.id.recycler_view);
        tv_selected = findViewById(R.id.tv_selected);
        tv_preview = findViewById(R.id.tv_preview);
        tv_directory = findViewById(R.id.tv_directory);
        mMaxSize = getIntent().getIntExtra("maxSize", 9);
    }

    @Override
    protected void loadData() {
        mPresenter.loadPictureByDirectoryName(mCurDirectoryName);
    }

    @Override
    protected void initListener() {
        tv_directory.setOnClickListener(this);
        tv_selected.setOnClickListener(this);
        tv_preview.setOnClickListener(this);

    }

    @Override
    public void loadAllPictureSuccess(List<LocalPictureBean> localPictureList) {
        if (data == null) {
            data = localPictureList;
        } else {
            data.clear();
            data.addAll(localPictureList);
        }

        //拍摄照片
        data.add(0, null);
        setOrNotifyAdapter();
    }


    @Override
    public void loadAllDirectorySuccess(final List<LocalDirectoryBean> directoryList) {
        final BottomSheetDialog dialog = new BottomSheetDialog(getMActivity());
        View contentView = LayoutInflater.from(getMActivity()).inflate(R.layout.layout_directory, getMContentView(), false);
        RecyclerView directory_recycler_view = (RecyclerView) contentView.findViewById(R.id.directory_recycler_view);
        PictureDirectoryAdapter pictureDirectoryAdapter;
        directory_recycler_view.setAdapter(pictureDirectoryAdapter = new PictureDirectoryAdapter(getMActivity(), directoryList));
        pictureDirectoryAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mCurDirectoryName = directoryList.get(position).directoryName;
                tv_directory.setText(directoryList.get(position).showName);
                mPresenter.loadPictureByDirectoryName(mCurDirectoryName);
                dialog.dismiss();
            }
        });
        dialog.setContentView(contentView);
        dialog.show();
    }

    private List<LocalPictureBean> data;
    private LocalPictureAdapter adapter;

    public void setOrNotifyAdapter() {
        if (adapter == null) {
            adapter = new LocalPictureAdapter(getMActivity(), data, mMaxSize);
            recycler_view.setLayoutManager(new GridLayoutManager(getMActivity(), 3));
            recycler_view.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    int position = parent.getChildAdapterPosition(view);
                    outRect.left = DensityUtil.dip2px(getMActivity(), 2);
                    outRect.top = DensityUtil.dip2px(getMActivity(), 2);
                    if ((position + 1) % 3 == 0) {
                        outRect.right = DensityUtil.dip2px(getMActivity(), 2);
                    } else {
                        outRect.right = 0;
                    }
                    outRect.bottom = 0;


                }
            });
            adapter.setOnItemSelectChangeListener(this);
            adapter.setOnTakePhotoListener(this);
            recycler_view.setAdapter(adapter);
            return;
        }
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_directory:
                mPresenter.loadAllDirectory();
                break;
            case R.id.tv_selected:
                if (adapter == null) {
                    toastShow("您还没有选择图片");
                }
                HashMap<String, LocalPictureBean> checkedPictureMap = adapter.getCheckedPictureMap();
                if (checkedPictureMap == null || checkedPictureMap.isEmpty()) {
                    toastShow("您还没有选择图片");
                    return;
                }


                ArrayList<PictureBean> checkedPicturePath = new ArrayList<>();
                Collection<LocalPictureBean> values = checkedPictureMap.values();
                for (LocalPictureBean info :
                        values) {
                    checkedPicturePath.add(new PictureBean(info.path));
                }
                Intent intent = new Intent();
                intent.putExtra("pictureList", checkedPicturePath);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.tv_preview://预览
                if (adapter == null) {
                    return;
                }

                ArrayList<String> checkedPictureList = new ArrayList<>();
                Collection<LocalPictureBean> checkedValues = adapter.getCheckedPictureMap().values();
                for (LocalPictureBean info :
                        checkedValues) {
                    checkedPictureList.add(info.path);
                }
                PictureLookActivity.launch(getMActivity(), checkedPictureList);

                break;
        }
    }


    @Override
    public void onChange(int size) {
        if (size == 0) {
            tv_selected.setEnabled(false);
            tv_preview.setEnabled(false);
            tv_selected.setText("确认");
        } else {
            tv_selected.setEnabled(true);
            tv_preview.setEnabled(true);
            tv_selected.setText("确认(" + size + "/" + mMaxSize + ")");
        }
    }


    String mPhotoSavePath = "";

    @Override
    public void onTakePhoto() {
        String fileName = String.valueOf(System.currentTimeMillis()) + ".png";
        mPhotoSavePath = CommonUtils.getExternalFilesDirPath(GlobalApplication.Companion.getMContext(),
                Environment.DIRECTORY_PICTURES) + "/" + fileName;
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri imageUri = Uri.fromFile(new File(CommonUtils.getExternalFilesDirPath(GlobalApplication.Companion.getMContext(),
                Environment.DIRECTORY_PICTURES), fileName));
        openCameraIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(openCameraIntent, PictureChooseUtils.PHOTOTAKE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PictureChooseUtils.PHOTOTAKE:// 拍照
                //通知系统有新图片
                if (new File((mPhotoSavePath)).exists()) {
                    try {
                        MediaStore.Images.Media.insertImage(getMActivity().getContentResolver(), mPhotoSavePath, new File(mPhotoSavePath).getName(), null);
                        // 最后通知图库更新
                        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + mPhotoSavePath));
                        sendBroadcast(intent);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    this.data.add(1, new LocalPictureBean(mPhotoSavePath, Environment.DIRECTORY_PICTURES));
                    setOrNotifyAdapter();
                }

                break;
        }

    }
}
