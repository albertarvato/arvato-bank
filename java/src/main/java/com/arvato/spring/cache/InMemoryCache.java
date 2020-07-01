package com.arvato.spring.cache;

import java.lang.ref.SoftReference;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCache implements Cache {

    private static final int CLEAN_UP_PERIOD_IN_SEC = 120;

    private final ConcurrentHashMap<String, SoftReference<CacheObject>> cache = new ConcurrentHashMap<>();

    public InMemoryCache() {
        Thread cleanerThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(CLEAN_UP_PERIOD_IN_SEC * 1000);
                    cache.entrySet().removeIf(entry -> Optional.ofNullable(entry.getValue()).map(SoftReference::get).map(CacheObject::isExpired).orElse(false));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        cleanerThread.setDaemon(true);
        cleanerThread.start();
    }

    @Override
    public void add(String key, Object value) {
        if (key == null) {
            return;
        }
        if (value == null) {
            cache.remove(key);
        } else {
            cache.put(key, new SoftReference<>(new CacheObject(value, System.currentTimeMillis())));
        }
    }

    @Override
    public void remove(String key) {
        cache.remove(key);
    }

    @Override
    public Object get(String key) {
        return Optional.ofNullable(cache.get(key)).map(SoftReference::get).filter(cacheObject -> !cacheObject.isExpired()).map(CacheObject::getAccountId).orElse(null);
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public long size() {
        return cache.entrySet().stream().filter(entry -> Optional.ofNullable(entry.getValue()).map(SoftReference::get).map(cacheObject -> !cacheObject.isExpired()).orElse(false)).count();
    }


    private static class CacheObject {

        private static final long availability = 120 * 1000;
        private Object accountId;
        private long creationTime;

        public CacheObject(Object accountId, long creationTime) {
            this.accountId = accountId;
            this.creationTime = creationTime;
        }

        public Object getAccountId() {
            return accountId;
        }

        public void setAccountId(Object accountId) {
            this.accountId = accountId;
        }

        public long getCreationTime() {
            return creationTime;
        }

        public void setCreationTime(long creationTime) {
            this.creationTime = creationTime;
        }

        boolean isExpired() {
            return System.currentTimeMillis() - creationTime > availability;
        }
    }
}