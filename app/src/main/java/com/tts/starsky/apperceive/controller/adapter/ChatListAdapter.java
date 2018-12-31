package com.tts.starsky.apperceive.controller.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tts.starsky.apperceive.R;
import com.tts.starsky.apperceive.bean.ChatListBean;

import java.util.List;

public class ChatListAdapter  extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {
    private List<ChatListBean> msgList;

    static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout leftLayout;
        RelativeLayout rightLayout;

        TextView leftMsg;
        TextView rightMsg;

        public ViewHolder(View itemView) {
            super(itemView);
            leftLayout=(LinearLayout) itemView.findViewById(R.id.left_layout);
            rightLayout=(RelativeLayout)itemView.findViewById(R.id.right_layout);
            leftMsg=(TextView) itemView.findViewById(R.id.left_msg);
            rightMsg=(TextView) itemView.findViewById(R.id.right_msg);
        }
    }

    public ChatListAdapter(List<ChatListBean> msgList) {
        this.msgList = msgList;
    }

    @Override
    public ChatListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatListAdapter.ViewHolder holder, int position) {
        ChatListBean msg=msgList.get(position);
        System.out.println("=========ChatListBean"+msg);
        switch (msg.getType()){
            case RECEIVED://接收的消息
                holder.leftLayout.setVisibility(View.VISIBLE);
                holder.rightLayout.setVisibility(View.GONE);
                holder.leftMsg.setText(msg.getContent());
                break;
            case SENT://发出的消息
                holder.leftLayout.setVisibility(View.GONE);
                holder.rightLayout.setVisibility(View.VISIBLE);
                holder.rightMsg.setText(msg.getContent());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }
}
