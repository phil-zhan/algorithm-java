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
            ac.insert("??????");
            ac.insert("??????");
            // ??????fail??????
            ac.build();

            List<String> contains = ac.containWords("abcdheksabcdheksabcdheksabcdheks??????kskd" +
                    "jfafhasldkflskdjhwqaeruvabcdheksabcdheksabcdheksabcdheks??????ks" +
                    "kdjfafhasldkflskdjhwqaeruvabcdheksabcdheksabcdheksabcdheks??????ksk" +
                    "djfafhasldkflskdjhwqaeruvabcdheksabcdheksabcdheksabcdheks??????ks" +
                    "kdjfafhasldkflskdjhwqaeruvabcdheksabcdheksabcdheksabcdheks??????ksk" +
                    "djfafhasldkflskdjhwqaer??????dheksabcdheksabcdheksabcdheks??????kskdjfa" +
                    "fhasldkflskdjhwqaeruvabcdheksabcdheksabcdheksabcdheks??????kskdjfa" +
                    "fhasldkflskdjhwqaeruvabcdheksabcdheksabcdheksabcdheks??????kskdjfaf" +
                    "hasldkflskdjhwqaeruvabcdheksabcdheksabcdheksabcdheks??????kskdjfafha" +
                    "sldkflskdjhwqaeruvabcdheksabcdheksabcdheksabcdheks??????kskdjfafhasldk" +
                    "flskdjhwqaeruvabcdheksabcdheksabcdheksabcdheks??????kskdjfafhasldkfls" +
                    "kdjhwqaeruvabcdheksabcdheksabcdheksabcdheks??????kskdjfafhasldkflskdjhwq" +
                    "aeruvabcdheksabcdheksabcdheksabcdheks??????kskdjfafhasldkflskdjhwqaeruvab" +
                    "cdheksabcdheksabcdheksabcdheks??????kskdjfafhasldkflskdjhwqaeru" +
                    "vabcdheksabcdheksabcdheksabcdheks??????kskdjfafhasldkflskdjh" +
                    "wqaeruvabcdheksabcdheksabcdheksabcdheks??????kskdjfafhasldk" +
                    "flskdjhwqaeruvabcdheksabcdheksabcdheksabcdheks??????kskdjfafhasld" +
                    "kflskdjhwqaeruvabcdheksabcdheksabcdheksabcdheks??????kskdjfafhasldk" +
                    "flskdjhwqaeruvabcdheksabcdheksabcdheksabcdheks??????kskdjfafhasl" +
                    "dkflskdjhwqaeruvabcdheksabcdheksabcdheksabcdheks??????kskdjfafhasl" +
                    "dkflskdjhwqaeruvabcdheksabcdheksabcdheksabcdheks??????kskdjfafhas" +
                    "ldkflskdjhwqaeruvabcdheksabcdheksabcdheksabcdheks??????kskdjfafhasl" +
                    "dkflskdjhwqaeruvabcdheksabcdheksabcdheksabcdheks??????kskdjfafhasldk" +
                    "flskdjhwqaeruvabcdheksabcdheksabcdheksabcdheks??????kskdjfafhasldkfl" +
                    "skdjhwqaeruvabcdheksabcdheksabcdheksabcdheks??????kskdjfafhasldkflskd" +
                    "jhwqaeruvabcdheksabcdheksabcdheksabcdheks??????kskdjfafhasldkflskdj" +
                    "hwqaeruvabcdheksabcdheksabcdheksabcdheks??????kskdjfafhasldkflskdj" +
                    "hwqaeruvabcdheksabcdheksabcdheksabcdheks??????kskdjfafhasldkflskdj" +
                    "hwqaeruvabcdheksabcdheksabcdheksabcdheks??????kskdjfafhasldkflskdj" +
                    "hwqaeruvabcdheksabcdheksabcdheksabcdheks??????kskdjfafhasldkflskdj" +
                    "hwqaeruvabcdheksabcdheksabcdheksabcdheks??????kskdjfafhasldkflskd" +
                    "jhwqaeruvabcdheksabcdheksabcdheksabcdheks??????kskdjfafhasldkflskd" +
                    "jhwqaeruvabcdheksabcdheksabcdheksabcdheks??????kskdjfafhasldkflskd" +
                    "jhwqaeruvabcdheksabcdheksabcdheksabcdheks??????kskdjfafhasldkflskd" +
                    "jhwqaeruvabcdheksabcdheksabcdheksabcdheks??????kskdjfafhasldkflskdj" +
                    "hwqaeruvabcdheksabcdheksabcdheksabcdheks??????kskdjfafhasldkflskdjh" +
                    "wqaeruvabcdheksabcdheksabcdheksabcdheks??????kskdjfafhasldkflskdjhw" +
                    "qaeruvabcdheksabcdheksabcdheksabcdheks??????kskdjfafhasldkflskdjhwq" +
                    "aeruvabcdheksabcdheksabcdheksabcdheks??????kskdjfafhasldkflskdjhwqae" +
                    "ruvabcdheksabcdheksabcdheksabcdheks??????kskdjfafhasldkflskdjhwqaeruv" +
                    "abcdheksabcdheksabcdheksabcdheks??????kskdjfafhasldkflskdjhwqaeruv");
            for (String word : contains) {
                System.out.println(word);
            }
        }
    }
}
