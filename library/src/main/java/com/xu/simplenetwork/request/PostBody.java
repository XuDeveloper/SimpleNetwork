package com.xu.simplenetwork.request;

import com.xu.simplenetwork.Utils;

/**
 * Created by Xu on 2016/10/8.
 */

public final class PostBody {

    private final MediaType type;
    private final String content;

    private PostBody(Builder builder) {
        this.type = builder.type;
        this.content = builder.content;
    }

    @Override
    public String toString() {
        return "PostBody{type="
                + type
                + ", content="
                + content
                + '}';
    }

    public static class Builder {
        private MediaType type;
        private String content;

        public Builder() {
            type = Utils.JSON;
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

        public PostBody build() {
            return new PostBody(this);
        }

    }

}
