package com.xu.xnetwork.response;


/**
 * Created by Xu on 2016/10/22.
 */

public final class ResponseBody {

    private final byte[] bytes;
    private final int contentLength;

    private ResponseBody(Builder builder) {
        this.bytes = builder.bytes;
        this.contentLength = builder.contentLength;
    }

    public byte[] bytes() {
        return bytes;
    }

    public static class Builder {
        private byte[] bytes;
        private int contentLength;

        public Builder bytes(byte[] bytes) {
            this.bytes = bytes;
            return this;
        }

        public Builder contentLength(int contentLength) {
            this.contentLength = contentLength;
            return this;
        }

        public ResponseBody build() {
            return new ResponseBody(this);
        }
    }

}
