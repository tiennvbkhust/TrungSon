package com.skynetsoftware.trungson.ui.notification;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.interfaces.ICallback;
import com.skynetsoftware.trungson.models.Notification;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.CategoryViewHoder> {

    List<Notification> list;
    Context context;
    SparseBooleanArray sparseBooleanArray;
    ICallback iCallback;

    public NotificationAdapter(List<Notification> listCategories, Context context, ICallback iCallback) {
        this.list = listCategories;
        this.context = context;
        sparseBooleanArray = new SparseBooleanArray();
        for (int i = 0; i < this.list.size(); i++) {
            sparseBooleanArray.append(i, list.get(i).isRead() == 1);
        }
        this.iCallback = iCallback;
    }

    @NonNull
    @Override
    public CategoryViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHoder(LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHoder holder, final int position) {
        holder.tvTitle.setText(list.get(position).getTitle());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.tvContent.setText(Html.fromHtml(list.get(position).getName(), Html.FROM_HTML_MODE_COMPACT));

        } else {
            holder.tvContent.setText(Html.fromHtml(list.get(position).getName()));
        }
        holder.tvTime.setText(list.get(position).getTime());

        if (sparseBooleanArray.get(position)) {
//            holder.card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white));
//            holder.tvTime.setTextColor(ContextCompat.getColor(context, R.color.black));
//            holder.tvContent.setTextColor(ContextCompat.getColor(context, R.color.black));
//            holder.tvTitle.setTextColor(ContextCompat.getColor(context, R.color.black));
            holder.imgDot.setVisibility(View.GONE);
        } else {
            holder.card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorPinkWhite));
//            holder.tvTime.setTextColor(ContextCompat.getColor(context, R.color.white));
//            holder.tvContent.setTextColor(ContextCompat.getColor(context, R.color.white));
            holder.imgDot.setVisibility(View.VISIBLE);

//            holder.tvTitle.setTextColor(ContextCompat.getColor(context, R.color.white));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iCallback.onCallBack(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class CategoryViewHoder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.tvContent)
        TextView tvContent;
        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.imgDot)
        ImageView imgDot;
        @BindView(R.id.card)
        CardView card;

        public CategoryViewHoder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
