package info.victorchu.algorithms.hash.consistent.ringhash;

import info.victorchu.algorithms.hash.consistent.Node;

/**
 * hash 环的虚拟节点.
 * 不可变类
 */
public class VirtualNode {
    /**
     * 虚拟节点对应的物理节点
     */
    private final Node physicalNode;
    /**
     * 虚拟节点对应的hash值
     */
    private final long hash;

    public VirtualNode(Node physicalNode, long hash) {
        this.physicalNode = physicalNode;
        this.hash = hash;
    }

    /**
     * 获取虚拟节点的真实节点
     * @return
     */
    public Node getPhysicalNode() {
        return physicalNode;
    }

    /**
     * 获取虚拟节点的hash值
     * @return
     */
    public long getHash() {
        return hash;
    }
}
