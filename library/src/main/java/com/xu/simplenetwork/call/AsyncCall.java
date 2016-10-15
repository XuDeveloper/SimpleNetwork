package com.xu.simplenetwork.call;

import com.xu.simplenetwork.SimpleNetworkClient;
import com.xu.simplenetwork.request.Request;

/**
 * Created by Administrator on 2016/10/15.
 */

public class AsyncCall extends NetworkCall {

    public AsyncCall(SimpleNetworkClient client, Request request) {
        setClient(client);
        setRequest(request);
    }

    @Override
    protected void connect() {

    }

    @Override
    protected void getResponse() {

    }
}
