package org.study;

import org.study.class05.Code003_QuickSort_2;
import org.study.class30.Code01_MorrisTraversal;

import java.util.*;

/**
 * @author phil
 * @date 2022/1/7 14:26
 */
public class MainTest {

    // ["Trie","insert","insert","insert","insert","insert","insert","search","search","search","search","search","search","search","search","search","startsWith","startsWith","startsWith","startsWith","startsWith","startsWith","startsWith","startsWith","startsWith"]
    //[[],["app"],["apple"],["beer"],["add"],["jam"],["rental"],["apps"],["app"],["ad"],["applepie"],["rest"],["jan"],["rent"],["beer"],["jam"],["apps"],["app"],["ad"],["applepie"],["rest"],["jan"],["rent"],["beer"],["jam"]]

    public static void main(String[] args) {
        MainTest mainTest = new MainTest();
        mainTest.insert("app");
        mainTest.insert("apple");
        mainTest.insert("beer");
        mainTest.insert("add");
        mainTest.insert("jam");
        mainTest.insert("rental");

        boolean apps = mainTest.search("apps");
        System.out.println(apps);
    }

    public Node root;

    public class Node{
        public Node[] nexts;
        public boolean isEnd;
        public Node(){
            this.nexts = new Node[26];
        }
    }

    public MainTest() {
        this.root = new Node();
    }

    public void insert(String word) {
        if(word == null || word.length() == 0){
            return;
        }

        char[] str = word.toCharArray();
        Node cur = root;
        for(int i=0;i<str.length;i++){
            if(cur.nexts[str[i]-'a'] == null){
                cur.nexts[str[i]-'a'] = new Node();
            }

            cur = cur.nexts[str[i]-'a'];
        }
        cur.isEnd = true;
    }

    public boolean search(String word) {
        if(word == null || word.length() == 0){
            return false;
        }
        char[] str = word.toCharArray();
        Node cur = root;
        for(int i=0;i<str.length;i++){
            if(cur.nexts[str[i]-'a'] == null){
                return false;
            }
            cur = cur.nexts[str[i]-'a'];
        }
        return cur.isEnd;
    }

    public boolean startsWith(String prefix) {
        if(prefix == null || prefix.length() == 0){
            return false;
        }

        char[] str = prefix.toCharArray();
        Node cur = root;
        for(int i=0;i<str.length;i++){
            if(cur.nexts[str[i]-'a'] == null){
                return false;
            }

            cur = cur.nexts[str[i]-'a'];
        }

        return true;
    }


}
