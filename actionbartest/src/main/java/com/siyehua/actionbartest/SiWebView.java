package com.siyehua.actionbartest;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * @类名:SiWebView
 * @功能描述:自定义WebView,支持弹窗,js交互
 * @作者:XuanKe'Huang
 * @时间:2014-10-23 下午3:31:05
 * @Copyright 2014
 */
@SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
public class SiWebView extends WebView {
    public SiWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public SiWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SiWebView(Context context) {
        super(context);
        init(context);
    }

    private Context mContext;// 上下文对象

    /**
     * 方法名: init
     * <p/>
     * 功能描述:初始化
     *
     * @param context 上下文对象
     * @return void
     * <p/>
     * </br>throws
     */
    private void init(Context context) {
        this.mContext = context;
        getSettings().setJavaScriptEnabled(true);// 可以使用javaScript
        getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        addJavascriptInterface(new JsObject(), "demo");
        setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("tel:")) {// 打电话
                    Intent intent = new Intent(Intent.ACTION_CALL,
                            Uri.parse(url));
                    mContext.startActivity(intent);
                } else if (url.startsWith("sms:")) {// 发短息
                    Uri uri = Uri.parse("smsto:" + url.split(":")[1]);
                    Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
                    sendIntent.putExtra("sms_body", "");
                    mContext.startActivity(sendIntent);
                } else {// 正常处理url
                    view.loadUrl(url);
                }
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (progressListener != null) {
                    progressListener.onPagerFinished();
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });
        setWebChromeClient(new MyWebChromeClient());// 设置浏览器可弹窗
    }

    /**
     * 浏览器可弹窗
     *
     * @author Administrator
     */
    final class MyWebChromeClient extends WebChromeClient {
        @Override
        public boolean onJsConfirm(WebView view, String url, String message,
                                   final JsResult result) {
            new AlertDialog.Builder(mContext)
                    .setTitle("App Titler")
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    result.confirm();
                                }
                            })
                    .setNegativeButton(android.R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    result.cancel();
                                }
                            }).create().show();
            return true;
        }
    }

    /**
     * js调用class
     *
     * @author Administrator
     */
    class JsObject {// @JavascriptInterface是为了支持4.2及以上的js交互,不支持4.0以上的Android系统.

        @JavascriptInterface
        public void loginsuc(final String code, final boolean flag) {
        }

        @JavascriptInterface
        public String toString() {
            return "demo";
        }
    }


    private ProgressListener progressListener;

    public void setOnPagerFinished(ProgressListener progressListener) {
        this.progressListener = progressListener;
    }

    public interface ProgressListener {
        void onPagerFinished();
    }
}