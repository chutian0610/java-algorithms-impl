package info.victorchu.algorithms.hash.consistent;

import java.util.Collection;

public interface ConsistentHash {

    /**
     * 返回 key 关联的节点
     *
     * @param key 目标key
     * @return
     */
    Node getNode(String key);

    /**
     * 向集群中添加新的节点
     *
     * @param nodes
     */
    void addNodes(Collection<? extends Node> nodes);


    /**
     * 从集群中删除节点
     *
     * @param nodes
     */
    void removeNodes(Collection<? extends Node> nodes);

    /**
     * 集群中节点数
     * @return
     */
    int nodeCount();
}
