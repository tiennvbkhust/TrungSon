package com.skynetsoftware.trungson.ui.tabhome;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.skynetsoftware.trungson.models.Shop;
import com.skynetsoftware.trungson.ui.views.SlideLoopAdapter;
import com.skynetsoftware.trungson.ui.views.SlideView;
import com.skynetsoftware.trungson.utils.PicassoUtils;
import com.skynetsoftware.trungson.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

public class SlidePhotoAdapter extends SlideLoopAdapter {
    List<Shop> list;

    public SlidePhotoAdapter(SlideView viewPager) {
        super(viewPager);
        this.list = new ArrayList<>();


    }

    public void setUrlPhotos(List<Shop> urlPhotos) {
        this.list = urlPhotos;

    }

    @Override
    public View getView(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_photo_slide, container, false);
        final ImageView imageView = view.findViewById(R.id.img);
        TextView tv = view.findViewById(R.id.tv);
        if (list.size() > 0 && position < list.size() && list.get(position).getAvatar() != null && !list.get(position).getAvatar().isEmpty()) {
            Glide.with(container.getContext()).load(list.get(position).getAvatar()).into(imageView);
            tv.setText(list.get(position).getName());
        } else {
            imageView.setImageResource(R.drawable.slide_default);
        }
        return view;
    }

    @Override
    protected int getRealCount() {
        return 3;
    }
}