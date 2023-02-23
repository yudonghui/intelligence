package com.ydh.intelligence.db;

public interface DbInterface<T> {
    void success(T result);

    void fail();
}
