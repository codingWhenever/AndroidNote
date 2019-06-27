package com.sz.leo.androidnote.chapter10.rxJava;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;


/**
 * @author：leo
 * @date：2019/6/25
 * @email：lei.lu@e-at.com
 */
public class Rx {
    private static final String TAG = "Rxxx";


    private static void create() {
        //创建被观察者
        Observable observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "=========================currentThread name: " + Thread.currentThread().getName());
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        });
        //创建观察者
        Observer observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "======================onSubscribe");
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "======================onNext " + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "======================onError");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "======================onComplete");
            }
        };
        observable.subscribe(observer);
    }

    private static void just() {
        Observable.just(1, 2, 3)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private static void fromArray() {
        Integer array[] = {1, 2, 3, 4};
        Observable.fromArray(array)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private static void fromCallable() {
        Observable.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 1;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d(TAG, "================accept " + integer);
            }
        });
    }

    private static void fromFuture() {
        final FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Log.d(TAG, "Callable is Running");
                return "返回结果";
            }
        });
        Observable.fromFuture(futureTask)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        futureTask.run();
                    }
                }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, "================accept " + s);
            }
        });
    }

    private static void fromIterable() {
        List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);

        Observable.fromIterable(list)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "=================onSubscribe");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "=================onNext " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "=================onError ");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "=================onComplete ");
                    }
                });
    }

    private Integer i = 100;

    private void defer() {
        Observable<Integer> observable = Observable.defer(new Callable<ObservableSource<? extends Integer>>() {
            @Override
            public ObservableSource<? extends Integer> call() throws Exception {
                return Observable.just(i);
            }
        });
        i = 200;
        Observer observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "================onNext " + integer);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribe(observer);

        i = 300;
        observable.subscribe(observer);
    }

    private static void timer() {
        Observable.timer(3, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 每隔一段时间就会发送一个事件，这个事件是从0开始，不断增1的数字
     */
    private static void interval() {
        Observable.interval(4, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 可以指定发送事件的开始值和数量，其他与 interval() 的功能一样
     */
    private static void intervalRange() {
        Observable.intervalRange(2, 5, 2, 1, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 同时发送一定范围的事件序列
     */
    private static void range() {
        Observable.range(2, 5)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * empty()  直接发送 onComplete() 事件
     * never()  不发送任何事件
     * error()  发送 onError() 事件
     */
    private static void empty() {
        Observable.empty()
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    /**
     * map 可以将被观察者发送的数据类型转变成其他的类型
     */
    private static void map() {
        Observable.just(1, 2, 3)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return "I'm " + integer;
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 这个方法可以将事件序列中的元素进行整合加工，返回一个新的被观察者
     */
    private static void flatMap() {
        List<Person> personList = new ArrayList<>();
        Observable.fromIterable(personList)
                .flatMap(new Function<Person, ObservableSource<Plan>>() {
                    @Override
                    public ObservableSource<Plan> apply(Person person) throws Exception {
                        return Observable.fromIterable(person.getPlanList());
                    }
                })
                .flatMap(new Function<Plan, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Plan plan) throws Exception {
                        return Observable.fromIterable(plan.getActionList());
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, "==================action: " + s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 将发送的数据进行分组，每个分组都会返回一个被观察者
     */
    private static void groupBy() {
        Observable.just(5, 2, 3, 4, 1, 6, 8, 9, 7, 10)
                .groupBy(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Exception {
                        return integer % 3;
                    }

                }).subscribe(new Observer<GroupedObservable<Integer, Integer>>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "====================onSubscribe ");
            }

            @Override
            public void onNext(final GroupedObservable<Integer, Integer> integerIntegerGroupedObservable) {
                Log.d(TAG, "====================onNext ");
                integerIntegerGroupedObservable.subscribe(new Observer<Integer>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "====================GroupedObservable onNext  groupName: " +
                                integerIntegerGroupedObservable.getKey() + " value: " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 将数据以一定的逻辑聚合起来
     */
    private static void scan() {
        Observable.just(1, 2, 3, 4, 5)
                .scan(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        return integer + integer2;
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "====================accept " + integer);
                    }
                });
    }

    private static void window() {
        Observable.just(1, 2, 3, 4, 5)
                .window(2)
                .subscribe(new Observer<Observable<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Observable<Integer> integerObservable) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
