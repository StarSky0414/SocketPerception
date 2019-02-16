package com.tts.starsky.apperceive.controller.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tts.starsky.apperceive.R;
import com.tts.starsky.apperceive.bean.TrendsListItemBean;
import com.tts.starsky.apperceive.view.MyActivity;
import com.tts.starsky.apperceive.view.fragment.MyFragment;

import java.util.List;

public class TrendsListAdapter extends RecyclerView.Adapter<TrendsListAdapter.ViewHolder> implements View.OnClickListener  {

    AdapterView.OnItemClickListener mOnItemClickListener;
    private List<TrendsListItemBean> dataList;
    private Context context;
    private int i;


    public TrendsListAdapter(Context mContext, List<TrendsListItemBean> dataList) {
        this.context = mContext;
        this.dataList = dataList;
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

    @NonNull
    @Override
    public TrendsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        context = viewGroup.getContext();
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_trends_item, viewGroup, false);
        TrendsListAdapter.ViewHolder viewHolder = new TrendsListAdapter.ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrendsListAdapter.ViewHolder viewHolder, final int i) {
        System.out.println(viewHolder.getItemId());
//viewHolder.iv_content.setImageResource();
        this.i = i;
        System.out.println(this.i);

        viewHolder.tv_user_nick.setOnClickListener(this);
        viewHolder.iv_user_head_photo.setOnClickListener(this);
//        if (mOnItemClickListener != null) {
//            MessageListOnClickListener messageListOnClickListener = new MessageListOnClickListener(dataList,i);
//            viewHolder.itemView.setOnClickListener(messageListOnClickListener);
//            viewHolder.itemView.setOnLongClickListener(messageListOnClickListener);
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_user_nick:
            case R.id.iv_user_head_photo:
                Toast.makeText(context, "onClick    "+i+"    "+dataList.get(i).getSendUserId(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this.context, MyActivity.class);
                context.startActivity(intent);
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (dataList == null){
            return 0;
        }
        return dataList.size();

    }


    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_user_head_photo;
        ImageView iv_content;
        ImageView iv_like;
        ImageView iv_comment;
        ImageView iv_collect;
        ImageView iv_liked;
        TextView tv_user_nick;
        TextView tv_content;
        TextView tv_like_num;

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

        }
    }



}