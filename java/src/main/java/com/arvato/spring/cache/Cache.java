package com.arvato.spring.cache;

public interface Cache {

    void add(String key, Object value);

    void remove(String key);

    Object get(String key);

    void clear();

    long size();
}