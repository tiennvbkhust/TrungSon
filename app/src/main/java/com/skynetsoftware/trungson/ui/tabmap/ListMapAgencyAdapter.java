package com.skynetsoftware.trungson.ui.tabmap;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.interfaces.ICallback;
import com.skynetsoftware.trungson.models.Shop;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListMapAgencyAdapter extends RecyclerView.Adapter<ListMapAgencyAdapter.AgencyHolder> {
    List<Shop> list;
    Context context;
    ICallback iCallback;


    public ListMapAgencyAdapter(List<Shop> list, Context context, ICallback iCallback) {
        this.list = list;
        this.context = context;
        this.iCallback = iCallback;
    }

    @NonNull
    @Override
    public AgencyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AgencyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.map_agency_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AgencyHolder holder, final int position) {
        Shop shop = list.get(position);
        if (shop.getAvatar() != null && !shop.getAvatar().isEmpty())
            Picasso.with(context).load(shop.getAvatar()).fit().centerCrop().into(holder.img);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class AgencyHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img)
        ImageView img;

        public AgencyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
