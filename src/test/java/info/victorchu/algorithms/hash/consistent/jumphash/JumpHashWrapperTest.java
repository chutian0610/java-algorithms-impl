package info.victorchu.algorithms.hash.consistent.jumphash;

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
 * @Date:2021/11/30 4:44 下午
 * @author:victorchutian
 */
class JumpHashWrapperTest {

    List<String> servers = new ArrayList<String>();
    JumpHashWrapper jumpHashWrapper;
    @BeforeEach
    void init(){
        List<Node> nodes = new ArrayList<>(SimpleNode.create(5));
        jumpHashWrapper=new JumpHashWrapper(nodes,new MurmurHashFunction());
    }

    @Test
    void getNode() {
        System.out.println("########## get node ############");
        Node node1 = jumpHashWrapper.getNode("victorchu");
        System.out.println("key[victorchu] mapper to node:"+node1);
        Node node2 = jumpHashWrapper.getNode("victorchu");
        System.out.println("key[victorchu] mapper to node:"+node2);
        Assertions.assertTrue(node1.name().equals(node2.name()));
    }

    @Test
    void addNodes() {
        System.out.println("########## add node ############");
        Node node1 = jumpHashWrapper.getNode("victorchu");
        System.out.println("key[victorchu] mapper to node:"+node1);
        jumpHashWrapper.addNodes( new ArrayList<>(SimpleNode.create(6,10)));

        Node node2 = jumpHashWrapper.getNode("victorchu");
        System.out.println("key[victorchu] mapper to node:"+node2);
        Assertions.assertFalse(node1.name().equals(node2.name()));
    }

    @Test
    void removeNodes() {
        System.out.println("########## remove node ############");
        Node node1 = jumpHashWrapper.getNode("victorchu");
        System.out.println("key[victorchu] mapper to node:"+node1);
        jumpHashWrapper.removeNodes( new ArrayList<>(SimpleNode.createReverse(2,5)));

        Node node2 = jumpHashWrapper.getNode("victorchu");
        System.out.println("key[victorchu] mapper to node:"+node2);
        Assertions.assertFalse(node1.name().equals(node2.name()));
    }

    @Test
    void nodeCount() {
        Assertions.assertTrue(jumpHashWrapper.nodeCount()>0);
    }
}