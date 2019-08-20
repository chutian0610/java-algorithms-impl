package info.victorchu.algorithms.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * @Author victor
 * @Email victorchu0610@outlook.com
 * @Data 2019/8/20
 * @Version 1.0
 * @Description 有向图拓扑排序
 */
public class TopologicalSortingGraph<V extends Comparable> {

    // 邻接表
    private List<Node<V>> nodes = new ArrayList<>();

    // 默认比较器,支持动态设置
    private Comparator<V> comparator = new Comparator<V>() {
        @Override
        public int compare(V o1, V o2) {
            return o1.compareTo(o2);
        }
    };

    public void setComparator(Comparator<V> comparator) {
        this.comparator = comparator;
    }

    public void addEdge(V from,V to){
        Node<V> node1 = findNode(from);
        if(node1 == null){
            node1 = new Node<>();
            node1.setInner(from);
            nodes.add(node1);
        }

        Node<V> node2 = findNode(to);
        if(node2 == null){
            node2 = new Node<>();
            node2.setInner(to);
            nodes.add(node2);

        }

        node1.addOutGoing(node2);
        node2.addInCome(node1);

    }

    private Node<V> findNode(V value){
        for (Node<V> item:nodes) {
            if(comparator.compare(item.getInner(),value)==0){
                return item;
            }
        }
        return null;
    }

    public List<V> sort(){
        List<V> results = new ArrayList<>();
        sort0(nodes,results);
        return results;
    }

    private static <V> void sort0(List<Node<V>> nodeList,List<V> results){
        if(nodeList.size()==0){
            return;
        }
        boolean containsEmptyInCome = false;
        Iterator<Node<V>> it = nodeList.iterator();
        while(it.hasNext()){
            Node<V> item = it.next();
            if(item.getInComingCount()==0){
                containsEmptyInCome= true;
                List<Node<V>> outGoings = item.getOutGoings();
                for (Node<V> outGoing:outGoings) {
                    outGoing.getInComes().remove(item);
                }
                it.remove();
                results.add(item.getInner());
            }
        }
        if(!containsEmptyInCome){
            throw new RuntimeException("DAG contains cyclic dependencies:"+ Arrays.toString(nodeList.toArray()));
        }
        sort0(nodeList,results);
    }

    static class Node<V> {
        public List<Node<V>> getInComes() {
            return inComes;
        }

        public List<Node<V>> inComes = new ArrayList<>();

        public List<Node<V>> getOutGoings() {
            return outGoings;
        }

        public List<Node<V>> outGoings = new ArrayList<>();
        private V inner;

        public V getInner() {
            return inner;
        }

        public void setInner(V inner) {
            this.inner = inner;
        }

        public void addOutGoing(Node<V> node){
            outGoings.add(node);
        }

        public void addInCome(Node<V> node){
            inComes.add(node);
        }

        public int getInComingCount(){
            return inComes.size();
        }
    }

    public static void main(String[] args) {
        TopologicalSortingGraph<Integer> topologicalSortingGraph = new TopologicalSortingGraph<>();
        topologicalSortingGraph.addEdge(1,2);
        topologicalSortingGraph.addEdge(1,4);
        topologicalSortingGraph.addEdge(2,4);
        topologicalSortingGraph.addEdge(2,3);
        topologicalSortingGraph.addEdge(4,3);
        topologicalSortingGraph.addEdge(4,5);
        topologicalSortingGraph.addEdge(3,5);
        System.out.println(Arrays.toString(topologicalSortingGraph.sort().toArray()));
    }
}
