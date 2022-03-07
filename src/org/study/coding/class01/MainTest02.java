package org.study.coding.class01;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class MainTest02 {

    public int getFileNumber(String path){
        File root = new File(path);
        if (!root.isDirectory() && !root.isFile()) {
            return 0;
        }
        if (root.isFile()) {
            return 1;
        }
        Queue<File> queue = new LinkedList<>();
        queue.add(root);
        int files = 0;
        while (!queue.isEmpty()) {
            File folder = queue.poll();
            for (File next : folder.listFiles()) {
                if (next.isFile()) {
                    files++;
                }
                if (next.isDirectory()) {
                    queue.add(next);
                }
            }
        }
        return files;
    }

    public static void main(String[] args) {
        System.out.println(new MainTest02().getFileNumber("E:\\work_space\\idea_work_space\\algorithm-java"));
    }
}
