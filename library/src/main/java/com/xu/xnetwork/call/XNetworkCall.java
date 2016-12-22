package com.xu.xnetwork.call;

import com.xu.xnetwork.XNetworkClient;
import com.xu.xnetwork.request.Request;
import com.xu.xnetwork.response.Response;

/**
 * Created by Xu on 2016/10/30.
 */

public interface XNetworkCall {

    Request getRequest();

    XNetworkClient getClient();

    Response execute();

    void enqueue(AsyncCallBack callBack);
}
