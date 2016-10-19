package com.xu.simplenetwork.response;

/**
 * Created by Xu on 2016/10/19.
 */

public class Response {

    private final String message;
    private final int code;

    private Response(Builder builder) {
        this.message = builder.message;
        this.code = builder.code;
    }

    public static class Builder {
        private String message;
        private int code;

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder code(int code) {
            this.code = code;
            return this;
        }

        public Response build() {
            return new Response(this);
        }


    }

}
