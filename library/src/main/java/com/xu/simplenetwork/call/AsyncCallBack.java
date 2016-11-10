package com.xu.simplenetwork.call;

import com.xu.simplenetwork.response.Response;

/**
 * Created by Xu on 2016/10/30.
 */

public interface AsyncCallBack {

    void onSuccess(Response response);

    void onFailure(Exception e);
}
