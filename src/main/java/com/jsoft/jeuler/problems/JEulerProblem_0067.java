package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class JEulerProblem_0067 extends EulerSolver {

    public JEulerProblem_0067(int problemNumber) {
        super(problemNumber);
    }

    class Data {
        public Data(int x, int y) {
            value = x;
            maxSum = y;
        }
        public int value;
        public int maxSum;
    }

    @Override
    public String solve() {

        int max = 0;

        try {
            int nGridSize = 100;
            Data[][] data = loadData("data/problem67/p067_triangle.txt");

            for (int i=0; i<nGridSize-1; i++) {
                for (int j=0; j<=i; j++) {
                    if (i == 0) {
                        data[i + 1][j].maxSum = data[i][j].value + data[i + 1][j].value;
                        data[i + 1][j + 1].maxSum = data[i][j].value + data[i + 1][j + 1].value;
                    } else {
                        int s = data[i][j].maxSum + data[i + 1][j].value;

                        if(data[i + 1][j].maxSum < s) {
                            data[i + 1][j].maxSum = s;
                        }

                        s = data[i][j].maxSum + data[i + 1][j+1].value;

                        if(data[i + 1][j+1].maxSum < s) {
                            data[i + 1][j+1].maxSum = s;
                        }
                    }
                }
            }

            for (int j=0; j<nGridSize; j++) {
                if(max < data[nGridSize-1][j].maxSum) {
                    max = data[nGridSize-1][j].maxSum;
                }
            }


        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

        return Integer.toString(max);
    }

    private Data[][] loadData(String file) throws FileNotFoundException {

        int nGridSize = 100;
        Data[][] data = new Data[nGridSize][nGridSize];

        Scanner input = new Scanner(new File(file));
        for (int i=0; i<nGridSize; i++) {
            for (int j=0; j<=i; j++) {
                int x = input.nextInt();
                data[i][j] = new Data(x , 0);
            }
        }

        return data;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=18";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}
