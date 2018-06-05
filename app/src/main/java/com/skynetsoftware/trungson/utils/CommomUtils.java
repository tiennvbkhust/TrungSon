package com.skynetsoftware.trungson.utils;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;


import com.skynetsoftware.trungson.ui.main.MainActivity;
import com.skynetsoftware.trungson.R;

import java.io.File;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

/**
 * Created by thaopt on 1/4/18.
 */

public class CommomUtils {
    //ref https://stackoverflow.com/questions/8658205/format-currency-without-currency-symbol
    public static String formatCurrency(Object number) {
        if (number == null) return "";
        if (number instanceof String) number = Double.parseDouble((String) number);
        DecimalFormat format = (DecimalFormat) NumberFormat.getCurrencyInstance();
        DecimalFormatSymbols symbols = format.getDecimalFormatSymbols();
        symbols.setCurrencySymbol("");
        format.setDecimalFormatSymbols(symbols);
        return String.valueOf(number);
    }

    public static void openMapToLocation(Activity activity,String location){
        Uri gmmIntentUri = Uri.parse("google.navigation:q="+location+"&avoid=tf");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        activity.startActivity(mapIntent);
    }
    private static final CharSequence NOTIFICATION_CHANNEL_NAME = "4547";

    public static Notification createNotificationWithMsg(Context context, String title, String msg, String data) {
        String NOTIFICATION_CHANNEL_ID = "4567";
//Notification Channel
        CharSequence channelName = NOTIFICATION_CHANNEL_NAME;
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel notificationChannel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.createNotificationChannel(notificationChannel);

        }



        Notification.Builder builder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new Notification.Builder(context, NOTIFICATION_CHANNEL_ID);
        } else {
            builder = new Notification.Builder(context);
        }
        builder.setSmallIcon(R.mipmap.ic_launcher);
        //   builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
        builder.setContentTitle(title);
        builder.setContentText(msg);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(NOTIFICATION_CHANNEL_ID);
        }
        builder.setAutoCancel(true).setOngoing(false);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
//        stackBuilder.addParentStack(SliderLayoutActivity.class);
        Intent intentMain = new Intent(context, MainActivity.class);
        intentMain.putExtra(AppConstant.NOTIFICATION_SOCKET, data);
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        builder.setVibrate(new long[]{0, 200, 200, 600, 600});
        stackBuilder.addNextIntent(intentMain);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        return builder.build();
    }

    private static String getRealPathFromURI(Context context, Uri uri) {
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }
    private static String getMatissePhotoPath() {
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return file.getAbsolutePath();
    }
    public static File scanFileMetisse(Context context,Uri uri) {
        String path = uri.getPath();
        String[] split = path.split("/");
        String filePath = getMatissePhotoPath() + File.separatorChar + split[split.length - 1];
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        File f = new File(filePath);
        if(!f.exists()) {
            f = new File(getRealPathFromURI(context,uri));
        }
        return f;
    }
}
