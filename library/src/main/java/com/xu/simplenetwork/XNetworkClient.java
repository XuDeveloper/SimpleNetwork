package com.xu.simplenetwork;

import com.xu.simplenetwork.call.RealNetworkCall;
import com.xu.simplenetwork.call.XNetworkCall;
import com.xu.simplenetwork.connection.HttpUrlConn;
import com.xu.simplenetwork.connection.XNetworkConnection;
import com.xu.simplenetwork.executor.XNetworkExecutor;
import com.xu.simplenetwork.executor.XNetworkQueue;
import com.xu.simplenetwork.request.Request;

import java.util.concurrent.TimeUnit;

/**
 * Created by Xu on 2016/10/11.
 */

public class XNetworkClient {

    final XNetworkConnection connection;
    final XNetworkExecutor executor;
    final int connectTimeout;
    final int readTimeout;
    final int writeTimeout;
    final XNetworkQueue queue;

    public XNetworkClient() {
        this(new Builder());
    }

    private XNetworkClient(Builder builder) {
        this.connection = builder.connection;
        this.connectTimeout = builder.connectTimeout;
        this.readTimeout = builder.readTimeout;
        this.writeTimeout = builder.writeTimeout;
        this.executor = builder.executor;
        this.queue = new XNetworkQueue();
    }

    public XNetworkExecutor executor() {
        return executor;
    }

    public XNetworkQueue queue() {
        return queue;
    }

    public XNetworkConnection connection() {
        return connection;
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

    public static XNetworkCall newNetworkCall(Request request) {
        XNetworkClient client = new XNetworkClient();
        return new RealNetworkCall(client, request);
    }

    public static class Builder {
        private XNetworkConnection connection;
        private XNetworkExecutor executor;
        private int connectTimeout;
        private int readTimeout;
        private int writeTimeout;

        public Builder() {
            connectTimeout = 10_000;
            readTimeout = 10_000;
            writeTimeout = 10_000;
            connection = new HttpUrlConn();
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

        public Builder executor(XNetworkExecutor networkExecutor) {
            executor = networkExecutor;
            return this;
        }
    }

}
