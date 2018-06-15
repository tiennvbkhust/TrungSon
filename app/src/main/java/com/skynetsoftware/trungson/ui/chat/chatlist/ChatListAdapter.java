package com.skynetsoftware.trungson.ui.chat.chatlist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.interfaces.ICallback;
import com.skynetsoftware.trungson.models.ChatItem;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatListHolder> {
    List<ChatItem> list;
    Context context;
    ICallback iCallback;
    SparseBooleanArray booleanArray;

    public ChatListAdapter(List<ChatItem> list, Context context, ICallback iCallback) {
        this.list = list;
        this.context = context;
        this.iCallback = iCallback;
    }

    @NonNull
    @Override
    public ChatListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatListHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListHolder holder, final int position) {
        holder.tvContent.setText(list.get(position).getLastMessage());
        holder.tvTime.setText(list.get(position).getTime().split(" ")[0]+" "+list.get(position).getTime().split(" ")[1].substring(0,list.get(position).getTime().split(" ")[1].lastIndexOf(':')));
        if (list.get(position).getUse().getAvatar() != null && !list.get(position).getUse().getAvatar().isEmpty()) {
            Picasso.with(context).load(list.get(position).getUse().getAvatar()).into(holder.imgAvt);
        }
        holder.tvName.setText(list.get(position).getUse().getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iCallback.onCallBack(position);
            }
        });
        if (list.get(position).getUserRead() == 1 && list.get(position).getShopRead() == 1) {
            holder.imgCheck.setImageResource(R.drawable.ic_check2);
            holder.imgCheck.setVisibility(View.VISIBLE);
        } else if (list.get(position).getShopRead() == 1 || list.get(position).getUserRead() == 1) {
            holder.imgCheck.setImageResource(R.drawable.ic_check);
            holder.imgCheck.setVisibility(View.VISIBLE);

        } else {
            holder.imgCheck.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ChatListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.imgAvt)
        CircleImageView imgAvt;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvContent)
        TextView tvContent;
        @BindView(R.id.imgCheck)
        ImageView imgCheck;

        public ChatListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
