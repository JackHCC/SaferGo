package xin.skingorz.isafety;

import android.support.v4.util.LruCache;

public class Cache extends LruCache {
    public Cache(int maxSize) {
        super(maxSize);
    }

    public void addDataMemoryCache(String key, Object value) {
        GlobalVariable.cache.put(key, value);
    }

    public Object getDataFromMemotyCache(String key){
        return GlobalVariable.cache.get(key);
    }

}
