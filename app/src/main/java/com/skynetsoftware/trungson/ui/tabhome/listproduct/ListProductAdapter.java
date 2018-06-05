package com.skynetsoftware.trungson.ui.tabhome.listproduct;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.interfaces.ICallback;
import com.skynetsoftware.trungson.models.Product;
import com.skynetsoftware.trungson.utils.AppConstant;
import com.skynetsoftware.trungson.utils.PicassoUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListProductAdapter extends RecyclerView.Adapter<ListProductAdapter.ProductHolder> {
    List<Product> list;
    Context context;
    ICallback iCallback;


    public ListProductAdapter(List<Product> list, Context context, ICallback iCallback) {
        this.list = list;
        this.context = context;
        this.iCallback = iCallback;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_product_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, final int position) {
        Product product = list.get(position);
        holder.tvNameProduct.setText(product.getName());
        holder.tvPriceProduct.setText(String.format("%,.0f vnđ", product.getPrice()));
        holder.tvIdProduct.setText(String.format("Mã sản phảm: %s", product.getCode()));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.tvDescriptionProduct.setText("Tác dụng : "+Html.fromHtml(list.get(position).getEffect(), Html.FROM_HTML_MODE_COMPACT));
            if (product.getStatus() == AppConstant.STATE_OUT_OF_STORE) {
                holder.tvStateProduct.setText(Html.fromHtml(
                        String.format(context.getString(R.string.product_state_format_ots), "Tạm hết Hàng"),
                        Html.FROM_HTML_MODE_COMPACT)
                );
            } else {
                holder.tvStateProduct.setText(
                        Html.fromHtml(
                                String.format(context.getString(R.string.product_state_format), "Còn hàng"),
                                Html.FROM_HTML_MODE_COMPACT));
            }
        } else {
            holder.tvDescriptionProduct.setText("Tác dụng : "+Html.fromHtml(list.get(position).getEffect()));
            if (product.getStatus() == AppConstant.STATE_OUT_OF_STORE) {
                holder.tvStateProduct.setText(Html.fromHtml(String.format(context.getString(R.string.product_state_format_ots), "Tạm hết Hàng")));
            } else {
                holder.tvStateProduct.setText(Html.fromHtml(String.format(context.getString(R.string.product_state_format), "Còn hàng")));
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iCallback.onCallBack(position);
            }
        });
        if (product.getImg() != null && !product.getImg().isEmpty())
            Picasso.with(context).load(product.getImg()).into(holder.imgProduct);



    }

    @Override
    public int getItemCount() {
        return list.size();
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

        public ProductHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
