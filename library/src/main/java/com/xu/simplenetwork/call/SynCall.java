package com.xu.simplenetwork.call;

import com.xu.simplenetwork.XNetworkClient;
import com.xu.simplenetwork.request.Request;

/**
 * Created by Xu on 2016/10/15.
 */

public class SynCall extends NetworkCall {

    public SynCall(XNetworkClient client, Request request) {
        this(request);
        this.client = client;
    }

    public SynCall(Request request) {
        this.client = getDefaultClient();
        this.request = request;
        this.time = System.currentTimeMillis();
    }

    public void execute() {
        client.executor().execute(this);
    }

    public int getSynCallpriority() {
        return this.request.priority();
    }

}
