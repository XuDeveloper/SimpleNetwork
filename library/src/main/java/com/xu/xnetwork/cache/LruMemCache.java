package com.xu.xnetwork.cache;

import android.util.LruCache;

import com.xu.xnetwork.response.Response;

/**
 * Created by Xu on 2017/1/13.
 */

public class LruMemCache implements Cache<String, Response> {

    private LruCache<String, Response> lruCache;

    public LruMemCache() {
        // 计算可使用的最大内存
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // 取八分之一的可用内存作为缓存
        final int cacheSize = maxMemory / 8;
        lruCache = new LruCache<String, Response>(cacheSize) {
            @Override
            protected int sizeOf(String key, Response value) {
                return value.body().contentLength() / 1024;
            }
        };


    }

    @Override
    public Response get(String key) {
        return lruCache.get(key);
    }

    @Override
    public void put(String key, Response response) {
        lruCache.put(key, response);
    }

    @Override
    public void remove(String key) {
        lruCache.remove(key);
    }
}
