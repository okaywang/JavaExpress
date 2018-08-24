package rxdemo;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.Semaphore;


/**
 * Created by wangguojun01 on 2018/8/24.
 */
public class App {
    public static void main(String[] args) throws InterruptedException {

        Observable<Object> observable = Observable.unsafeCreate(i -> {
            i.onNext(1);
            i.onNext(2);
            i.onNext(3);
            System.out.println("我是被观察者"+Thread.currentThread().getName());
            // i.onCompleted();//被观察者complete后，仍然发送数据。观察者不接受数据
            i.onNext(4);//观察者不接收该数据
            System.out.println("=======");
        });

        observable.subscribeOn(Schedulers.newThread()) //被观察者在新的线程（创建多个 只有第一个有效）
                .observeOn(Schedulers.newThread()) //观察者在新的线程观察 （可创建多个线程）
                .subscribe(getObserver()); //绑定观察与被观察者
        new Semaphore(-1).acquire();
    }

    public static Observer<Object> getObserver(){
        Observer<Object> o=  new Observer<Object>() {

            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {
                while (1==(int)o){
                    try {
                        Thread.sleep(1000);
                        break;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("我是观察者线程:"+Thread.currentThread().getName()+" 值是:"+ o);
            }
        };
        return o;

    }
}
