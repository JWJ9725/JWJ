package com.jwj.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jwj.R;


/**
 * Created by JWJ on 2016-02-18.
 */
public class CommMainActivity extends AppCompatActivity {

    public static TextView mLogsUI;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        mLogsUI = (TextView) findViewById(R.id.logs);
    }


    public static void addLog(String type, String data) {
        addLog(type, data, null);
    }

    public static void addLog(String type, String data, Exception e) {
        try {
            mLogsUI.append(type + ": " + data + (e == null ? "" : "\nException : " + e.getMessage()) + "\n");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void clearLog() {
        mLogsUI.setText("");
    }

}
