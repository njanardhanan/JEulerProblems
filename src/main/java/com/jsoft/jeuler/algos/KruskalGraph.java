package com.jsoft.jeuler.algos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KruskalGraph {
    class Edge implements Comparable<Edge> {

        int src, dest, weight;

        Edge(int s, int d, int w) {
            this.src = s;
            this.dest = d;
            this.weight = w;
        }

        @Override
        public int compareTo(Edge e) {
            return this.weight - e.weight;
        }
    }

    class Subset
    {
        int parent, rank;
    }

    int noOfVertices;
    List<Edge> edges;

    public KruskalGraph(int v) {
        noOfVertices = v;
        edges = new ArrayList<>();
    }


    public void addEdge(int s, int d, int w) {
        edges.add(new Edge(s, d, w));
    }

    public int getCost() {
        int cost = 0;
        for (Edge e : edges) {
            cost += e.weight;
        }
        return cost;
    }

    public int find(Subset[] subsets, int i) {
        // find root and make root as parent of i
        // (path compression)
        if (subsets[i].parent != i)
            subsets[i].parent = find(subsets, subsets[i].parent);

        return subsets[i].parent;
    }

    public void union(Subset[] subsets, int x, int y) {
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);

        // Attach smaller rank tree under root
        // of high rank tree (Union by Rank)
        if (subsets[xroot].rank < subsets[yroot].rank) {
            subsets[xroot].parent = yroot;
        } else if (subsets[xroot].rank > subsets[yroot].rank) {
            subsets[yroot].parent = xroot;

            // If ranks are same, then make one as
            // root and increment its rank by one
        } else {
            subsets[yroot].parent = xroot;
            subsets[xroot].rank++;
        }
    }

    public int kruskalMST() {
        // This will store the resultant MST
        Edge result[] = new Edge[noOfVertices];

        // Step 1:  Sort all the edges in non-decreasing
        // order of their weight.  If we are not allowed to
        // change the given graph, we can create a copy of
        // array of edges
        Collections.sort(edges);

        // Allocate memory for creating V subsets
        Subset subsets[] = new Subset[noOfVertices];
        for (int i = 0; i < noOfVertices; ++i)
            subsets[i] = new Subset();

        // Create V subsets with single elements
        for (int v = 0; v < noOfVertices; ++v) {
            subsets[v].parent = v;
            subsets[v].rank = 0;
        }

        int i = 0; // Index used to pick next edge
        int e = 0;

        // Number of edges to be taken is equal to V-1
        while (e < noOfVertices - 1) {
            // Step 2: Pick the smallest edge. And increment
            // the index for next iteration
            Edge next_edge = edges.get(i++);

            int x = find(subsets, next_edge.src);
            int y = find(subsets, next_edge.dest);

            // If including this edge does't cause cycle,
            // include it in result and increment the index
            // of result for next edge
            if (x != y) {
                //minimumSpanningTree.add(next_edge);
                result[e++] = next_edge;
                union(subsets, x, y);
            }
            // Else discard the next_edge
        }

        // print the contents of result[] to display
        // the built MST
        //System.out.println("Following are the edges in the constructed MST");
        int minimumCost = 0;
        for (i = 0; i < e; ++i) {
            //System.out.println(result[i].src + " -- " + result[i].dest + " == " + result[i].weight);
            minimumCost += result[i].weight;
        }
        //System.out.println("Minimum Cost Spanning Tree " + minimumCost);
        return minimumCost;
    }
}
