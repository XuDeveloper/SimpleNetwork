package com.xu.simplenetwork.call;

import com.xu.simplenetwork.XNetworkClient;
import com.xu.simplenetwork.request.Request;
import com.xu.simplenetwork.response.Response;

/**
 * Created by Xu on 2016/10/30.
 */

public interface XNetworkCall {

    Request getRequest();

    XNetworkClient getClient();

    Response execute();

    void enqueue(AsyncCallBack callBack);
}
