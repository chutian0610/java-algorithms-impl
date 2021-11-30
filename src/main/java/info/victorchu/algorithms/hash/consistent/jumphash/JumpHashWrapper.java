package info.victorchu.algorithms.hash.consistent.jumphash;

import info.victorchu.algorithms.hash.consistent.ConsistentHash;
import info.victorchu.algorithms.hash.consistent.HashFunction;
import info.victorchu.algorithms.hash.consistent.Node;
import info.victorchu.utils.PredicateExec;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static info.victorchu.utils.Predicates.collectionNotEmpty;
import static info.victorchu.utils.Predicates.integerGT0;
import static info.victorchu.utils.Predicates.stringNotBlank;

/**
 * Jump Hash 函数和 一致性Hash的适配器.
 */
public class JumpHashWrapper implements ConsistentHash {
    private final JumpHash inner;
    private final RouterMap routerMap;

    public JumpHashWrapper(Collection<? extends Node> nodes, HashFunction hashFunction) {
        int size = PredicateExec.check(collectionNotEmpty, nodes, "cluster must have at least one node").size();
        this.routerMap = new RouterMap(size);
        for (Node node : nodes) {
            PredicateExec.check(Objects::nonNull, node, "The node to add must not null");
            final int bucket = routerMap.size();
            routerMap.put(node, bucket);
        }
        this.inner = new JumpHash(size, hashFunction);
    }


    @Override
    public Node getNode(String key) {
        int bucket = inner.getBucket(
                PredicateExec.check(stringNotBlank, key, "The key to evaluate must not empty")
        );
        return routerMap.get(bucket);
    }

    @Override
    public void addNodes(Collection<? extends Node> nodes) {
        PredicateExec.check(collectionNotEmpty, nodes, "The nodes to add must not empty");
        for (Node node : nodes) {
            int bucket = inner.getSize();
            routerMap.put(node, bucket);
            inner.increase();
        }
    }

    @Override
    public void removeNodes(Collection<? extends Node> nodes) {
        PredicateExec.check(collectionNotEmpty, nodes, "The nodes to remove must not empty");
        PredicateExec.check(item -> inner.getSize() > item.size(), nodes,
                "Trying to remove more nodes than available");


        for (Node node : nodes) {

            final int bucket = routerMap.remove(node);
            PredicateExec.check(item -> item == inner.getSize() - 1, bucket,
                    "Only the last inserted node can be removed");
            inner.decrease();

        }
    }

    @Override
    public int nodeCount() {
        return inner.getSize();
    }

    /**
     * 这是bucket和机器节点的路由表.
     */
    public static class RouterMap {

        private Map<Node, Integer> nodeToBucket;
        private Map<Integer, Node> bucketToNode;
        /**
         * bucket的上限
         */
        private int capacity;

        public RouterMap(int size) {
            PredicateExec.check(integerGT0, size, "The initial size cannot be negative");
            this.nodeToBucket = new HashMap<>(size);
            this.bucketToNode = new HashMap<>(size);
            capacity = 0;
        }

        /**
         * 添加节点和bucket的映射关系
         *
         * @param node
         * @param bucket
         */
        public void put(Node node, int bucket) {

            PredicateExec.check(Objects::nonNull, node, "The node to add cannot be null");
            PredicateExec.check(
                    item -> item >= 0 && item <= capacity, bucket,
                    "The bucket must be in the interval [0," + capacity + "] but was " + bucket
            );

            PredicateExec.check(item -> !nodeToBucket.containsKey(item), node, "Duplicated node " + node);
            PredicateExec.check(item -> !bucketToNode.containsKey(item), bucket, "Duplicated bucket " + bucket);

            nodeToBucket.put(node, bucket);
            bucketToNode.put(bucket, node);

            if (bucket == capacity)
                ++capacity;
        }

        /**
         * 返回节点对应的bucket
         *
         * @param node 机器节点
         * @return bucket ID
         */
        public int get(Node node) {

            final Integer bucket = nodeToBucket.get(
                    PredicateExec.check(Objects::nonNull, node, "The node to search for cannot be null")
            );

            return PredicateExec.check(Objects::nonNull, bucket, "Node " + node + " is not mapped to any bucket");
        }

        /**
         * 返回bucket 对应的节点
         *
         * @param bucket
         * @return
         */
        public Node get(int bucket) {

            PredicateExec.check(
                    item -> item >= 0 && item < capacity,
                    bucket,
                    "The bucket must be in the interval [0," + capacity + ") but was " + bucket
            );

            final Node node = bucketToNode.get(bucket);
            return PredicateExec.check(Objects::nonNull, node, "Bucket " + bucket + " is not mapped to any node");

        }

        /**
         * 删除节点
         *
         * @param node
         * @return
         */
        public int remove(Node node) {
            final int bucket = get(node);
            nodeToBucket.remove(node);
            bucketToNode.remove(bucket);

            if (bucket == capacity - 1) {
                resizeCapacity();

            }
            return bucket;
        }

        public Node remove(int bucket) {

            final Node node = get(bucket);

            nodeToBucket.remove(node);
            bucketToNode.remove(bucket);

            if (bucket == capacity - 1)
                resizeCapacity();

            return node;

        }

        public int size() {
            return nodeToBucket.size();
        }

        public int capacity() {
            return capacity;
        }

        private void resizeCapacity() {
            do {
                --capacity;
            } while (capacity > 0 && !bucketToNode.containsKey(capacity - 1));

        }
    }
}
