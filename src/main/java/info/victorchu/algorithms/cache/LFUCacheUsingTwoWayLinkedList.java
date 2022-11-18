package info.victorchu.algorithms.cache;

import info.victorchu.utils.Pair;

import java.util.HashMap;

/**
 * LFU（Least Frequently Used）最近最少使用算法.
 * 它是基于“如果一个数据在最近一段时间内使用次数很少，那么在将来一段时间内被使用的可能性也很小”的思路。
 * 如果访问次数相同的元素有多个，则移除最久访问的那个
 * @author victor
 * @mail victorchu0610@outlook.com
 * @date 2019/4/28
 * @version 1.0
 */
public class LFUCacheUsingTwoWayLinkedList<K, V> {
    /**
     * 链表尾部的NodeQueue,指向访问次数最少的节点
     */
    public CacheNodeQueue tail;
    /**
     * 容量
     */
    public int capacity;

    public HashMap<K, Pair<Integer,V>> getMap() {
        HashMap<K, Pair<Integer,V>> hashMap = new HashMap<>();
        map.forEach((x,y)->{
            hashMap.put(x,Pair.of(y.currentLevelQueue().frequency,y.value));
        });
        return hashMap;
    }
    /**
     * 存储key-value对的HashMap
     */
    private HashMap<K, CacheNode<K,V>> map;

    //构造方法
    public LFUCacheUsingTwoWayLinkedList(int capacity) {
        this.capacity = capacity;
        map = new HashMap<K, CacheNode<K,V>>(capacity);
    }

    /**
     * Node升级到下一个queue
     * @param n
     */
    private void moveNodeToNextLevelQueueHead(CacheNode n){
        CacheNodeQueue current = n.currentLevelQueue();
        int valueFrequency= current.frequency+1;
        CacheNodeQueue nextLevel = current.high;
        if(current.isHeadQueue()){
            if(current.isSingleQueue()) {
                // 单元素节点直接增加queue的frequency
                current.frequency++;
            }else {
                //在下一级直接加上一个新节点
                unlinkNode(n);
                new CacheNodeQueue(current, null, n, n, valueFrequency);
            }
        }else {
            if (current.high.frequency == valueFrequency) {
                if(current.isSingleQueue()) {
                    unlinkNode(n);
                    nextLevel.addNodeToHead(n);
                    if(current.isTailQueue()){
                        tail = nextLevel;
                        nextLevel.low = null;
                    }else {
                        current.low.high = nextLevel;
                        nextLevel.low = current.low;
                    }
                }else {
                    //下一级NodeQueue的访问次数与Node当前访问次数一样，
                    //把Node插入到下一级NodeQueue的头部
                    unlinkNode(n);
                    nextLevel.addNodeToHead(n);
                }
            }else if (current.high.frequency > valueFrequency) {
                if(current.isSingleQueue()) {
                    if(current.isTailQueue()){
                        current.frequency++;
                    }else {
                        unlinkNode(n);
                        nextLevel = new CacheNodeQueue(current, current.high, n, n, valueFrequency);
                        current.low.high = nextLevel;
                        nextLevel.low = current.low;
                    }
                }else {
                    //下一级NodeQueue的访问次数大于Node当前访问次数，需要在两个NodeQueue之间插入一个新的NodeQueue
                    unlinkNode(n);
                    new CacheNodeQueue(current, current.high, n, n, valueFrequency);
                }
            }
        }
    }
    public CacheNode unlinkNode(CacheNode n) {
        if(n.currentLevelQueue().isSingleQueue()){
            n.currentLevelQueue().head = null;
            n.currentLevelQueue().tail = null;
            n.prev = null;
            n.next = null;
            n.nq = null;
            return n;
        }
        if (n == n.currentLevelQueue().head){
            n.currentLevelQueue().head = n.next;
            n.next.prev = null;
            n.prev = null;
            n.next = null;
            n.nq = null;
            return n;
        }
        if (n == n.currentLevelQueue().tail){
            n.currentLevelQueue().tail = n.prev;
            n.prev.next = null;
            n.prev = null;
            n.next = null;
            n.nq = null;
            return n;
        }

        n.prev.next = n.next;
        n.next.prev = n.prev;
        n.prev = null;
        n.next = null;

        n.nq = null;
        return n;
    }

    public V get(K key) {
        CacheNode n = map.get(key);
        if (n == null) {
            return null;
        }
        moveNodeToNextLevelQueueHead(n);
        return (V) n.value;
    }

    public void put(K key, V value) {
        if (capacity == 0) {
            return;
        }

        CacheNode cn = map.get(key);
        //key已存在的情况下，更新value值，并将Node右移
        if (cn != null) {
            cn.value = value;
            moveNodeToNextLevelQueueHead(cn);
            return;
        }
        //cache已满的情况下，把外层链表尾部的内层链表的尾部Node移除
        if (map.size() == capacity) {
            CacheNodeQueue current = tail;
            if(current.isSingleQueue()){
                tail = current.high;
            }
            CacheNode node= unlinkNode(current.tail);
            map.remove(node.key);

        }
        //插入新的Node
        CacheNode n = new CacheNode(key, value);
        if (this.tail == null) {
            //tail为null说明此时cache中没有元素，直接把Node封装到NodeQueue里加入
            CacheNodeQueue nq = new CacheNodeQueue(null, null, n, n,0);
            this.tail = nq;
        } else if (this.tail.frequency == 0) {
            //外层链表尾部元素的访问次数是0，那么将Node加入到外层链表尾部元素的头部
            // 之所以加到头部，是因为要求“如果访问次数相同的元素有多个，则移除最久访问的那个”
            this.tail.addNodeToHead(n);
        } else {
            //外层链表尾部元素的访问次数不是0，那么实例化一个只包含此Node的NodeQueue，加入外层链表尾部
            CacheNodeQueue nq = new CacheNodeQueue( null,this.tail, n, n,0);
            this.tail = nq;
        }
        //最后把key和Node存入HashMap中
        map.put(key, n);
    }
}

class CacheNode<K, V> {
    K key;
    V value;
    /**
     * 下一元素
     */
    CacheNode next;
    /**
     * 前一元素
     */
    CacheNode prev;
    /**
     * 所属的外层链表元素
     */
    CacheNodeQueue nq;

    CacheNode(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public int getFrequency(){
       return nq.frequency;
    }

    public CacheNodeQueue currentLevelQueue(){
        return nq;
    }
}

class CacheNodeQueue {
    /**
     * 低级别元素
     */
    CacheNodeQueue low;
    /**
     * 高级别元素
     */
    CacheNodeQueue high;

    /**
     * 访问次数
     */
    Integer frequency = 0;

    /**
     * 尾部Node
     */
    CacheNode tail;
    /**
     * 头部Node
     */
    CacheNode head;

    public CacheNodeQueue(CacheNodeQueue low, CacheNodeQueue high, CacheNode tail, CacheNode head, Integer frequency ) {
        this.low = low;
        this.high = high;
        this.tail = tail;
        this.head = head;
        this.frequency = frequency;
        if(low !=null){
            low.high = this;
        }
        if(high !=null){
            high.low = this;
        }
        if(head !=null){
            head.nq = this;
        }
        if(tail !=null){
            tail.nq = this;
        }

    }

    /**
     * 判断是否是只包含单个节点
     * @return
     */
    public boolean isSingleQueue(){
        return head == tail;
    }

    public boolean isTailQueue(){
        return  low == null;
    }
    public boolean isHeadQueue(){
        return  high == null;
    }

    /**
     * 1->3
     *
     * 1->2->3
     * @param n
     */
    public void addNodeToHead(CacheNode n){
        n.next = head;
        head.prev = n;

        this.head = n;

        n.nq = this;
    }
}