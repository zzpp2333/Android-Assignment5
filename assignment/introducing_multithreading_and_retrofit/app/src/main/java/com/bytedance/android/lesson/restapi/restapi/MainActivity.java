package com.bytedance.android.lesson.restapi.restapi;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bytedance.android.lesson.restapi.restapi.bean.Joke;
import com.bytedance.android.lesson.restapi.restapi.bean.OSList;
import com.bytedance.android.lesson.restapi.restapi.utils.NetworkUtils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static String RAW =
            "{\"os\":[{\"name\":\"Pie\",\"code\":28}," +
                    "{\"name\":\"Oreo\",\"code\":27}]}";
    public TextView mTv;
    private View mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTv = findViewById(R.id.tv);
        mBtn = findViewById(R.id.btn);
//        mTv.setText(parseFirstNameWithJSON()); // json test
//        mTv.setText(parseFirstNameWithGson()); // json test
        initListeners();
    }

    private void initListeners() {
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                requestData(v);
            }
        });
    }

    private static String parseFirstNameWithGson() {
        OSList list = new Gson()
                .fromJson(RAW, OSList.class);
        return list.getOs()[0].getName();
    }

    private static String parseFirstNameWithJSON() {
        String result = null;
        try {
            JSONObject root = new JSONObject(RAW);
            JSONArray os = root.optJSONArray("os");
            result = os.optJSONObject(0).
                    optString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void requestData(View view) {
        //new DownloadTask().execute("https://api.icndb.com/jokes/random");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    final String s = NetworkUtils.getResponseWithHttpURLConnection("https://api.icndb.com/jokes/random");
                    mTv.post(new Runnable() {
                        @Override
                        public void run() {
                            mTv.setText(s);
                        }
                    });

                    final Joke j = NetworkUtils.getResponseWithRetrofit();
                    mTv.post(new Runnable() {
                        @Override
                        public void run() {
                            mTv.setText(j.getValue().getJoke());
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        /*
        // HttpURLConnection
        String s = NetworkUtils.getResponseWithHttpURLConnection("https://api.icndb.com/jokes/random");
        mTv.setText(s);

        // Retrofit
        Joke j = NetworkUtils.getResponseWithRetrofit();
        mTv.setText(j.getValue().getJoke());
         */

    }
    private class DownloadTask extends AsyncTask<String, Void, String> {
        /** The system calls this to perform work in a worker thread and
         * delivers it the parameters given to AsyncTask.execute() */
        @Override
        protected String doInBackground(String... urls) {
            String s = NetworkUtils.getResponseWithHttpURLConnection(urls[0]);
            return s;
        }

        /** The system calls this to perform work in the UI thread and delivers
         * the result from doInBackground() */
        @Override
        protected void onPostExecute(String result) {
            mTv.setText(result);
            Joke j = NetworkUtils.getResponseWithRetrofit();
            mTv.setText(j.getValue().getJoke());
        }
    }
}