package info.victorchu.algorithms.cache;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Copyright:www.xiaojukeji.com Inc. All rights reserved.
 * @Description:
 * @Date:2022/11/18 12:47
 * @Author:victorchutian
 */
class LFUCacheTest {
    @Test
    public void main(String[] args) {
        LFUCache<String,String> cache = new LFUCache<>(4);
        cache.put("a","A");
        cache.put("b","B");
        cache.put("c","C");
        cache.put("d","D");
        cache.get("a");
        cache.get("a");
        cache.get("b");
        cache.get("b");
        cache.get("b");
        cache.get("c");
        cache.get("c");
        cache.get("d"); // this will be remove according to LFU policy
        cache.put("e","E");
        cache.getMap().forEach((k,v)->{
            System.out.println(k+":"+v.value);
        });
    }
}