package com.xu.simplenetwork.connection;

import com.xu.simplenetwork.response.Response;

/**
 * Created by Xu on 2016/11/27.
 */

public interface IHandleResponse {

    Response handleResponse(Response original);
}
