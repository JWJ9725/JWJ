package com.jwj.common;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import com.jwj.R;

import java.io.File;

/**
 * Created by JWJ on 2016-02-16.
 */
public class StoragePath {

    private final static String TAG = "StoragePath";

    private static String externalPath;


    private static Context context;


    public StoragePath(Context context) {
        this.context = context;
    }


    public static String getPath() {
        // SD Card checking
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(context, R.string.no_sdcard_message, Toast.LENGTH_LONG).show();
        } else {
            externalPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
            Log.d(TAG, "ExternalPath : " + externalPath);
        }
        return externalPath;
    }

}
