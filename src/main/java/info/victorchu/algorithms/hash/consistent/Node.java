package info.victorchu.algorithms.hash.consistent;

/**
 * 集群中的机器节点
 */
public interface Node extends Comparable<Node> {
    /**
     * 必须唯一
     * @return 节点的名字
     */
    String name();

    /**
     * 机器节点比较方法
     * @param other 另一个节点
     * @return 比较结果
     */
    @Override
    default int compareTo(Node other) {
        return this.name().compareTo(other.name());
    }
}
