package com.jwj.multimemo;

import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.jwj.R;
import com.jwj.common.media.CameraSurfaceView;
import com.jwj.common.view.TitleBitmapButton;

import java.io.File;
import java.io.IOException;

/**
 * 동영상 녹화  액티비티
 *
 * @author Mike
 * @date 2011-07-01
 */
public class VideoRecordingActivity extends Activity {

	public static final String TAG = "VideoRecordingActivity";

	MediaRecorder mRecorder = null;
	TitleBitmapButton mStartStopBtn;
	boolean isStart = false;
	CameraSurfaceView mCameraSurfaceView;
	FrameLayout mSurfaceViewLayout;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//final Window win = getWindow();
        //win.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ////requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.video_recording_activity);

		mCameraSurfaceView = new CameraSurfaceView(getApplicationContext());
		mSurfaceViewLayout = (FrameLayout) findViewById(R.id.recording_surfaceViewLayout);
		mSurfaceViewLayout.addView(mCameraSurfaceView);

		setRecordingBtn();


	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
    		if(isStart) {
    			Toast.makeText(getApplicationContext(), R.string.video_recording_message, Toast.LENGTH_LONG).show();
    		} else {
    			finish();
    		}

			return true;
		}

        return false;
    }



	public void setRecordingBtn() {
		mStartStopBtn = (TitleBitmapButton)findViewById(R.id.recording_startstopBtn);
		mStartStopBtn.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.btn_voice_record, 0, 0);
		mStartStopBtn.setText(R.string.video_recording_start_title);

		mStartStopBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (isStart == false) {
					mCameraSurfaceView.stopPreview();
					prepareVideoRecording();

					mRecorder.start();
					isStart = true;
					mStartStopBtn.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.btn_voice_stop, 0, 0);
					mStartStopBtn.setText(R.string.video_recording_stop_title);
				} else {
					mRecorder.stop();
					mRecorder.release();
					mRecorder = null;
					isStart = false;
					mCameraSurfaceView.startPreview();

					setResult(RESULT_OK);
					mStartStopBtn.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.btn_voice_record, 0, 0);
					mStartStopBtn.setText(R.string.video_recording_start_title);
				}
			}
		});
	}

	public void onDestroy() {
		super.onDestroy();
		if (mRecorder != null) {
			mRecorder.release();
			mRecorder = null;
		}
	}

    /**
	 * check recording folder
	 */
	public void checkVideoFolder() {
    	File videoFolder = new File(BasicInfo.FOLDER_VIDEO);
		if(!videoFolder.isDirectory()){
			Log.d(TAG, "creating video folder : " + videoFolder);

			videoFolder.mkdirs();
		}
    }

	public void prepareVideoRecording() {
		checkVideoFolder();

		String videoName = BasicInfo.FOLDER_VIDEO + "recorded";

		if (mRecorder == null) {
			mRecorder = new MediaRecorder();
		} else {
			mRecorder.reset();
		}

		mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
		mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);
		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
		
		mRecorder.setVideoFrameRate(30);
		mRecorder.setOutputFile(videoName);
		
		mRecorder.setPreviewDisplay(mCameraSurfaceView.getSurface());

		try {
			mRecorder.prepare();
		} catch (IllegalStateException e) {
			Toast.makeText(VideoRecordingActivity.this, "IllegalStateException", Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			Toast.makeText(VideoRecordingActivity.this, "IOException", Toast.LENGTH_LONG).show();
		}

	}
}