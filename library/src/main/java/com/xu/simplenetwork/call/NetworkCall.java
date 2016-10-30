package com.xu.simplenetwork.call;

import com.xu.simplenetwork.SimpleNetworkClient;
import com.xu.simplenetwork.request.Request;

/**
 * Created by Administrator on 2016/10/30.
 */

// TODO: 2016/10/30  1.SynCall加入优先级；2.Executor搞定；3.实现INetWorkConnection（Client和UrlConnection）
public class NetworkCall {

    protected SimpleNetworkClient client;
    protected Request request;

    public SimpleNetworkClient getDefaultClient() {
        return new SimpleNetworkClient();
    }

}
