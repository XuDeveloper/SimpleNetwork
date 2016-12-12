package com.xu.simplenetwork.connection;

import com.xu.simplenetwork.util.CloseUtils;
import com.xu.simplenetwork.util.HttpUtils;
import com.xu.simplenetwork.XNetworkClient;
import com.xu.simplenetwork.call.XNetworkCall;
import com.xu.simplenetwork.request.Request;
import com.xu.simplenetwork.response.Response;
import com.xu.simplenetwork.response.ResponseBody;

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

    private XNetworkClient client;
    private Request request;
    private HttpURLConnection connection;

    @Override
    public Response performCall(XNetworkCall call) {
        HttpURLConnection connection = null;
        client = call.getClient();
        request = call.getRequest();
        try {
            connection = createUrlConnection(client, request);
            setRequestHeaders(connection, request);
            setRequestParams(connection, request);
            return fetchResponse(connection);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    private HttpURLConnection createUrlConnection(XNetworkClient client, Request request) throws IOException {
        connection = (HttpURLConnection) new URL(request.url()).openConnection();
        connection.setReadTimeout(client.readTimeout());
        connection.setConnectTimeout(client.connectTimeout());
        connection.setRequestMethod(request.method());
        connection.setDoInput(true);
        connection.setDoOutput(true);
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
//         add params
        byte[] body = request.body().bytes();
        if (body != null) {
            // enable output
            connection.setDoOutput(true);
            // set content type
            connection
                    .addRequestProperty("Content-Type", request.body().type().toString());
//             write params data to connection
            DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
            dataOutputStream.write(body);
            dataOutputStream.close();
        }
    }

    private Response fetchResponse(HttpURLConnection connection) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = connection.getInputStream();
            outputStream = connection.getOutputStream();
            byte[] bytes = HttpUtils.InputStreamTOByte(inputStream);
            String HTTPStatus = connection.getResponseMessage();
            int responseCode = connection.getResponseCode();
            if (responseCode == -1) {
                throw new IOException("Could not retrieve response code from HttpUrlConnection.");
            }
            ResponseBody responseBody = new ResponseBody.Builder().bytes(bytes).build();
            Response.Builder builder = HttpUtils.readResponseHeader(connection);
            Response response = builder
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

        } finally {
            CloseUtils.closeQuietly(inputStream);
            CloseUtils.closeQuietly(outputStream);
        }
        return null;

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
        connection = createUrlConnection(client, newRequest);
        setRequestHeaders(connection, request);
        setRequestParams(connection, request);
        return fetchResponse(connection);
    }

}
