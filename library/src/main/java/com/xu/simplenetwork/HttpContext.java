package com.xu.simplenetwork;

import com.xu.simplenetwork.request.Request;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Xu on 2016/10/22.
 */

public final class HttpContext {

    private XNetworkClient client;
    private Request request;
    private HttpURLConnection connection;

    public HttpContext(XNetworkClient client, Request request) throws IOException {
        this.client = client;
        this.request = request;
        connection = (HttpURLConnection) new URL(request.url()).openConnection();
        connection.setReadTimeout(client.readTimeout());
        connection.setConnectTimeout(client.connectTimeout());
        connection.setRequestMethod(request.method());
        connection.setDoInput(true);
        connection.setDoOutput(true);
    }

    public HttpURLConnection getConnection() {
        return connection;
    }

    public int getResponseCode() throws IOException {
        return connection.getResponseCode();
    }

    public InputStream getInputStream() throws IOException {
        return connection.getInputStream();
    }

    public OutputStream getOutputStream() throws IOException {
        return connection.getOutputStream();
    }

    public void setRequestProperty(String key, String value) {
        connection.setRequestProperty(key, value);
    }

    public void setDefaultRequestProperty() throws IOException {
        connection.setRequestProperty("Content-Type", request.body().type().toString());
        connection.setRequestProperty("Content-Length", String.valueOf(request.body().content().length()));
        Utils.writeContentByOutputStream(connection.getOutputStream(), request.body().content());
    }

    public boolean isGet() {
        return request.method().equals("get");
    }

    public boolean isPost() {
        return request.method().equals("post");
    }
}
