package com.example.client_processing.util;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
public class CacheEntry {
    private Object value;

    @Value("${value.limitTime}")
    private long timestamp;

    private long timeCreate;

    public CacheEntry(Object value, long timestamp) {
        this.value = value;
        this.timestamp = timestamp;
        timeCreate = System.currentTimeMillis();
    }

    public boolean checkTime() {
        if (timeCreate + timestamp < System.currentTimeMillis()) {
            return false;
        } else {
            return true;
        }
    }

}
