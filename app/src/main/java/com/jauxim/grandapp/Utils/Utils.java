package com.jauxim.grandapp.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Base64;
import android.view.View;

import com.jauxim.grandapp.R;
import com.jauxim.grandapp.ui.Activity.ActivityEdit.ActivityEditActivity;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

import javax.net.ssl.HttpsURLConnection;


public class Utils {

    public static String getPriceFormated(Long eurocents) {
        return String.format("%.2l€", eurocents);
    }

    public static String getTimeFormated(String time) {
        //TODO: TBI
        return "";
    }

    public static float getAbsoluteDistance(Double lat1, Double lon1, double lat2, double lon2) {
        Location loc1 = new Location("");
        loc1.setLatitude(lat1);
        loc1.setLongitude(lon1);

        Location loc2 = new Location("");
        loc2.setLatitude(lat2);
        loc2.setLongitude(lon2);

        return loc1.distanceTo(loc2);
    }

    /*
    public static String getWalkingDistance(final double lat1, final double lon1, final double lat2, final double lon2) {
        final String[] parsedDistance = {null};
        final String[] response = {null};

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //Log.i("Output1", String.valueOf(lat1));
                    //Log.i("Output2", String.valueOf(lon1));
                    //Log.i("Output3", String.valueOf(lat2));
                    //Log.i("Output4", String.valueOf(lon2));
                    URL url = new URL("https://maps.googleapis.com/maps/api/directions/json?origin=" + lat1 + "," + lon1 + "&destination=" + lat2 + "," + lon2 + "&units=metric&mode=walking&key=AIzaSyCNhz3l4i6fPHxw0rvNpT_YUVZtsyGXs6o");
                    //Log.i("Output4.5", url.toString());
                    final HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    InputStream in = new BufferedInputStream(conn.getInputStream());
                    response[0] = org.apache.commons.io.IOUtils.toString(in, "UTF-8");

                    JSONObject jsonObject = new JSONObject(response[0]);
                    //Log.i("Output5", jsonObject.toString());
                    JSONArray array = jsonObject.getJSONArray("routes");
                    //Log.i("Output6", array.toString());
                    JSONObject routes = array.getJSONObject(0);
                    //Log.i("Output7", routes.toString());
                    JSONArray legs = routes.getJSONArray("legs");
                    JSONObject steps = legs.getJSONObject(0);
                    JSONObject distance = steps.getJSONObject("distance");
                    parsedDistance[0] = distance.getString("text");

                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return parsedDistance[0];
    }
    */

    public static String getCountDownTime(Long millis, Long milisEnd) {
        Long diff = millis - System.currentTimeMillis();

        if (diff < 0) {
            if (System.currentTimeMillis()> milisEnd) return "GONE!";
            else return "IN CURSE";
        }

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

    public static CropImage.ActivityBuilder createCropCamera(boolean ovalMask){
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

        return builder;
    }

    public static Bitmap getBitmapFromUri(Context context, Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                context.getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();

        return compressBitmap(image);
    }

    public static void changeMapFragment(AppCompatActivity activity, int layoutId, Fragment fragment, String tag) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(layoutId, fragment, tag);
        fragmentTransaction.commit();
    }

    public static String getBase64(Bitmap bitmap)
    {
        try{
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();

            return Base64.encodeToString(byteArray, Base64.NO_WRAP);
        }
        catch(Exception e)
        {
            return null;
        }
    }

    public static Bitmap getBitmap(String base64){
        byte[] decodedString = Base64.decode(base64, Base64.NO_WRAP);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    public static String getFullDataFormat(Long timestamp) {
        Calendar timeCalendar = Calendar.getInstance();
        timeCalendar.setTimeInMillis(timestamp);
        Calendar now = Calendar.getInstance();
        return DateFormat.format("MMMM dd yyyy, h:mm aa", timeCalendar).toString();
    }

    private static Bitmap compressBitmap(Bitmap bitmap){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, out);
        return BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
    }

    public static View getToolbarNavigationIcon(Toolbar toolbar){
        //check if contentDescription previously was set
        boolean hadContentDescription = TextUtils.isEmpty(toolbar.getNavigationContentDescription());
        String contentDescription = !hadContentDescription ? toolbar.getNavigationContentDescription().toString() : "navigationIcon";
        toolbar.setNavigationContentDescription(contentDescription);
        ArrayList<View> potentialViews = new ArrayList<View>();
        //find the view based on it's content description, set programatically or with android:contentDescription
        toolbar.findViewsWithText(potentialViews,contentDescription, View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
        //Nav icon is always instantiated at this point because calling setNavigationContentDescription ensures its existence
        View navIcon = null;
        if(potentialViews.size() > 0){
            navIcon = potentialViews.get(0); //navigation icon is ImageButton
        }
        //Clear content description if not previously present
        if(hadContentDescription)
            toolbar.setNavigationContentDescription(null);
        return navIcon;
    }

    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
}
