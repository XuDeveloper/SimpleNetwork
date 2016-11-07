package com.xu.simplenetwork.executor;

import com.xu.simplenetwork.call.AsyncCall;
import com.xu.simplenetwork.call.AsyncCallComparator;
import com.xu.simplenetwork.call.SynCall;
import com.xu.simplenetwork.response.Response;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Xu on 2016/10/30.
 */

public class XNetworkExecutor {

    private INetworkConnection connection;
    private ExecutorService synExecutorService;
    private ExecutorService asyncExecutorService;
    private Response response;

//    private BlockingQueue<SynCall> synCalls;

    private BlockingQueue<Runnable> asyncCalls;

    public XNetworkExecutor(INetworkConnection connection) {
        this.connection = connection;
        asyncCalls = new PriorityBlockingQueue<>(60, new AsyncCallComparator<>());
        synExecutorService = Executors.newSingleThreadExecutor();
        // custom ExecutorService
        asyncExecutorService = new ThreadPoolExecutor(3, 3, 0L, TimeUnit.SECONDS, asyncCalls);
    }

    public Response execute(final SynCall synCall) {
        synExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                response = connection.performCall(synCall);
            }
        });
        return response;
    }

    public void enqueue(AsyncCall asyncCall) {
         asyncCalls.add(asyncCall.new AsyncCallRunnable(asyncCall) {
             @Override
             public void execute() {

             }
         });
    }

    public void run() {
//        for (Runnable runnable: asyncCalls) {
//            asyncExecutorService.execute(runnable);
//        }
    }
}
