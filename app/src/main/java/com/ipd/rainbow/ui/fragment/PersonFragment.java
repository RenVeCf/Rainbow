package com.ipd.rainbow.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ipd.jumpbox.jumpboxlibrary.widget.CircleImageView;
import com.ipd.rainbow.R;
import com.ipd.rainbow.bean.UserHomeBean;
import com.ipd.rainbow.imageload.ImageLoader;
import com.ipd.rainbow.platform.global.GlobalParam;
import com.ipd.rainbow.platform.http.HttpUrl;
import com.ipd.rainbow.presenter.MinePresenter;
import com.ipd.rainbow.ui.BaseFragment;
import com.ipd.rainbow.ui.activity.SignInActivity;
import com.ipd.rainbow.ui.activity.address.DeliveryAddressActivity;
import com.ipd.rainbow.ui.activity.balance.MyBalanceActivity;
import com.ipd.rainbow.ui.activity.coupon.DiscountCouponActivity;
import com.ipd.rainbow.ui.activity.coupon.MyIntegralActivity;
import com.ipd.rainbow.ui.activity.group.GroupBookingActivity;
import com.ipd.rainbow.ui.activity.message.MessageActivity;
import com.ipd.rainbow.ui.activity.mine.PersonInformationActivity;
import com.ipd.rainbow.ui.activity.mine.published.MineClassRoomActivity;
import com.ipd.rainbow.ui.activity.mine.published.MineJoinTopicActivity;
import com.ipd.rainbow.ui.activity.mine.published.PublishedTalkActivity;
import com.ipd.rainbow.ui.activity.mine.published.PublishedTaxiuActivity;
import com.ipd.rainbow.ui.activity.order.MyOrderActivity;
import com.ipd.rainbow.ui.activity.order.ReturnMoneyCommodityActivity;
import com.ipd.rainbow.ui.activity.pet.MyPetActivity;
import com.ipd.rainbow.ui.activity.pet.PetBibleActivity;
import com.ipd.rainbow.ui.activity.referral.ReferralCodeActivity;
import com.ipd.rainbow.ui.activity.setting.MyCollectActivity;
import com.ipd.rainbow.ui.activity.setting.SettingActivity;
import com.ipd.rainbow.ui.activity.setting.SocialContactActivity;
import com.ipd.rainbow.utils.GuideUtil;
import com.ipd.rainbow.widget.CircleMenuLayout;
import com.ipd.rainbow.widget.guideview.GuideBuilder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * Created by Miss on 2018/7/19
 */
public class PersonFragment extends BaseFragment implements View.OnClickListener, MinePresenter.IUserHomeView {
    private CircleMenuLayout rl_wait_pay, rl_wait_shipments, rl_wait_delivery, rl_my_group, rl_published_taxiu, rl_mine_classroom,
            rl_mine_join_topic, rl_mine_talk, rl_return_record, rl_message;
    private RelativeLayout rl_all_order, rl_off_the_stocks;
    private RelativeLayout rl_setting, rl_referral, rl_delivery_address, rl_pet_bible,
            rl_my_pet, rl_pet_housekeeper;
    private LinearLayout ll_sign_in, ll_my_collect, ll_my_fans, ll_attention_num;
    private RelativeLayout rl_my_integral, rl_discount_coupon, rl_my_balance;
    private RelativeLayout rl_header;

    private MinePresenter mPresenter;
    private CircleImageView civ_header;
    private TextView tv_nickname;
    private TextView tv_signature;
    private TextView tv_collect_num;
    private TextView tv_attention_num;
    private TextView tv_fans_num;

    @Override
    protected int getBaseLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
    }

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        mPresenter = new MinePresenter<>();
        mPresenter.attachView(getContext(), this);
    }

    @Override
    protected void onViewDetach() {
        super.onViewDetach();
        mPresenter.detachView();
        mPresenter = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    protected void loadData() {
        mPresenter.getUserHome();
    }

    @Override
    protected void initListener() {
        rl_all_order.setOnClickListener(this);
        rl_wait_pay.setOnClickListener(this);
        rl_wait_shipments.setOnClickListener(this);
        rl_wait_delivery.setOnClickListener(this);
        rl_off_the_stocks.setOnClickListener(this);
        rl_return_record.setOnClickListener(this);
        rl_setting.setOnClickListener(this);
        rl_message.setOnClickListener(this);
        rl_referral.setOnClickListener(this);
        rl_delivery_address.setOnClickListener(this);
        rl_pet_bible.setOnClickListener(this);
        rl_my_pet.setOnClickListener(this);
        ll_sign_in.setOnClickListener(this);
        ll_my_collect.setOnClickListener(this);
        ll_my_fans.setOnClickListener(this);
        ll_attention_num.setOnClickListener(this);
        rl_my_group.setOnClickListener(this);
        rl_pet_housekeeper.setOnClickListener(this);

        rl_published_taxiu.setOnClickListener(this);
        rl_mine_classroom.setOnClickListener(this);
        rl_mine_join_topic.setOnClickListener(this);
        rl_mine_talk.setOnClickListener(this);
        rl_my_integral.setOnClickListener(this);
        rl_discount_coupon.setOnClickListener(this);
        rl_my_balance.setOnClickListener(this);
        rl_header.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), MyOrderActivity.class);
        Intent intent1;
        switch (v.getId()) {
            case R.id.rl_all_order:
                intent.putExtra("status", 0);
                startActivity(intent);
                break;
            case R.id.rl_wait_pay:
                intent.putExtra("status", 1);
                startActivity(intent);
                break;
            case R.id.rl_wait_shipments:
                intent.putExtra("status", 2);
                startActivity(intent);
                break;
            case R.id.rl_wait_delivery:
                intent.putExtra("status", 3);
                startActivity(intent);
                break;
            case R.id.rl_off_the_stocks:
                intent.putExtra("status", 4);
                startActivity(intent);
                break;
            case R.id.rl_return_record:
                intent1 = new Intent(getActivity(), ReturnMoneyCommodityActivity.class);
                startActivity(intent1);
                break;
            case R.id.rl_setting:
                intent1 = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent1);
                break;
            case R.id.rl_message:
                MessageActivity.Companion.launch(getMActivity());
                break;
            case R.id.rl_referral:
                intent1 = new Intent(getActivity(), ReferralCodeActivity.class);
                startActivity(intent1);
                break;
            case R.id.rl_delivery_address:
                DeliveryAddressActivity.Companion.launch(getMActivity(), DeliveryAddressActivity.Companion.getNORMAL());
                break;
            case R.id.rl_header:
                intent1 = new Intent(getActivity(), PersonInformationActivity.class);
                startActivity(intent1);
                break;
            case R.id.rl_pet_bible:
                intent1 = new Intent(getActivity(), PetBibleActivity.class);
                startActivity(intent1);
                break;
            case R.id.rl_my_pet:
                intent1 = new Intent(getActivity(), MyPetActivity.class);
                startActivity(intent1);
                break;
            case R.id.ll_sign_in:
                SignInActivity.Companion.launch(getMActivity());
                break;
            case R.id.ll_my_collect:
                intent1 = new Intent(getActivity(), MyCollectActivity.class);
                startActivity(intent1);
                break;
            case R.id.ll_my_fans:
                intent1 = new Intent(getActivity(), SocialContactActivity.class);
                intent1.putExtra("TYPE", 2);
                startActivity(intent1);
                break;
            case R.id.ll_attention_num:
                intent1 = new Intent(getActivity(), SocialContactActivity.class);
                intent1.putExtra("TYPE", 1);
                startActivity(intent1);
                break;
            case R.id.rl_my_group:
                GroupBookingActivity.Companion.launch(getMActivity());
                break;
            case R.id.rl_pet_housekeeper:
                toastShow("此功能暂未开发，敬请期待");
                break;
            case R.id.rl_published_taxiu:
                PublishedTaxiuActivity.Companion.launch(getMActivity());
                break;
            case R.id.rl_mine_classroom:
                MineClassRoomActivity.Companion.launch(getMActivity());
                break;
            case R.id.rl_mine_join_topic:
                MineJoinTopicActivity.Companion.launch(getMActivity());
                break;
            case R.id.rl_mine_talk:
                PublishedTalkActivity.Companion.launch(getMActivity());
                break;
            case R.id.rl_my_integral:
                intent1 = new Intent(getActivity(), MyIntegralActivity.class);
                startActivity(intent1);
                break;
            case R.id.rl_discount_coupon:
                DiscountCouponActivity.Companion.launch(getMActivity());
                break;
            case R.id.rl_my_balance:
                MyBalanceActivity.Companion.launch(getMActivity());
                break;
        }
    }

    @Override
    protected void initBaseLayout(@Nullable ViewGroup rootView) {
        super.initBaseLayout(rootView);
        rl_all_order = rootView.findViewById(R.id.rl_all_order);
        rl_wait_pay = rootView.findViewById(R.id.rl_wait_pay);
        rl_wait_shipments = rootView.findViewById(R.id.rl_wait_shipments);
        rl_wait_delivery = rootView.findViewById(R.id.rl_wait_delivery);
        rl_off_the_stocks = rootView.findViewById(R.id.rl_off_the_stocks);
        rl_return_record = rootView.findViewById(R.id.rl_return_record);
        rl_setting = rootView.findViewById(R.id.rl_setting);
        rl_message = rootView.findViewById(R.id.rl_message);
        rl_referral = rootView.findViewById(R.id.rl_referral);
        rl_delivery_address = rootView.findViewById(R.id.rl_delivery_address);
        rl_pet_bible = rootView.findViewById(R.id.rl_pet_bible);
        rl_my_pet = rootView.findViewById(R.id.rl_my_pet);
        ll_sign_in = rootView.findViewById(R.id.ll_sign_in);
        ll_my_collect = rootView.findViewById(R.id.ll_my_collect);
        ll_my_fans = rootView.findViewById(R.id.ll_my_fans);
        ll_attention_num = rootView.findViewById(R.id.ll_attention_num);
        rl_my_group = rootView.findViewById(R.id.rl_my_group);
        rl_pet_housekeeper = rootView.findViewById(R.id.rl_pet_housekeeper);

        rl_published_taxiu = rootView.findViewById(R.id.rl_published_taxiu);
        rl_mine_classroom = rootView.findViewById(R.id.rl_mine_classroom);
        rl_mine_join_topic = rootView.findViewById(R.id.rl_mine_join_topic);
        rl_mine_talk = rootView.findViewById(R.id.rl_mine_talk);
        rl_my_integral = rootView.findViewById(R.id.rl_my_integral);
        rl_discount_coupon = rootView.findViewById(R.id.rl_discount_coupon);
        rl_my_balance = rootView.findViewById(R.id.rl_my_balance);
        rl_header = rootView.findViewById(R.id.rl_header);

        civ_header = rootView.findViewById(R.id.civ_header);
        tv_nickname = rootView.findViewById(R.id.tv_nickname);
        tv_signature = rootView.findViewById(R.id.tv_signature);
        tv_collect_num = rootView.findViewById(R.id.tv_collect_num);
        tv_attention_num = rootView.findViewById(R.id.tv_attention_num);
        tv_fans_num = rootView.findViewById(R.id.tv_fans_num);
    }

    @Override
    public void getInfoSuccess(@NotNull UserHomeBean data) {
        if (data != null) {
            ImageLoader.loadImgFromLocal(getContext(), HttpUrl.IMAGE_URL + data.LOGO, civ_header);
            tv_nickname.setText(data.NICKNAME);
            if (!TextUtils.isEmpty(data.TAG)) {
                tv_signature.setText(data.TAG);
            }
            tv_attention_num.setText(data.ATTENTION_NUM + "");
            tv_collect_num.setText(data.COLLECT_NUM + "");
            tv_fans_num.setText(data.FANS_NUM + "");

            rl_wait_pay.setCircleNum(data.WAIT_PAY);
            rl_wait_shipments.setCircleNum(data.WAIT_SEND);
            rl_wait_delivery.setCircleNum(data.WAIT_RECEIPT);
            rl_my_group.setCircleNum(data.TEAM_NUM);
            rl_published_taxiu.setCircleNum(data.SHOW_NUM);
            rl_mine_classroom.setCircleNum(data.CLASS_NUM);
            rl_mine_join_topic.setCircleNum(data.TOPIC_NUM);
            rl_mine_talk.setCircleNum(data.QUESTION_NUM);
            rl_return_record.setCircleNum(data.REFUND_NUM);
            rl_message.setCircleNum(data.NEWS_NUM);

            showGuideView();

        }
    }

    private void showGuideView() {
        if (!GlobalParam.getFirstEnterMine()) return;
        ll_sign_in.post(new Runnable() {
            @Override
            public void run() {
                if (!GlobalParam.getFirstEnterMine()) return;
                GlobalParam.setFirstEnterMine(false);
                GuideUtil.INSTANCE.getSignGuide(R.id.ll_sign_in, new GuideBuilder.OnVisibilityChangedListener() {
                    @Override
                    public void onShown() {
                    }

                    @Override
                    public void onDismiss() {
                        GuideUtil.INSTANCE.getIntegralGuide(R.id.rl_my_integral, new GuideBuilder.OnVisibilityChangedListener() {
                            @Override
                            public void onShown() {

                            }

                            @Override
                            public void onDismiss() {

                            }
                        }).show(getMActivity());


                    }
                }).show(getMActivity());

            }
        });
    }

    @Override
    public void getInfoFail(@NotNull String errMsg) {
        toastShow(errMsg);
    }
}

