package com.xu.xnetwork.executor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Xu on 2016/10/30.
 */

public final class XNetworkExecutor {

    // 默认核心池大小
    private static final int DEFAULT_CORE_SIZE = Runtime.getRuntime().availableProcessors() + 1;
    // 最大线程数
    private static final int DEFAULT_MAX_SIZE = DEFAULT_CORE_SIZE * 2 + 1;
    // 池中空余线程存活时间
    private static final long DEFAULT_KEEP_ALIVE_TIME = 10L;
    // 时间单位
    private static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;
    // 线程池阻塞队列(默认队列长度为50)
    private static final int BLOCKING_QUEUE_SIZE = 50;
    private static BlockingQueue<Runnable> defaultQueue = new ArrayBlockingQueue<>(BLOCKING_QUEUE_SIZE);

    private ThreadPoolExecutor executor;

    public XNetworkExecutor() {
        executor = new ThreadPoolExecutor(DEFAULT_CORE_SIZE, DEFAULT_MAX_SIZE,
                DEFAULT_KEEP_ALIVE_TIME, DEFAULT_TIME_UNIT, defaultQueue);
    }

    public void execute(Runnable runnable) {
        executor.execute(runnable);
    }

    public BlockingQueue<Runnable> getQueue() {
        return executor.getQueue();
    }

    /**
     * 清空阻塞队列
     */
    public void removeAllTask() {
        getQueue().clear();
    }

    /**
     * 从阻塞队列中删除指定任务
     *
     * @param runnable
     * @return
     */
    public boolean removeTaskFromQueue(Runnable runnable) {
        if (!getQueue().contains(runnable)) {
            return false;
        }

        getQueue().remove(runnable);
        return true;
    }

    /**
     * 关闭，并等待任务执行完成，不接受新任务
     */
    public void shutdown() {
        if (executor != null) {
            executor.shutdown();
        }
    }

    /**
     * 关闭，立即关闭，并挂起所有正在执行的线程，不接受新任务
     */
    public void shutdownRightnow() {
        if (executor != null) {
            executor.shutdownNow();
            try {
                // 设置超时极短，强制关闭所有任务
                executor.awaitTermination(1, TimeUnit.MICROSECONDS);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
