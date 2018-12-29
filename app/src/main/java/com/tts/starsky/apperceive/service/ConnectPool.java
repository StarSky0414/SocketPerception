package com.tts.starsky.apperceive.service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * 线程池配置
 */
public class ConnectPool {
    //核心线程数量
    private static final int POOLSIZE = 5;
    //最大线程数
    private static final int MAXIMUMPOOLSIZE = 7;
    //存活时间 单位是s
    private static final int KEEPALIVETIME = 1;
    //队列等待数量
    private static final int CAPACITY = 15;

    private static ThreadPoolExecutor executor;


    /**
     * 初始化线程池
     */
    public static void init() throws IOException {
        executor = new ThreadPoolExecutor(POOLSIZE, MAXIMUMPOOLSIZE, KEEPALIVETIME, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(CAPACITY),new ThreadPoolExecutor.DiscardPolicy());

    }


    public void execute(Runnable runnable) throws IOException, InterruptedException {

        executor.execute(runnable);
        Thread.sleep(100);
//        client.close();
        System.out.println("线程池中线程数目："+executor.getPoolSize()+"，队列中等待执行的任务数目："+
                executor.getQueue().size()+"，已执行完别的任务数目："+executor.getCompletedTaskCount());
    }

    /**
     *  网络编程不使用
     */
    public static void shutdown(){
        executor.shutdown();
    }

}
