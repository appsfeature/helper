package com.helper.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.base.app.AppProgress;
import com.helper.Helper;
import com.helper.callback.ActivityLifecycleListener;
import com.helper.task.AsyncThread;
import com.helper.util.BaseUtil;
import com.helper.util.StyleUtil;

public class MainActivity extends AppCompatActivity {

    private Button btnAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StyleUtil.setStatusBarDarkMode(this, true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAsyncTask = findViewById(R.id.btn_async_tack);
        btnAsyncTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DataLoader().execute(true);
            }
        });
        Helper.getInstance()
                .addActivityLifecycleListener(MainActivity.this.hashCode(), new ActivityLifecycleListener() {
                    @Override
                    public void onActivityPreCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

                    }

                    @Override
                    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

                    }
                });

    }

    public class DataLoader extends AsyncThread<Boolean, Integer, String> {

        @Override
        protected String doInBackground(Boolean... booleans) {
            try {
                for(int i = 1; i <= 10; i++) {
                    Thread.sleep(500);
                    publishProgress(i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Success";
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            btnAsyncTask.setText("Progress-" + values[0]);
        }

        @Override
        protected void onPostExecute(String integer) {
            super.onPostExecute(integer);
            btnAsyncTask.setText(integer);
        }
    }

    public void onOpenBrowser(View view) {
//        BaseUtil.openLinkInAppBrowser(this,"","");
        AppProgress.show(this, "Processing...");
//        SocialUtil.openLinkInBrowserChrome(this, "https://pixabay.com/");
/*
        TaskRunner.getInstance().executeAsync(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                for(int i= 0; i< 10; i++){
                    Log.d("TaskRunner","Task1:" +i);
                    Thread.sleep(100);
                }
                return null;
            }
        });
        TaskRunner.getInstance().executeAsync(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                for(int i= 0; i< 10; i++){
                    Log.d("TaskRunner","Task2:" +i);
                    Thread.sleep(100);
                }
                return null;
            }
        });
*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        BaseUtil.showToast(this, "Connected : " + BaseUtil.isConnected(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Helper.getInstance().removeActivityLifecycleListener(MainActivity.this.hashCode());
    }
}
