package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

public class JEulerProblem_0033 extends EulerSolver {

    public JEulerProblem_0033(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {

        long numProduct = 1;
        long denumProduct = 1;

        for(int i=1; i<10; i++) {
            for(int j=1; j<10; j++) {
                int denum = i * 10 + j;
                for(int a=1; a<10; a++) {
                    for(int b=1; b<10; b++) {
                        int num = a * 10 + b;
                        if(num >= denum) continue;

                        //Multiply by 100 to avoid decimal comparision complication.
                        if(i==b && (a*100/j) == (num*100/denum)) {
                            System.out.printf("%d/%d == %d/%d\n", num, denum, a, j);
                            numProduct *= a;
                            denumProduct *= j;
                        }

                        if(j==a && (b*100/i) == (num*100/denum)) {
                            System.out.printf("%d/%d == %d/%d\n", num, denum, b, i);
                            numProduct *= b;
                            denumProduct *= i;
                        }
                    }
                }

            }
        }

        long gcd = NumericHelper.gcd(numProduct, denumProduct);
        numProduct /= gcd;
        denumProduct /= gcd;


        return String.format("%d", denumProduct);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=17";
    }
}
