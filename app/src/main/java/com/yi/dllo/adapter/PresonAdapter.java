package com.yi.dllo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yi.dllo.bean.SmsByContectBean;
import com.yi.dllo.bean.SmsData;
import com.yi.dllo.phonebook.R;

/**
 * Created by dllo on 16/1/25.
 */
public class PresonAdapter extends RecyclerView.Adapter {

    private SmsByContectBean smsByContectBean;
    public static final int SEND_MESSAGE = 1;
    public static final int RECEIVER_MESSAGE = 0;
    private Context context;

    public PresonAdapter(Context context, SmsByContectBean smsByContectBean) {
        this.smsByContectBean = smsByContectBean;
        this.context = context;


    }

    //加入向适配其中加入单条数据
    public void addSingleSms(SmsByContectBean smsByContectBean) {
        this.smsByContectBean = smsByContectBean;
        notifyDataSetChanged();//更新 UI
    }

    //确定加载的数据类型
    public int getitemViewType(int position) {
        int viewType = smsByContectBean.getSingleSmsBeans().get(position).getType();
        if (viewType == 1) {
            return RECEIVER_MESSAGE;
        } else return SEND_MESSAGE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case SEND_MESSAGE:
                View sendView = LayoutInflater.from(context).inflate(R.layout.recycler_person_item_send, parent, false);
                viewHolder = new SendMessageViewHolder(sendView);
                break;
            default:
                View receiverView = LayoutInflater.from(context).inflate(R.layout.recycler_person_item_receiver, parent, false);
                viewHolder = new ReceiverMessageViewHolder(receiverView);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewtype = getitemViewType(position);//调用方法得到类型
        switch (viewtype) {
            case SEND_MESSAGE:
                SendMessageViewHolder sendViewHolder = (SendMessageViewHolder) holder;
                sendViewHolder.sendTv.setText(smsByContectBean.getSingleSmsBeans().get(position).getSmsBody());
                break;
            default:
                ReceiverMessageViewHolder receiverViewHolder = (ReceiverMessageViewHolder) holder;
                receiverViewHolder.receiverTv.setText(smsByContectBean.getSingleSmsBeans().get(position).getSmsBody());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return smsByContectBean.getSize();
    }

    class SendMessageViewHolder extends RecyclerView.ViewHolder {
        TextView sendTv;

        public SendMessageViewHolder(View itemView) {
            super(itemView);
            sendTv = (TextView) itemView.findViewById(R.id.person_send_tv);

        }
    }

    class ReceiverMessageViewHolder extends RecyclerView.ViewHolder {
        TextView receiverTv;

        public ReceiverMessageViewHolder(View itemView) {
            super(itemView);
            receiverTv = (TextView) itemView.findViewById(R.id.person_receiver_tv);
        }
    }
}
