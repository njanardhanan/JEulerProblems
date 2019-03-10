package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JEulerProblem_0011 extends EulerSolver {

    public JEulerProblem_0011(int problemNumber) {
        super(problemNumber);
    }

    class Data {
        public Data(int x, int y) {
            value = x;
            maxProduct = y;
        }
        public int value;
        public int maxProduct;
    }

    @Override
    public String solve() {
        int MAX = 20;
        int answer = 0;
        try {
            Data[][] data = loadData("data/problem11/data.txt");
            for (int i=0; i<MAX; i++) {
                for (int j=0; j<MAX; j++) {

                    int currentMaxProduct = data[i][j].maxProduct;

                    //Top left diagonal
                    int maxProduct = 1;
                    for(int d=0; d<4; d++) {
                        if (i-d >= 0 && j-d >= 0) {
                            maxProduct = maxProduct * data[i-d][j-d].value;
                        }
                    }

                    if(currentMaxProduct < maxProduct) currentMaxProduct = maxProduct;

                    //Top
                    maxProduct = 1;
                    for(int d=0; d<4; d++) {
                        if (i-d >= 0) {
                            maxProduct = maxProduct * data[i-d][j].value;
                        }
                    }

                    if(currentMaxProduct < maxProduct) currentMaxProduct = maxProduct;

                    //Top Right
                    maxProduct = 1;
                    for(int d=0; d<4; d++) {
                        if (i-d >= 0 && j+d < MAX) {
                            maxProduct = maxProduct * data[i-d][j+d].value;
                        }
                    }

                    if(currentMaxProduct < maxProduct) currentMaxProduct = maxProduct;

                    //Right
                    maxProduct = 1;
                    for(int d=0; d<4; d++) {
                        if (j+d < MAX) {
                            maxProduct = maxProduct * data[i][j+d].value;
                        }
                    }

                    if(currentMaxProduct < maxProduct) currentMaxProduct = maxProduct;

                    //Bottom Right
                    maxProduct = 1;
                    for(int d=0; d<4; d++) {
                        if (i+d < MAX && j+d < MAX) {
                            maxProduct = maxProduct * data[i+d][j+d].value;
                        }
                    }

                    if(currentMaxProduct < maxProduct) currentMaxProduct = maxProduct;

                    //Bottom
                    maxProduct = 1;
                    for(int d=0; d<4; d++) {
                        if (i+d < MAX) {
                            maxProduct = maxProduct * data[i+d][j].value;
                        }
                    }

                    if(currentMaxProduct < maxProduct) currentMaxProduct = maxProduct;

                    //Bottom Left
                    maxProduct = 1;
                    for(int d=0; d<4; d++) {
                        if (i+d < MAX && j-d >= 0) {
                            maxProduct = maxProduct * data[i+d][j-d].value;
                        }
                    }

                    if(currentMaxProduct < maxProduct) currentMaxProduct = maxProduct;


                    //Left
                    maxProduct = 1;
                    for(int d=0; d<4; d++) {
                        if (j-d >= 0) {
                            maxProduct = maxProduct * data[i][j-d].value;
                        }
                    }

                    if(currentMaxProduct < maxProduct) currentMaxProduct = maxProduct;

                    data[i][j].maxProduct = currentMaxProduct;

                    if(answer < currentMaxProduct) {
                        answer = currentMaxProduct;
                    }


                    //System.out.print(data[i][j].value + ", ");
                }
                //System.out.println();
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        return Integer.toString(answer);
    }

    private Data[][] loadData(String file) throws FileNotFoundException {

        Data[][] data = new Data[20][20];

        Scanner input = new Scanner(new File(file));
        for (int i=0; i<20; i++) {
            for (int j=0; j<20; j++) {
                int x = input.nextInt();
                data[i][j] = new Data(x , 1);
            }
        }

        return data;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=11";
    }
}
