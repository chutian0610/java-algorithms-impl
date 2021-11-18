package info.victorchu.algorithms.hash;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Function;

/**
 * 基于TreeMap 实现的一致性hash
 * @param <S>
 */
public class TreeMapConsistentHash<S> {
    private TreeMap<Integer, S> nodes; // 虚拟节点
    private List<S> shards; // 真实机器节点
    private final int NODE_NUM = 100; // 每个机器节点关联的虚拟节点个数
    private Function<String,Integer> hashGenerator;

    public TreeMapConsistentHash(List<S> shards, Function<String,Integer> hash) {
        super();
        this.shards = shards;
        hashGenerator = hash;
        init();
    }

    private void init() { // 初始化一致性hash环
        nodes = new TreeMap<Integer, S>();
        for (int i = 0; i != shards.size(); ++i) { // 每个真实机器节点都需要关联虚拟节点
            final S shardInfo = shards.get(i);

            for (int n = 0; n < NODE_NUM; n++)
                // 一个真实机器节点关联NODE_NUM个虚拟节点
                nodes.put(hashGenerator.apply("SHARD-" + i + "-NODE-" + n), shardInfo);

        }
    }

    public S getShardInfo(String key) {
        SortedMap<Integer, S> tail = nodes.tailMap(hashGenerator.apply(key)); // 沿环的顺时针找到一个虚拟节点
        if (tail.size() == 0) {
            // 没有找到，返回头结点
            return nodes.get(nodes.firstKey());
        }
        return tail.get(tail.firstKey()); // 返回该虚拟节点对应的真实机器节点的信息
    }

    static class DefaultHashGenerator implements Function<String,Integer>{
        /**
         *  Hash算法
         */
        @Override
        public Integer apply(String key) {
            // 可以切换不同的hash实现
            // 最简单的方法就是
            return key.hashCode();
        }
    }
}
