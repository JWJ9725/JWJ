package com.jwj.common;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by JWJ on 2016-02-16.
 */
public class CheckPermission implements ActivityCompat.OnRequestPermissionsResultCallback {

    ArrayList<String> permissionList;

    String[] permissions;

    private Activity activity;

    public CheckPermission(Activity activity, String[] permissions) {
        this.activity = activity;
        this.permissions = permissions;
        permissionList = new ArrayList<String>();
    }

    public void checkDangerousPermissions() {
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(activity, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(activity, "권한 있음", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(activity, "권한 없음", Toast.LENGTH_LONG).show();
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[0])) {
                Toast.makeText(activity, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(activity, permissions, 1);

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(activity, permissions[i] + " 권한이 승인됨.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(activity, permissions[i] + " 권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}
