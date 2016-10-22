package com.xu.simplenetwork.request;

/**
 * Created by Xu on 2016/10/8.
 */

public final class Request {

    private final String url;
    private final String method;
    private final RequestBody body;
    private final boolean isAsync;
//    private final Headers headers;
//    private final RequestBody body;
//    private final Object tag;

    private Request(Builder builder) {
        this.url = builder.url;
        this.method = builder.method;
        this.body = builder.body;
        this.isAsync = builder.isAsync;
    }

    public String url() {
        return url;
    }

    public String method() {
        return method;
    }

    public RequestBody body() {
        return body;
    }

    public boolean isAsync() {
        return isAsync;
    }

    @Override
    public String toString() {
        return "Request{method="
                + method
                + ", url="
                + url
                + '}';
    }

    public static class Builder {
        private String url;
        private String method;
        private RequestBody body;
        private boolean isAsync;

        public Builder() {

        }

        public Builder url(String url) {
            if (url == null) throw new NullPointerException("url == null");
            this.url = url;
            return this;
        }

        public Builder isAsync(boolean isAsync) {
            this.isAsync = isAsync;
            return this;
        }

        public Request buildPostRequest(RequestBody body) {
            this.method = "post";
            this.body = body;
            return new Request(this);
        }

        public Request buildGetRequest() {
            this.method = "get";
            return new Request(this);
        }
    }
}

