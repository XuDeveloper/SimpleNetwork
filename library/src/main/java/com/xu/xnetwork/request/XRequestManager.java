package com.xu.xnetwork.request;

import com.xu.xnetwork.call.XNetworkCall;
import com.xu.xnetwork.call.XNetworkCallBack;
import com.xu.xnetwork.config.XNetworkConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xu on 2016/12/24.
 */

public class XRequestManager {

    private List<XNetworkCall> list;

    public XRequestManager() {
        list = new ArrayList<>();
    }

    private void addRequest(XNetworkCall call) {
        list.add(call);
    }

    public XNetworkCall createNetworkCall(Request request, XNetworkConfig config, XNetworkCallBack callBack) {
        XNetworkCall call = new XNetworkCall(request, config, callBack);
        addRequest(call);
        return call;
    }

}
