package com.ipd.taxiu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ipd.taxiu.R;
import com.ipd.taxiu.bean.ProductBean;
import com.ipd.taxiu.imageload.ImageLoader;
import com.ipd.taxiu.utils.Order;

import java.util.List;

/**
 * Created by Miss on 2018/7/20
 */
public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {
    private RecyclerView mRecyclerView;

    private List<ProductBean> data;
    private Context mContext;

    private View VIEW_FOOTER;
    private View VIEW_HEADER;

    //Type
    private int TYPE_NORMAL = 1000;
    private int TYPE_HEADER = 1001;
    private int TYPE_FOOTER = 1002;

    private int orderStatus;

    public OrderDetailAdapter(List<ProductBean> data, Context mContext, int orderStatus) {
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
            ProductBean info = data.get(position);
            ImageLoader.loadNoPlaceHolderImg(mContext, info.LOGO, holder.iv_commodity_head);
            holder.tv_commodity_name.setText(info.PROCUCT_NAME);
            holder.tv_commodity_explain.setText(info.TASTE);
            holder.tv_commodity_price.setText("￥" + info.CURRENT_PRICE);
            holder.tv_commodity_num.setText("数量：x" + info.BUY_NUM);
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

            if (orderStatus == Order.PAYMENT) {
                tv_order_status.setText("待付款");
                iv_order_status.setImageResource(R.mipmap.detail_wait_pay);
            } else if (orderStatus == Order.WAIT_SEND) {
                tv_order_status.setText("待发货");
                iv_order_status.setImageResource(R.mipmap.detail_wait_shipments);
            } else if (orderStatus == Order.WAIT_RECEIVE) {
                tv_order_status.setText("待收货");
                iv_order_status.setImageResource(R.mipmap.detail_wait_delivery);
            } else if (orderStatus == Order.EVALUATE) {
                tv_order_status.setText("待评价");
                iv_order_status.setImageResource(R.mipmap.detail_wait_evaluate);
            } else if (orderStatus == Order.FINFISH) {
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

            TextView payment_time = footerView.findViewById(R.id.payment_time);
            TextView payment_method = footerView.findViewById(R.id.payment_method);
            TextView tv_shipments_time = footerView.findViewById(R.id.tv_shipments_time);
            TextView tv_delivery_time = footerView.findViewById(R.id.tv_delivery_time);

            payment_time.setVisibility(orderStatus >= Order.WAIT_SEND ? View.VISIBLE : View.GONE);
            payment_method.setVisibility(orderStatus >= Order.WAIT_SEND ? View.VISIBLE : View.GONE);
            tv_shipments_time.setVisibility(orderStatus >= Order.WAIT_RECEIVE ? View.VISIBLE : View.GONE);
            tv_delivery_time.setVisibility(orderStatus > Order.WAIT_RECEIVE ? View.VISIBLE : View.GONE);
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

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_commodity_head;
        TextView tv_commodity_name;
        TextView tv_commodity_explain;
        TextView tv_commodity_price;
        TextView tv_commodity_num;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_commodity_head = itemView.findViewById(R.id.iv_commodity_head);
            tv_commodity_name = itemView.findViewById(R.id.tv_commodity_name);
            tv_commodity_explain = itemView.findViewById(R.id.tv_commodity_explain);
            tv_commodity_price = itemView.findViewById(R.id.tv_commodity_price);
            tv_commodity_num = itemView.findViewById(R.id.tv_commodity_num);
        }
    }
}
