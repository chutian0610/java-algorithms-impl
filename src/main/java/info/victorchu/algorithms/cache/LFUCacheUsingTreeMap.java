package info.victorchu.algorithms.cache;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 */
public class LFUCacheUsingTreeMap<K, V> {

    Map<K, Node<K,V>> cache;
    /**
     * K是频率，V是按顺序插入的节点
     */
    TreeMap<Integer, LinkedHashSet<Node<K,V>>> freqMap;
    int capacity;

    public LFUCacheUsingTreeMap(int capacity) {
        this.capacity = capacity;
        cache = new HashMap<>();
        freqMap = new TreeMap<>();
    }

    public V get(K key) {
        if (!cache.containsKey(key)) {
            return null;
        }
        Node<K,V> node = cache.get(key);
        //更新访问次数
        refreshFreq(node);
        return node.val;
    }

    public void put(K key, V value) {
        if (capacity == 0) {
            return;
        }
        if (cache.containsKey(key)) {
            Node<K,V> node = cache.get(key);
            node.val = value;
            //更新访问次数
            refreshFreq(node);
        } else {
            if (cache.size() == capacity) {
                //删除频率最低的
                LinkedHashSet<Node<K,V>> nodes = freqMap.get(freqMap.keySet().stream().findFirst().get());
                Node node = nodes.stream().findFirst().get();
                cache.remove(node.key);
                deleteNodeFromFreqMap(node);
            }
            Node node = new Node(key, value);
            cache.put(key, node);
            addNodeToFreqMap(node);
        }
    }

    private void refreshFreq(Node node) {
        deleteNodeFromFreqMap(node);
        node.freq++;
        addNodeToFreqMap(node);
    }

    private void addNodeToFreqMap(Node<K,V> node) {
        if (!freqMap.containsKey(node.freq)) {
            freqMap.put(node.freq, new LinkedHashSet<>());
        }
        freqMap.get(node.freq).add(node);
    }

    private void deleteNodeFromFreqMap(Node<K,V> node) {
        LinkedHashSet<Node<K,V>> nodes = freqMap.get(node.freq);
        nodes.remove(node);
        if (nodes.isEmpty()) {
            freqMap.remove(node.freq);
        }
    }
}
class Node<K,V>{
    K key;
    V val;
    int freq;
    public Node(){}
    public Node(K k,V v){
        this.key=k;
        this.val =v;
        this.freq=1;
    }
}
