package org.example.util;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class CacheEntry {

    private Object value;
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
