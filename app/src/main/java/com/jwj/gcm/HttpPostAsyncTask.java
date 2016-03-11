package com.jwj.gcm;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by JWJ on 2016-02-23.
 */
public class HttpPostAsyncTask extends AsyncTask<String, String, JSONObject> {


    @Override
    public JSONObject doInBackground(String... params) {


        URL url = null;
        HttpURLConnection conn = null;
        OutputStream os = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;

        JSONObject responseJSON = null;

        try {

            //url = new URL("http://192.168.25.6:8080/GCM_SERVER/RegServlet?regId=" + params[0]);
            url = new URL("http://192.168.25.6:8080/GCM_SERVER/RegServlet");

            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10 * 1000);
            conn.setReadTimeout(15 * 1000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            /*url일 경우 처리
            conn.connect();
            int responseCode = conn.getResponseCode();
            */

            JSONObject job = new JSONObject();
            job.put("regId", params[0]);
            //job.put("name", params[1]);
            //job.put("address", params[2]);

            os = conn.getOutputStream();
            os.write(job.toString().getBytes());
            os.flush();

            String response;
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {

                is = conn.getInputStream();
                baos = new ByteArrayOutputStream();
                byte[] byteBuffer = new byte[1024];
                byte[] byteData = null;
                int nLength = 0;
                while ((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                    baos.write(byteBuffer, 0, nLength);
                }
                byteData = baos.toByteArray();

                response = new String(byteData);

                if(response  == null)
                    return null;
                responseJSON = new JSONObject(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return responseJSON;
    }


}

