package com.jwj;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gcm.GCMBaseIntentService;
import com.jwj.gcm.GCMInfo;
import com.jwj.gcm.HttpPostAsyncTask;

import java.net.URLDecoder;
import java.util.Random;

/**
 * Created by JWJ on 2016-02-23.
 */

public class GCMIntentService extends GCMBaseIntentService {

    /**
     * GCM Server로부터 발급받은 Project ID를 통해 SuperClass인
     * GCMBaseIntentService를 생성해야한다.
     */
    public GCMIntentService() {
        super(GCMInfo.PROJECT_ID);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onError(Context arg0, String arg1) {
        // TODO Auto-generated method stub
        /**
         * GCM 오류 발생 시 처리해야 할 코드를 작성한다. 오류작성을 해야지
         * ErrorCode에 대해선 GCM 홈페이지와 GCMConstants 내 static variable 참조한다.
         */

    }

    @Override
    protected void onMessage(Context context, Intent intent) {
        // TODO Auto-generated method stub
        /**
         * GCMServer가 전송하는 메시지가 정상 처리 된 경우 구현하는 메소드이다.
         * Notification, 앱 실행 등등 개발자가 하고 싶은 로직을 해당 메소드에서 구현한다.
         * 전달받은 메시지는 Intent.getExtras().getString(key)를 통해 가져올 수 있다.
         */

        String from = intent.getStringExtra("from");
        String command = intent.getStringExtra("command");  // 서버에서 보낸 command 라는 키의 value 값
        String type = intent.getStringExtra("type");        // 서버에서 보낸 type 라는 키의 value 값
        String rawData = intent.getStringExtra("data");     // 서버에서 보낸 data 라는 키의 value 값
        String data = "";
        try {
            data = URLDecoder.decode(rawData, "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Log.d(TAG, "from : " + from + ", command : " + command + ", type : " + type + ", data : " + data);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context).setSmallIcon(R.drawable.btn_voice_play).setContentTitle("GCM Notification").setStyle(new NotificationCompat.BigTextStyle().bigText(data)).setContentText(data);
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(randomInt(), mBuilder.build());
    }

    @Override
    protected void onRegistered(Context context, String regId) {
        //GCMRegistrar.getRegistrationId(context)가 실행되어 registrationId를 발급받은 경우 해당 메소드가 콜백된다.
        HttpPostAsyncTask task = new HttpPostAsyncTask();
        task.execute(regId);
        Toast.makeText(context, "onRegistered", Toast.LENGTH_LONG);
    }

    @Override
    protected void onUnregistered(Context context, String regId) {
        //GCMRegistrar.unregister(context) 호출로 해당 디바이스의 registrationId를 해지요청한 경우 해당 메소드가 콜백된다.
    }

    public int randomInt() {
        Random random = new Random(System.currentTimeMillis());
        int messageCollapseKey = Math.abs(random.nextInt());
        return messageCollapseKey;
    }


}


