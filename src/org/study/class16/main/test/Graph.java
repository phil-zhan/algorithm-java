package org.study.class16.main.test;

import java.util.*;

/**
 * 图的数据结构
 *
 * @author phil
 * @date 2021/7/13 14:00
 */
public class Graph {

    public Map<Integer,Node> nodeMap;

    public Set<Edge> edgeSet;

    public Graph(){
        nodeMap = new HashMap<>();
        edgeSet = new HashSet<>();
    }
    public static class Node{
        public int value;
        public int in;
        public int out;
        public List<Node> nodes;
        public List<Edge> edges;

        public Node(int value){
            this.value = value;
            this.in = 0;
            this.out = 0;
            this.nodes = new ArrayList<>();
            this.edges = new ArrayList<>();
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Node node = (Node) o;
            return value == node.value && in == node.in && out == node.out && Objects.equals(nodes, node.nodes) && Objects.equals(edges, node.edges);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, in, out, nodes, edges);
        }
    }

    public static class Edge{
        public int weight;
        public Node from;
        public Node to;

        public Edge(int weight,Node from,Node to){
            this.weight = weight;
            this.from = from;
            this.to = to;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Edge edge = (Edge) o;
            return weight == edge.weight && Objects.equals(from, edge.from) && Objects.equals(to, edge.to);
        }

        @Override
        public int hashCode() {
            return Objects.hash(weight, from, to);
        }
    }
}
