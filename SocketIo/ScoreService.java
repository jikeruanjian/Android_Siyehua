package com.wbai.qqsd.match.score.info;

import java.net.URISyntaxException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.wbai.qqsd.common.Constants;
import com.wbai.qqsd.model.match.MatchSocket;
import com.wbai.qqsd.util.log.LogUitls;
import com.wbai.qqsd.util.ohter.StringUtil;

/**
 * @类名:ScoreService
 * @作者:XuanKe'Huang
 * @时间:2015-4-1 下午7:38:09
 * @Copyright @2015
 */
public class ScoreSocket {
    private static ScoreSocket scoreService;

    private ScoreSocket() {
    };

    public synchronized static ScoreSocket getInstance() {
        if (scoreService == null)
            scoreService = new ScoreSocket();
        return scoreService;
    }

    private Socket mSocket;// socket通信对象
    {
        try {
            mSocket = IO.socket(Constants.SCORE_SOCKET_URL);// 连接到服务器
        } catch (URISyntaxException e) {
        }
    }

    /**
     * Socket连错错误监听
     */
    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {// 监控回调
            LogUitls.print("siyehua-error", args[0].toString());
        }
    };
    /**
     * Socket字段监听:login
     */
    private Emitter.Listener onLogin = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {// 监控回调
            LogUitls.print("siyehua-login", args[0].toString());
        }
    };
    /**
     * Socket字段监听:init
     */
    private Emitter.Listener onInit = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {// 监控回调
            LogUitls.print("siyehua-init", args[0].toString());
        }
    };
    /**
     * Socket字段监听:change
     */
    private Emitter.Listener onChange = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {// 监控回调
            LogUitls.print("siyehua-change", args[0].toString());
        }
    };

    private DataChangerListener listener;// 接口回调对象

    /**
     * @方法名: setOnDataChangeListener
     * @功能描述: 设置socket监听(提供给外部调用)
     * @param listener
     *            监听器
     * @return void
     */
    public void setOnDataChangeListener(DataChangerListener listener) {
        this.listener = listener;
    }

    /**
     * @方法名: startSocket
     * @功能描述: 开启Socket通信
     * @return void
     */
    public void startSocket() {
        // handler.sendEmptyMessageDelayed(10086, 10 * 1000);//测试用
        /************************* 设置Socket要监听的字段 *************************/
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);// 错误监听
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);// 超时监听
        mSocket.on("login", onLogin);// 登陆字段监听
        mSocket.on("init", onInit);// 初始化字段监听
        mSocket.on("change", onChange);// 改变字段监听
        mSocket.connect();
    }

    /**
     * @方法名: stopSocket
     * @功能描述: 停止Socket
     * @return void
     */
    public void stopSocket() {
        /************************* 取消要监听的字段 *************************/
        mSocket.disconnect();// 先断开连接
        mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);// 取消监听错误
        mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);// 取消监听超时
        mSocket.off("login", onLogin);// 登陆字段监听
        mSocket.off("init", onInit);// 初始化字段监听
        mSocket.off("change", onChange);// 改变字段监听
        scoreService = null;// 置空服务
    }

    /**
     * @类名:DataChangerListener
     * @功能描述:数据变化接口回调
     * @作者:XuanKe'Huang
     * @时间:2015-4-3 上午11:43:50
     * @Copyright @2015
     */
    public interface DataChangerListener {
        /**
         * @方法名: login
         * @功能描述: 登陆
         * @return void
         */
        void login();

        /**
         * @方法名: init
         * @功能描述: 初始化
         * @param list
         *            需要被初始化的数据集合
         */
        void init(ArrayList<MatchSocket> list);

        /**
         * @方法名: change
         * @功能描述:有数据改变
         * @param list
         *            需要被初始化的数据集合(写成集合是为了统一使用)
         * @return void
         */
        void change(ArrayList<MatchSocket> list);

    }
}
