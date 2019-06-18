package com.sz.leo.androidnote.chapter08.countdownlatch;

import java.util.concurrent.TimeUnit;

/**
 * @author：leo
 * @date：2019/6/18
 * @email：lei.lu@e-at.com
 */
public class Participant implements Runnable {
    private String name;
    private VideoConference mVideoConference;

    public Participant(String name, VideoConference videoConference) {
        this.name = name;
        this.mVideoConference = videoConference;
    }

    @Override
    public void run() {
        long duration = (long) (Math.random() * 10);
        try {
            TimeUnit.SECONDS.sleep(duration);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        mVideoConference.arrive(name);
    }

    public static void main(String[] args) {
        VideoConference videoConference = new VideoConference(10);
        Thread thread = new Thread(videoConference);
        thread.start();

        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(new Participant("p-" + i, videoConference));
        }

        for (int i = 0; i < 10; i++) {
            threads[i].start();
        }
    }
}
