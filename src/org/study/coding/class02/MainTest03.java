package org.study.coding.class02;

import java.util.HashMap;

/**
 * @author phil
 * @since 2022-0303 20:52:03
 */
public class MainTest03 {

    public static class Node{
        public String value;
        public Node next;

        public Node(String info){
            this.value = info;
        }
    }
    public static class MessageBox{
        public HashMap<Integer,Node> headMap;
        public HashMap<Integer,Node> tailMap;
        public int wait;

        public MessageBox(){
            this.headMap = new HashMap<>();
            this.tailMap = new HashMap<>();
            wait = 1;
        }

        public void receive(int num,String info){
            if (num < 1){
                return;
            }

            Node node = new Node(info);
            headMap.put(num,node);
            tailMap.put(num,node);

            if (headMap.containsKey(num+1)){
                node.next = headMap.get(num+1);
                headMap.remove(num+1);
                tailMap.remove(num);
            }

            if (tailMap.containsKey(num-1)){
                tailMap.get(num-1).next = node;
                headMap.remove(num);
                tailMap.remove(num-1);
            }

            if (wait == num){
                print();
            }

        }

        public void print(){
            Node head = headMap.get(wait);
            headMap.remove(wait);
            while (head != null){
                System.out.print(head.value);
                head = head.next;
                wait++;
            }

            tailMap.remove(wait-1);
            System.out.println();
        }
    }

    public static void main(String[] args) {
        // MessageBox only receive 1~N
        MessageBox box = new MessageBox();
        // 1....
        System.out.println("这是2来到的时候");

        // - 2"
        box.receive(2,"B");
        System.out.println("这是1来到的时候");

        // 1 2 -> print, trigger is 1
        box.receive(1,"A");

        // - 4
        box.receive(4,"D");

        // - 4 5
        box.receive(5,"E");

        // - 4 5 - 7
        box.receive(7,"G");

        // - 4 5 - 7 8
        box.receive(8,"H");

        // - 4 5 6 7 8
        box.receive(6,"F");

        // 3 4 5 6 7 8 -> print, trigger is 3
        box.receive(3,"C");

        // 9 -> print, trigger is 9
        box.receive(9,"I");

        // 10 -> print, trigger is 10
        box.receive(10,"J");

        // - 12
        box.receive(12,"L");

        // - 12 13
        box.receive(13,"M");

        // 11 12 13 -> print, trigger is 11
        box.receive(11,"K");

    }
}
