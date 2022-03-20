package org.study.coding.class19;

import java.util.HashMap;

/**
 * 本题测试链接 : https://leetcode.com/problems/lru-cache/
 * 提交时把类名和构造方法名改成 : LRUCache
 * <p>
 * <p>
 * 请你设计并实现一个满足 LRU (最近最少使用) 缓存 约束的数据结构。
 * 实现 LRUCache 类：
 * LRUCache(int capacity) 以 正整数 作为容量capacity 初始化 LRU 缓存
 * int get(int key) 如果关键字 key 存在于缓存中，则返回关键字的值，否则返回 -1 。
 * void put(int key, int value)如果关键字key 已经存在，则变更其数据值value ；
 * 如果不存在，则向缓存中插入该组key-value 。如果插入操作导致关键字数量超过capacity ，则应该 逐出 最久未使用的关键字。
 * 函数 get 和 put 必须以 O(1) 的平均时间复杂度运行。
 * <p>
 * 示例：
 * <p>
 * 输入
 * ["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
 * [[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
 * 输出
 * [null, null, null, 1, null, -1, null, -1, 3, 4]
 * <p>
 * 解释
 * LRUCache lRUCache = new LRUCache(2);
 * lRUCache.put(1, 1); // 缓存是 {1=1}
 * lRUCache.put(2, 2); // 缓存是 {1=1, 2=2}
 * lRUCache.get(1);    // 返回 1
 * lRUCache.put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
 * lRUCache.get(2);    // 返回 -1 (未找到)
 * lRUCache.put(4, 4); // 该操作会使得关键字 1 作废，缓存是 {4=4, 3=3}
 * lRUCache.get(1);    // 返回 -1 (未找到)
 * lRUCache.get(3);    // 返回 3
 * lRUCache.get(4);    // 返回 4
 * <p>
 * 每个记录，都记下其操作的时间点【增改查都更新时间点】
 * 当缓存满了后，淘汰掉最长时间没操作的记录
 * 要求get、put三个方法，每次调用，都是 O(1)的时间复杂度。肯定就不能每次遍历去做。有序表也不行（时间复杂度是log）。
 * <p>
 * 解法：
 * 双向链表+Hash表实现
 * <p>
 * put的时候，在hash表中存有个
 * hash表：key：就是缓存的key。value：是将缓存key和缓存value打包后的节点Node
 * 实际数据存储，采用双向链表【即有前后指针】
 * 新节点来的时候，直接挂在这个双向链表的后面【可以用这个双向链表来判断出谁是较近操作的，谁是较晚操作的】
 * 【越靠近这个双向链表的尾巴，就是越近操作的。越靠近这个双向链表的头，就是越远操作的。】
 * 双向链表直接记一个头和一个尾。【方便操作】
 * 更新的时候先在hash表里面维护好【没有就新增，有就修改】，
 * 然后通过key，在hash里面拿到对应的节点。如果之前就存在的，将其从双向链表中分离出来，挂到后面去。如果是之前不存在的，直接挂在后面去
 * 查询的时候，也是一样。没有就返回null。
 * 有就先通过缓存key，在hash中，找到其对应的节点。
 * 然后将双向链表中对应的节点断连摘出来，然后挂到尾后面去
 * <p>
 * 当数据满了的时候。有来一条新记录。直接删除双线链表的头结点。【通过头节点，拿到节点内的key。将该key对应的记录在hash表中remove掉。】
 * 然后将节点加入hash表，同时挂在hash表的尾后面。
 *
 * @since 2022-03-19 10:38:19
 */
public class Code01_LRUCache {

    public Code01_LRUCache(int capacity) {
        cache = new MyCache<>(capacity);
    }

    private final MyCache<Integer, Integer> cache;

    public int get(int key) {
        Integer ans = cache.get(key);
        return ans == null ? -1 : ans;
    }

    public void put(int key, int value) {
        cache.set(key, value);
    }

    public static class Node<K, V> {
        public K key;
        public V value;
        public Node<K, V> last;
        public Node<K, V> next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * 手动实现双向链表
     *
     * @since 2022-03-19 11:40:22
     */
    public static class NodeDoubleLinkedList<K, V> {
        private Node<K, V> head;
        private Node<K, V> tail;

        public NodeDoubleLinkedList() {
            head = null;
            tail = null;
        }

        // 现在来了一个新的node，请挂到尾巴上去
        public void addNode(Node<K, V> newNode) {
            if (newNode == null) {
                return;
            }
            if (head == null) {
                head = newNode;
                tail = newNode;
            } else {
                tail.next = newNode;
                newNode.last = tail;
                tail = newNode;
            }
        }

        // node 入参，一定保证！node在双向链表里！
        // node原始的位置，左右重新连好，然后把node分离出来
        // 挂到整个链表的尾巴上
        public void moveNodeToTail(Node<K, V> node) {
            if (tail == node) {
                return;
            }
            if (head == node) {
                head = node.next;
                head.last = null;
            } else {
                node.last.next = node.next;
                node.next.last = node.last;
            }
            node.last = tail;
            node.next = null;
            tail.next = node;
            tail = node;
        }

        // 删除双线链表的头接地
        public Node<K, V> removeHead() {
            if (head == null) {
                return null;
            }
            Node<K, V> res = head;
            if (head == tail) {
                head = null;
                tail = null;
            } else {
                head = res.next;
                res.next = null;
                head.last = null;
            }
            return res;
        }

    }

    public static class MyCache<K, V> {
        private final HashMap<K, Node<K, V>> keyNodeMap;
        private final NodeDoubleLinkedList<K, V> nodeList;
        private final int capacity;

        public MyCache(int cap) {
            keyNodeMap = new HashMap<>();
            nodeList = new NodeDoubleLinkedList<>();
            capacity = cap;
        }

        public V get(K key) {
            if (keyNodeMap.containsKey(key)) {
                Node<K, V> res = keyNodeMap.get(key);
                nodeList.moveNodeToTail(res);
                return res.value;
            }
            return null;
        }

        // set(Key, Value)
        // 新增  更新value的操作
        public void set(K key, V value) {
            if (keyNodeMap.containsKey(key)) {
                Node<K, V> node = keyNodeMap.get(key);
                node.value = value;
                nodeList.moveNodeToTail(node);
            } else { // 新增！
                Node<K, V> newNode = new Node<K, V>(key, value);
                keyNodeMap.put(key, newNode);
                nodeList.addNode(newNode);
                if (keyNodeMap.size() == capacity + 1) {
                    removeMostUnusedCache();
                }
            }
        }

        private void removeMostUnusedCache() {
            Node<K, V> removeNode = nodeList.removeHead();
            keyNodeMap.remove(removeNode.key);
        }

    }

}
