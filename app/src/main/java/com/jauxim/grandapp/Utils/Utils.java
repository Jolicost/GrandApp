package com.jauxim.grandapp.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import com.jauxim.grandapp.R;
import com.jauxim.grandapp.ui.Activity.ActivityEdit.ActivityEditActivity;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class Utils {

    public static String getPriceFormated(Long eurocents) {
        return String.format("%.2l€", eurocents);
    }

    public static String getTimeFormated(String time) {
        //TODO: TBI
        return "";
    }

    public static float getAbsoluteDistance(Double lat1, Double lon1, float lat2, float lon2) {
        Location loc1 = new Location("");
        loc1.setLatitude(lat1);
        loc1.setLongitude(lon1);

        Location loc2 = new Location("");
        loc2.setLatitude(lat2);
        loc2.setLongitude(lon2);

        return loc1.distanceTo(loc2);
    }

    public static String getCountDownTime(Long millis) {
        Long diff = System.currentTimeMillis() - millis;

        if (diff < 0)
            return "GONE!";

        Long min = diff / (60 * 1000);
        if (min < 60)
            return min + " min";

        Long hours = min / 60;
        if (hours < 24)
            return hours + " hours";

        Long days = hours / 24;
        if (days < 7)
            return days + " days";

        return days / 7 + " weeks";
    }

    public static void startCropImage(Activity context, boolean ovalMask){
        CropImage.ActivityBuilder builder = com.theartofdev.edmodo.cropper.CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setActivityTitle("Crop Image")
                //.setCropMenuCropButtonIcon(R.drawable.check)
                .setScaleType(CropImageView.ScaleType.CENTER_INSIDE)
                .setMultiTouchEnabled(false);

        if (ovalMask) {
            builder.setAspectRatio(1, 1);
            builder.setCropShape(CropImageView.CropShape.OVAL);
            builder.setFixAspectRatio(true);
        } else {
            builder.setCropShape(CropImageView.CropShape.RECTANGLE);
        }

        builder.start(context);
    }

    public static Bitmap getBitmapFromUri(Context context, Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                context.getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }
}
