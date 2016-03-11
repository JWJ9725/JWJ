package com.jwj.gcm;


import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class GCMIntentServiceCoustom extends IntentService {

	private static final String TAG = "GCMIntentServiceCoustom";
	
	/**
	 * 생성자
	 */
    public GCMIntentServiceCoustom() {
        super(TAG);
        Log.d(TAG, "GCMIntentServiceCoustom() called.");
    }

    /*
     * 전달받은 인텐트 처리
     */
	@Override
	protected void onHandleIntent(Intent intent) {
		String action = intent.getAction();
		
		Log.d(TAG, "action : " + action);
		
	}

}