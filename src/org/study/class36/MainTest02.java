package org.study.class36;

import java.util.ArrayList;

/**
 * @author phil
 * @date 2021/10/11 14:52
 */
public class MainTest02 {

    public static class SkipListNode<K extends Comparable<K>, V> {
        public K key;
        public V value;
        public ArrayList<SkipListNode<K, V>> nextNodes;

        public SkipListNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.nextNodes = new ArrayList<>();
        }

        public boolean isKeyLess(K otherKey) {
            return otherKey != null && (key == null || otherKey.compareTo(key) < 0);
        }

        public boolean isKeyEqual(K otherKey) {
            return (key == null && otherKey == null) || (key != null && otherKey != null && otherKey.compareTo(key) == 0);
        }
    }


    public static class SkipListMap<K extends Comparable<K>, V> {
        private static final double PROBABILITY = 0.5;

        private SkipListNode<K,V> head;
        private int size;
        private int maxLevel;


        public SkipListMap(){
            head = new SkipListNode<>(null,null);
            head.nextNodes.add(null);
            size = 0;
            maxLevel = 0;
        }

        private SkipListNode<K,V> mostRightLessNodeInTree(K key){
            if (null == key) {
                return null;
            }

            int level = maxLevel;
            SkipListNode<K,V> cur = head;
            while (level >= 0){

                cur = mostRightLessNodeInLevel(key,cur,level--);
            }

            return cur;
        }

        private SkipListNode<K,V> mostRightLessNodeInLevel(K key, SkipListNode<K,V> cur, int level) {

            SkipListNode<K,V> next = cur.nextNodes.get(level);
            while (next != null && next.isKeyLess(key)){
                cur = next;
                next = cur.nextNodes.get(level);
            }
            return cur;
        }
    }
}
