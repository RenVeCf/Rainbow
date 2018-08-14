package com.ipd.taxiu.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ipd.jumpbox.jumpboxlibrary.widget.CircleImageView;
import com.ipd.taxiu.R;
import com.ipd.taxiu.ui.BaseFragment;
import com.ipd.taxiu.ui.activity.SignInActivity;
import com.ipd.taxiu.ui.activity.address.DeliveryAddressActivity;
import com.ipd.taxiu.ui.activity.balance.MyBalanceActivity;
import com.ipd.taxiu.ui.activity.coupon.DiscountCouponActivity;
import com.ipd.taxiu.ui.activity.coupon.MyIntegralActivity;
import com.ipd.taxiu.ui.activity.group.GroupBookingActivity;
import com.ipd.taxiu.ui.activity.message.MessageActivity;
import com.ipd.taxiu.ui.activity.mine.PersonInformationActivity;
import com.ipd.taxiu.ui.activity.mine.published.MineClassRoomActivity;
import com.ipd.taxiu.ui.activity.mine.published.MineJoinTopicActivity;
import com.ipd.taxiu.ui.activity.mine.published.PublishedTalkActivity;
import com.ipd.taxiu.ui.activity.mine.published.PublishedTaxiuActivity;
import com.ipd.taxiu.ui.activity.order.MyOrderActivity;
import com.ipd.taxiu.ui.activity.order.ReturnMoneyCommodityActivity;
import com.ipd.taxiu.ui.activity.pet.MyPetActivity;
import com.ipd.taxiu.ui.activity.pet.PetBibleActivity;
import com.ipd.taxiu.ui.activity.referral.ReferralCodeActivity;
import com.ipd.taxiu.ui.activity.setting.MyCollectActivity;
import com.ipd.taxiu.ui.activity.setting.SettingActivity;
import com.ipd.taxiu.ui.activity.setting.SocialContactActivity;

import org.jetbrains.annotations.Nullable;


/**
 * Created by Miss on 2018/7/19
 */
public class PersonFragment extends BaseFragment implements View.OnClickListener {
    private RelativeLayout rl_all_order, rl_wait_pay, rl_wait_shipments, rl_wait_delivery, rl_off_the_stocks;
    private RelativeLayout rl_return_record, rl_setting, rl_message, rl_referral, rl_delivery_address, rl_pet_bible,
            rl_my_pet, rl_pet_housekeeper, rl_published_taxiu, rl_mine_classroom, rl_mine_join_topic, rl_mine_talk;
    private LinearLayout ll_sign_in, ll_my_collect, ll_my_fans, ll_attention_num;
    private RelativeLayout rl_my_group,rl_my_integral,rl_discount_coupon,rl_my_balance;
    private RelativeLayout rl_header;

    @Override
    protected int getBaseLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {

    }

    @Override
    protected void loadData() {

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
                DeliveryAddressActivity.Companion.launch(getMActivity());
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
                intent1.putExtra("contact", "fans");
                startActivity(intent1);
                break;
            case R.id.ll_attention_num:
                intent1 = new Intent(getActivity(), SocialContactActivity.class);
                intent1.putExtra("contact", "attention");
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
                intent1 = new Intent(getActivity(),MyIntegralActivity.class);
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
    }
}
