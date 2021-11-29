package info.victorchu.algorithms.hash.consistent.ringhash;

import info.victorchu.algorithms.hash.consistent.HashFunction;
import info.victorchu.algorithms.hash.consistent.Node;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.BiConsumer;

/**
 * hash 环实现一致性hash
 */
public class RingHash {

    /**
     * 真实节点对应的虚拟节点默认值.
     */
    private static final int DEFAULT_VIRTUAL_NODE_COUNT = 1000;

    /**
     * 真实节点对应的虚拟节点数.
     */
    private final int vNodeCount;
    /**
     * 一致性Hash环
     */
    private final SortedMap<Long, VirtualNode> ring;
    /**
     * Hashing function .
     */
    private final HashFunction hashFunction;

    /**
     * hash 环构造器
     *
     * @param hashFunction 内部使用的hash 方法
     */
    public RingHash(HashFunction hashFunction) {
        this(DEFAULT_VIRTUAL_NODE_COUNT, hashFunction);

    }

    /**
     * hash 环构造器
     *
     * @param vNodeCount   真实节点对应的虚拟节点数.
     * @param hashFunction 内部使用的hash 方法
     */
    public RingHash(int vNodeCount, HashFunction hashFunction) {
        this.ring = new TreeMap<>();
        this.vNodeCount = vNodeCount;
        this.hashFunction = hashFunction;
    }

    /**
     * 基于key找到对应的机器节点.
     *
     * @param key
     * @return
     */
    public Node getNode(String key) {
        // 使用设置的hash方法获取key的hash值
        final long keyHash = hashFunction.hash(key);
        final SortedMap<Long, VirtualNode> tailMap = ring.tailMap(keyHash);
        // 在ring上查找
        final Long nodeHash
                = !tailMap.isEmpty()
                ? tailMap.firstKey()
                : ring.firstKey();
        return ring.get(nodeHash).getPhysicalNode();
    }

    /**
     * 向一致性hash中添加节点
     *
     * @param pNode
     */
    public Collection<VirtualNode> addNode(Node pNode) {
        final List<VirtualNode> vNodes = new LinkedList<>();
        for (int i = 0; i < vNodeCount; i++) {
            long hash = hashFunction.hash(pNode.name(), i);
            while (ring.containsKey(hash)) {
                hash = hashFunction.hash(hash, i);
            }
            // 创建虚拟节点
            final VirtualNode vNode = new VirtualNode(pNode, hash);
            ring.put(hash, vNode);
            vNodes.add(vNode);
        }
        return vNodes;
    }

    /**
     * 从一致性hash中删除节点
     *
     * @param toRemove
     */
    public void removeNodes(Collection<VirtualNode> toRemove) {
        for (VirtualNode vNode : toRemove) {
            ring.remove(vNode.getHash());
        }
    }

    /**
     * 虚拟节点数
     *
     * @return
     */
    public int virtualNodesCount() {
        return ring.size();
    }

    /**
     * 遍历hash环的节点
     *
     * @param consumer 对节点操作的action
     */
    public void forEach(BiConsumer<Long, VirtualNode> consumer) {
        ring.forEach(consumer);
    }

}
