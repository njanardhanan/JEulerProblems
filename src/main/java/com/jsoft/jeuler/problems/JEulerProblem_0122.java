package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JEulerProblem_0122 extends EulerSolver {

    public JEulerProblem_0122(int problemNumber) {
        super(problemNumber);
    }

    private static int MAX_VALUE = 200;

    class Node {
        int value;
        int level;
        Node parent;

        Node(int v, int l) {
            value = v;
            level = l;
            parent = null;
        }

        Node(int v, int l, Node p) {
            value = v;
            level = l;
            parent = p;
        }

        public int getValue() {
            return value;
        }

        public int getLevel() {
            return level;
        }

        public Node getParent() {
            return parent;
        }
    }

    @Override
    public String solve() {
        /**
         * http://wwwhomes.uni-bielefeld.de/achim/addition_chain.html
         */

        Set<Integer> fullSet = new HashSet<>();
        for(int i=2; i<=MAX_VALUE; i++) {
            fullSet.add(i);
        }

        Node root = new Node(1, 0);
        List<List<Node>> nodes = new ArrayList<>();
        Map<Integer, Node> nodeMap = new HashMap<>();

        List<Node> nodeList = new ArrayList<>();
        nodeList.add(root);
        nodes.add(nodeList);

        nodeMap.put(0, root);

        for(int level = 1;; level++) {
            //Get all the nodes from previous level and generate current level.

            List<Node> currentLevelNodes = nodes.get(level-1);
            List<Node> nextLevelNodes = new ArrayList<>();
            for(Node n : currentLevelNodes) {
                int newValue = n.getValue() * 2;
                if (isEligible(newValue, level, nodeMap)) {
                    Node newNode = new Node(newValue, level, n);
                    nodeMap.put(newValue, newNode);
                    nextLevelNodes.add(newNode);

                    //Remove from set
                    fullSet.remove(newValue);
                }

                Node parentNode = n.getParent();
                while(parentNode != null) {
                    newValue = parentNode.getValue() + n.getValue();
                    if (isEligible(newValue, level, nodeMap)) {
                        Node newNode = new Node(newValue, level, n);
                        nodeMap.put(newValue, newNode);
                        nextLevelNodes.add(newNode);

                        //Remove from set
                        fullSet.remove(newValue);
                    }

                    parentNode = parentNode.getParent();
                }
            }

            nodes.add(nextLevelNodes);
            if(fullSet.size() == 0) {
                break;
            }

        }

        int answer = 0;
        for (Map.Entry<Integer, Node> entry : nodeMap.entrySet()) {
            System.out.printf("%d - %d\n", entry.getKey(), entry.getValue().getLevel());
            answer += entry.getValue().getLevel();
        }

        return Integer.toString(answer);
    }

    private boolean isEligible(int v, int l, Map<Integer, Node> map) {
        if (v > MAX_VALUE) return false;
        if (!map.containsKey(v)) return true;

        Node n = map.get(v);
        if (l == n.getLevel()) return true;

        return false;
    }



    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=122";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}
