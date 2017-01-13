package com.xu.xnetwork.response;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.gson.Gson;
import com.xu.xnetwork.request.Header;

/**
 * Created by Xu on 2016/10/19.
 */

public final class Response {

    private final String HTTPstatus;
    private final int code;
    private final Header headers;
    private final ResponseBody body;
    private final long receivedResponseAtMillis;

    private Response(Builder builder) {
        this.HTTPstatus = builder.HTTPstatus;
        this.code = builder.code;
        this.headers = builder.headers.build();
        this.body = builder.body;
        this.receivedResponseAtMillis = builder.receivedResponseAtMillis;
    }

    public String header(String name) {
        return header(name, null);
    }

    public String header(String name, String defaultValue) {
        String result = headers.get(name);
        return result != null ? result : defaultValue;
    }

    public Header headers() {
        return headers;
    }

    public <T> T json(Class<T> className) {
        Gson gson = new Gson();
        return gson.fromJson(string(), className);
    }

    public String string() {
        if (body.bytes() == null) {
            throw new RuntimeException("response is null!");
        }
        return new String(body.bytes());
    }

    public Bitmap bitmap() {
        if (body.bytes() == null) {
            throw new RuntimeException("response is null!");
        }
        return BitmapFactory.decodeByteArray(body.bytes(), 0, body.bytes().length);
    }

    public ResponseBody body() {
        return body;
    }

    public static class Builder {
        private String HTTPstatus;
        private int code;
        private Header.Builder headers;
        private ResponseBody body;
        private long receivedResponseAtMillis;

        public Builder() {
            headers = new Header.Builder();
        }

        public Builder message(String HTTPstatus) {
            this.HTTPstatus = HTTPstatus;
            return this;
        }

        public Builder code(int code) {
            this.code = code;
            return this;
        }

        public Builder addHeader(String name, String value) {
            headers.add(name, value);
            return this;
        }

        public Builder removeHeader(String name) {
            headers.removeAll(name);
            return this;
        }

        public Builder responseBody(ResponseBody body) {
            this.body = body;
            return this;
        }

        public Builder receivedResponseAtMillis(long receivedResponseAtMillis) {
            this.receivedResponseAtMillis = receivedResponseAtMillis;
            return this;
        }

        public Response build() {
            return new Response(this);
        }

    }

}
