package info.victorchu.algorithms.hash.consistent.ringhash;

import info.victorchu.algorithms.hash.consistent.Node;
import info.victorchu.algorithms.hash.consistent.SimpleNode;
import info.victorchu.algorithms.hash.consistent.MurmurHashFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @Date:2021/11/30 3:47 下午
 * @author:victorchutian
 */
class RingHashWrapperTest {

    List<String> servers = new ArrayList<String>();
    RingHashWrapper ringHashWrapper;
    @BeforeEach
    void init(){
        List<Node> nodes = new ArrayList<>(SimpleNode.create(5));
        ringHashWrapper=new RingHashWrapper(nodes,new MurmurHashFunction());
    }

    @Test
    void getNode() {
        System.out.println("########## get node ############");
        Node node1 = ringHashWrapper.getNode("victorchu");
        System.out.println("key[victorchu] mapper to node:"+node1);
        Node node2 = ringHashWrapper.getNode("victorchu");
        System.out.println("key[victorchu] mapper to node:"+node2);
        Assertions.assertTrue(node1.name().equals(node2.name()));
    }

    @Test
    void addNodes() {
        System.out.println("########## add node ############");

        Node node1 = ringHashWrapper.getNode("victorchu");
        System.out.println("key[victorchu] mapper to node:"+node1);
        ringHashWrapper.addNodes( new ArrayList<>(SimpleNode.create(6,10)));
        Node node2 = ringHashWrapper.getNode("victorchu");
        System.out.println("key[victorchu] mapper to node:"+node2);
        Assertions.assertFalse(node1.name().equals(node2.name()));
    }

    @Test
    void removeNodes() {
        System.out.println("########## remove node ############");
        Node node1 = ringHashWrapper.getNode("victorchu");
        System.out.println("key[victorchu] mapper to node:"+node1);
        ringHashWrapper.removeNodes( new ArrayList<>(SimpleNode.create(1,2)));
        Node node2 = ringHashWrapper.getNode("victorchu");
        System.out.println("key[victorchu] mapper to node:"+node2);
        Assertions.assertFalse(node1.name().equals(node2.name()));
    }

    @Test
    void nodeCount() {
        Assertions.assertTrue(ringHashWrapper.nodeCount()>0);
    }
}