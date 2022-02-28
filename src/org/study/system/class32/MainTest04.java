package org.study.system.class32;

import java.util.*;

/**
 * @author phil
 * @date 2021/10/6 10:36
 */
public class MainTest04 {

    public static class Node {
        public String end;

        public boolean endUse;

        public Node fail;


        // public Node[] childrens;

        public HashMap<Character,Node> next;

        public Node() {
            end = null;
            endUse = false;
            fail = null;

            // childrens = new Node[26];
            next = new HashMap<>();
        }
    }


    public static class ACAutomation {
        public Node root;


        public ACAutomation() {
            root = new Node();
        }

        public void insert(String s1) {
            char[] str = s1.toCharArray();
            Node cur = root;
            for (char c : str) {
                if (!cur.next.containsKey(c)) {
                    cur.next.put(c,new Node());
                }

                cur = cur.next.get(c);
            }

            cur.end = s1;
        }

        public void build() {
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);
            Node cur;
            Node curFail;
            while (!queue.isEmpty()) {
                cur = queue.poll();

                for (char c :cur.next.keySet()) {
                    cur.next.get(c).fail = root;
                    curFail = cur.fail;
                    while (curFail != null) {
                        if (curFail.next.get(c) != null) {
                            cur.next.get(c).fail = curFail.next.get(c);
                            break;
                        }
                        curFail = curFail.fail;
                    }

                    queue.add(cur.next.get(c));
                }
            }
        }


        public List<String> containWords(String content) {
            char[] str = content.toCharArray();
            List<String> ans = new ArrayList<>();

            Node cur = root;
            Node follow;

            for (char c : str) {

                while (cur.next.get(c) == null && cur != root) {
                    cur = cur.fail;
                }

                cur = cur.next.get(c) != null ? cur.next.get(c) : root;

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
            ac.insert("西餐");
            ac.insert("中午");
            // 设置fail指针
            ac.build();

            List<String> contains = ac.containWords("abcdheksabcdheksabcdheksabcdheks中午kskd" +
                    "jfafhasldkflskdjhwqaeruvabcdheksabcdheksabcdheksabcdheks中午ks" +
                    "kdjfafhasldkflskdjhwqaeruvabcdheksabcdheksabcdheksabcdheks中午ksk" +
                    "djfafhasldkflskdjhwqaeruvabcdheksabcdheksabcdheksabcdheks中午ks" +
                    "kdjfafhasldkflskdjhwqaeruvabcdheksabcdheksabcdheksabcdheks中午ksk" +
                    "djfafhasldkflskdjhwqaer西餐dheksabcdheksabcdheksabcdheks中午kskdjfa" +
                    "fhasldkflskdjhwqaeruvabcdheksabcdheksabcdheksabcdheks中午kskdjfa" +
                    "fhasldkflskdjhwqaeruvabcdheksabcdheksabcdheksabcdheks中午kskdjfaf" +
                    "hasldkflskdjhwqaeruvabcdheksabcdheksabcdheksabcdheks中午kskdjfafha" +
                    "sldkflskdjhwqaeruvabcdheksabcdheksabcdheksabcdheks中午kskdjfafhasldk" +
                    "flskdjhwqaeruvabcdheksabcdheksabcdheksabcdheks中午kskdjfafhasldkfls" +
                    "kdjhwqaeruvabcdheksabcdheksabcdheksabcdheks中午kskdjfafhasldkflskdjhwq" +
                    "aeruvabcdheksabcdheksabcdheksabcdheks中午kskdjfafhasldkflskdjhwqaeruvab" +
                    "cdheksabcdheksabcdheksabcdheks中午kskdjfafhasldkflskdjhwqaeru" +
                    "vabcdheksabcdheksabcdheksabcdheks中午kskdjfafhasldkflskdjh" +
                    "wqaeruvabcdheksabcdheksabcdheksabcdheks中午kskdjfafhasldk" +
                    "flskdjhwqaeruvabcdheksabcdheksabcdheksabcdheks中午kskdjfafhasld" +
                    "kflskdjhwqaeruvabcdheksabcdheksabcdheksabcdheks中午kskdjfafhasldk" +
                    "flskdjhwqaeruvabcdheksabcdheksabcdheksabcdheks中午kskdjfafhasl" +
                    "dkflskdjhwqaeruvabcdheksabcdheksabcdheksabcdheks中午kskdjfafhasl" +
                    "dkflskdjhwqaeruvabcdheksabcdheksabcdheksabcdheks中午kskdjfafhas" +
                    "ldkflskdjhwqaeruvabcdheksabcdheksabcdheksabcdheks中午kskdjfafhasl" +
                    "dkflskdjhwqaeruvabcdheksabcdheksabcdheksabcdheks中午kskdjfafhasldk" +
                    "flskdjhwqaeruvabcdheksabcdheksabcdheksabcdheks中午kskdjfafhasldkfl" +
                    "skdjhwqaeruvabcdheksabcdheksabcdheksabcdheks中午kskdjfafhasldkflskd" +
                    "jhwqaeruvabcdheksabcdheksabcdheksabcdheks中午kskdjfafhasldkflskdj" +
                    "hwqaeruvabcdheksabcdheksabcdheksabcdheks中午kskdjfafhasldkflskdj" +
                    "hwqaeruvabcdheksabcdheksabcdheksabcdheks中午kskdjfafhasldkflskdj" +
                    "hwqaeruvabcdheksabcdheksabcdheksabcdheks中午kskdjfafhasldkflskdj" +
                    "hwqaeruvabcdheksabcdheksabcdheksabcdheks中午kskdjfafhasldkflskdj" +
                    "hwqaeruvabcdheksabcdheksabcdheksabcdheks中午kskdjfafhasldkflskd" +
                    "jhwqaeruvabcdheksabcdheksabcdheksabcdheks中午kskdjfafhasldkflskd" +
                    "jhwqaeruvabcdheksabcdheksabcdheksabcdheks中午kskdjfafhasldkflskd" +
                    "jhwqaeruvabcdheksabcdheksabcdheksabcdheks中午kskdjfafhasldkflskd" +
                    "jhwqaeruvabcdheksabcdheksabcdheksabcdheks中午kskdjfafhasldkflskdj" +
                    "hwqaeruvabcdheksabcdheksabcdheksabcdheks中午kskdjfafhasldkflskdjh" +
                    "wqaeruvabcdheksabcdheksabcdheksabcdheks中午kskdjfafhasldkflskdjhw" +
                    "qaeruvabcdheksabcdheksabcdheksabcdheks中午kskdjfafhasldkflskdjhwq" +
                    "aeruvabcdheksabcdheksabcdheksabcdheks中午kskdjfafhasldkflskdjhwqae" +
                    "ruvabcdheksabcdheksabcdheksabcdheks中午kskdjfafhasldkflskdjhwqaeruv" +
                    "abcdheksabcdheksabcdheksabcdheks中午kskdjfafhasldkflskdjhwqaeruv");
            for (String word : contains) {
                System.out.println(word);
            }
        }
    }
}
