package com.tts.starsky.apperceive.controller.adapter;


import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tts.starsky.apperceive.R;
import com.tts.starsky.apperceive.controller.listonclicklistener.MessageListOnClickListener;
import com.tts.starsky.apperceive.db.bean.MessageListBean;

import java.util.ArrayList;
import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> {

    AdapterView.OnItemClickListener mOnItemClickListener;
    private List<MessageListBean> dataList;
    private Context context;


    public MessageListAdapter(Context mContext, List<MessageListBean> dataList) {
        this.context = mContext;
        this.dataList = dataList;
    }

    public void addtData(List<MessageListBean> newDataList) {
        if (null != newDataList) {
            this.dataList.addAll(newDataList);
            notifyDataSetChanged();
        }
    }

    public void changeData(List<MessageListBean> newDataList) {
        dataList = newDataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessageListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        context = viewGroup.getContext();
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        MessageListAdapter.ViewHolder viewHolder = new MessageListAdapter.ViewHolder(inflate);
//        recyclerView.addItemDecoration(new XRecyclerView.DividerItemDecoration(this,XRecyclerView.DividerItemDecoration.VERTICAL));
//        inflate.findViewById()
//        viewGroup.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Toast.makeText(context,"点击了我"+dataList.get(i).getTime(), Toast.LENGTH_SHORT).show();
////            }
////        });
        Glide.with(context).
                load(dataList.get(i).getHeadPhoto()).
                into(viewHolder.iv_image);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageListAdapter.ViewHolder viewHolder, final int i) {
        System.out.println(viewHolder.getItemId());
        Glide.with(context).
                load(dataList.get(i).getHeadPhoto()).
                into(viewHolder.iv_image);
        viewHolder.tv_context.setText(dataList.get(i).getNewMessage());
        viewHolder.tv_fruit.setText(dataList.get(i).getUserNickName());
        if (dataList.get(i).getUnreadMessageNumber() != 0) {
            viewHolder.tv_zt.setText(String.valueOf(dataList.get(i).getUnreadMessageNumber()));
            viewHolder.tv_zt.setBackground(context.getResources().getDrawable(R.drawable.reccler_new_message));
        } else {
            viewHolder.tv_zt.setText(null);
            viewHolder.tv_zt.setBackgroundColor(Color.parseColor("#00ffffff"));
        }
        viewHolder.tv_time.setText(dataList.get(i).getTime().substring(10, 19));
        System.out.println(i);


//        if (mOnItemClickListener != null) {
        MessageListOnClickListener messageListOnClickListener = new MessageListOnClickListener(dataList, i);
        viewHolder.itemView.setOnClickListener(messageListOnClickListener);
        viewHolder.itemView.setOnLongClickListener(messageListOnClickListener);
//        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_image;
        TextView tv_fruit;
        TextView tv_context;
        TextView tv_zt;
        TextView tv_time;

        ViewHolder(View itemView) {
            super(itemView);
            iv_image = itemView.findViewById(R.id.iv_image);
            tv_fruit = itemView.findViewById(R.id.tv_fruit);
            tv_context = itemView.findViewById(R.id.tv_context);
            tv_zt = itemView.findViewById(R.id.tv_zt);
            tv_time = itemView.findViewById(R.id.tv_time);
        }
    }

}