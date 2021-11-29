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
     * @param nodes 加入的节点集合
     */
    void addNodes(Collection<? extends Node> nodes);


    /**
     * 从集群中删除节点
     *
     * @param nodes 删除的节点集合
     */
    void removeNodes(Collection<? extends Node> nodes);

    /**
     * 集群中节点数
     * @return 节点个数
     */
    int nodeCount();
}
