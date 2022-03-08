package org.study.coding.class04;

import java.util.*;

public class MainTest08 {

    public static class Node {
        public int x;
        public boolean isAdd;
        public int height;

        public Node(int x, boolean isAdd, int height) {
            this.x = x;
            this.isAdd = isAdd;
            this.height = height;
        }
    }


    public List<List<Integer>> getSkyline(int[][] buildings) {
        if (buildings == null || buildings.length == 0 || buildings[0] == null || buildings[0].length == 0) {
            return null;
        }

        Node[] nodes = new Node[buildings.length << 1];

        for (int i = 0; i < buildings.length; i++) {
            nodes[i << 1] = new Node(buildings[i][0], true, buildings[i][2]);
            nodes[i << 1 | 1] = new Node(buildings[i][1], false, buildings[i][2]);
        }

        Arrays.sort(nodes, (Comparator.comparingInt(o -> o.x)));

        TreeMap<Integer, Integer> heightTimes = new TreeMap<>();

        TreeMap<Integer, Integer> locationMaxHeight = new TreeMap<>();

        for (Node node : nodes) {
            if (node.isAdd) {
                heightTimes.put(node.height, heightTimes.getOrDefault(node.height,0) + 1);
            } else {
                if (heightTimes.get(node.height) == 1) {
                    heightTimes.remove(node.height);
                } else {
                    heightTimes.put(node.height, heightTimes.get(node.height) - 1);
                }
            }

            if (heightTimes.isEmpty()) {
                locationMaxHeight.put(node.x, 0);
            } else {
                locationMaxHeight.put(node.x, heightTimes.lastKey());
            }
        }

        // collect answer
        List<List<Integer>> ans = new ArrayList<>();


        for (Integer key : locationMaxHeight.keySet()) {
            if (ans.isEmpty() || !Objects.equals(ans.get(ans.size() - 1).get(1), locationMaxHeight.get(key))) {
                ans.add(Arrays.asList(key, locationMaxHeight.get(key)));
            }
        }

        return ans;
    }
}
