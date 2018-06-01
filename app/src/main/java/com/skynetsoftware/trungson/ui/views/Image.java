package com.skynetsoftware.trungson.ui.views;

import android.os.Parcelable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class Image implements Parcelable {
    private Map<Long, String> imageWidthToSizeMap;

    public abstract double getAspectRatio();

    public abstract long id();

    abstract Map<String, String> uris();

    private boolean checkImageRatio(String st1r) {
      String[] str = st1r.split("x");
        if (Math.round(getAspectRatio() * 100.0d) == Math.round((Double.parseDouble(str[0]) / Double.parseDouble(str[1])) * 100.0d)) {
            return true;
        }
        return false;
    }

    private Map<Long, String> getImageWidthToSizeMap() {
        if (this.imageWidthToSizeMap == null) {
            this.imageWidthToSizeMap = new LinkedHashMap();
            for (String str : uris().keySet()) {
                if (checkImageRatio(str)) {
                    this.imageWidthToSizeMap.put(Long.valueOf(Long.parseLong(str.split("x")[0])), str);
                }
            }
        }
        return this.imageWidthToSizeMap;
    }


}
