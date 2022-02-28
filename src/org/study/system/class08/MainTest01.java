package org.study.system.class08;

/**
 * @author phil
 * @date 2022/1/21 9:17
 */
public class MainTest01 {

    public static class Node {
        public int pass;
        public int end;
        public Node[] edges;

        public Node() {
            pass = 0;
            end = 0;
            edges = new Node[26];
        }

        public Node(int pass, int end, Node[] edges) {
            this.pass = pass;
            this.end = end;
            this.edges = edges;
        }
    }

    public static class TrieTree {
        public Node root;

        public TrieTree() {
            root = new Node();
        }

        public void build(String word) {

            if (null == word) {
                return;
            }
            char[] chars = word.toCharArray();

            Node node = root;
            node.pass++;
            int index;
            for (char aChar : chars) {
                index = aChar - 'a';
                if (node.edges[index] == null) {
                    node.edges[index] = new Node();
                }
                node = node.edges[index];
                node.pass++;
            }
            node.end++;
        }

        public int search(String word) {
            if (null == word) {
                return 0;
            }

            Node node = root;
            char[] chars = word.toCharArray();
            int index = 0;
            for (char aChar : chars) {
                index = aChar - 'a';
                if (node.edges[index] == null) {
                    return 0;
                }
                node = node.edges[index];
            }

            return node.end;
        }

        public void delete(String word) {
            if (null == word) {
                return;
            }

            if (search(word) > 0) {
                char[] chars = word.toCharArray();
                int index = 0;
                Node node = root;
                for (char aChar : chars) {
                    index = aChar - 'a';
                    if (--node.edges[index].pass == 0) {
                        node.edges[index] = null;
                        return;
                    }
                    node = node.edges[index];
                }
                node.end--;
            }
        }

        public int prefixNumber(String word) {
            if (null == word) {
                return 0;
            }
            Node node = root;
            int index = 0;
            char[] chars = word.toCharArray();
            for (char aChar : chars) {
                index = aChar - 'a';
                if (node.edges[index] == null) {
                    return 0;
                }
                node = node.edges[index];
            }
            return node.pass;
        }
    }

    public static void main(String[] args) {
        String word1 = "abccaaasss";
        String word2 = "abcdfghssss";
        String word3 = "cfddfghaafs";

        TrieTree trieTree = new TrieTree();
        trieTree.build(word1);
        trieTree.build(word2);
        trieTree.build(word3);

        System.out.println(trieTree.prefixNumber("abcd"));
        System.out.println(trieTree.search("cfddfghaafs"));
    }

}
