package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.algos.KruskalGraph;
import com.jsoft.jeuler.solver.EulerSolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JEulerProblem_0107 extends EulerSolver {

    public JEulerProblem_0107(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        //p107_network
        List<String[]> data = null;
        try {
            //data = loadData("data/problem107/p107_network_test.txt");
            data = loadData("data/problem107/p107_network.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        KruskalGraph graph = new KruskalGraph(data.size());
        for(int i=0; i<data.size(); i++) {
            for(int j=i+1; j<data.size(); j++) {
                if (data.get(i)[j].equals("-")) {
                    continue;
                }
                graph.addEdge(i, j, Integer.parseInt(data.get(i)[j]));
                //System.out.printf("%d -> %d => %d\n", i, j, Integer.parseInt(data.get(i)[j]));
            }
        }
        int ans = graph.getCost() - graph.kruskalMST();
        return Integer.toString(ans);
    }

    private List<String[]> loadData(String filename) throws FileNotFoundException {
        Scanner input = new Scanner(new File(filename));
        List<String[]> data = new ArrayList<>();
        while(input.hasNextLine()) {
            String[] d = input.nextLine().split(",");
            //System.out.println(Arrays.toString(d));
            data.add(d);
        }
        return data;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=107\n";
    }
}
