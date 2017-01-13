package com.xu.xnetwork.cache;

/**
 * Created by Xu on 2017/1/13.
 */

public interface Cache<K, V> {

    V get(K key);

    void put(K key, V value);

    void remove(K key);
}
