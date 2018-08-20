package com.ipd.taxiu.ui.activity.referral;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ipd.jumpbox.jumpboxlibrary.widget.CircleImageView;
import com.ipd.taxiu.R;
import com.ipd.taxiu.adapter.HomepageAdapter;
import com.ipd.taxiu.bean.AttentionBean;
import com.ipd.taxiu.bean.HomepageBean;
import com.ipd.taxiu.bean.UserBean;
import com.ipd.taxiu.imageload.ImageLoader;
import com.ipd.taxiu.platform.http.HttpUrl;
import com.ipd.taxiu.presenter.MinePresenter;
import com.ipd.taxiu.ui.BaseActivity;
import com.ipd.taxiu.widget.MessageDialog;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Miss on 2018/7/25
 * 查看他人主页
 */
public class HomepageActivity extends BaseActivity implements View.OnClickListener, MinePresenter.IAttentionView {
    private ImageView iv_back;
    private RecyclerView recycler_view;
    private List<HomepageBean> data;
    private HomepageAdapter mAdapter;

    private LinearLayout attention, add_attention, done_attention;
    private MinePresenter mPresenter;
    private AttentionBean bean;

    @BindView(R.id.civ_header)
    CircleImageView civ_header;

    @BindView(R.id.tv_friend_nickname)
    TextView tv_friend_nickname;

    @Override
    protected int getBaseLayout() {
        return R.layout.activity_homepage;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        ButterKnife.bind(this);
        bean = (AttentionBean) getIntent().getSerializableExtra("AttentionBean");
        iv_back = findViewById(R.id.iv_back);
        recycler_view = findViewById(R.id.swipe_target);
        attention = findViewById(R.id.attention);
        add_attention = findViewById(R.id.add_attention);
        done_attention = findViewById(R.id.done_attention);

        ImageLoader.loadAvatar(this, HttpUrl.IMAGE_URL+bean.LOGO,civ_header);
        tv_friend_nickname.setText(bean.NICKNAME);
        setAttention();
    }

    private void setAttention(){
        if (bean.IS_ATTEN == 0){
            add_attention.setVisibility(View.VISIBLE);
            done_attention.setVisibility(View.GONE);
            attention.setVisibility(View.GONE);
        }
        if (bean.IS_ATTEN == 1){
            add_attention.setVisibility(View.GONE);
            done_attention.setVisibility(View.VISIBLE);
            attention.setVisibility(View.GONE);
        }
        if (bean.IS_ATTEN == 2){
            add_attention.setVisibility(View.GONE);
            done_attention.setVisibility(View.GONE);
            attention.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        mPresenter = new MinePresenter();
        mPresenter.attachView(this, this);
    }

    @Override
    protected void onViewDetach() {
        super.onViewDetach();
        mPresenter.detachView();
        mPresenter = null;
    }

    @Override
    protected void loadData() {
        initData();
        mAdapter = new HomepageAdapter(this, data);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        recycler_view.setAdapter(mAdapter);

    }

    @Override
    protected void initListener() {
        iv_back.setOnClickListener(this);
        attention.setOnClickListener(this);
        add_attention.setOnClickListener(this);
        done_attention.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.attention:
                mPresenter.attention(bean.USER_ID);
                break;
            case R.id.add_attention:
                mPresenter.attention(bean.USER_ID);
                break;
            case R.id.done_attention:
                mPresenter.attention(bean.USER_ID);
                break;
        }
    }

    private void initData() {
        data = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            data.add(new HomepageBean());
        }
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFail(@NotNull String errMsg) {
        toastShow(errMsg);
    }
}
