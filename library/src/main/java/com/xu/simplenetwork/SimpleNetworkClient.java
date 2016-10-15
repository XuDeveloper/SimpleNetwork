package com.xu.simplenetwork;

import com.xu.simplenetwork.call.AsyncCall;
import com.xu.simplenetwork.call.NetworkCall;
import com.xu.simplenetwork.call.SynCall;
import com.xu.simplenetwork.request.Request;

import java.net.HttpURLConnection;
import java.util.concurrent.TimeUnit;

/**
 * Created by Xu on 2016/10/11.
 */

public class SimpleNetworkClient {

    final HttpURLConnection connection;
    final int connectTimeout;
    final int readTimeout;
    final int writeTimeout;

    public SimpleNetworkClient() {
        this(new Builder());
    }

    private SimpleNetworkClient(Builder builder) {
        this.connection = builder.connection;
        this.connectTimeout = builder.connectTimeout;
        this.readTimeout = builder.readTimeout;
        this.writeTimeout = builder.writeTimeout;
    }

    public int connectTimeout() {
        return connectTimeout;
    }

    public int readTimeout() {
        return readTimeout;
    }

    public int writeTimeout() {
        return writeTimeout;
    }

    public NetworkCall newNetworkCall(Request request) {
        if (request.isAsync()) {
            return new AsyncCall(this, request);
        } else {
            return new SynCall(this, request);
        }
    }

    public static class Builder {
        private HttpURLConnection connection;
        private int connectTimeout;
        private int readTimeout;
        private int writeTimeout;

        public Builder() {
            connectTimeout = 10_000;
            readTimeout = 10_000;
            writeTimeout = 10_000;
        }

        public Builder connectTimeout(long timeout, TimeUnit unit) {
            if (timeout < 0) throw new IllegalArgumentException("timeout < 0");
            if (unit == null) throw new NullPointerException("unit == null");
            long millis = unit.toMillis(timeout);
            if (millis > Integer.MAX_VALUE)
                throw new IllegalArgumentException("Timeout too large.");
            if (millis == 0 && timeout > 0)
                throw new IllegalArgumentException("Timeout too small.");
            connectTimeout = (int) millis;
            return this;
        }

        public Builder readTimeout(long timeout, TimeUnit unit) {
            if (timeout < 0) throw new IllegalArgumentException("timeout < 0");
            if (unit == null) throw new NullPointerException("unit == null");
            long millis = unit.toMillis(timeout);
            if (millis > Integer.MAX_VALUE)
                throw new IllegalArgumentException("Timeout too large.");
            if (millis == 0 && timeout > 0)
                throw new IllegalArgumentException("Timeout too small.");
            readTimeout = (int) millis;
            return this;
        }

        public Builder writeTimeout(long timeout, TimeUnit unit) {
            if (timeout < 0) throw new IllegalArgumentException("timeout < 0");
            if (unit == null) throw new NullPointerException("unit == null");
            long millis = unit.toMillis(timeout);
            if (millis > Integer.MAX_VALUE)
                throw new IllegalArgumentException("Timeout too large.");
            if (millis == 0 && timeout > 0)
                throw new IllegalArgumentException("Timeout too small.");
            writeTimeout = (int) millis;
            return this;
        }
    }

}
