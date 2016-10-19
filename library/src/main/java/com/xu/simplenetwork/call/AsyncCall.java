package com.xu.simplenetwork.call;

import com.xu.simplenetwork.SimpleNetworkClient;
import com.xu.simplenetwork.request.Request;
import com.xu.simplenetwork.response.Response;

/**
 * Created by Xu on 2016/10/15.
 */

public class AsyncCall extends NetworkCall {

    private Response response;

    public AsyncCall(SimpleNetworkClient client, Request request) {
        setClient(client);
        setRequest(request);
    }

    @Override
    protected void connect() {

    }

    @Override
    protected Response getResponse() {
        return this.response;
    }
}
