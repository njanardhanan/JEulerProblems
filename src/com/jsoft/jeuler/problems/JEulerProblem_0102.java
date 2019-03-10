package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class JEulerProblem_0102 extends EulerSolver {

    public JEulerProblem_0102(int problemNumber) {
        super(problemNumber);
    }

    class Data {
        int x1, x2, x3;
        int y1, y2, y3;

        Data(String[] s) {
            this(s[0], s[1],
                    s[2], s[3],
                    s[4], s[5]);
        }

        Data(String x1, String y1,
             String x2, String y2,
             String x3, String y3) {
            this.x1 = Integer.parseInt(x1);
            this.y1 = Integer.parseInt(y1);

            this.x2 = Integer.parseInt(x2);
            this.y2 = Integer.parseInt(y2);

            this.x3 = Integer.parseInt(x3);
            this.y3 = Integer.parseInt(y3);
        }
    }

    @Override
    public String solve() {
        int count = 0;
        try {
            Data[] data = loadData("data/problem102/p102_triangles.txt");
            for(Data d : data) {
                //System.out.println(isInside( d.x1, d.y1, d.x2, d.y2, d.x3, d.y3, 0, 0));
                if(isInside( d.x1, d.y1, d.x2, d.y2, d.x3, d.y3, 0, 0)) {
                    count++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return Integer.toString(count);
    }

    private static double areaOfTheTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        return Math.abs((x1*(y2-y3) + x2*(y3-y1)+ x3*(y1-y2))/2.0);
    }

    private static boolean isInside(int x1, int y1, int x2,
                            int y2, int x3, int y3, int x, int y)
    {
       /* Calculate area of triangle ABC */
        double A = areaOfTheTriangle (x1, y1, x2, y2, x3, y3);

       /* Calculate area of triangle PBC */
        double A1 = areaOfTheTriangle (x, y, x2, y2, x3, y3);

       /* Calculate area of triangle PAC */
        double A2 = areaOfTheTriangle (x1, y1, x, y, x3, y3);

       /* Calculate area of triangle PAB */
        double A3 = areaOfTheTriangle (x1, y1, x2, y2, x, y);

       /* Check if sum of A1, A2 and A3 is same as A */
        return (A == A1 + A2 + A3);
    }

    private Data[] loadData(String file) throws FileNotFoundException {

        int size = 1000;
        Data[] data = new Data[size];

        Scanner input = new Scanner(new File(file));
        for (int i=0; i<size; i++) {
            String line = input.nextLine();
            String[] s = line.split(",");

            data[i] = new Data(s);
        }

        return data;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=102";
    }
}
