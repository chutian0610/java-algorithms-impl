package info.victorchu.algorithms.hash.consistent.ringhash;

import info.victorchu.algorithms.hash.consistent.ConsistentHash;
import info.victorchu.algorithms.hash.consistent.HashFunction;
import info.victorchu.algorithms.hash.consistent.Node;
import info.victorchu.utils.Pair;
import info.victorchu.utils.PredicateExec;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static info.victorchu.utils.Predicates.stringNotBlank;

/**
 * hash环 包装类
 */
public class RingHashWrapper implements ConsistentHash {

    private final RingHash inner;

    /**
     * 用于存储
     * 1. 真实节点和虚拟节点的映射关系
     * 2. 用于检查真实节点Name的唯一性
     */
    private final Map<String, Pair<Node, Collection<VirtualNode>>> nodeMap;

    /**
     * RingHashWrapper 构造器
     *
     * @param nodes        机器节点集合
     * @param hashFunction hash方法
     */
    public RingHashWrapper(Collection<? extends Node> nodes, HashFunction hashFunction) {
        super();
        this.nodeMap = new HashMap<>();
        this.inner = new RingHash(hashFunction);
        this.addNodes(nodes);
    }

    /**
     * RingHashWrapper 构造器
     *
     * @param nodes        机器节点集合
     * @param vNodeCount   机器对应虚拟节点数
     * @param hashFunction hash方法
     */
    public RingHashWrapper(Collection<? extends Node> nodes, int vNodeCount, HashFunction hashFunction) {
        super();
        this.nodeMap = new HashMap<>();
        this.inner = new RingHash(vNodeCount, hashFunction);
        this.addNodes(nodes);
    }

    @Override
    public Node getNode(String key) {
        // 参数检查,计算的key不能为空
        PredicateExec.check(stringNotBlank, key, "The key to evaluate must not empty");
        // 调用内部RingHash
        return inner.getNode(key);
    }

    @Override
    public void addNodes(Collection<? extends Node> nodes) {
        PredicateExec.check(nodes1 -> nodes1 != null && nodes1.size() > 0, nodes,
                "The resources to add must not empty");
        for (Node node : nodes) {
            PredicateExec.check(node1 -> node1 != null, node, "The resource to add cannot be null");
        }
    }

    @Override
    public void removeNodes(Collection<? extends Node> nodes) {

    }

    @Override
    public int nodeCount() {
        return 0;
    }
}
