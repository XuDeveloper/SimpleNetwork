package com.xu.xnetwork.call;

import com.xu.xnetwork.response.Response;

/**
 * Created by Xu on 2016/10/30.
 */

public interface XNetworkCallBack {

    void onSuccess(Response response);

    void onFailure(Exception e);
}
