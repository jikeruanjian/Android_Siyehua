package com.siyehua.srcollshowandhide;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.siyehua.srcollshowandhide.myview.MyScrollView;


public class MainActivity extends Activity {
    private MyScrollView contentScrollView;//内容
    private ImageView titleImageView, belowImageView;//标题,底部

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contentScrollView = (MyScrollView) findViewById(R.id.activity_main_scrollview_content);
        titleImageView = (ImageView) findViewById(R.id.activity_main_imageview_title);
        belowImageView = (ImageView) findViewById(R.id.activity_main_imageview_below);
        contentScrollView.setOnScrollAction(findViewById(R.id.activity_main_textview_content), titleImageView, belowImageView);

    }

}
