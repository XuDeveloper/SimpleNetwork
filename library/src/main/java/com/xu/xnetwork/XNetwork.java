package com.xu.xnetwork;

import android.content.Context;

import com.xu.xnetwork.config.XNetworkConfig;

import java.util.concurrent.TimeUnit;

/**
 * Created by Xu on 2016/12/24.
 */

public class XNetwork {

    public static XNetworkClient init(XNetworkConfig config) {
        return new XNetworkClient(config);
    }

    public static XNetworkClient defaultInit(Context context) {
        XNetworkConfig config = new XNetworkConfig.Builder()
                .context(context)
                .connectTimeout(5000, TimeUnit.MILLISECONDS)
                .readTimeout(5000, TimeUnit.MILLISECONDS)
                .writeTimeout(5000, TimeUnit.MILLISECONDS)
                .build();
        return new XNetworkClient(config);
    }
}
