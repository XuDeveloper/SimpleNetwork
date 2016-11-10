package com.xu.simplenetwork.response;

/**
 * Created by Xu on 2016/10/19.
 */

public final class Response {

    private final String message;
    private final int code;
    private final ResponseBody body;
    private final long receivedResponseAtMillis;

    private Response(Builder builder) {
        this.message = builder.message;
        this.code = builder.code;
        this.body = builder.body;
        this.receivedResponseAtMillis = builder.receivedResponseAtMillis;
    }

    public static class Builder {
        private String message;
        private int code;
        private ResponseBody body;
        private long receivedResponseAtMillis;

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder code(int code) {
            this.code = code;
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
