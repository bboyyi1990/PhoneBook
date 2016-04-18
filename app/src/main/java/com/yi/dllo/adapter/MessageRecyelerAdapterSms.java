package com.yi.dllo.adapter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yi.dllo.Interface.MessageIterface;
import com.yi.dllo.activity.PersonActivity;
import com.yi.dllo.bean.SmsByContectBean;
import com.yi.dllo.bean.SmsData;
import com.yi.dllo.phonebook.R;

/**
 * Created by dllo on 16/1/21.
 */
public class MessageRecyelerAdapterSms extends RecyclerView.Adapter<MessageRecyelerAdapterSms.MessageViewHolder> {
    private SmsData smsData;
    private MessageIterface messageIterface;

    public void
    addData(SmsData smsData) {
        this.smsData = smsData;
        notifyDataSetChanged();

    }

    public void setSmsDataListener(MessageIterface smsDataListener) {
        this.messageIterface = smsDataListener;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MessageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_message, parent, false));
    }

    @Override
    public void onBindViewHolder(final MessageViewHolder holder, int position) {
        holder.name.setText(smsData.getSmsByContectBeans().get(position).getName());
        holder.body.setText(smsData.getSmsByContectBeans().get(position).getSingleSmsBeans().get(smsData.getSmsByContectBeans().get(position).getSingleSmsBeans().size() - 1).getSmsBody());
        holder.position = position;
    }

    @Override
    public int getItemCount() {
        return smsData.getCount();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView name, body;
        private LinearLayout linearLayout;
        private int position;

        public MessageViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.message_recyclerview_name);
            body = (TextView) itemView.findViewById(R.id.message_recyclerview_body);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.message_recyclerview_linelayout);
            linearLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            SmsByContectBean bean = smsData.getSmsByContectBeans().get(position);
            messageIterface.messageItemlistener(bean, position);


        }
    }
}
