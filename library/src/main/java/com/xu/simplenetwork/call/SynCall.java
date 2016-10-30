package com.xu.simplenetwork.call;

import com.xu.simplenetwork.SimpleNetworkClient;
import com.xu.simplenetwork.request.Request;

/**
 * Created by Xu on 2016/10/15.
 */

public class SynCall extends NetworkCall {

    public SynCall(SimpleNetworkClient client, Request request) {
        this.client = client;
        this.request = request;
    }

    public SynCall(Request request) {
        this.client = getDefaultClient();
        this.request = request;
    }

    public void execute() {
        client.executor().execute(this);
    }

}
