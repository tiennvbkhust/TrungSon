package com.skynetsoftware.trungson.ui.favourite;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.interfaces.ICallback;
import com.skynetsoftware.trungson.models.Product;
import com.skynetsoftware.trungson.models.Shop;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchShopAdapter extends RecyclerView.Adapter<SearchShopAdapter.ShopHolder> {
    List<Product> list;
    Context context;
    ICallback iCallback;
    SparseBooleanArray sparseBooleanArray;
    IToggleCheckbox iToggleCheckbox;
    CallBackService callBackService;

    public SearchShopAdapter(List<Product> list, Context context, ICallback iCallback, IToggleCheckbox toggleCheckbox, CallBackService callBackService) {
        this.callBackService = callBackService;
        this.list = list;
        this.context = context;
        this.iCallback = iCallback;
        this.iToggleCheckbox = toggleCheckbox;
        sparseBooleanArray = new SparseBooleanArray();
        updateChecked();
    }

    public void updateChecked() {
        for (int i = 0; i < this.list.size(); i++) {
            sparseBooleanArray.append(i, this.list.get(i).getIs_favourite() == 1);
        }
    }

    @NonNull
    @Override
    public ShopHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ShopHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShopHolder holder, final int position) {
        Product product = list.get(position);
        holder.name.setText(product.getName());
        holder.cbFavourite.setVisibility(View.VISIBLE);
        holder.price.setText(String.format("%,.0f vnđ", product.getPrice()));
        if (product.getImg() != null && !product.getImg().isEmpty()) {
//            Picasso.with(context).load(product.getImg()).fit().centerCrop().into(holder.img);
            Glide.with(context).asBitmap().load(product.getImg()).into(holder.img);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iCallback.onCallBack(position);
            }
        });
        if (position == 0 || position == 1) {
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.Card.getLayoutParams();
            params.topMargin = (int) context.getResources().getDimension(R.dimen.dp16);
            holder.Card.setLayoutParams(params);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iCallback.onCallBack(position);
            }
        });
        holder.cbFavourite.setOnCheckedChangeListener(null);
        if (sparseBooleanArray.get(position)) {
            holder.cbFavourite.setChecked(true);
        } else {
            holder.cbFavourite.setChecked(false);
        }

        holder.cbFavourite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                list.get(position).setIs_favourite(isChecked ? 1 : 0);
                sparseBooleanArray.put(position, isChecked);
                iToggleCheckbox.toggle(position, isChecked);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface IToggleCheckbox {
        void toggle(int pos, boolean value);
    }

    class ShopHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.card)
        CardView Card;
        @BindView(R.id.cb)
        CheckBox cbFavourite;

        public ShopHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public interface CallBackService {
        void callBack(Product service);
    }
}
