package com.ipd.taxiu.adapter;

import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ipd.jumpbox.jumpboxlibrary.utils.ToastCommom;
import com.ipd.taxiu.R;
import com.ipd.taxiu.bean.OrderDetailBean;
import com.ipd.taxiu.ui.BaseUIActivity;

import java.util.List;

import static com.ipd.taxiu.adapter.OrderListAdapter.DELIVERY;
import static com.ipd.taxiu.adapter.OrderListAdapter.DETAIL;
import static com.ipd.taxiu.adapter.OrderListAdapter.EVALUATE;
import static com.ipd.taxiu.adapter.OrderListAdapter.PAYMENT;

/**
 * Created by Miss on 2018/7/20
 */
public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {
    private RecyclerView mRecyclerView;

    private List<OrderDetailBean> data;
    private Context mContext;

    private View VIEW_FOOTER;
    private View VIEW_HEADER;

    //Type
    private int TYPE_NORMAL = 1000;
    private int TYPE_HEADER = 1001;
    private int TYPE_FOOTER = 1002;

    private String orderStatus;

    public OrderDetailAdapter(List<OrderDetailBean> data, Context mContext,String orderStatus) {
        this.data = data;
        this.mContext = mContext;
        this.orderStatus = orderStatus;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            return new ViewHolder(VIEW_FOOTER);
        } else if (viewType == TYPE_HEADER) {
            return new ViewHolder(VIEW_HEADER);
        } else {
            return new ViewHolder(getLayout(R.layout.item_order_detail));
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (!isHeaderView(position) && !isFooterView(position)) {
            if (haveHeaderView()) position--;
            //商品信息
        }
    }


    @Override
    public int getItemCount() {
        int count = (data == null ? 0 : data.size());
        if (VIEW_FOOTER != null) {
            count++;
        }

        if (VIEW_HEADER != null) {
            count++;
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderView(position)) {
            return TYPE_HEADER;
        } else if (isFooterView(position)) {
            return TYPE_FOOTER;
        } else {
            return TYPE_NORMAL;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        try {
            if (mRecyclerView == null && mRecyclerView != recyclerView) {
                mRecyclerView = recyclerView;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private View getLayout(int layoutId) {
        return LayoutInflater.from(mContext).inflate(layoutId, null);
    }

    public void addHeaderView(View headerView) {
        if (haveHeaderView()) {
            throw new IllegalStateException("hearview has already exists!");
        } else {
            //避免出现宽度自适应
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            headerView.setLayoutParams(params);
            VIEW_HEADER = headerView;
            notifyItemInserted(0);

            TextView tv_order_status = headerView.findViewById(R.id.tv_order_status);
            ImageView iv_order_status = headerView.findViewById(R.id.iv_order_status);

            if (orderStatus.equals(PAYMENT)){
                tv_order_status.setText("待付款");
                iv_order_status.setImageResource(R.mipmap.detail_wait_pay);
            }else if (orderStatus.equals(DETAIL)){
                tv_order_status.setText("待发货");
                iv_order_status.setImageResource(R.mipmap.detail_wait_shipments);
            }else if (orderStatus.equals(DELIVERY)){
                tv_order_status.setText("待收货");
                iv_order_status.setImageResource(R.mipmap.detail_wait_delivery);
            }else if (orderStatus.equals(EVALUATE)){
                tv_order_status.setText("已完成");
                iv_order_status.setImageResource(R.mipmap.detail_off_the_stocks);
            }

        }

    }

    public void addFooterView(View footerView) {
        if (haveFooterView()) {
            throw new IllegalStateException("footerView has already exists!");
        } else {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            footerView.setLayoutParams(params);
            VIEW_FOOTER = footerView;
            notifyItemInserted(getItemCount() - 1);

            TextView tv_copy = footerView.findViewById(R.id.tv_copy);
            final TextView tv_order_code = footerView.findViewById(R.id.tv_order_code);

            tv_copy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager cmb = (ClipboardManager)mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                    cmb.setText(tv_order_code.getText().toString());
                    ToastCommom toastCommom = new ToastCommom();
                    toastCommom.show(mContext,"已复制");
                }
            });
        }
    }


    private boolean haveHeaderView() {
        return VIEW_HEADER != null;
    }

    public boolean haveFooterView() {
        return VIEW_FOOTER != null;
    }

    private boolean isHeaderView(int position) {
        return haveHeaderView() && position == 0;
    }

    private boolean isFooterView(int position) {
        return haveFooterView() && position == getItemCount() - 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
