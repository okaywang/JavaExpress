package exception;

/**
 * Created by Administrator on 2017/9/22.
 */
/*
 * 第一步：定义符合线程异常处理器规范的“异常处理器”
 * 实现Thread.UncaughtExceptionHandler规范
 */
class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    /*
     * Thread.UncaughtExceptionHandler.uncaughtException()会在线程因未捕获的异常而临近死亡时被调用
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("currentThread" + Thread.currentThread() + "caught caught caught    " + e);
    }
}