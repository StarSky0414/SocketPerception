package com.tts.starsky.apperceive.controller.adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tts.starsky.apperceive.R;
import com.tts.starsky.apperceive.bean.TrendsListItemBean;
import com.tts.starsky.apperceive.bean.service.SendTrendsBean;
import com.tts.starsky.apperceive.view.OtherUserActivity;
import com.tts.starsky.apperceive.view.SendTrendActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class TrendsListAdapter extends RecyclerView.Adapter<TrendsListAdapter.ViewHolder> implements View.OnClickListener {

    private List<TrendsListItemBean> dataList;
    private Context context;
    private int i;

    private enum HideSign {
        My, OtherUser
    }

    private HideSign hideSign;
    private AlertDialog.Builder builder;


    public List<TrendsListItemBean> getDataList() {
        return dataList;
    }

    public void removeDataList(int i) {
        dataList.remove(i);
    }

    public TrendsListAdapter(Context mContext, List<TrendsListItemBean> dataList) {
        this.context = mContext;
        this.dataList = dataList;


        //======================
        //  初始化Dialog，设置监听
        //======================

//    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
        builder = new AlertDialog.Builder(context);
        //    设置Title的图标
        builder.setIcon(R.drawable.ic_launcher_background);
        //    设置Title的内容
//        builder.setTitle("弹出警告框");
        //    设置Content来显示一个信息
        builder.setMessage("确定删除吗？");
        //    设置一个PositiveButton
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "positive: " + i, Toast.LENGTH_SHORT).show();
                SendTrendsBean sendTrendsBean = new SendTrendsBean();
                TrendsListItemBean trendsListItemBean = getDataList().get(i);
                sendTrendsBean.setTrendId(String.valueOf(trendsListItemBean.getId()));
                System.out.println("============== i: " + i);
                System.out.println("====== dataList" + getDataList().get(i));
                removeDataList(i);
                notifyDataSetChanged();
                EventBus.getDefault().post(sendTrendsBean);
            }
        });
        //    设置一个NegativeButton
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "negative: " + which, Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void addtData(List<TrendsListItemBean> newDataList) {
        if (null != newDataList) {
            this.dataList.addAll(newDataList);
            notifyDataSetChanged();
        }
    }

    public void changeData(List<TrendsListItemBean> newDataList) {
        dataList = newDataList;
        notifyDataSetChanged();
    }

    public int getMinId() {
        for (TrendsListItemBean trendsListItemBean : dataList) {
            System.out.println("trendsListItemBean =============== : " + trendsListItemBean.toString());
        }
        int id = dataList.get(dataList.size() - 1).getId();
        System.out.println("getMinId ============ ：" + id);
        return id;
    }

    public void setHideOtherUser() {
        hideSign = HideSign.OtherUser;
    }

    public void setHideMy() {
        hideSign = HideSign.My;
    }


    @NonNull
    @Override
    public TrendsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        context = viewGroup.getContext();
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_trends_item, viewGroup, false);
        TrendsListAdapter.ViewHolder viewHolder = new TrendsListAdapter.ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final TrendsListAdapter.ViewHolder viewHolder, final int i) {
        System.out.println("[============" + i);

        viewHolder.tv_user_nick.setOnClickListener(this);
        viewHolder.iv_user_head_photo.setOnClickListener(this);
        viewHolder.bt_trend_my_dele.setOnClickListener(this);
        viewHolder.bt_trend_my_update.setOnClickListener(this);

        viewHolder.tv_content.setText(dataList.get(i).getContent());
        System.out.println("[============" + dataList.get(i).getUrl());
        Glide.with(context).
                load(dataList.get(i).getUrl()).
                into(viewHolder.iv_content);


        Toast.makeText(context, "v: " + i, Toast.LENGTH_SHORT).show();


        viewHolder.tv_trend_time.setText(dataList.get(i).getTime());

        viewHolder.bt_trend_my_dele.setTag(i);
        viewHolder.tv_user_nick.setTag(i);
        viewHolder.iv_user_head_photo.setTag(i);
        viewHolder.bt_trend_my_update.setTag(i);

        switch (hideSign) {
            case My:
                viewHolder.iv_like.setVisibility(View.GONE);
                viewHolder.iv_comment.setVisibility(View.GONE);
                viewHolder.iv_collect.setVisibility(View.GONE);

                break;
            case OtherUser:

                viewHolder.bt_trend_my_dele.setVisibility(View.GONE);
                viewHolder.bt_trend_my_update.setVisibility(View.GONE);
                break;
        }


    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {

            case R.id.tv_user_nick:
            case R.id.iv_user_head_photo:
                i = (Integer) v.getTag();
                Toast.makeText(context, "onClick    " + i + "    " + dataList.get(i).getSendUserId(), Toast.LENGTH_SHORT).show();
                intent = new Intent(this.context, OtherUserActivity.class);
                context.startActivity(intent);
                break;
            case R.id.bt_trend_my_dele:
                i = (Integer) v.getTag();
                //    显示出该对话框
                builder.show();
                break;
            case R.id.bt_trend_my_update:
                System.out.println("================ bt_trend_my_update ==============");
                i = (Integer) v.getTag();
                TrendsListItemBean trendsListItemBean = dataList.get(i);
                intent = new Intent();
                //用Bundle携带数据
                Bundle bundle = new Bundle();
                //传递name参数为tinyphp
                System.out.println("================= trendsListItemBean"+ trendsListItemBean.toString());
                intent.putExtra("trendsBeanId", String.valueOf(trendsListItemBean.getId()));//设置参数
                intent.putExtra("trendsBeanContent", trendsListItemBean.getContent());//设置参数
                intent.putExtra("trendsBeanUrl", trendsListItemBean.getUrl());//设置参数
                intent.setClass(context, SendTrendActivity.class);//从哪里跳到哪里
                context.startActivity(intent);
                break;
//
        }
    }

    @Override
    public int getItemCount() {
        if (dataList == null) {
            return 0;
        }
        return dataList.size();

    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_user_head_photo;
        ImageView iv_content;

        ImageView iv_liked;
        TextView tv_user_nick;
        TextView tv_content;
        TextView tv_like_num;
        TextView tv_trend_time;

        Button bt_trend_my_dele;
        Button bt_trend_my_update;

        ImageView iv_like;
        ImageView iv_comment;
        ImageView iv_collect;


        ViewHolder(View itemView) {
            super(itemView);
            iv_user_head_photo = itemView.findViewById(R.id.iv_user_head_photo);
            iv_content = itemView.findViewById(R.id.iv_content);
            iv_like = itemView.findViewById(R.id.iv_like);
            iv_comment = itemView.findViewById(R.id.iv_comment);
            iv_collect = itemView.findViewById(R.id.iv_collect);
            iv_liked = itemView.findViewById(R.id.iv_liked);
            tv_user_nick = itemView.findViewById(R.id.tv_user_nick);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_like_num = itemView.findViewById(R.id.tv_like_num);
            tv_trend_time = itemView.findViewById(R.id.tv_trend_time);
            bt_trend_my_dele = itemView.findViewById(R.id.bt_trend_my_dele);
            bt_trend_my_update = itemView.findViewById(R.id.bt_trend_my_update);

        }
    }


}