    package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

    public class JEulerProblem_0082 extends EulerSolver {

        public class Graph {

            private Map<String, Node> nodes = new HashMap();

            public void addNode(Node node) {
                nodes.put(node.name, node);
            }

            public Node getNode(String name) {
                if(nodes.containsKey(name)) {
                    return nodes.get(name);

                }
                return null;
            }

            public Map<String, Node> getAllNodes() {
                return nodes;
            }
        }

        public class Node {
            private String name;
            private int value;
            private int distance =Integer.MAX_VALUE;
            private Node leftNode;
            private Node rightNode;
            private Node topNode;
            private Node bottomNode;

            private List<Node> shortestPath = new LinkedList<>();

            Map<Node, Integer> adjacentNodes = new HashMap<>();

            private String getKey(int i, int j) {
                StringBuilder sb = new StringBuilder();
                sb.append(i);
                sb.append('_');
                sb.append(j);
                return sb.toString();
            }

            public Node(int i, int j) {
                this.name = getKey(i, j);
            }


            public Node(int i, int j, int v) {
                this.name = getKey(i, j);
                value = v;
                leftNode = null;
                rightNode = null;
                topNode = null;
                bottomNode = null;
            }

            public void setRelation(Node l, Node r, Node t, Node b) {
                leftNode = l;
                rightNode = r;
                topNode = t;
                bottomNode = b;

                //Only up, down and right movement is allowed.
                if(topNode != null) {
                    adjacentNodes.put(topNode, value + topNode.value);
                }
                if(bottomNode != null) {
                    adjacentNodes.put(bottomNode, value + bottomNode.value);
                }
                if(rightNode != null) {
                    adjacentNodes.put(rightNode, value + rightNode.value);
                }
            }

            private void addDestination(Node destination, int distance) {
                adjacentNodes.put(destination, distance);
            }

            public Map<Node, Integer> getAdjacentNodes() {
                return adjacentNodes;
            }

            public int getDistance() {
                return distance;
            }

            public void setDistance(int distance) {
                this.distance = distance;
            }

            public List<Node> getShortestPath() {
                return shortestPath;
            }

            public void setShortestPath(List<Node> shortestPath) {
                this.shortestPath = shortestPath;
            }

            public String getName() {
                return name;
            }
        }

        public JEulerProblem_0082(int problemNumber) {
            super(problemNumber);
        }

        @Override
        public String solve() {

//            int LIMIT = 5;
//            int[][] matrix = {{131, 673, 234, 103, 18},
//                    {201, 96, 342, 965, 150},
//                    {630, 803, 746, 422, 111},
//                    {537, 699, 497, 121, 956},
//                    {805, 732, 524, 37, 331}};

            int LIMIT = 80;
            int[][] matrix = new int[0][];

            try {
                matrix = loadData("data/problem82/p082_matrix.txt", LIMIT);
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }

            int currentShortestDistance = Integer.MAX_VALUE;
            String currentFromNode = "@", currentToNode = "@";

            for (int sourceName = 0; sourceName < LIMIT; sourceName++) {

                Graph graph = new Graph();
                Graph sourceGraph = new Graph();
                Graph targetGraph = new Graph();


                //Create graph with relations
                for (int i = 0; i < LIMIT; i++) {
                    for (int j = 0; j < LIMIT; j++) {
                        Node n = new Node(i, j, matrix[i][j]);
                        graph.addNode(n);
                        if (j == 0) {
                            sourceGraph.addNode(n);
                        } else if (j == LIMIT - 1) {
                            targetGraph.addNode(n);
                        }
                    }
                }

                //Create relation
                for (int i = 0; i < LIMIT; i++) {
                    for (int j = 0; j < LIMIT; j++) {

                        Node currentNode = graph.getNode(getKey(i, j));
                        Node leftNode = graph.getNode(getKey(i, j - 1));
                        Node rightNode = graph.getNode(getKey(i, j + 1));
                        Node topNode = graph.getNode(getKey(i - 1, j));
                        Node bottomNode = graph.getNode(getKey(i + 1, j));

                        currentNode.setRelation(leftNode, rightNode, topNode, bottomNode);
                    }
                }



                Node startNode = sourceGraph.getNode(getKey(sourceName, 0));
                calculateShortestPathFromSource(graph, startNode);

                for (Map.Entry<String, Node> t : targetGraph.getAllNodes().entrySet()) {
                    int shortestValue = 0;
                    for (Node n : t.getValue().getShortestPath()) {
                        shortestValue += n.value;
                    }
                    shortestValue += t.getValue().value;

                    if (currentShortestDistance > shortestValue) {
                        currentShortestDistance = shortestValue;
                        currentFromNode = startNode.getName();
                        currentToNode = t.getKey();
                    }
                }
            }

            System.out.printf("%s - %s with a value of %d\n", currentFromNode, currentToNode, currentShortestDistance);

            return Integer.toString(currentShortestDistance);
        }

        public Graph calculateShortestPathFromSource(Graph graph, Node source) {
            source.setDistance(0);
            Set<String> visitedNodes = new HashSet();
            Set<Node> unVisitedNodes = new HashSet();

            unVisitedNodes.add(source);

            while(unVisitedNodes.size() != 0) {
                Node currentNode = getLowestNodeDistance(unVisitedNodes);
                unVisitedNodes.remove(currentNode);
                //System.out.print("Anylsing : " + currentNode.name + " - ");
                for(Map.Entry<Node, Integer> adjacencyPair : currentNode.getAdjacentNodes().entrySet()) {
                    Node adjacentNode = adjacencyPair.getKey();
                    //System.out.print(adjacentNode.name + ", ");
                    Integer edgeWeight = adjacencyPair.getValue();

                    if(!visitedNodes.contains(adjacentNode.name)) {
                        calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                        unVisitedNodes.add(adjacentNode);
                    }
                }
                //System.out.println();
                visitedNodes.add(currentNode.name);

            }

            return graph;
        }

        private void calculateMinimumDistance(Node evaluationNode, Integer edgeWeight, Node sourceNode) {
            Integer sourceDistance = sourceNode.getDistance();
            if (sourceDistance + edgeWeight < evaluationNode.getDistance()) {
                evaluationNode.setDistance(sourceDistance + edgeWeight);
                LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
                shortestPath.add(sourceNode);
                evaluationNode.setShortestPath(shortestPath);
            }
        }

        private Node getLowestNodeDistance(Set<Node> unVisitedNodes) {
            Node lowestDistanceNode = null;
            int lowestDistance = Integer.MAX_VALUE;
            for (Node node: unVisitedNodes) {
                int nodeDistance = node.getDistance();
                if (nodeDistance < lowestDistance) {
                    lowestDistance = nodeDistance;
                    lowestDistanceNode = node;
                }
            }
            return lowestDistanceNode;
        }

        private String getKey(int i, int j) {
            StringBuilder sb = new StringBuilder();
            sb.append(i);
            sb.append('_');
            sb.append(j);
            return sb.toString();
        }

        private int[][] loadData(String file, int limit) throws FileNotFoundException {
            int[][] matrix = new int[limit][limit];
            Scanner input = new Scanner(new File(file));
            for(int i=0; i<80; i++) {
                String line = input.nextLine();
                matrix[i] = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();
            }

            return matrix;
        }


        @Override
        public String getProblemStatement() {
            return "https://projecteuler.net/problem=82";
        }
    }
