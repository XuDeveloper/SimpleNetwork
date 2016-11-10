package com.xu.simplenetwork.executor;

import com.xu.simplenetwork.call.XNetworkCall;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by Xu on 2016/11/7.
 */

public final class XNetworkQueue {

    private BlockingQueue<XNetworkCall> calls;

    private BlockingQueue<Runnable> asyncCalls;

    public XNetworkQueue() {
        calls = new PriorityBlockingQueue<>();
        asyncCalls = new PriorityBlockingQueue<>();
    }

    public void add(XNetworkCall XNetworkCall) {
        calls.add(XNetworkCall);
    }

    public void add(Runnable runnable) {
        asyncCalls.add(runnable);
    }

    public int synCallsLength() {
        return calls.size();
    }

    public XNetworkCall takeSynCall() throws InterruptedException {
        return calls.take();
    }

    public Runnable takeAsyncCall() throws InterruptedException {
        return asyncCalls.take();
    }

}
