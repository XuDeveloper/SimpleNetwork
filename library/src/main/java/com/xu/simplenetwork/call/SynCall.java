package com.xu.simplenetwork.call;

import com.xu.simplenetwork.HttpContext;
import com.xu.simplenetwork.SimpleNetworkClient;
import com.xu.simplenetwork.Utils;
import com.xu.simplenetwork.request.Request;
import com.xu.simplenetwork.response.Response;

import java.net.HttpURLConnection;

/**
 * Created by Xu on 2016/10/15.
 */

public class SynCall extends NetworkCall {

    private Response response;
    private int code;
    private String message;

    public SynCall(SimpleNetworkClient client, Request request) {
        setClient(client);
        setRequest(request);
    }

    @Override
    protected void connect() {
        try {
            SimpleNetworkClient client = getClient();
            Request request = getRequest();
            HttpContext context = new HttpContext(client, request);
            if (context.getResponseCode() == HttpURLConnection.HTTP_OK) {
                if (context.isGet()) {
                    message = Utils.getStringByInputStream(context.getInputStream());
                } else if (context.isPost()) {
                    context.setDefaultRequestProperty();
                    message = Utils.getStringByInputStream(context.getInputStream());
                }
            }
        } catch (Exception e) {

        } finally {

        }

    }

    @Override
    protected Response getResponse() {
        response = new Response.Builder()
                .code(code)
                .message(message)
                .build();
        return this.response;
    }


}
