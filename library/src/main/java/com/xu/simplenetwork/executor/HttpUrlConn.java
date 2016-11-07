package com.xu.simplenetwork.executor;

import com.xu.simplenetwork.XNetworkClient;
import com.xu.simplenetwork.call.NetworkCall;
import com.xu.simplenetwork.request.Request;
import com.xu.simplenetwork.response.Response;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Xu on 2016/11/3.
 */

public class HttpUrlConn implements INetworkConnection {

    private XNetworkClient client;
    private Request request;
    private HttpURLConnection connection;

    @Override
    public Response performCall(NetworkCall call) {
        try {
            client = call.getClient();
            request = call.getRequest();
            createUrlConnection(client, request);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    private void createUrlConnection(XNetworkClient client, Request request) throws IOException {
        connection = (HttpURLConnection) new URL(request.url()).openConnection();
        connection.setReadTimeout(client.readTimeout());
        connection.setConnectTimeout(client.connectTimeout());
        connection.setRequestMethod(request.method());
        connection.setDoInput(true);
        connection.setDoOutput(true);
    }

}
