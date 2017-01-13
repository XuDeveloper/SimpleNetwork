package com.xu.xnetwork.call;

import com.xu.xnetwork.config.XNetworkConfig;
import com.xu.xnetwork.request.Request;
import com.xu.xnetwork.response.Response;

/**
 * Created by Xu on 2016/10/30.
 */

public class XNetworkCall implements Runnable {

    private Request request;
    private XNetworkConfig config;
    private XNetworkCallBack mCallBack;

    public XNetworkCall(Request request, XNetworkConfig config, XNetworkCallBack callBack) {
        this.request = request;
        this.config = config;
        this.mCallBack = callBack;
    }

    @Override
    public void run() {
        Response response = config.connection().performCall(this);
        if (mCallBack != null) {
            mCallBack.onSuccess(response);
        } else {
            mCallBack.onFailure(new Exception());
        }
    }

    public XNetworkConfig getConfig() {
        return config;
    }

    public Request getRequest() {
        return request;
    }

    public void disconnect() {
        config.connection().disconnect();
    }

}
