package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;
import com.jsoft.jeuler.sudoku.SudokuSolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class JEulerProblem_0096 extends EulerSolver {

    public JEulerProblem_0096(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {

        //SudokuSolver solver = new SudokuSolver();
        int answer = 0;
        String path = "data/problem96/p096_sudoku.txt";
        try {
            Scanner file = new Scanner(new File(path));
            for (int gridNo = 0; gridNo < 50; gridNo++) {
                int[][] board = new int[9][9];
                // load the board from the file, assuming that each number
                // is separated by a space
                String ss = file.nextLine();
                if(gridNo > 0) {
                    ss = file.nextLine();
                }
                for (int y = 0; y < 9; y++) {
                    char[] characters = file.next().toCharArray();
                    for (int x = 0; x < 9; x++) {
                        // verify that the digit is between 1 and 9, inclusive
                        char digit = characters[x];
                        if (digit == '0') {
                            board[y][x] = 0;
                        } else if (digit > '9' || digit < '1') {
                            System.out.println("No. out of range");
                        } else {
                            board[y][x] = digit - '0';
                        }
                    }
                }

                SudokuSolver solver = new SudokuSolver(board);
                if (!solver.solve())
                    System.out.println("No solution.\n");
                else {
                    //System.out.println(solver);
                    int[][] solution = solver.getBoard();
                    int val = solution[0][0] * 100 + solution[0][1] * 10 + solution[0][2];
                    answer += val;
                    //System.out.printf("Grid #%d = %d\n", gridNo+1, val);
                }

            }
            file.close();
        }
        catch (FileNotFoundException exception) {
            System.out.println(exception.getMessage());
        }

        return Integer.toString(answer);
    }




    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=96";
    }
}
