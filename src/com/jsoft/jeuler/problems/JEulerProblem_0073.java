package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

public class JEulerProblem_0073 extends EulerSolver {

    public JEulerProblem_0073(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {

        int LIMIT = 12000;
        //int LIMIT = 8;

        double lowLimit = 1.0/3.0;
        double upperLimit = 1.0/2.0;

        int count = 0;
        for(int d=1; d<=LIMIT; d++) {
            //Given : Search between 1/3 to 1/2
            for(int n=((1*d)/3); (n/d * 1.0) < (1/2.0); n++) {
                double tmp = n/(d * 1.0);
                if(tmp > lowLimit && tmp < upperLimit) {
                    int gcd = NumericHelper.gcd(n, d);
                    if(gcd == 1) {
                        count++;
                    }
                }
            }
        }

        return Integer.toString(count);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=73";
    }
}
