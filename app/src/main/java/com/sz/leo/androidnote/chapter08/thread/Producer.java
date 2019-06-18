package com.sz.leo.androidnote.chapter08.thread;

import java.util.ArrayList;
import java.util.List;

/**
 * @author：leo
 * @date：2019/6/18
 * @email：lei.lu@e-at.com
 */
public class Producer extends Thread {
    List<Message> mMessageList = new ArrayList<>();

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(3000);
                Message message = new Message();
                synchronized (mMessageList) {
                    mMessageList.add(message);
                    mMessageList.notify();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Message waitMessage() {
        synchronized (mMessageList) {
            if (mMessageList.isEmpty()) {
                try {
                    mMessageList.wait();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            return mMessageList.remove(0);
        }
    }
}
