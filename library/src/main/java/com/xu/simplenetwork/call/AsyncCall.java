package com.xu.simplenetwork.call;

import com.xu.simplenetwork.XNetworkClient;
import com.xu.simplenetwork.request.Request;

/**
 * Created by Xu on 2016/10/15.
 */

public class AsyncCall extends NetworkCall {

    private AsyncCallBack callBack;

    public AsyncCall(XNetworkClient client, Request request, AsyncCallBack asyncCallBack) {
        this(request, asyncCallBack);
        this.client = client;
    }

    public AsyncCall(Request request, AsyncCallBack asyncCallBack) {
        this.client = getDefaultClient();
        this.request = request;
        this.callBack = asyncCallBack;
        this.time = System.currentTimeMillis();
    }

    public void execute() {
        client.executor().enqueue(this);
    }

    public AsyncCallBack getCallBack() {
        return callBack;
    }

    public int getAsyncCallpriority() {
        return this.request.priority();
    }

    public abstract class AsyncCallRunnable implements Runnable {

        private final AsyncCall call;

        public AsyncCallRunnable(AsyncCall asyncCall) {
            this.call = asyncCall;
        }

        public AsyncCall getAsyncCall() {
            return call;
        }

        @Override
        public void run() {
            execute();
        }

        public abstract void execute();
    }
}
