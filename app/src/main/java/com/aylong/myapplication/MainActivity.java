package com.aylong.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import menu.NetSpeed;
import menu.NetSpeedTimer;
import menu.OnSettingMenuListener;
import menu.StreamDeskMenuView;

/**
 * @author admin
 */
public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";
    private NetSpeedTimer mNetSpeedTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = findViewById(R.id.tv_net_speed);
        Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                textView.setText((String) msg.obj);
                return false;
            }
        });
        //创建NetSpeedTimer实例
        mNetSpeedTimer = new NetSpeedTimer(this, new NetSpeed(), handler).setDelayTime(1000).setPeriodTime(2000);
        //在想要开始执行的地方调用该段代码
        mNetSpeedTimer.startSpeedTimer();


        WebView webView = new WebView(this);
        webView.loadUrl("http://www.baidu.com");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });
        FrameLayout frameLayout = findViewById(R.id.fl);
        frameLayout.addView(webView);


        StreamDeskMenuView menuView = findViewById(R.id.menu);
        menuView.setOnSettingMenuListener(new OnSettingMenuListener() {
            @Override
            public void onClickedGaccountAssistant() {

            }

            @Override
            public void onClickedReCharge() {

            }

            @Override
            public void onCLickedExitUse() {
                Log.d(TAG, "onCLickedExitUse: exit");
            }

            @Override
            public void onClickedStretchVideo(boolean stretchVideo) {

            }

            @Override
            public void onClickedRealTimeMonitor(boolean enable) {

            }

            @Override
            public void onClickedMouseMode(boolean mouseMode) {

            }

            @Override
            public void onClickedGestureInstruction() {

            }

            @Override
            public void onClickedTaskManager() {

            }

            @Override
            public void onClickedProcessSwitch() {

            }

            @Override
            public void onClickedDiscountPeriodTip(String tip) {

            }

            @Override
            public void onClickedToAnliang(String tip) {

            }

            @Override
            public void onClickedLeaveDesktop() {

            }

            @Override
            public void onClickedGameKeyboard() {
                Log.d(TAG, "onClickedGameKeyboard: keyBord");
            }

            @Override
            public void onClickedPictureQuality(int bitrate) {
                Log.d(TAG, "onClickedPictureQuality: " + bitrate);
            }

            @Override
            public void onClickedMouseSpeed() {

            }

            @Override
            public void onClickedAudioSwitch(boolean isOpen, View view) {

            }

            @Override
            public void onClickedWordkeyboardSwitch(boolean isOpen) {

            }

            @Override
            public void onClickedViberate(boolean isOpen) {

            }

            @Override
            public void onClickedSensor(int gyroMode) {

            }

            @Override
            public void onClickedSensorSensitivity(int sensitivity) {

            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
