package com.ipd.rainbow.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ipd.jumpbox.jumpboxlibrary.utils.CommonUtils;
import com.ipd.jumpbox.jumpboxlibrary.widget.CircleImageView;
import com.ipd.rainbow.R;
import com.ipd.rainbow.bean.PetBean;
import com.ipd.rainbow.imageload.ImageLoader;
import com.ipd.rainbow.platform.http.HttpUrl;
import com.ipd.rainbow.ui.activity.pet.PetInformationActivity;

import java.util.List;


/**
 * Created by Miss on 2018/7/25
 */
public class MyPetAdapter extends RecyclerView.Adapter<MyPetAdapter.ViewHolder> {
    private Context context;
    private List<PetBean> datas;

    public MyPetAdapter(Context context, List<PetBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyPetAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_my_pet, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ImageLoader.loadImgFromLocal(context, HttpUrl.IMAGE_URL+datas.get(position).LOGO,holder.civ_header);
        holder.tv_pet_nickname.setText(datas.get(position).NICKNAME);
        holder.tv_pet_kind.setText(datas.get(position).PET_TYPE_NAME);
        int status = datas.get(position).STATUS;
        switch (status){
            case 1:
                holder.tv_marital_status.setText("正常");
                break;
            case 2:
                holder.tv_marital_status.setText("寻求好心人领养");
                break;
            case 3:
                holder.tv_marital_status.setText("寻求配偶");
                break;
            case 4:
                holder.tv_marital_status.setText("走失了");
                break;
        }
        holder.tv_pet_birthday.setText("生日："+ CommonUtils.textCut(datas.get(position).BIRTHDAY," "));
        if (datas.get(position).GENDER == 1){
            holder.iv_pet_sex.setImageResource(R.mipmap.boy);
        }else {
            holder.iv_pet_sex.setImageResource(R.mipmap.girl);
        }

        holder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PetInformationActivity.class);
                intent.putExtra("PET_ID",datas.get(position).PET_ID);
                intent.putExtra("PET_TYPE_ID",datas.get(position).PET_TYPE_ID);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rl_item;
        CircleImageView civ_header;
        TextView tv_pet_nickname;
        TextView tv_pet_birthday;
        TextView tv_pet_kind;
        TextView tv_marital_status;
        ImageView iv_pet_sex;
        public ViewHolder(View itemView) {
            super(itemView);
            rl_item = itemView.findViewById(R.id.rl_item);
            civ_header = itemView.findViewById(R.id.civ_header);
            tv_pet_nickname = itemView.findViewById(R.id.tv_pet_nickname);
            tv_pet_birthday = itemView.findViewById(R.id.tv_pet_birthday);
            tv_pet_kind = itemView.findViewById(R.id.tv_pet_kind);
            tv_marital_status = itemView.findViewById(R.id.tv_marital_status);
            iv_pet_sex = itemView.findViewById(R.id.iv_pet_sex);
        }
    }
}
