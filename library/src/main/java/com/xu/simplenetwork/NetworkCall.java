package com.xu.simplenetwork;

import com.xu.simplenetwork.request.Request;

/**
 * Created by Xu on 2016/10/11.
 */

public class NetworkCall {

    final SimpleNetworkClient client;
    final Request request;

    public NetworkCall(SimpleNetworkClient client, Request request) {
        this.client = client;
        this.request = request;
    }
}
