package org.study.system.class15;

import java.util.*;

/**
 * 并查集
 *
 * @author phil
 * @date 2021/7/8 17:16
 */
public class MainTest01 {

    private static class Node<T>{
        public T value;

        public Node(T value){
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Node<?> node = (Node<?>) o;
            return Objects.equals(value, node.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

    public static class UnionFind<V>{

        private final Map<V,Node<V>> valueAndNodeMap;

        private final Map<Node<V>,Node<V>> parentsMap;

        private final Map<Node<V>,Integer> sizeMap;

        public UnionFind(List<V> lists){
            valueAndNodeMap = new HashMap<>();
            parentsMap = new HashMap<>();
            sizeMap = new HashMap<>();

            for (V value:lists){
                Node<V> node = new Node<>(value);

                valueAndNodeMap.put(value,node);
                parentsMap.put(node,node);
                sizeMap.put(node,1);
            }
        }


        private Node<V> findFather(Node<V> curNode){
            Stack<Node<V>> stack = new Stack<>();
            while (parentsMap.get(curNode) != curNode){
                stack.push(curNode);
                curNode = parentsMap.get(curNode);

            }

            while (!stack.isEmpty()){
                parentsMap.put(stack.pop(),curNode);
            }

            return curNode;
        }


        public void union(V a1,V a2){
            Node<V> a1Head = findFather(valueAndNodeMap.get(a1));
            Node<V> a2Head = findFather(valueAndNodeMap.get(a2));

            if(a1Head != a2Head){
                int a1Size = sizeMap.get(a1Head);
                int a2Size = sizeMap.get(a2Head);

                Node<V> big = a1Size>a2Size?a1Head:a2Head;
                Node<V> small = a1Size>a2Size?a2Head:a1Head;

                parentsMap.put(small,big);
                sizeMap.put(big,a1Size+a2Size);
                sizeMap.remove(small);
            }
        }


        public boolean isSameSet(V a1,V a2){
            return findFather(valueAndNodeMap.get(a1)) == findFather(valueAndNodeMap.get(a2));
        }
    }
}
