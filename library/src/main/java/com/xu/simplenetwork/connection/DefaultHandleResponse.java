package com.xu.simplenetwork.connection;

import com.xu.simplenetwork.response.Response;

/**
 * Created by Xu on 2016/11/27.
 */

public class DefaultHandleResponse implements IHandleResponse {

    @Override
    public Response handleResponse(Response original) {
        return original;
    }
}
