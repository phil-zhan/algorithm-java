package org.study.coding.class19;

import java.util.HashMap;
import java.util.Objects;

/**
 * 本题测试链接 : https://leetcode.com/problems/lfu-cache/
 * 提交时把类名和构造方法名改为 : LFUCache
 * <p>
 * 实现 LFUCache 类：
 * <p>
 * LFUCache(int capacity) - 用数据结构的容量capacity 初始化对象
 * int get(int key)- 如果键key 存在于缓存中，则获取键的值，否则返回 -1 。
 * void put(int key, int value)- 如果键key 已存在，则变更其值；如果键不存在，请插入键值对。当缓存达到其容量capacity 时，
 * 则应该在插入新项之前，移除最不经常使用的项。在此问题中，当存在平局（即两个或更多个键具有相同使用频率）时，应该去除 最近最久未使用 的键。
 * 为了确定最不常使用的键，可以为缓存中的每个键维护一个 使用计数器 。使用计数最小的键是最久未使用的键。
 * <p>
 * 当一个键首次插入到缓存中时，它的使用计数器被设置为 1 (由于 put 操作)。对缓存中的键执行 get 或 put 操作，使用计数器的值将会递增。
 * <p>
 * 函数 get 和 put 必须以 O(1) 的平均时间复杂度运行。
 * <p>
 * 题意：
 * 也是设计一种缓存算法
 * 缓存容器有容量大小。当达到这个阈值之后。根据各条数据使用的频率。淘汰使用频率低的数据
 * 提供两个方法，get和put方法，访问时间复杂度都要求O(1)
 * <p>
 * <p>
 * 解法：
 * 每条数据。记录原始key和value。同时也记录其被访问的次数和最近一次访问的时间点
 * 当数据量达到缓存的阈值。删除访问次数少的。
 * 如果有多条记录访问次数一样少。就优先删除最后访问时间较早的那个
 * <p>
 * 准备词频桶。在一个桶里面的数据。每个节点的访问次数是一样的。【节点是双向链表】【有头有尾】
 * 准备一个hash表。key就是缓存key。value是封装了缓存key、缓存value以及对应的访问次数。当然。还有上下指针【双向链表】
 * 再准备一个hash表。key是对应节点。value：是该节点属于哪个桶
 * 桶内是双向链表。桶间也是双向链表。【相当于是二维双向链表】
 * <p>
 * 更新的时候。通过key，在hash中找到对应的Node节点。将该节点从对应的桶中移除出来。
 * 【节点从桶中出来后，如果桶中没有节点了，直接将桶 kill 掉】【记得维护桶间的指针和桶内的指针】
 * 通过该Node节点，找到其属于那个桶。将该节点放入到下一个桶的尾部。
 * 如果下一个桶是空，或者下一个桶的词频不是当前的词频加1，直接新建一个桶【桶间是按照访问次数升序排列的（从头到尾）】
 * <p>
 * 当数据达到阈值之后，删除的是最头的那个桶的最前面的记录。也就是删掉头中头【桶间按照词频升序连接。桶内按照最后访问时间升序连接】
 * 实现起来不是很容易。尤其是指针的维护
 *
 * @since 2022-03-20 11:51:27
 */
public class Code02_LFUCache {

    public Code02_LFUCache(int K) {
        capacity = K;
        size = 0;
        records = new HashMap<>();
        heads = new HashMap<>();
        headList = null;
    }

    /**
     * 缓存的大小限制，即K
     *
     * @since 2022-03-20 11:52:22
     */
    private final int capacity;

    /**
     * 缓存目前有多少个节点
     *
     * @since 2022-03-20 11:52:30
     */
    private int size;
    /**
     * 表示key(Integer)由哪个节点(Node)代表
     *
     * @since 2022-03-20 11:52:37
     */
    private final HashMap<Integer, Node> records;

    /**
     * 表示节点(Node)在哪个桶(NodeList)里
     *
     * @since 2022-03-20 11:52:43
     */
    private final HashMap<Node, NodeList> heads;

    /**
     * 整个结构中位于最左的桶
     *
     * @since 2022-03-20 11:52:50
     */
    private NodeList headList;

    /**
     * 节点的数据结构
     *
     * @since 2022-03-20 11:53:03
     */
    public static class Node {
        public Integer key;
        public Integer value;

        // 这个节点发生get或者set的次数总和
        public Integer times;

        // 节点之间是双向链表所以有上一个节点
        public Node up;

        // 节点之间是双向链表所以有下一个节点
        public Node down;

        public Node(int k, int v, int t) {
            key = k;
            value = v;
            times = t;
        }
    }

    /**
     * 桶结构
     *
     * @since 2022-03-20 11:53:13
     */
    public static class NodeList {

        // 桶的头节点
        public Node head;

        // 桶的尾节点
        public Node tail;

        // 桶之间是双向链表所以有前一个桶
        public NodeList last;

        // 桶之间是双向链表所以有后一个桶
        public NodeList next;

        public NodeList(Node node) {
            head = node;
            tail = node;
        }

        // 把一个新的节点加入这个桶，新的节点都放在顶端变成新的头部
        public void addNodeFromHead(Node newHead) {
            newHead.down = head;
            head.up = newHead;
            head = newHead;
        }

        // 判断这个桶是不是空的
        public boolean isEmpty() {
            return head == null;
        }

        // 删除node节点并保证node的上下环境重新连接
        public void deleteNode(Node node) {
            if (head == tail) {
                head = null;
                tail = null;
            } else {
                if (node == head) {
                    head = node.down;
                    head.up = null;
                } else if (node == tail) {
                    tail = node.up;
                    tail.down = null;
                } else {
                    node.up.down = node.down;
                    node.down.up = node.up;
                }
            }
            node.up = null;
            node.down = null;
        }
    }


    /**
     * // removeNodeList：刚刚减少了一个节点的桶
     * // 这个函数的功能是，判断刚刚减少了一个节点的桶是不是已经空了。
     * // 1）如果不空，什么也不做
     * //
     * // 2)如果空了，removeNodeList还是整个缓存结构最左的桶(headList)。
     * // 删掉这个桶的同时也要让最左的桶变成removeNodeList的下一个。
     * //
     * // 3)如果空了，removeNodeList不是整个缓存结构最左的桶(headList)。
     * // 把这个桶删除，并保证上一个的桶和下一个桶之间还是双向链表的连接方式
     * //
     * // 函数的返回值表示刚刚减少了一个节点的桶是不是已经空了，空了返回true；不空返回false
     *
     * @since 2022-03-20 11:53:41
     */
    private boolean modifyHeadList(NodeList removeNodeList) {
        if (removeNodeList.isEmpty()) {
            if (headList == removeNodeList) {
                headList = removeNodeList.next;
                if (headList != null) {
                    headList.last = null;
                }
            } else {
                removeNodeList.last.next = removeNodeList.next;
                if (removeNodeList.next != null) {
                    removeNodeList.next.last = removeNodeList.last;
                }
            }
            return true;
        }
        return false;
    }


    /**
     * // 函数的功能
     * // node这个节点的次数+1了，这个节点原来在oldNodeList里。
     * // 把node从oldNodeList删掉，然后放到次数+1的桶中
     * // 整个过程既要保证桶之间仍然是双向链表，也要保证节点之间仍然是双向链表
     *
     * @since 2022-03-20 11:53:53
     */
    private void move(Node node, NodeList oldNodeList) {
        oldNodeList.deleteNode(node);
        // preList表示次数+1的桶的前一个桶是谁
        // 如果oldNodeList删掉node之后还有节点，oldNodeList就是次数+1的桶的前一个桶
        // 如果oldNodeList删掉node之后空了，oldNodeList是需要删除的，所以次数+1的桶的前一个桶，是oldNodeList的前一个
        NodeList preList = modifyHeadList(oldNodeList) ? oldNodeList.last : oldNodeList;
        // nextList表示次数+1的桶的后一个桶是谁
        NodeList nextList = oldNodeList.next;
        if (nextList == null) {
            NodeList newList = new NodeList(node);
            if (preList != null) {
                preList.next = newList;
            }
            newList.last = preList;
            if (headList == null) {
                headList = newList;
            }
            heads.put(node, newList);
        } else {
            if (nextList.head.times.equals(node.times)) {
                nextList.addNodeFromHead(node);
                heads.put(node, nextList);
            } else {
                NodeList newList = new NodeList(node);
                if (preList != null) {
                    preList.next = newList;
                }
                newList.last = preList;
                newList.next = nextList;
                nextList.last = newList;
                if (headList == nextList) {
                    headList = newList;
                }
                heads.put(node, newList);
            }
        }
    }

    public void put(int key, int value) {
        if (capacity == 0) {
            return;
        }
        if (records.containsKey(key)) {
            Node node = records.get(key);
            node.value = value;
            node.times++;
            NodeList curNodeList = heads.get(node);
            move(node, curNodeList);
        } else {
            if (size == capacity) {
                Node node = headList.tail;
                headList.deleteNode(node);
                modifyHeadList(headList);
                records.remove(node.key);
                heads.remove(node);
                size--;
            }
            Node node = new Node(key, value, 1);
            if (headList == null) {
                headList = new NodeList(node);
            } else {
                if (headList.head.times.equals(node.times)) {
                    headList.addNodeFromHead(node);
                } else {
                    NodeList newList = new NodeList(node);
                    newList.next = headList;
                    headList.last = newList;
                    headList = newList;
                }
            }
            records.put(key, node);
            heads.put(node, headList);
            size++;
        }
    }

    public int get(int key) {
        if (!records.containsKey(key)) {
            return -1;
        }
        Node node = records.get(key);
        node.times++;
        NodeList curNodeList = heads.get(node);
        move(node, curNodeList);
        return node.value;
    }

}