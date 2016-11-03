package com.xu.simplenetwork.call;

import com.xu.simplenetwork.SimpleNetworkClient;
import com.xu.simplenetwork.request.Request;

/**
 * Created by Xu on 2016/10/30.
 */


public class NetworkCall {

    protected SimpleNetworkClient client;
    protected Request request;
    protected long time;

    public SimpleNetworkClient getDefaultClient() {
        return new SimpleNetworkClient();
    }

    public SimpleNetworkClient getClient() {
        if (client != null) {
            return client;
        }
        return getDefaultClient();
    }

    public Request getRequest() {
        return request;
    }

    public long getCreateTime() {
        return time;
    }

//    @Override
//    public void run() {
//        execute();
//    }
//
//    protected abstract void execute();
}
