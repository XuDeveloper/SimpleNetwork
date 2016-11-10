package com.xu.simplenetwork.call;

import com.xu.simplenetwork.XNetworkClient;
import com.xu.simplenetwork.request.Request;
import com.xu.simplenetwork.response.Response;

import java.io.IOException;

/**
 * Created by Xu on 2016/11/10.
 */

public class RealNetworkCall implements XNetworkCall {

    private Request request;
    private XNetworkClient client;

    public RealNetworkCall(XNetworkClient client, Request request) {
        this.client = client;
        this.request = request;
    }

    @Override
    public Request getRequest() {
        return request;
    }

    @Override
    public XNetworkClient getClient() {
        return client;
    }

    @Override
    public Response execute() {
//        client.queue().add(this);
        return client.executor().execute(this);
    }

    @Override
    public void enqueue(AsyncCallBack callBack) {
        Response response = client.executor().execute(this);
        if (response != null) {
            callBack.onSuccess(response);
        } else {
            callBack.onFailure(new IOException());
        }
    }

}
