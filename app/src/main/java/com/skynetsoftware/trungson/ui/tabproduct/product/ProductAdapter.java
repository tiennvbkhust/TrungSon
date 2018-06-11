package com.skynetsoftware.trungson.ui.tabproduct.product;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.interfaces.ICallback;
import com.skynetsoftware.trungson.models.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    List<Product> list;
    Context context;
    ICallback iCallback;


    public ProductAdapter(List<Product> list, Context context, ICallback iCallback) {
        this.list = list;
        this.context = context;
        this.iCallback = iCallback;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, final int position) {
        Product product = list.get(position);
        holder.name.setText(product.getName());
        holder.price.setText(String.format("%,.0f vnđ",product.getPrice()));
        if(product.getImg()!= null && !product.getImg().isEmpty()){
//            Picasso.with(context).load(product.getImg()).fit().centerCrop().into(holder.img);
            Glide.with(context).asBitmap().load(product.getImg()).into(holder.img);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iCallback.onCallBack(position);
            }
        });
        if(position == 0 || position == 1){
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.Card.getLayoutParams();
            params.topMargin = (int) context.getResources().getDimension(R.dimen.dp16);
            holder.Card.setLayoutParams(params);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.price)
        TextView price;
   @BindView(R.id.card)
   CardView Card;

        public ProductViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
