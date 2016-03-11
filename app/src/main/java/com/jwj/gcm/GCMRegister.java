package com.jwj.gcm;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;
import com.google.android.gms.gcm.GoogleCloudMessaging;


/**
 * Created by JWJ on 2016-02-23.
 */
public class GCMRegister {

    public static final String TAG = "GCMRegister";

    Context context;

    public GCMRegister(Context context) {
        this.context = context;
    }

    public GCMRegister() {
        super();
    }


    public void startGCM(Context context) {
        try {
            GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
            String regId = gcm.register(GCMInfo.PROJECT_ID);

            println(context, "푸시 서비스를 위해 단말을 등록했습니다.");
            println(context, "등록 ID : " + regId);

            HttpPostAsyncTask task = new HttpPostAsyncTask();
            task.execute(regId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    //gcm.jar 파일을 이용
    public void startGCM() {

        //GCM Service가 이용 가능한 Device인지 체크한다.
        try {
            GCMRegistrar.checkDevice(context);
            GCMRegistrar.checkManifest(context);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "This device can't use GCM");
            return;
        }

        //SharedPreference에 저장된 RegistrationID가 있는지 확인한다.
        String regId = GCMRegistrar.getRegistrationId(context);

        /**
         * Registration Id가 없는 경우(어플리케이션 최초 설치로 발급받은 적이 없거나,
         * 삭제 후 재설치 등 SharedPreference에 저장된 Registration Id가 없는 경우가 이에 해당한다.)
         */
        if (regId == null || "".equals(regId)) {
            /**
             * 3.RegstrationId가 없는 경우 GCM Server로 Regsitration ID를 발급 요청한다.
             * 발급 요청이 정상적으로 이루어진 경우 Registration ID는 SharedPreference에 저장되며,
             * GCMIntentService.class의 onRegistered를 콜백한다.
             */
            GCMRegistrar.register(context, GCMInfo.PROJECT_ID);

            regId = GCMRegistrar.getRegistrationId(context);
            println(context, "푸시 서비스를 위해 단말을 등록했습니다.");

        } else {
            println(context, "Exist Registration Id: " + regId);
        }
    }

    private void println(final Context context, final String msg) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).start();

    }

}
