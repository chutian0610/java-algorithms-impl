package info.victorchu.algorithms.hash.consistent;

/**
 * 集群中的机器节点
 */
public interface Node extends Comparable<Node> {
    String name();

    /**
     * 机器节点比较方法
     * @param other
     * @return
     */
    @Override
    default int compareTo(Node other) {
        return this.name().compareTo(other.name());
    }
}
