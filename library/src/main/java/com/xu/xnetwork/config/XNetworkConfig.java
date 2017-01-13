package com.xu.xnetwork.config;

import android.content.Context;

import com.xu.xnetwork.cache.Cache;
import com.xu.xnetwork.connection.HttpUrlConn;
import com.xu.xnetwork.connection.XNetworkConnection;

import java.util.concurrent.TimeUnit;

/**
 * Created by Xu on 2016/12/24.
 */

public final class XNetworkConfig {

    private Context context;
    private int connectTimeout;
    private int readTimeout;
    private int writeTimeout;
    private XNetworkConnection mConnection;
    private Cache cache;

    private XNetworkConfig() {

    }

    private XNetworkConfig(Builder builder) {
        this.context = builder.mContext;
        this.connectTimeout = builder.connectTimeout;
        this.readTimeout = builder.readTimeout;
        this.writeTimeout = builder.writeTimeout;
        this.mConnection = builder.connection;
        this.cache = builder.cache;
    }

    public Context context() {
        return context;
    }

    public XNetworkConnection connection() {
        return mConnection;
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

    public Cache cache() {
        return cache;
    }

    public static class Builder {
        private Context mContext;
        private int connectTimeout;
        private int readTimeout;
        private int writeTimeout;
        private XNetworkConnection connection;
        private Cache cache;

        public Builder() {
            connectTimeout = 10_000;
            readTimeout = 10_000;
            writeTimeout = 10_000;
            connection = new HttpUrlConn();
        }

        public Builder context(Context context) {
            if (context == null) throw new NullPointerException("context == null");
            mContext = context;
            return this;
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

        public Builder connection(XNetworkConnection connection) {
            this.connection = connection;
            return this;
        }

        public Builder cache(Cache cache) {
            this.cache = cache;
            return this;
        }

        public XNetworkConfig build() {
            return new XNetworkConfig(this);
        }
    }
}
