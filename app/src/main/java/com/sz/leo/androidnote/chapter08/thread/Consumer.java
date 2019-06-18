package com.sz.leo.androidnote.chapter08.thread;

/**
 * @author：leo
 * @date：2019/6/18
 * @email：lei.lu@e-at.com
 */
public class Consumer extends Thread {

    private Producer mProducer;

    public Consumer(String name, Producer producer) {
        super(name);
        this.mProducer = producer;
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            Message message = mProducer.waitMessage();
            System.out.println("Consumer " + getName() + " get a msg");
        }
    }

    public static void main(String[] args) {
        Producer producer = new Producer();
        producer.start();
        new Consumer("Consumer1", producer).start();
        new Consumer("Consumer2", producer).start();
        new Consumer("Consumer3", producer).start();
    }
}
