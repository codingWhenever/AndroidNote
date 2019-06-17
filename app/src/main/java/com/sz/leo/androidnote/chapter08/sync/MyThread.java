package com.sz.leo.androidnote.chapter08.sync;

/**
 * @author：leo
 * @date：2019/6/17
 * @email：lei.lu@e-at.com
 */
public class MyThread extends Thread {
    private Sync mSync;

    public MyThread() {
    }

    public MyThread(Sync sync) {
        this.mSync = sync;
    }

    @Override
    public void run() {
        super.run();
//        mSync.test();
        Sync sync = new Sync();
        sync.test();
    }
}
