package com.wei.wanandroid.javasources.rxjava;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author: WEI
 * @date: 2018/7/26
 */
public class RxJavaTest {
    public static void main(String[] args) {
        RxJavaTest rxJavaTest = new RxJavaTest();
//        rxJavaTest.testMap();
//        rxJavaTest.testFlatMap();
//        rxJavaTest.testConcatMap();
//        rxJavaTest.testBuffer();
//        rxJavaTest.testConcat();
//        rxJavaTest.testMerge();
//        rxJavaTest.testZip();
//        rxJavaTest.testReduce();
//        rxJavaTest.testCollect();
//        rxJavaTest.testCount();
//        rxJavaTest.testInterval();
        rxJavaTest.test();
    }

    private void test() {
        Observable.just(1)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return integer + ",";
                    }
                })
                .compose(cpu_main())
                .subscribe(new CusObserver<>());
    }

    private <T> ObservableTransformer<T, T> cpu_main() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    private void testInterval() {
        Observable.interval(2, 1, TimeUnit.SECONDS)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        System.out.println("第 " + aLong + " 次輪詢");
                        /// TODO 请求网络
                    }
                }).subscribe(new CusObserver<>());
    }

    private void testCount() {
        Observable.just(1, 3, 4, 6)
                .count()
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        System.out.println("發送的事件數量 = " + aLong);
                    }
                });
    }

    private void testCollect() {
        Observable.just(1, 2, 3, 4, 5, 6)
                .collect(new Callable<ArrayList<Integer>>() {
                    @Override
                    public ArrayList<Integer> call() throws Exception {
                        return new ArrayList<>();
                    }
                }, new BiConsumer<ArrayList<Integer>, Integer>() {
                    @Override
                    public void accept(ArrayList<Integer> list, Integer integer) throws Exception {
                        list.add(integer);
                    }
                }).subscribe(new Consumer<ArrayList<Integer>>() {
            @Override
            public void accept(ArrayList<Integer> integers) throws Exception {
                System.out.println("本次發送的數據是：" + integers);
            }
        });
    }

    /**
     * 作用：
     * 把被观察者需要发送的事件聚合成1个事件 & 发送
     * 聚合的逻辑根据需求撰写，但本质都是前2个数据聚合，然后与后1个数据继续进行聚合，依次类推
     */
    private void testReduce() {
        Observable.just(1, 2, 5, 9, 11, 20, 4)
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        System.out.println(integer + " 乘以 " + integer2);
                        return integer * integer2;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println("最终计算结果：" + integer);
            }
        });
    }

    /**
     * Zip（）
     * 作用： 合并 多个被观察者（Observable）发送的事件，生成一个新的事件序列（即组合过后的事件序列），并最终发送
     * 特别注意：
     * 事件组合方式 = 严格按照原先事件序列 进行对位合并
     * 最终合并的事件数量 = 多个被观察者（Observable）中数量最少的数量
     */
    private void testZip() {
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                System.out.println("被观察者1发送了事件1");
                e.onNext(1);
                System.out.println("被观察者1发送了事件2");
                e.onNext(2);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io());

        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                System.out.println("被观察者2发送了事件A");
                e.onNext("A");
                System.out.println("被观察者2发送了事件B");
                e.onNext("B");
                System.out.println("被观察者2发送了事件C");
                e.onNext("C");
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io());

        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer + "," + s;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void testMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return integer + ", ";
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                // 1,
                //2,
                //3,
                System.out.println(s);
            }
        });
    }

    /**
     * 输出结果无序
     */
    private void testFlatMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> strings = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    strings.add("我是事件 " + integer + "拆分后的子事件" + i);
                }
                return Observable.fromIterable(strings);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        });
    }

    /**
     * 与flatMap类似，但是输出结果有序
     */
    private void testConcatMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }).concatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> strings = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    strings.add("我是事件 " + integer + "拆分后的子事件" + i);
                }
                return Observable.fromIterable(strings);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        });
    }

    private void testBuffer() {
        Observable.just(1, 2, 3, 4, 5)
                // 设置缓存区大小 & 步长
                // 缓存区大小 = 每次从被观察者中获取的事件数量
                // 步长 = 每次获取新事件的数量
                .buffer(3, 3)
                .subscribe(new Observer<List<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Integer> integers) {
                        System.out.println("缓存区里的事件数量 = " + integers.size());
                        for (Integer integer : integers) {
                            System.out.println("事件 = " + integer);
                        }
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
     * concat（） / concatArray（）
     * 作用：组合多个被观察者一起发送数据，合并后 按发送顺序串行执行
     * 二者区别：组合被观察者的数量，即concat（）组合被观察者数量≤4个，而concatArray（）则可＞4个
     */
    private void testConcat() {
        Observable.concat(Observable.just(1, 2, 3),
                Observable.just(4, 5, 6, 7, 8),
                Observable.just(9, 10),
                Observable.just(11, 12, 13))
                .subscribe(new CusObserver<>());

        Observable.concatArray(Observable.just(1, 2, 3),
                Observable.just(4, 5, 6, 7, 8),
                Observable.just(9, 10),
                Observable.just(11, 12, 13),
                Observable.just(14, 15, 16))
                .subscribe(new CusObserver<>());
    }

    /**
     * merge（） / mergeArray（）
     * 作用：组合多个被观察者一起发送数据，合并后 按时间线并行执行
     * 二者区别：组合被观察者的数量，即merge（）组合被观察者数量≤4个，而mergeArray（）则可＞4个
     * 区别上述concat（）操作符：同样是组合多个被观察者一起发送数据，但concat（）操作符合并后是按发送顺序串行执行
     */
    private void testMerge() {
        Observable.merge(
                // 从0开始发送、共发送3个数据、第1次事件延迟发送时间 = 1s、间隔时间 = 1s
                Observable.intervalRange(0, 3, 1, 1, TimeUnit.SECONDS),
                // 从2开始发送、共发送3个数据、第1次事件延迟发送时间 = 1s、间隔时间 = 1s
                Observable.intervalRange(2, 3, 1, 1, TimeUnit.SECONDS))
                .subscribe(new CusObserver<>());
    }

    class CusObserver<T> implements Observer<T> {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(T t) {
            System.out.println("接收事件 ：" + t);
        }

        @Override
        public void onError(Throwable e) {
            System.out.println("接收事件出错 ：" + e.getMessage());
        }

        @Override
        public void onComplete() {
            System.out.println("事件接收完成");
        }
    }
}
