package com.xu.xnetwork.connection;

import com.xu.xnetwork.call.XNetworkCall;
import com.xu.xnetwork.config.XNetworkConfig;
import com.xu.xnetwork.request.Request;
import com.xu.xnetwork.request.RequestBody;
import com.xu.xnetwork.response.Response;
import com.xu.xnetwork.response.ResponseBody;
import com.xu.xnetwork.util.CloseUtils;
import com.xu.xnetwork.util.HttpUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Set;

/**
 * Created by Xu on 2016/11/3.
 */

public class HttpUrlConn implements XNetworkConnection {

    private Request request;
    private XNetworkConfig config;

    @Override
    public Response performCall(XNetworkCall call) {
        HttpURLConnection connection = null;
        Response response = null;
        config = call.getConfig();
        request = call.getRequest();
        try {
            connection = createUrlConnection(config, request);
            setRequestHeaders(connection, request);
            setRequestParams(connection, request);
            response = fetchResponse(connection);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return response;
    }

    private HttpURLConnection createUrlConnection(XNetworkConfig config, Request request) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(request.url()).openConnection();
        connection.setReadTimeout(config.readTimeout());
        connection.setConnectTimeout(config.connectTimeout());
        connection.setRequestMethod(request.method());
        return connection;
    }

    private void setRequestHeaders(HttpURLConnection connection, Request request) {
        Set<String> headersKeys = request.header().names();
        for (String headerName : headersKeys) {
            connection.addRequestProperty(headerName, request.header().get(headerName));
        }
        if (!headersKeys.contains("Connection")) {
            connection.addRequestProperty("Connection", "Keep-Alive");
        }

    }

    private void setRequestParams(HttpURLConnection connection, Request request)
            throws IOException {
        String method = request.method();
        connection.setRequestMethod(method);
        // add params
        RequestBody body = request.body();
        if (body != null) {
            // enable output
            connection.setDoOutput(true);
            // set content type
            connection
                    .addRequestProperty("Content-Type", request.body().type().toString());
//             write params data to connection
            DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
            dataOutputStream.write(body.bytes());
            dataOutputStream.close();
        }
    }

    private Response fetchResponse(HttpURLConnection connection) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        Response response = null;
        try {
            inputStream = connection.getInputStream();
            if (request.method() == "POST") {
                outputStream = connection.getOutputStream();
            }
            byte[] bytes = HttpUtils.InputStreamTOByte(inputStream);
            String HTTPStatus = connection.getResponseMessage();
            int responseCode = connection.getResponseCode();
            if (responseCode == -1) {
                throw new IOException("Could not retrieve response code from HttpUrlConnection.");
            }
            int contentLength = bytes.length;
            ResponseBody responseBody = new ResponseBody.Builder()
                    .bytes(bytes)
                    .contentLength(contentLength)
                    .build();
            Response.Builder builder = HttpUtils.readResponseHeader(connection);
            response = builder
                    .message(HTTPStatus)
                    .receivedResponseAtMillis(System.currentTimeMillis())
                    .responseBody(responseBody)
                    .code(connection.getResponseCode())
                    .build();
            switch (responseCode) {
                // 300: multi choice; 301: moven permanently;
                // 302: moved temporarily; 303: see other;
                // 307: redirect temporarily; 308: redirect permanently
                case 300:
                case 301:
                case 302:
                case 303:
                case 307:
                case 308:
                    return handleRedirect(response);
                default:
                    return response;
            }
        } catch (IOException e) {
            Response.Builder builder = HttpUtils.readResponseHeader(connection);
            ResponseBody responseBody = new ResponseBody.Builder()
                    .bytes(null)
                    .contentLength(0)
                    .build();
            response = builder
                    .message(e.getMessage())
                    .receivedResponseAtMillis(System.currentTimeMillis())
                    .responseBody(responseBody)
                    .code(0)
                    .build();
        } finally {
            CloseUtils.closeQuietly(inputStream);
            CloseUtils.closeQuietly(outputStream);
        }
        return response;

    }

    private Response handleRedirect(Response original) throws IOException {
        String nextUrl = original.header("Location");
        Request.Builder newRequestBuilder = request.newBuilder();
        Request newRequest = null;
        if (request.method() == "post") {
            newRequest = newRequestBuilder.url(nextUrl).buildPostRequest(request.body());
        } else if (request.method() == "get") {
            newRequest = newRequestBuilder.url(nextUrl).buildGetRequest();
        }
        HttpURLConnection connection = createUrlConnection(config, newRequest);
        setRequestHeaders(connection, request);
        setRequestParams(connection, request);
        return fetchResponse(connection);
    }

}
