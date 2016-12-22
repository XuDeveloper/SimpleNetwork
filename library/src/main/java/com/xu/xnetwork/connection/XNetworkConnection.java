package com.xu.xnetwork.connection;

import com.xu.xnetwork.call.XNetworkCall;
import com.xu.xnetwork.response.Response;

/**
 * Created by Xu on 2016/10/30.
 */

public interface XNetworkConnection {

    Response performCall(XNetworkCall call);
}
