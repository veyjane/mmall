package com.mmall.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class TokenCache {
    //声明日志
    private static Logger logger = LoggerFactory.getLogger(TokenCache.class);

    public static final String TOKEN_PREFIX = "token_";

    //声明静态内存块,guava本地缓存
    //初始缓存大小1000，当达到最大10000时使用LRU算法清除缓存，时间有效期12小时
    private static LoadingCache<String,String> localCache = CacheBuilder.newBuilder().initialCapacity(1000).maximumSize(10000).expireAfterAccess(12, TimeUnit.HOURS)
            .build(new CacheLoader<String, String>() {
                //默认的数据加载实现，当调用get取值的时候，如果key没有对应的值，就调用这个方法进行加载。
                @Override
                public String load(String s) throws Exception {
                    return "null";
                }
            });

    public static void  setKey(String key, String value){
        localCache.put(key,value);
    }

    public static String getKey(String key){
        String value = null;
        try {
            value = localCache.get(key);
            if("null".equals(value)){
                return null;
            }
        }catch (Exception e){
            logger.error("localCache get error",e);
        }
        return null;
    }
}
