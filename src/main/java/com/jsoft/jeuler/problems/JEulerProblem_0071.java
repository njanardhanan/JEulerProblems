package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

public class JEulerProblem_0071 extends EulerSolver {

    public JEulerProblem_0071(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {

        int LIMIT = NumericHelper.ONE_MILLION_INT;

        int GIVEN_NUMERATOR = 3;
        int GIVEN_DENOMERATOR = 7;

        int answer_numerator = 1;
        int answer_denomerator = NumericHelper.ONE_MILLION_INT * 10;

        //Wild guess - denomerator has to be maximum, so search only last 100 denomerator.
        for(int d=LIMIT-100; d<=LIMIT; d++) {

            //Given : Search between 3/8 to 3/7
            for(int n=((3*d)/8); (n/d * 1.0) < (3/7.0); n++) {
                double tmp = n/(d * 1.0);
                if(tmp < GIVEN_NUMERATOR/(GIVEN_DENOMERATOR * 1.0)) {
                    if(tmp > answer_numerator/(answer_denomerator * 1.0)) {
                        answer_numerator = n;
                        answer_denomerator = d;
                    }
                }
            }
        }
        int gcd = NumericHelper.gcd(answer_numerator, answer_denomerator);

        answer_numerator = answer_numerator / gcd;
        answer_denomerator = answer_denomerator / gcd;

        System.out.printf("%d / %d\n", answer_numerator, answer_denomerator);

        return Integer.toString(answer_numerator);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=71";
    }
}
