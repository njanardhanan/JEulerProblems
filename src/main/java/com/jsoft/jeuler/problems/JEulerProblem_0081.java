    package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

    public class JEulerProblem_0081 extends EulerSolver {

        public JEulerProblem_0081(int problemNumber) {
            super(problemNumber);
        }

        @Override
        public String solve() {

            int LIMIT = 80;

//            int[][] matrix = {{131, 673, 234, 103, 18},
//                    {201, 96, 342, 965, 150},
//                    {630, 803, 746, 422, 111},
//                    {537, 699, 497, 121, 956},
//                    {805, 732, 524, 37, 331}};

            int[][] matrix = new int[0][];
            try {
                matrix = loadData("data/problem81/p081_matrix.txt", LIMIT);

                for(int i=0; i<LIMIT; i++) {
                    for(int j=0; j<LIMIT; j++) {
                        //Both path from up to down and left to right are possible.
                        if(i-1 >= 0 && j-1 >= 0) {
                            int upToDown = matrix[i][j] + matrix[i-1][j];
                            int leftToRight = matrix[i][j] + matrix[i][j-1];
                            matrix[i][j] = (upToDown < leftToRight) ? upToDown : leftToRight;
                        } else {
                            //if up to down is possible
                            if(i-1 >= 0) {
                                matrix[i][j] = matrix[i][j] + matrix[i-1][j];
                            } else if(j-1 >=0 ) {
                                //if left to right is possible
                                matrix[i][j] = matrix[i][j] + matrix[i][j-1];
                            }
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            return Integer.toString(matrix[LIMIT-1][LIMIT-1]);
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
            return "https://projecteuler.net/problem=81";
        }

        @Override
        public List<String> getTags() {
            return null;
        }
    }
