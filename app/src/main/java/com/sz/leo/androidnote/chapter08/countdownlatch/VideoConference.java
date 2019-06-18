package com.sz.leo.androidnote.chapter08.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * @author：leo
 * @date：2019/6/18
 * @email：lei.lu@e-at.com
 */
public class VideoConference implements Runnable {
    private final CountDownLatch controller;

    public VideoConference(int number) {
        controller = new CountDownLatch(number);
    }

    public void arrive(String name) {
        System.out.printf("%s has arrived.\n", name);
        controller.countDown();
        System.out.printf("VideoConference: Waiting for %d participants.\n", controller.getCount());
    }

    @Override
    public void run() {
        System.out.printf("VideoConference: Initialization:%d participants.\n", controller.getCount());
        try {
            controller.await();
            System.out.printf("VideoConference: All the participants have come.\n");
        } catch (Exception ex) {

        }
    }
}
