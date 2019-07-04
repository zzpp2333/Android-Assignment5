package com.bytedance.android.lesson.restapi.solution;

import com.bytedance.android.lesson.restapi.solution.bean.Cat;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bytedance.android.lesson.restapi.solution.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.RecyclerView.Adapter;
import static android.support.v7.widget.RecyclerView.ViewHolder;

public class Solution2C1Activity extends AppCompatActivity {

    private static final String TAG = Solution2C1Activity.class.getSimpleName();
    public Button mBtn;
    public RecyclerView mRv;
    private List<Cat> mCats = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution2_c1);
        mBtn = findViewById(R.id.btn);
        mRv = findViewById(R.id.rv);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setAdapter(new Adapter() {
            @NonNull @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                ImageView imageView = new ImageView(viewGroup.getContext());
                imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                imageView.setAdjustViewBounds(true);
                return new MyViewHolder(imageView);
            }

            @Override
            public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
                ImageView iv = (ImageView) viewHolder.itemView;

                // TODO-C1 (4) Uncomment these 2 lines, assign image url of Cat to this url variable
                String url = mCats.get(i).getUrl();
                Glide.with(iv.getContext()).load(url).into(iv);
            }

            @Override public int getItemCount() {
                return mCats.size();
            }
        });
    }

    public static class MyViewHolder extends ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public void requestData(View view) {
        mBtn.setText(R.string.requesting);
        mBtn.setEnabled(false);
        // TODO-C1 (3) Send request for 5 random cats here, don't forget to use {@link retrofit2.Call#enqueue}
        // Call restoreBtn() and loadPics(response.body()) if success
        // Call restoreBtn() if failure

        MyTask myTask = new MyTask();
        myTask.execute("https://api.thecatapi.com/v1/images/search?limit=5");
    }

    private void loadPics(List<Cat> cats) {
        Log.i("result","pics loaded");
        mCats = cats;
        mRv.getAdapter().notifyDataSetChanged();
    }

    private void restoreBtn() {
        mBtn.setText(R.string.request_data);
        mBtn.setEnabled(true);
    }

    private class MyTask extends AsyncTask<String,Void,List<Cat>>{

        protected List<Cat> doInBackground(String ... urls){
            String result = NetworkUtils.getResponseWithHttpURLConnection(urls[0]);
            Log.i("result",result);
            List<Cat> j = NetworkUtils.getResponseWithRetrofit();
            Log.i("result","result got");

            /*String result = null;
            HttpURLConnection catURLConnection = null;
            InputStream in = null;
            try{
                URL catURL = new URL(urls[0]);
                catURLConnection = (HttpURLConnection)catURL.openConnection();
                catURLConnection.setRequestMethod("GET");
                in = new BufferedInputStream(catURLConnection.getInputStream());
                Scanner scanner = new Scanner(in);
                scanner.useDelimiter("\\A");
                result = scanner.next();
                Log.i("result",result);
            }catch (IOException e)
            {
                e.printStackTrace();
            }finally{
                if(catURLConnection != null){
                    catURLConnection.disconnect();
                    if(in != null){
                        try{
                            in.close();
                        }catch (IOException e1){e1.printStackTrace();}
                    }
                }
            }*/
            return j;
        }
        @Override
        protected void onPostExecute(List<Cat> result){
            if(result != null){
                loadPics(result);
            }
            Log.i("result","run");
            restoreBtn();
        }
    }
}
