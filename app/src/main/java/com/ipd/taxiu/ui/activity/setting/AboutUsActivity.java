package com.ipd.taxiu.ui.activity.setting;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.ipd.taxiu.R;
import com.ipd.taxiu.bean.TextBean;
import com.ipd.taxiu.presenter.TextPresenter;
import com.ipd.taxiu.ui.BaseUIActivity;
import com.ipd.taxiu.utils.HtmlImageGetter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * Created by Miss on 2018/7/24
 * 关于我们
 */
public class AboutUsActivity extends BaseUIActivity implements TextPresenter.ITextView{
    private TextPresenter mPresenter;

    TextView textView;

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        mPresenter = new TextPresenter();
        mPresenter.attachView(this,this);
    }

    @Override
    protected void onViewDetach() {
        super.onViewDetach();
        mPresenter.detachView();
        mPresenter = null;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        initToolbar();
        textView = findViewById(R.id.company_explain);
    }

    @Override
    protected void loadData() {
        mPresenter.getTextInfo(2);
    }

    @Override
    protected void initListener() {

    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        return "关于我们";
    }

    @Override
    public void onSuccess(@NotNull TextBean data) {
        textView.setText(Html.fromHtml(data.CONTENT,new HtmlImageGetter(this, textView), null));
    }

    @Override
    public void onFail(@NotNull String errMsg) {
        toastShow(errMsg);
    }
}
