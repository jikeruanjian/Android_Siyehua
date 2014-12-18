package com.siyehua.srcollshowandhide.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * 自定义ScrollView<br>
 * 使用说明:setOnScrollAction()方法可触发特效.<br><br>
 * 特效设置:<br>
 * fingerMoveDistance//默认手指移动的距离可触发动画.也就是说手指只是轻微的点击一下屏幕,并不会触发特效,需要移动一定的距离才会.默认为30.可修改<br>
 * animationDurationTime //动画播放的时间.默认为750.单位为毫秒,可修改.<br>
 * distanceFromTop //距离顶部多少的时候就开始显示头部和尾部.默认为100,可修改<br>
 * distanceFromBelow //距离底部多少的时候就开始显示尾部.默认为0,可修改<br><br>
 * Created by Siyehua on 2014/12/17.
 */
public class MyScrollView extends ScrollView {
    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private View childView, titleView, belowView;//子View,头部View,底部View
    private int startY;//开始点
    private int distanceY;//移动的距离
    private boolean ifUpFlag = false;//是否得到手指按下的手势,默认为没有
    public int fingerMoveDistance = 30;//默认手指移动的距离可触发动画.也就是说手指只是轻微的点击一下屏幕,并不会触发特效,需要移动一定的距离才会.默认为30.可修改
    public int animatonDurationTime = 750;//动画播放的时间.默认为750.单位为毫秒,可修改.
    public int distanceFromTop = 100;//距离顶部多少的时候就开始显示头部和尾部.默认为100,可修改
    public int distanceFromBelow = 0;//距离底部多少的时候就开始显示尾部.默认为0,可修改

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN://手指按下
                startY = (int) ev.getRawY();
                ifUpFlag = true;
                break;
            case MotionEvent.ACTION_MOVE://手指移动
                distanceY = (int) (ev.getRawY() - startY);
                if (ifUpFlag) {//多重条件判断.
                    if (distanceY > fingerMoveDistance) {//如果往下滑动
                        if (getScrollY() <= distanceFromTop) {//如果滚动到了距离顶部一定距离的时候(这里设置为100),则显示头部和底部
                            showTitleView(titleView);
                            showBelowView(belowView);
                        } else {//如果距离顶部还很远(大于100),则需要隐藏头部和尾部
                            hideTitleView(titleView);
                            hideBelowView(belowView);
                        }
                    } else if (distanceY < -fingerMoveDistance) {//如果往上滑动
                        if (getScrollY() + getHeight() + distanceFromBelow >= childView.getHeight()) {//大于等于子View的高度,说明滚动到了底部.
                            showBelowView(belowView);//滚动到底部的时候要把底部View显示出来
                        } else {//如果距离底部还有一定的距离,说明还有达到底部,这个时候,需要隐藏头部和尾部
                            hideTitleView(titleView);
                            hideBelowView(belowView);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP://手指离开
                startY = 0;//手指离开之后,需要重置变量为默认值
                distanceY = 0;
                ifUpFlag = false;
                break;
        }
        return super.onTouchEvent(ev);
    }


    /**
     * 设置滑动特效
     *
     * @param childView ScrollView的子布局
     * @param titleView 头部View
     * @param belowView 底部View
     */
    public void setOnScrollAction(View childView, View titleView, View belowView) {
        this.childView = childView;
        this.titleView = titleView;
        this.belowView = belowView;
    }

    /**
     * 显示头部View
     *
     * @param titleView 要操作的头部View
     */
    private void showTitleView(View titleView) {
        if (titleView.getVisibility() != View.VISIBLE) {
            Animation animation = new TranslateAnimation(0, 0, -titleView.getHeight(), 0);
            animation.setDuration(animatonDurationTime);
            titleView.startAnimation(animation);
            titleView.setVisibility(View.VISIBLE);
            titleView.bringToFront();
        }
    }

    /**
     * 隐藏头部View
     *
     * @param titleView 要操作的头部View
     */
    private void hideTitleView(View titleView) {
        if (titleView.getVisibility() != View.INVISIBLE) {
            Animation animation = new TranslateAnimation(0, 0, 0, -titleView.getHeight());
            animation.setDuration(animatonDurationTime);
            titleView.startAnimation(animation);
            titleView.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 显示底部View
     *
     * @param belowView 要操作的底部View
     */
    private void showBelowView(View belowView) {
        if (belowView.getVisibility() != View.VISIBLE) {
            Animation animation = new TranslateAnimation(0, 0, belowView.getHeight(), 0);
            animation.setDuration(animatonDurationTime);
            belowView.startAnimation(animation);
            belowView.setVisibility(View.VISIBLE);
            belowView.bringToFront();
        }
    }

    /**
     * 隐藏底部View
     *
     * @param belowView 要操作的底部View
     */
    private void hideBelowView(View belowView) {
        if (belowView.getVisibility() != View.INVISIBLE) {
            Animation animation = new TranslateAnimation(0, 0, 0, belowView.getHeight());
            animation.setDuration(animatonDurationTime);
            belowView.startAnimation(animation);
            belowView.setVisibility(View.INVISIBLE);
        }
    }


}
