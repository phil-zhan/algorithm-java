package org.study.coding.class37;

import java.util.concurrent.locks.LockSupport;

public class Test {

    @SuppressWarnings("all")
    public static class Printer {
        private final Object obj = new Object();
        private String[] str1 = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26"};
        private String[] str2 = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

        public void syncPrint() {
            new Thread(() -> syncPrint1(str1)).start();
            new Thread(() -> syncPrint2(str2)).start();
        }

        private void syncPrint1(String[] str) {
            synchronized (obj) {
                for (String s : str) {
                    try {
                        System.out.println(s);
                        obj.notify();
                        obj.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                obj.notify();
            }


        }

        private void syncPrint2(String[] str) {
            synchronized (obj) {
                for (String s : str) {
                    try {
                        System.out.println(s);
                        obj.notify();
                        obj.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                obj.notify();
            }
        }

        public void lockSupportPrint(){
            /*Thread thread1 = null;
            Thread thread2 = null;

            thread1 = new Thread(()->{
                for (String s:str1) {
                    System.out.println(s);
                    LockSupport.unpark(thread2);
                    LockSupport.park();
                }
            });
            thread2 = new Thread(()->{
                for (String s:str1) {
                    LockSupport.park();
                    System.out.println(s);
                    LockSupport.unpark(thread1);
                }
            });

            thread1.start();
            thread2.start();*/

        }

        private void lockSupportPrint1(String[] str1,Thread thread2){

        }

        private void lockSupportPrint2(String[] str2,Thread thread1){

        }
    }

    public static void main(String[] args) {
        Printer printer = new Printer();
        printer.syncPrint();


    }
}
