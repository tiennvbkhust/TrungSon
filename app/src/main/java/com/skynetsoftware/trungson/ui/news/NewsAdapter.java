package com.skynetsoftware.trungson.ui.news;

import android.content.Context;
import android.os.Build;
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
import com.skynetsoftware.trungson.models.News;
import com.skynetsoftware.trungson.ui.cart.tabcart.ListProductCartAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<News> models;

    private ListProductCartAdapter.OnListenerClickItemEvent onListenerClickItemEvent;
    Context context;

    public NewsAdapter(Context context, List<News> models, ListProductCartAdapter.OnListenerClickItemEvent mOnListenerClickItemEvent) {
        this.models = models;
        this.context = context;
        this.onListenerClickItemEvent = mOnListenerClickItemEvent;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new ProducHistorytHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holders, final int position) {
        ProducHistorytHolder holder = (ProducHistorytHolder) holders;
        final News product = models.get(position);
        holder.tvTime.setText(product.getDate());
        holder.name.setText(product.getTitle());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.content.setText(
                    Html.fromHtml(
                           product.getContent(),
                            Html.FROM_HTML_MODE_COMPACT));

        } else {
            holder.content.setText(
                    Html.fromHtml(
                            product.getContent()));
        }
        if(product.getImg()!=null && !product.getImg().isEmpty()){
            Picasso.with(context).load(product.getImg()).fit().centerCrop().into(holder.img);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onListenerClickItemEvent.onClickAccept(position);
            }
        });
    }




    class ProducHistorytHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.content)
        TextView content;
        @BindView(R.id.tvTime)
        TextView tvTime;
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