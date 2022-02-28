package org.study.system.class36;

/**
 * @author phil
 * @date 2021/10/11 14:52
 */
public class MainTest01 {

    public static class SizeBalanceNode<K extends Comparable<K>, V> {
        public K key;
        public V value;
        public int size;

        public SizeBalanceNode<K, V> left;
        public SizeBalanceNode<K, V> right;

        public SizeBalanceNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.size = 1;
        }
    }

    public static class SizeBalancedTreeMap<K extends Comparable<K>, V> {
        public SizeBalanceNode<K, V> root;

        public SizeBalanceNode<K, V> leftRotate(SizeBalanceNode<K, V> cur) {
            SizeBalanceNode<K, V> right = cur.right;
            cur.right = right.left;
            right.left = cur;

            // 更新size
            right.size = cur.size;
            cur.size = (cur.left != null ? cur.left.size : 0) + (cur.right != null ? cur.right.size : 0) + 1;

            return right;
        }

        public SizeBalanceNode<K, V> rightRotate(SizeBalanceNode<K, V> cur) {
            SizeBalanceNode<K, V> left = cur.left;
            cur.left = left.right;
            left.right = cur;

            // 调节size
            left.size = cur.size;
            cur.size = (cur.left != null ? cur.left.size : 0) + (cur.right != null ? cur.right.size : 0) + 1;

            return left;
        }

        public SizeBalanceNode<K, V> maintain(SizeBalanceNode<K, V> cur) {
            if (null == cur) {
                return null;
            }

            int leftSize = cur.left != null ? cur.left.size : 0;
            int leftLeftSize = cur.left != null && cur.left.left != null ? cur.left.left.size : 0;
            int leftRightSize = cur.left != null && cur.left.right != null ? cur.left.right.size : 0;

            int rightSize = cur.right != null ? cur.right.size : 0;
            int rightLeftSize = cur.right != null && cur.right.left != null ? cur.right.left.size : 0;
            int rightRightSize = cur.right != null && cur.right.right != null ? cur.right.right.size : 0;

            if (leftLeftSize > rightSize) {
                // LL
                cur = rightRotate(cur);
                cur.right = maintain(cur.right);
                cur = maintain(cur);
            } else if (leftRightSize > rightSize) {
                // LR
                cur.left = leftRotate(cur.left);
                cur = rightRotate(cur);
                cur.left = maintain(cur.left);
                cur.right = maintain(cur.right);
                cur = maintain(cur);
            } else if (rightLeftSize > leftSize) {
                // RL
                cur.right = rightRotate(cur.right);
                cur = leftRotate(cur);
                cur.left = maintain(cur.left);
                cur.right = maintain(cur.right);
                cur = maintain(cur);
            } else if (rightRightSize > leftSize) {
                // RR
                cur = leftRotate(cur);
                cur.left = maintain(cur.left);
                cur = maintain(cur);
            }

            return cur;
        }

        // 现在，以cur为头的树上，新增，加(key, value)这样的记录
        // 加完之后，会对cur做检查，该调整调整
        // 返回，调整完之后，整棵树的新头部
        private SizeBalanceNode<K, V> add(SizeBalanceNode<K, V> cur, K key, V value) {
            if (cur == null) {
                return new SizeBalanceNode<>(key, value);
            } else {
                cur.size++;
                if (key.compareTo(cur.key) < 0) {
                    cur.left = add(cur.left, key, value);
                } else {
                    cur.right = add(cur.right, key, value);
                }
                return maintain(cur);
            }
        }

        // 在cur这棵树上，删掉key所代表的节点
        // 返回cur这棵树的新头部
        private SizeBalanceNode<K, V> delete(SizeBalanceNode<K, V> cur, K key) {
            cur.size--;
            if (key.compareTo(cur.key) > 0) {
                cur.right = delete(cur.right, key);
            } else if (key.compareTo(cur.key) < 0) {
                cur.left = delete(cur.left, key);
            } else { // 当前要删掉cur
                if (cur.left == null && cur.right == null) {
                    // free cur memory -> C++
                    cur = null;
                } else if (cur.left == null) {
                    // free cur memory -> C++
                    cur = cur.right;
                } else if (cur.right == null) {
                    // free cur memory -> C++
                    cur = cur.left;
                } else { // 有左有右
                    SizeBalanceNode<K, V> pre = null;
                    SizeBalanceNode<K, V> des = cur.right;
                    des.size--;
                    while (des.left != null) {
                        pre = des;
                        des = des.left;
                        des.size--;
                    }
                    if (pre != null) {
                        pre.left = des.right;
                        des.right = cur.right;
                    }
                    des.left = cur.left;
                    des.size = des.left.size + (des.right == null ? 0 : des.right.size) + 1;
                    // free cur memory -> C++
                    cur = des;
                }
            }
            // cur = maintain(cur);
            return cur;
        }
    }
}
