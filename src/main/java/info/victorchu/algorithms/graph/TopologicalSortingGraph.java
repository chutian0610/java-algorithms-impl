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
        int count =0;
        // 找到输入为0的节点
        List<Integer> todo = new ArrayList<>();
        for (Node<V> item : nodeList) {
            if (item.getInComingCount() == 0) {
                todo.add(count);
            }
            count++;
        }
        todo.sort(Comparator.reverseOrder());
        // 倒序删除输入为0的节点
        for (int index:todo) {
//            System.out.println(nodeList.get(index).getInner());
            containsEmptyInCome= true;
            List<Node<V>> outGoings = nodeList.get(index).getOutGoings();
            for (Node<V> outGoing:outGoings) {
                outGoing.getInComes().remove(nodeList.get(index));
            }
            results.add(nodeList.get(index).getInner());
            nodeList.remove(index);
        }
        if(!containsEmptyInCome){
            throw new RuntimeException("DAG contains cyclic dependencies:"+ Arrays.toString(nodeList.toArray()));
        }
        // 递归
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
}
