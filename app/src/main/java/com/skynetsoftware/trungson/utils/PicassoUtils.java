package com.skynetsoftware.trungson.utils;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

public class PicassoUtils {

    public static void loadImage(Context context, String uri, ImageView view) {
        if (uri != null && !uri.isEmpty())
            Picasso.with(context).load(uri).fit().centerCrop().into(view);
    }

    public static void loadImage(Context context, File file, ImageView view) {

    }


}
