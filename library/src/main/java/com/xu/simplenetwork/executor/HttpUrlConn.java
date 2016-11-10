package com.xu.simplenetwork.executor;

import com.xu.simplenetwork.Utils;
import com.xu.simplenetwork.XNetworkClient;
import com.xu.simplenetwork.call.XNetworkCall;
import com.xu.simplenetwork.request.Request;
import com.xu.simplenetwork.response.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Xu on 2016/11/3.
 */

public class HttpUrlConn implements XNetworkConnection {

    private XNetworkClient client;
    private Request request;
    private HttpURLConnection connection;
    private Response response;
    private byte[] bytes;

    @Override
    public Response performCall(XNetworkCall call) {
        try {
            client = call.getClient();
            request = call.getRequest();
            createUrlConnection(client, request);
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                OutputStream outputStream = connection.getOutputStream();
                bytes = Utils.InputStreamTOByte(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return response;
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
