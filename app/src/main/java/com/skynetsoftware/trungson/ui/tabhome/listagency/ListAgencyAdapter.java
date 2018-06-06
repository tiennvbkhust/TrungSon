package com.skynetsoftware.trungson.ui.tabhome.listagency;

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
import com.skynetsoftware.trungson.models.Product;
import com.skynetsoftware.trungson.models.Shop;
import com.skynetsoftware.trungson.utils.AppConstant;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListAgencyAdapter extends RecyclerView.Adapter<ListAgencyAdapter.AgencyHolder> {
    List<Shop> list;
    Context context;
    ICallback iCallback;


    public ListAgencyAdapter(List<Shop> list, Context context, ICallback iCallback) {
        this.list = list;
        this.context = context;
        this.iCallback = iCallback;
    }

    @NonNull
    @Override
    public AgencyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AgencyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_agency_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AgencyHolder holder, final int position) {
        Shop product = list.get(position);
        holder.tvNameAgency.setText(product.getName());
        holder.tvAddressAgency.setText(String.format("%.2fkm - %s", product.getDistance(),product.getAddress()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.tvDescriptionAgency.setText(Html.fromHtml(list.get(position).getDescription(), Html.FROM_HTML_MODE_COMPACT));

        } else {
            holder.tvDescriptionAgency.setText(Html.fromHtml(list.get(position).getDescription()));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iCallback.onCallBack(position);
            }
        });
        if (product.getAvatar() != null && !product.getAvatar().isEmpty())
            Picasso.with(context).load(product.getAvatar()).into(holder.imgAgency);
        holder.rateAgency.setRating((float) product.getStar());
        if(position == 0) {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.card.getLayoutParams();
            layoutParams.topMargin= (int) context.getResources().getDimension(R.dimen.dp16);
            holder.card.setLayoutParams(layoutParams);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class AgencyHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imgAgency)
        ImageView imgAgency;
        @BindView(R.id.tvNameAgency)
        TextView tvNameAgency;
        @BindView(R.id.tvDescriptionAgency)
        TextView tvDescriptionAgency;
        @BindView(R.id.tvAddressAgency)
        TextView tvAddressAgency;
        @BindView(R.id.rateAgency)
        AppCompatRatingBar rateAgency;
        @BindView(R.id.card)
        CardView card;
        public AgencyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
