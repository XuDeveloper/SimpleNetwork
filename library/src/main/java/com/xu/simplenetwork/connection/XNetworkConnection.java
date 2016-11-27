package com.xu.simplenetwork.connection;

import com.xu.simplenetwork.call.XNetworkCall;
import com.xu.simplenetwork.response.Response;

/**
 * Created by Xu on 2016/10/30.
 */

public interface XNetworkConnection {

    Response performCall(XNetworkCall call);
}
