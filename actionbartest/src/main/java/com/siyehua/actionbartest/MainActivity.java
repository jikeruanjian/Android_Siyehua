package com.siyehua.actionbartest;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

/**
 * Name:MainActivity <br>
 * Description:描述 <br>
 * Created by Ary@siyehua on 2015/2/9
 */

public class MainActivity extends Activity {
    private SiWebView siWebView;
    private ProgressBar progressBar;
    private int animationDurationTime = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        setStaticViewAndData();
        setOnClick();
    }

    /**
     * 查找对应的控件
     */
    private void findView() {
        siWebView = (SiWebView) findViewById(R.id.activityMain_SiWebView_content);
        progressBar = (ProgressBar) findViewById(R.id.activityMain_ProgressBar_wait);
    }

    /**
     * 初始化View和数据
     */
    private void setStaticViewAndData() {
        siWebView.setVisibility(View.GONE);
        animationDurationTime = getResources().getInteger(android.R.integer.config_longAnimTime);
        siWebView.loadUrl("http://www.baidu.com");
    }

    /**
     * 设置监听
     */
    private void setOnClick() {
        siWebView.setOnPagerFinished(new SiWebView.ProgressListener() {
            @Override
            public void onPagerFinished() {
                showContent();
            }
        });
    }


    private void showContent() {
        siWebView.animate().alpha(1f).setDuration(animationDurationTime).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                siWebView.setVisibility(View.VISIBLE);
            }
        });
        progressBar.animate().alpha(0f).setDuration(animationDurationTime).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                progressBar.setVisibility(View.GONE);
            }
        });

    }
}
