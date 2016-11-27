package com.xu.simplenetwork.executor;

import com.xu.simplenetwork.XNetworkClient;
import com.xu.simplenetwork.call.XNetworkCall;
import com.xu.simplenetwork.connection.XNetworkConnection;
import com.xu.simplenetwork.response.Response;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Xu on 2016/10/30.
 */

public class XNetworkExecutor {

    private XNetworkConnection connection;
    private XNetworkClient client;
    private ExecutorService executorService;
    //    private ExecutorService asyncExecutorService;
    private Response response;

//    private BlockingQueue<SynCall> synCalls;

//    private BlockingQueue<Runnable> asyncCalls;

    public XNetworkExecutor(XNetworkClient client) {
        this.connection = client.connection();
        this.client = client;
//        asyncCalls = new PriorityBlockingQueue<>(60, new AsyncCallComparator<>());
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
        // custom ExecutorService
//        asyncExecutorService = new ThreadPoolExecutor(3, 3, 0L, TimeUnit.SECONDS, asyncCalls);
    }

    public Response execute(final XNetworkCall XNetworkCall) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                response = client.connection().performCall(XNetworkCall);
            }
        });
        return response;
    }

//    public void enqueue() throws InterruptedException {
//        AsyncCallRunnable runnable = (AsyncCallRunnable) client.queue().takeAsyncCall();
//        asyncExecutorService.submit(runnable);
//    }

}
