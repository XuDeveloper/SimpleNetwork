package com.xu.simplenetwork.call;

import com.xu.simplenetwork.SimpleNetworkClient;
import com.xu.simplenetwork.request.Request;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Xu on 2016/10/15.
 */

public class SynCall extends NetworkCall {

    public SynCall(SimpleNetworkClient client, Request request) {
        setClient(client);
        setRequest(request);
    }

    @Override
    protected void connect() throws IOException {
        SimpleNetworkClient client = getClient();
        Request request = getRequest();
        HttpURLConnection connection = (HttpURLConnection) new URL(request.url()).openConnection();
        connection.setReadTimeout(client.readTimeout());
        connection.setConnectTimeout(client.connectTimeout());
        connection.setRequestMethod(request.method());
        connection.setDoInput(true);
        if (request.method().equals("get")) {

        } else if (request.method().equals("post")) {

        }
    }

    @Override
    protected void getResponse() {

    }
}
