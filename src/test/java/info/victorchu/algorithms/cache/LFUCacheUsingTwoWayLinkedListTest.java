package info.victorchu.algorithms.cache;

import org.junit.jupiter.api.Test;

/**
 * @description:
 * @Date:2022/11/18 12:47
 * @author:victorchutian
 */
class LFUCacheUsingTwoWayLinkedListTest {
    @Test
    public void test() {
        LFUCacheUsingTwoWayLinkedList<String,String> cache = new LFUCacheUsingTwoWayLinkedList<>(4);
        cache.put("a","A");
        cache.put("b","B");
        cache.put("c","C");
        cache.put("d","D");
        cache.get("a");
        cache.get("a");
        cache.get("a");
        cache.get("b");
        cache.get("b");
        cache.get("b");
        cache.get("c");
        cache.get("c");
        cache.get("d");
        cache.get("d");
        cache.get("c");
        // d will be remove according to LFU policy
        cache.put("e","E");
        cache.get("e");
        cache.getMap().forEach((k,v)->{
            System.out.println(k+":"+v.getLeft()+":"+v.getRight());
        });
    }
}