package com.xu.simplenetwork.executor;

import com.xu.simplenetwork.call.AsyncCall;
import com.xu.simplenetwork.call.SynCall;
import com.xu.simplenetwork.response.Response;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by Administrator on 2016/10/30.
 */

public class SimpleNetworkExecutor {

    private INetworkConnection connection;
    private ExecutorService executorService;

    private BlockingQueue<SynCall> synCalls;

    private BlockingQueue<AsyncCall> asyncCalls;

    public SimpleNetworkExecutor(INetworkConnection connection) {
        this.connection = connection;
        synCalls = new PriorityBlockingQueue<>();
        asyncCalls = new ArrayBlockingQueue<>(100, false);
    }

    public Response execute(SynCall synCall) {
        return connection.performCall(synCall);
    }

    public void enqueue() {

    }
}
