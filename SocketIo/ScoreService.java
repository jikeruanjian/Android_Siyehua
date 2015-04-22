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
 * @����:ScoreService
 * @����:XuanKe'Huang
 * @ʱ��:2015-4-1 ����7:38:09
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

    private Socket mSocket;// socketͨ�Ŷ���
    {
        try {
            mSocket = IO.socket(Constants.SCORE_SOCKET_URL);// ���ӵ�������
        } catch (URISyntaxException e) {
        }
    }

    /**
     * Socket����������
     */
    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {// ��ػص�
            LogUitls.print("siyehua-error", args[0].toString());
        }
    };
    /**
     * Socket�ֶμ���:login
     */
    private Emitter.Listener onLogin = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {// ��ػص�
            LogUitls.print("siyehua-login", args[0].toString());
        }
    };
    /**
     * Socket�ֶμ���:init
     */
    private Emitter.Listener onInit = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {// ��ػص�
            LogUitls.print("siyehua-init", args[0].toString());
        }
    };
    /**
     * Socket�ֶμ���:change
     */
    private Emitter.Listener onChange = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {// ��ػص�
            LogUitls.print("siyehua-change", args[0].toString());
        }
    };

    private DataChangerListener listener;// �ӿڻص�����

    /**
     * @������: setOnDataChangeListener
     * @��������: ����socket����(�ṩ���ⲿ����)
     * @param listener
     *            ������
     * @return void
     */
    public void setOnDataChangeListener(DataChangerListener listener) {
        this.listener = listener;
    }

    /**
     * @������: startSocket
     * @��������: ����Socketͨ��
     * @return void
     */
    public void startSocket() {
        // handler.sendEmptyMessageDelayed(10086, 10 * 1000);//������
        /************************* ����SocketҪ�������ֶ� *************************/
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);// �������
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);// ��ʱ����
        mSocket.on("login", onLogin);// ��½�ֶμ���
        mSocket.on("init", onInit);// ��ʼ���ֶμ���
        mSocket.on("change", onChange);// �ı��ֶμ���
        mSocket.connect();
    }

    /**
     * @������: stopSocket
     * @��������: ֹͣSocket
     * @return void
     */
    public void stopSocket() {
        /************************* ȡ��Ҫ�������ֶ� *************************/
        mSocket.disconnect();// �ȶϿ�����
        mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);// ȡ����������
        mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);// ȡ��������ʱ
        mSocket.off("login", onLogin);// ��½�ֶμ���
        mSocket.off("init", onInit);// ��ʼ���ֶμ���
        mSocket.off("change", onChange);// �ı��ֶμ���
        scoreService = null;// �ÿշ���
    }

    /**
     * @����:DataChangerListener
     * @��������:���ݱ仯�ӿڻص�
     * @����:XuanKe'Huang
     * @ʱ��:2015-4-3 ����11:43:50
     * @Copyright @2015
     */
    public interface DataChangerListener {
        /**
         * @������: login
         * @��������: ��½
         * @return void
         */
        void login();

        /**
         * @������: init
         * @��������: ��ʼ��
         * @param list
         *            ��Ҫ����ʼ�������ݼ���
         */
        void init(ArrayList<MatchSocket> list);

        /**
         * @������: change
         * @��������:�����ݸı�
         * @param list
         *            ��Ҫ����ʼ�������ݼ���(д�ɼ�����Ϊ��ͳһʹ��)
         * @return void
         */
        void change(ArrayList<MatchSocket> list);

    }
}
