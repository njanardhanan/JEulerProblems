package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class JEulerProblem_0099 extends EulerSolver {

    public JEulerProblem_0099(int problemNumber) {
        super(problemNumber);
    }

    class Data {
        public Data(int x, int y) {
            base = x;
            exp = y;
        }
        public int base;
        public int exp;
    }

    @Override
    public String solve() {
        int currentLine = 1;
        try {
            Data[] data = loadData("data/problem99/p099_base_exp.txt");
            double x = data[0].exp * Math.log10(data[0].base);
            for(int i=1; i<1000; i++) {
                double y = data[i].exp * Math.log10(data[i].base);
                if(y > x) {
                    x = y;
                    currentLine = i+1;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return Integer.toString(currentLine);
    }

    private Data[] loadData(String file) throws FileNotFoundException {

        int LIMIT = 1000;

        Data[] data = new Data[LIMIT];

        Scanner input = new Scanner(new File(file));
        for (int i=0; i<LIMIT; i++) {
                String line = input.nextLine();
                String[] d = line.split(",");
                data[i] = new Data(Integer.parseInt(d[0]), Integer.parseInt(d[1]));
        }

        return data;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=99";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}
