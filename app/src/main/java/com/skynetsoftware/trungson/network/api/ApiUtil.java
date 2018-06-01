package com.skynetsoftware.trungson.network.api;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.FileUtils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ApiUtil {

    public static TrungSonAPI createNotTokenApi() {
        return ServiceGenerator.createService(TrungSonAPI.class);
    }

    public static TrungSonAPI createTokenApi() {
        return ServiceGenerator.createServiceToken(TrungSonAPI.class);
    }
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";
    @NonNull
    public static MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        File file = FileUtils.getFileByPath(fileUri.getPath());
//        RequestBody requestFile =
//                RequestBody.create(
//                        MediaType.parse(context.getContentResolver().getType(fileUri)),
//                        file
//                );

        RequestBody reqFile = RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData(partName, file.getName(), reqFile);
        return body;
    }

    @NonNull
    public static RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                MultipartBody.FORM, descriptionString);
    }
}
