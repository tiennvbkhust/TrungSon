package com.skynetsoftware.trungson.ui.tabhome;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.skynetsoftware.trungson.models.Shop;
import com.skynetsoftware.trungson.ui.views.SlideLoopAdapter;
import com.skynetsoftware.trungson.ui.views.SlideView;
import com.skynetsoftware.trungson.utils.PicassoUtils;
import com.skynetsoftware.trungson.R;

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
        ImageView imageView = view.findViewById(R.id.img);
        if (list.size() > 0 && position < getRealCount() && list.get(position).getAvatar() != null && !list.get(position).getAvatar().isEmpty()) {
            PicassoUtils.loadImage(container.getContext(), list.get(position).getAvatar(), imageView);
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