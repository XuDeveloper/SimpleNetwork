package com.xu.xnetwork.executor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Xu on 2016/10/30.
 */

public final class XNetworkExecutor {

    private Executor synExecutor;
    private Executor asyncExecutor;

    public XNetworkExecutor() {
        synExecutor = Executors.newSingleThreadExecutor();
        asyncExecutor = Executors.newCachedThreadPool();
    }

    public void executeSyn(Runnable runnable) {
        synExecutor.execute(runnable);
    }

    public void executeAsync(Runnable runnable) {
        asyncExecutor.execute(runnable);
    }
}
