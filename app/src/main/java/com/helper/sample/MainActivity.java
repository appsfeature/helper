package com.helper.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.helper.util.BaseUtil;
import com.helper.util.StyleUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StyleUtil.setStatusBarDarkMode(this, true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onOpenBrowser(View view) {
        BaseUtil.openLinkInAppBrowser(this,"","");
    }

    @Override
    protected void onResume() {
        super.onResume();
        BaseUtil.showToast(this, "Connected : "+BaseUtil.isConnected(this));
    }
}
