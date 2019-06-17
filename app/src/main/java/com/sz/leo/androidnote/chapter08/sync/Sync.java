package com.sz.leo.androidnote.chapter08.sync;

import android.widget.CompoundButton;

/**
 * @author：leo
 * @date：2019/6/17
 * @email：lei.lu@e-at.com
 */
public class Sync {
    public void test() {
        synchronized (Sync.class) {
            System.out.println("test begin...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("test end...");
        }
    }

    public void testInner() {
        synchronized (Sync.class) {
            System.out.println("test begin...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("test end...");
        }
    }


    public static void main(String[] args) {
//        for (int i = 0; i < 3; i++) {
//            new MyThread().start();
//        }

        Sync sync = new Sync();
        for (int i = 0; i < 3; i++) {
            new MyThread().start();
        }

    }
}
