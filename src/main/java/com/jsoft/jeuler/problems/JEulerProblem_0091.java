package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JEulerProblem_0091 extends EulerSolver {

    class Point {
        int x;
        int y;
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }

    public JEulerProblem_0091(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        System.out.println("NOT SOLVED, MISSING SOME CORNER CASES");
        int limit = 50;
        int count = 0;
        Set<String> pointSet = new HashSet<>();
        for(int x1=0; x1<=limit; x1++) {
            for(int y1=0; y1<=limit; y1++) {
                if(x1 == 0 && y1 == 0) continue;
                Point p1 = new Point(x1, y1);
                for(int x2=0; x2<=limit; x2++) {
                    for(int y2=0; y2<=limit; y2++) {
                        if(x2 == 0 && y2 == 0) continue;
                        if(x1 == x2 && y1 == y2) continue;
                        Point p2 = new Point(x2, y2);
                        if(pointSet.contains(p1 + "-" + p2)) {
                            continue;
                        }
                        if(IsRightAngleTriangle(p1, p2)) {
                            pointSet.add(p1 + "-" + p2);
                            pointSet.add(p2 + "-" + p1);
                            //System.out.println(p1 + " - " + p2);
                            count++;
                        }
                    }
                }
            }
        }
        System.out.println(count);
        return "0";
    }

    private boolean IsRightAngleTriangle(Point p1, Point p2) {
        return IsRightAngleTriangle(new Point(0, 0), p1, p2);
    }

    private boolean IsRightAngleTriangle(Point p1, Point p2, Point p3) {
        double a = getLength(p1, p2);
        double b = getLength(p1, p3);
        double c = getLength(p2, p3);
        if (getAngle(a, b, c) == 90 || getAngle(b, c, a) == 90 || getAngle(c, a, b) == 90) {
            return true;
        }
        return false;
    }

    private double getLength(Point p1, Point p2) {
        int a = (p1.x - p2.x) * (p1.x - p2.x);
        int b = (p1.y - p2.y) * (p1.y - p2.y);
        return Math.sqrt(a+b);
    }

    private long getAngle(double a, double b, double c) {
        double num = (a*a) + (b*b) - (c*c);
        double denum = 2 * a * b;
        return Math.round(Math.toDegrees(Math.acos(num/denum)));
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=91";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}
