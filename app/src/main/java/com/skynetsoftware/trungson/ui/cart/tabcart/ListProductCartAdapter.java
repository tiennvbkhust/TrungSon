package com.skynetsoftware.trungson.ui.cart.tabcart;

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
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.interfaces.ICallback;
import com.skynetsoftware.trungson.models.Product;
import com.skynetsoftware.trungson.utils.AppConstant;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListProductCartAdapter extends RecyclerSwipeAdapter<RecyclerView.ViewHolder> {

    private final List<Product> models;
    private OnListenerClickItemEvent onListenerClickItemEvent;
    Context context;

    public ListProductCartAdapter(Context context, List<Product> models, OnListenerClickItemEvent mOnListenerClickItemEvent) {
        this.models = models;
        this.context = context;
        this.onListenerClickItemEvent = mOnListenerClickItemEvent;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holders, final int position) {
        ProductHolder holder = (ProductHolder) holders;
        final Product product = models.get(position);
        holder.tvNameProduct.setText(product.getName());
        holder.tvPriceProduct.setText(String.format("%,.0f vnđ", product.getPrice()));
        holder.tvIdProduct.setText(String.format("Mã sản phảm: %s", product.getCode()));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.tvDescriptionProduct.setText("Tác dụng : " + Html.fromHtml(product.getEffect(), Html.FROM_HTML_MODE_COMPACT));
            holder.tvStateProduct.setText(
                    Html.fromHtml(
                            String.format(context.getString(R.string.product_state_format), "Còn hàng"),
                            Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.tvDescriptionProduct.setText("Tác dụng : " + Html.fromHtml(product.getEffect()));
            if (product.getStatus() == AppConstant.STATE_OUT_OF_STORE) {
                holder.tvStateProduct.setText(Html.fromHtml(String.format(context.getString(R.string.product_state_format_ots), "Tạm hết Hàng")));
            } else {
                holder.tvStateProduct.setText(Html.fromHtml(String.format(context.getString(R.string.product_state_format), "Còn hàng")));
            }
        }


        if (product.getImg() != null && !product.getImg().isEmpty())
            Picasso.with(context).load(product.getImg()).into(holder.imgProduct);
        if (position == 0) {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.card.getLayoutParams();
            layoutParams.topMargin = (int) context.getResources().getDimension(R.dimen.dp16);
            holder.card.setLayoutParams(layoutParams);
        }

        holder.tvNumber.setText(product.getNumberOfProduct()+"");
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

        holder.btnShowDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onListenerClickItemEvent.onClickAccept(position);
                mItemManger.closeAllItems();
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

    class ProductHolder extends RecyclerView.ViewHolder {
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
        @BindView(R.id.btnShowDetail)
        FrameLayout btnShowDetail;
        @BindView(R.id.btnDelete)
        FrameLayout btnDelete;
        @BindView(R.id.tvNumber)
        TextView tvNumber;

        public ProductHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public interface OnListenerClickItemEvent {
        void onClickNoAccept(int position);

        void onClickAccept(int position);
    }

//    extends RecyclerView.Adapter<ListProductCartAdapter.ProductHolder> {
//    List<Product> list;
//    Context context;
//    ICallback iCallback;
//
//
//    public ListProductCartAdapter(List<Product> list, Context context, ICallback iCallback) {
//        this.list = list;
//        this.context = context;
//        this.iCallback = iCallback;
//    }
//
//    @NonNull
//    @Override
//    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return new ProductHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false));
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ProductHolder holder, final int position) {
//        Product product = list.get(position);
//        holder.tvNameProduct.setText(product.getName());
//        holder.tvPriceProduct.setText(String.format("%,.0f vnđ", product.getPrice()));
//        holder.tvIdProduct.setText(String.format("Mã sản phảm: %s", product.getCode()));
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            holder.tvDescriptionProduct.setText("Tác dụng : "+Html.fromHtml(list.get(position).getEffect(), Html.FROM_HTML_MODE_COMPACT));
//            if (product.getStatus() == AppConstant.STATE_OUT_OF_STORE) {
//                holder.tvStateProduct.setText(Html.fromHtml(
//                        String.format(context.getString(R.string.product_state_format_ots), "Tạm hết Hàng"),
//                        Html.FROM_HTML_MODE_COMPACT)
//                );
//            } else {
//                holder.tvStateProduct.setText(
//                        Html.fromHtml(
//                                String.format(context.getString(R.string.product_state_format), "Còn hàng"),
//                                Html.FROM_HTML_MODE_COMPACT));
//            }
//        } else {
//            holder.tvDescriptionProduct.setText("Tác dụng : "+Html.fromHtml(list.get(position).getEffect()));
//            if (product.getStatus() == AppConstant.STATE_OUT_OF_STORE) {
//                holder.tvStateProduct.setText(Html.fromHtml(String.format(context.getString(R.string.product_state_format_ots), "Tạm hết Hàng")));
//            } else {
//                holder.tvStateProduct.setText(Html.fromHtml(String.format(context.getString(R.string.product_state_format), "Còn hàng")));
//            }
//        }
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                iCallback.onCallBack(position);
//            }
//        });
//        if (product.getImg() != null && !product.getImg().isEmpty())
//            Picasso.with(context).load(product.getImg()).into(holder.imgProduct);
//        if(position == 0) {
//            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.card.getLayoutParams();
//            layoutParams.topMargin= (int) context.getResources().getDimension(R.dimen.dp16);
//            holder.card.setLayoutParams(layoutParams);
//        }
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    class ProductHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.imgProduct)
//        ImageView imgProduct;
//        @BindView(R.id.tvNameProduct)
//        TextView tvNameProduct;
//        @BindView(R.id.tvIdProduct)
//        TextView tvIdProduct;
//        @BindView(R.id.tvPriceProduct)
//        TextView tvPriceProduct;
//        @BindView(R.id.tvStateProduct)
//        TextView tvStateProduct;
//        @BindView(R.id.tvDescriptionProduct)
//        TextView tvDescriptionProduct;
//        @BindView(R.id.card)
//        CardView card;
//
//
//        public ProductHolder(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this,itemView);
//        }
//    }
}
