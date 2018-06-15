package com.skynetsoftware.trungson.ui.history;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.interfaces.ICallback;
import com.skynetsoftware.trungson.models.Booking;
import com.skynetsoftware.trungson.models.Product;
import com.skynetsoftware.trungson.ui.cart.tabcart.ListProductCartAdapter;
import com.skynetsoftware.trungson.utils.AppConstant;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryAdapter extends RecyclerSwipeAdapter<RecyclerView.ViewHolder> {

    private final List<Booking> models;
    private ListProductCartAdapter.OnListenerClickItemEvent onListenerClickItemEvent;
    Context context;

    public HistoryAdapter(Context context, List<Booking> models, ListProductCartAdapter.OnListenerClickItemEvent mOnListenerClickItemEvent) {
        this.models = models;
        this.context = context;
        this.onListenerClickItemEvent = mOnListenerClickItemEvent;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        return new ProducHistorytHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holders, final int position) {
        ProducHistorytHolder holder = (ProducHistorytHolder) holders;
        final Booking product = models.get(position);
        holder.tvNameProduct.setText("Đơn hàng "+ product.getId());
        holder.tvPriceProduct.setText(String.format("%,.0f vnđ", product.getPrice()));
        holder.tvIdProduct.setText(String.format("Mã đơn hàng: %s", product.getId()));
        holder.tvDescriptionProduct.setText("Ngày mua : " + product.getDate_booking());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.tvStateProduct.setText(
                    Html.fromHtml(
                            String.format(context.getString(R.string.product_state_format), "Còn hàng"),
                            Html.FROM_HTML_MODE_COMPACT));
            if (product.getActive() == 1) {
                holder.tvStateProduct.setText(Html.fromHtml(String.format(context.getString(R.string.product_state_format_ots), "Đã đặt"),
                        Html.FROM_HTML_MODE_COMPACT));
            } else if(product.getActive() == 2){
                holder.tvStateProduct.setText(Html.fromHtml(String.format(context.getString(R.string.product_state_format), "Đang giao"),
                        Html.FROM_HTML_MODE_COMPACT));
            }else if(product.getActive() == 3){
                holder.tvStateProduct.setText(Html.fromHtml(String.format(context.getString(R.string.product_state_format), "Đã giao"),
                        Html.FROM_HTML_MODE_COMPACT));
            }else{
                holder.tvStateProduct.setText(Html.fromHtml(String.format(context.getString(R.string.product_state_format), "Đã hủy"),
                        Html.FROM_HTML_MODE_COMPACT));
            }
        } else {
            if (product.getActive() == 1) {
                holder.tvStateProduct.setText(Html.fromHtml(String.format(context.getString(R.string.product_state_format_ots), "Đã đặt")));
            } else if(product.getActive() == 2){
                holder.tvStateProduct.setText(Html.fromHtml(String.format(context.getString(R.string.product_state_format), "Đang giao")));
            }else if(product.getActive() == 3){
                holder.tvStateProduct.setText(Html.fromHtml(String.format(context.getString(R.string.product_state_format), "Đã giao")));
            }else{
                holder.tvStateProduct.setText(Html.fromHtml(String.format(context.getString(R.string.product_state_format), "Đã hủy")));
            }
        }

        if (product.getImg() != null && !product.getImg().isEmpty())
            Picasso.with(context).load(product.getImg()).into(holder.imgProduct);
        if (position == 0) {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.card.getLayoutParams();
            layoutParams.topMargin = (int) context.getResources().getDimension(R.dimen.dp16);
            holder.card.setLayoutParams(layoutParams);
        }

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                models.remove(position);
                notifyItemRemoved(position);
                notifyDataSetChanged();
                onListenerClickItemEvent.onClickNoAccept(position);
                mItemManger.closeAllItems();
            }
        });
        holder.layoutContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onListenerClickItemEvent.onClickAccept(position);
            }
        });
        mItemManger.bindView(holder.itemView, position);
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    public void changeNotitfy() {
        notifyDataSetChanged();
        mItemManger.closeAllItems();
    }

    class ProducHistorytHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imgProduct)
        ImageView imgProduct;
        @BindView(R.id.tvNameProduct)
        TextView tvNameProduct;
        @BindView(R.id.tvIdProduct)
        TextView tvIdProduct;
        @BindView(R.id.tvPriceProduct)
        TextView tvPriceProduct;
        @BindView(R.id.tvStateProduct)
        TextView tvStateProduct;
        @BindView(R.id.tvDescriptionProduct)
        TextView tvDescriptionProduct;
        @BindView(R.id.card)
        CardView card;
        @BindView(R.id.swipe)
        SwipeLayout swipeLayout;
        @BindView(R.id.btnDelete)
        FrameLayout btnDelete;
        @BindView(R.id.layoutContent)
        LinearLayout layoutContent;

        public ProducHistorytHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    @Override
    public int getItemCount() {
        return models.size();
    }


}