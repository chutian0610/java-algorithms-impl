package info.victorchu.algorithms.hash.consistent;

import info.victorchu.utils.PredicateExec;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static info.victorchu.utils.Predicates.*;

/**
 * Node 的简单实现。
 */
public class SimpleNode implements Node {

    /**
     * Unique name of the node.
     */
    private final String name;

    private SimpleNode(String name) {
        this.name = PredicateExec.check(STRING_NOT_BLANK, name, "The name cannot be empty or blank");
    }

    /*  FACTORY METHODS  */

    public static SimpleNode of(String name) {

        return new SimpleNode(name);
    }

    public static SimpleNode of(Integer index) {

        final String name = "node_" + PredicateExec.check(INTEGER_GTE_0, index, "The node index cannot be negative");
        return new SimpleNode(name);

    }

    public static List<SimpleNode> create(Integer size) {

        PredicateExec.check(INTEGER_GT_0, size, "The requested number of nodes must be strict positive");
        return IntStream
                .range(0, size)
                .mapToObj(SimpleNode::of)
                .collect(Collectors.toList());

    }

    public static List<SimpleNode> create(Integer from,Integer to) {
        PredicateExec.check(INTEGER_GT_0, from, "The requested begin number of nodes must be strict positive");
        PredicateExec.check(INTEGER_GT_0, to-from, "The requested number of nodes must be strict positive");
        return IntStream
                .range(from, to)
                .mapToObj(SimpleNode::of)
                .collect(Collectors.toList());
    }

    public static List<SimpleNode> createReverse(Integer from,Integer to) {
        List<SimpleNode> list= create(from,to);
        Collections.reverse(list);
        return list;
    }


    @Override
    public String name() {
        return name;
    }

    @Override
    public boolean equals( Object other )
    {
        if(other instanceof SimpleNode){
            if(this.name.equals(((SimpleNode) other).name)){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }
    @Override
    public int hashCode()
    {

        return  name.hashCode();

    }

    @Override
    public String toString()
    {

        return name;

    }
}
