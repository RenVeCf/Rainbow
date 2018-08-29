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
import com.ipd.taxiu.bean.OtherBean;
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
public class HomepageActivity extends BaseActivity implements View.OnClickListener, MinePresenter.IAttentionView, MinePresenter.IOtherView {
    private ImageView iv_back;
    private RecyclerView recycler_view;
    private List<HomepageBean> data;
    private HomepageAdapter mAdapter;

    private LinearLayout attention, add_attention, done_attention;
    private MinePresenter mPresenter;
    private AttentionBean bean;
    private int attentionId = -1;

    @BindView(R.id.civ_header)
    CircleImageView civ_header;

    @BindView(R.id.tv_friend_nickname)
    TextView tv_friend_nickname;

    @BindView(R.id.tv_tag)
    TextView tv_tag;

    @BindView(R.id.tv_attention_num)
    TextView tv_attention_num;

    @BindView(R.id.tv_fans_num)
    TextView tv_fans_num;

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
    }

    private void setAttention() {
        if (attentionId == 0) {
            add_attention.setVisibility(View.VISIBLE);
            done_attention.setVisibility(View.GONE);
            attention.setVisibility(View.GONE);
        }
        if (attentionId == 1) {
            add_attention.setVisibility(View.GONE);
            done_attention.setVisibility(View.VISIBLE);
            attention.setVisibility(View.GONE);
        }
        if (attentionId == 2) {
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
        mPresenter.other(bean.USER_ID + "");
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
    public void onFail(@NotNull String errMsg) {
        toastShow(errMsg);
    }

    @Override
    public void onSuccess(@NotNull String msg, int data) {
        mPresenter.other(bean.USER_ID + "");
        toastShow(msg);
    }

    @Override
    public void onGetOtherSuccess(OtherBean data) {
        ImageLoader.loadAvatar(this, HttpUrl.IMAGE_URL + data.LOGO, civ_header);
        tv_friend_nickname.setText(data.NICKNAME);
        tv_tag.setText(data.TAG);
        attentionId = data.IS_ATTEN;
        setAttention(); 
        tv_attention_num.setText(data.ATTENTION_NUM + "");
        tv_fans_num.setText(data.FANS_NUM + "");
    }

    @Override
    public void onGetOtherFail(@NotNull String errMsg) {
        toastShow(errMsg);
    }
}
