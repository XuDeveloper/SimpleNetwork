package com.xu.simplenetwork.executor;

import com.xu.simplenetwork.call.NetworkCall;
import com.xu.simplenetwork.response.Response;

/**
 * Created by Xu on 2016/10/30.
 */

public interface INetworkConnection {

    Response performCall(NetworkCall call);
}
