package com.xu.xnetwork.request;

import com.xu.xnetwork.util.HttpUtils;

/**
 * Created by Xu on 2016/10/8.
 */

public final class RequestBody {

    private final MediaType type;
    private final String content;

    private RequestBody(Builder builder) {
        this.type = builder.type;
        this.content = builder.content;
    }

    @Override
    public String toString() {
        return "RequestBody{type="
                + type
                + ", content="
                + content
                + '}';
    }

    public MediaType type() {
        return type;
    }

    public String content() {
        return content;
    }

    public byte[] bytes() {
        return content.getBytes(type.charset());
    }

    public static class Builder {
        private MediaType type;
        private String content;

        public Builder() {
            type = HttpUtils.JSON;
        }

        public Builder type(MediaType type) {
            if (type == null) throw new NullPointerException("type is null");
            this.type = type;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public RequestBody build() {
            return new RequestBody(this);
        }

    }

}
