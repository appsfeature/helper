package com.helper.sample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.helper.Helper;
import com.helper.callback.ActivityLifecycleListener;
import com.helper.task.TaskRunner;
import com.helper.util.BaseUtil;
import com.helper.util.SocialUtil;
import com.helper.util.StyleUtil;

import java.util.concurrent.Callable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StyleUtil.setStatusBarDarkMode(this, true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Helper.getInstance().addActivityLifecycleListener(new ActivityLifecycleListener() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

            }
        });
    }

    public void onOpenBrowser(View view) {
//        BaseUtil.openLinkInAppBrowser(this,"","");

        SocialUtil.openLinkInAppBrowser(this , "title" , "");
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
        BaseUtil.showToast(this, "Connected : "+BaseUtil.isConnected(this));
    }
}
