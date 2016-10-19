package com.xu.simplenetwork.call;

import com.xu.simplenetwork.SimpleNetworkClient;
import com.xu.simplenetwork.request.Request;
import com.xu.simplenetwork.response.Response;

import java.io.IOException;

/**
 * Created by Xu on 2016/10/11.
 */

public abstract class NetworkCall {

//    public NetworkCall(SimpleNetworkClient client, Request request) {
//        this.client = client;
//        this.request = request;
//    }

    private SimpleNetworkClient client;
    private Request request;

    public SimpleNetworkClient getClient() {
        return client;
    }

    public void setClient(SimpleNetworkClient client) {
        this.client = client;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public final Response execute() {
        try {
            connect();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return getResponse();
    }

    protected abstract void connect() throws IOException;

    protected abstract Response getResponse();


}
