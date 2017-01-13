package com.xu.xnetwork.request;

/**
 * Created by Xu on 2016/10/8.
 */

public final class Request {

    private final String url;
    private final String method;
    private final Header header;
    private final RequestBody body;
    private final boolean isAsync;
    private final int priority;
    private final boolean isCache;
//    private final Headers headers;
//    private final RequestBody body;
//    private final Object tag;

    private Request(Builder builder) {
        this.url = builder.url;
        this.method = builder.method;
        this.header = builder.headers.build();
        this.body = builder.body;
        this.isAsync = builder.isAsync;
        this.priority = builder.priority;
        this.isCache = builder.isCache;
    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    public String url() {
        return url;
    }

    public String method() {
        return method;
    }

    public Header header() {
        return header;
    }

    public RequestBody body() {
        return body;
    }

    public int priority() {
        return priority;
    }

    public boolean isAsync() {
        return isAsync;
    }

    public boolean isCache() {
        return isCache;
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
        private Header.Builder headers;
        private RequestBody body;
        private boolean isAsync;
        private int priority;
        private boolean isCache;

        public Builder() {
            this.headers = new Header.Builder();
        }

        private Builder(Request request) {
            this.url = request.url;
            this.method = request.method;
            this.headers = request.header.newBuilder();
            this.body = request.body;
//            this.tag = request.tag;
            this.priority = request.priority;
            this.isCache = false;
        }

        public Builder url(String url) {
            if (url == null) throw new NullPointerException("url == null");
            this.url = url;
            return this;
        }

//        public Builder isAsync(boolean isAsync) {
//            this.isAsync = isAsync;
//            return this;
//        }
//
//        public Builder priority(int priority) {
//            if (priority > 1000 || priority < 0) {
//                throw new RuntimeException("priority out of range");
//            }
//            this.priority = priority;
//            return this;
//        }

        public Builder addHeader(String name, String value) {
            headers.add(name, value);
            return this;
        }

        public Builder removeHeader(String name) {
            headers.removeAll(name);
            return this;
        }

        public Builder method(String method) {
            if (method == null) throw new NullPointerException("method == null");
            this.method = method;
            return this;
        }

        public Builder isCache(boolean isCache) {
            this.isCache = isCache;
            return this;
        }

        public Request buildPostRequest(RequestBody body) {
            this.method = "POST";
            this.body = body;
            return new Request(this);
        }

        public Request buildGetRequest() {
            this.method = "GET";
            return new Request(this);
        }
    }
}

