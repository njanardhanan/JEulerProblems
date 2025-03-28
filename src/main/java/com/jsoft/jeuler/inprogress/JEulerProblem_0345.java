package com.jsoft.jeuler.inprogress;

import com.jsoft.jeuler.algos.HungarianAlgorithm;
import com.jsoft.jeuler.solver.EulerSolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class JEulerProblem_0345 extends EulerSolver {

    public JEulerProblem_0345(int problemNumber) {
        super(problemNumber);
    }

    private static final int ROW_LEN = 15;
    private static final int COL_LEN = 15;

    @Override
    public String solve() {
        int sum = 0;
        try {
            int[][] matrix = loadData("data/problem345/data.txt");
            //int[][] matrix = loadData("data/problem345/data_test.txt");
            HungarianAlgorithm ha = new HungarianAlgorithm();
            int[][] result = ha.execute(matrix, true);

            for (int i = 0; i < result.length; i++) {
                sum += matrix[result[i][0]][result[i][1]];
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return Integer.toString(sum);
    }

    private int[][] loadData(String file) throws FileNotFoundException {

        int[][] data = new int[ROW_LEN][COL_LEN];

        Scanner input = new Scanner(new File(file));
        for (int i=0; i<ROW_LEN; i++) {
            for (int j=0; j<COL_LEN; j++) {
                int x = input.nextInt();
                System.out.print("(" + x + "), ");
                data[i][j] = x;
            }
            System.out.println();
        }

        return data;
    }

    @Override
    public String getProblemStatement() {
        return "This is a template file";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList();
    }
}
