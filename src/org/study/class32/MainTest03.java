package org.study.class32;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author phil
 * @date 2021/10/6 10:36
 */
public class MainTest03 {

    public static class Node {
        public String end;

        public boolean endUse;

        public Node fail;


        public Node[] childrens;

        public Node() {
            end = null;
            endUse = false;
            fail = null;

            childrens = new Node[26];
        }
    }


    public static class ACAutomation {
        public Node root;


        public ACAutomation() {
            root = new Node();
        }

        public void insert(String s1) {
            char[] str = s1.toCharArray();
            int index;
            Node cur = root;
            for (char c : str) {
                index = c - 'a';
                if (cur.childrens[index] == null) {
                    cur.childrens[index] = new Node();
                }

                cur = cur.childrens[index];
            }

            cur.end = s1;
        }

        public void build() {
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);
            Node cur = null;
            Node curFail = null;
            while (!queue.isEmpty()) {
                cur = queue.poll();
                for (int i = 0; i < 26; i++) {
                    if (cur.childrens[i] != null) {
                        cur.childrens[i].fail = root;
                        curFail = cur.fail;
                        while (curFail != null) {
                            if (curFail.childrens[i] != null) {
                                cur.childrens[i].fail = curFail.childrens[i];
                                break;
                            }
                            curFail = curFail.fail;
                        }

                        queue.add(cur.childrens[i]);
                    }
                }
            }
        }


        public List<String> containWords(String content) {
            char[] str = content.toCharArray();
            List<String> ans = new ArrayList<>();

            Node cur = root;
            Node follow = null;
            int index;

            for (char c : str) {

                index = c - 'a';
                while (cur.childrens[index] == null && cur != root) {
                    cur = cur.fail;
                }

                cur = cur.childrens[index] != null ? cur.childrens[index] : root;

                follow = cur;
                while (follow != root) {
                    if (follow.endUse) {
                        break;
                    }
                    if (follow.end != null) {
                        follow.endUse = true;
                        ans.add(follow.end);
                    }
                    follow = follow.fail;
                }

            }

            return ans;

        }

        public static void main(String[] args) {
            ACAutomation ac = new ACAutomation();
            ac.insert("dhe");
            ac.insert("he");
            ac.insert("abcdheks");
            ac.insert("sabcdhe");
            ac.insert("asldk");
            // 设置fail指针
            ac.build();

            List<String> contains = ac.containWords("abcdheksabcdheksabcdheksabcdheksabcdhekskdjfafhasldkflskdjhwqaeruv");
            for (String word : contains) {
                System.out.println(word);
            }
        }
    }
}
