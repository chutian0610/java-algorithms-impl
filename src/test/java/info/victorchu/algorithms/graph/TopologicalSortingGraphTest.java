package info.victorchu.algorithms.graph;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TopologicalSortingGraphTest {

    /*
     * ┌───┐     ┌───┐
     * │ 1 ├─────► 2 ├──────────┐
     * └─┬─┘     └─┬─┘          │
     *   │         │          ┌─▼─┐
     *   │         │          │ 3 ├─┐
     *   │         │          └─▲─┘ │
     *   │       ┌─▼─┐          │   │
     *   └───────► 4 ├──────────┘   │
     *           └─┬─┘              │
     *             │             ┌──▼┐
     *             └─────────────► 5 │
     *                           └───┘
     */
    @Test
    public void sort(){
        TopologicalSortingGraph<Integer> topologicalSortingGraph = new TopologicalSortingGraph<>();
        topologicalSortingGraph.addEdge(1,2);
        topologicalSortingGraph.addEdge(1,4);
        topologicalSortingGraph.addEdge(2,4);
        topologicalSortingGraph.addEdge(2,3);
        topologicalSortingGraph.addEdge(4,3);
        topologicalSortingGraph.addEdge(4,5);
        topologicalSortingGraph.addEdge(3,5);
        List<Integer> result = new ArrayList<Integer>(){
            {
                this.add(1);
                this.add(2);
                this.add(4);
                this.add(3);
                this.add(5);
            }
        };
        Assertions.assertTrue(topologicalSortingGraph.sort().equals(result));

    }
}