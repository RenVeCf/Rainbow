package com.ipd.taxiu.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;
import com.ipd.taxiu.R;
import com.ipd.taxiu.imageload.ImageLoader;
import com.ipd.taxiu.ui.BaseActivity;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by jumpbox on 2017/8/7.
 */

public class PictureLookActivity extends BaseActivity {

    private ArrayList<String> mPictureList;
    private int mCurPos;
    public static final int LOCAL = 0, URL = 1;
    private int mType;

    public static void launch(Activity activity, ArrayList<String> pictureList) {
        launch(activity, pictureList, 0, LOCAL);
    }

    public static void launch(Activity activity, ArrayList<String> pictureList, int curPos, int type) {
        Intent intent = new Intent(activity, PictureLookActivity.class);
        intent.putStringArrayListExtra("pictureList", pictureList);
        intent.putExtra("curPos", curPos);
        intent.putExtra("type", type);
        activity.startActivity(intent);
    }

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_picture_look;
    }

    @Override
    protected void onViewAttach() {

    }

    @Override
    protected void onViewDetach() {

    }

    ViewPager view_pager;
    TextView tv_index;

    @Override
    protected void initView(@Nullable Bundle bundle) {
        ButterKnife.bind(this);
        view_pager = findViewById(R.id.view_pager);
        tv_index = findViewById(R.id.tv_index);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        mPictureList = getIntent().getStringArrayListExtra("pictureList");
        if (mPictureList == null || mPictureList.isEmpty()) {
            finish();
        }

        mCurPos = getIntent().getIntExtra("curPos", 0);
        mType = getIntent().getIntExtra("type", LOCAL);
        tv_index.setText((mCurPos + 1) + "/" + mPictureList.size());


    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initListener() {
        view_pager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mPictureList == null ? 0 : mPictureList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View contentView = LayoutInflater.from(getMActivity()).inflate(R.layout.layout_preview, container, false);
                PhotoView photoView = (PhotoView) contentView.findViewById(R.id.photo_view);
                if (mPictureList.size() > position) {
                    String imagePath = mPictureList.get(position);
                    if (mType == LOCAL) {
                        ImageLoader.loadImgFromLocal(getMActivity(), imagePath, photoView);
                    } else {
                        ImageLoader.loadImgWithPlaceHolder(getMActivity(), imagePath, R.mipmap.banner_default, photoView);
                    }
                }


                container.addView(contentView);
                return contentView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });

        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tv_index.setText((position + 1) + "/" + mPictureList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        view_pager.setCurrentItem(mCurPos);

    }

    @OnClick({R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
