package com.xu.simplenetwork.call;

import com.xu.simplenetwork.SimpleNetworkClient;
import com.xu.simplenetwork.request.Request;
import com.xu.simplenetwork.response.Response;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by Xu on 2016/10/15.
 */

public class SynCall extends NetworkCall {

    private Response response;
    private int code;

    public SynCall(SimpleNetworkClient client, Request request) {
        setClient(client);
        setRequest(request);
    }

    @Override
    protected void connect() {
        try {
            SimpleNetworkClient client = getClient();
            Request request = getRequest();
            HttpURLConnection connection = (HttpURLConnection) new URL(request.url()).openConnection();
            connection.setReadTimeout(client.readTimeout());
            connection.setConnectTimeout(client.connectTimeout());
            connection.setRequestMethod(request.method());
            connection.setDoInput(true);
            code = connection.getResponseCode();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                if (request.method().equals("get")) {
                    InputStream is = connection.getInputStream();
                    BufferedInputStream bis = new BufferedInputStream(is);

                } else if (request.method().equals("post")) {

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
                .build();
        return this.response;
    }


}
