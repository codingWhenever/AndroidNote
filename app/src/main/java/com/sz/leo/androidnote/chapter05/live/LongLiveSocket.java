package com.sz.leo.androidnote.chapter05.live;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author：leo
 * @date：2019/5/29
 * @email：lei.lu@e-at.com
 */
public class LongLiveSocket {
    private static final String TAG = "LongLiveSocket";
    private static final int HEART_BEAT_INTERVAL_MILLIS = 20;

    public interface ErrorCallback {
        boolean onError();
    }

    public interface DataCallback {
        void onData(byte[] data, int offset, int len);
    }

    public interface WritingCallback {
        void onSuccess();

        void onFail(byte[] data, int offset, int len);
    }

    public LongLiveSocket(String host, int port,
                          DataCallback dataCallback, ErrorCallback callback) {

    }

    public void write(byte[] data, WritingCallback writingCallback) {

    }

    public void write(final byte[] data, final int offset, final int len, final WritingCallback callback) {
        mWriteHandler.post(new Runnable() {
            @Override
            public void run() {
                Socket socket = getSocket();
                if (socket == null) {
                    throw new IllegalStateException("");
                }
                try {
                    OutputStream outputStream = socket.getOutputStream();
                    DataOutputStream out = new DataOutputStream(outputStream);
                    out.write(len);
                    out.write(data, offset, len);
                    callback.onSuccess();
                } catch (IOException e) {
                    Log.e(TAG, "write : " + e.getMessage());
                    closeSocket();
                    callback.onFail(data, offset, len);
                    //todo 重连

                }
            }
        });
    }

    private Socket getSocket() {
        return null;
    }

    public void closeSocket() {

    }

    public void close() {

    }

    private final Handler mWriteHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    private final Handler mUIHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    private final Runnable mHeartBeatTask = new Runnable() {
        private byte[] mHeartBeat = new byte[0];

        @Override
        public void run() {
            write(mHeartBeat, new WritingCallback() {
                @Override
                public void onSuccess() {
                    mWriteHandler.postDelayed(mHeartBeatTask, HEART_BEAT_INTERVAL_MILLIS);
                    mUIHandler.postDelayed(mHeartBeatTimeoutTask, HEART_BEAT_INTERVAL_MILLIS);
                }

                @Override
                public void onFail(byte[] data, int offset, int len) {

                }
            });
        }
    };

    private final Runnable mHeartBeatTimeoutTask = new Runnable() {
        @Override
        public void run() {
            Log.e(TAG, "mHeartBeatTimeoutTask#run: heart beat time out");
        }
    };


}
