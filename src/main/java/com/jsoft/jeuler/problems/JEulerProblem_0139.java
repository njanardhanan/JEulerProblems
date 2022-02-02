package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.List;

public class JEulerProblem_0139 extends EulerSolver {

    public JEulerProblem_0139(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        /**
         Refer : https://projecteuler.net/problem=75
         Refer : https://projecteuler.net/problem=94

         Refer : https://en.wikipedia.org/wiki/Pythagorean_triple
         Euclid's formula states that
         a = 2*m*n
         b = m*m - n*n
         c = m*m + n*n

         The above formula generates Pythagorean triple if m and n are coprime and not both odd.

         **/
        long LIMIT = 100l * (long)NumericHelper.ONE_MILLION_INT;
        long mLimit = (long)Math.sqrt(LIMIT/2) + 1;

        long answer = 0;
        for(long m=2l; m<=mLimit; m++) {
            for(long n=1l; n<m; n++) {
                if((m+n) % 2l == 1l && NumericHelper.gcd(m, n) == 1l) {
                    long a = m*m - n*n;
                    long b = 2l*m*n;
                    long c = m*m + n*n;
                    long p = a + b + c;

                    if(p < LIMIT) {
                        long tileSize = a-b;
                        if(c%tileSize == 0) {
                            System.out.printf("(%d, %d, %d) - %d\n", a, b, c, tileSize);
                            answer += (LIMIT/p);
                        }
                    }

                }
            }
        }

        return Long.toString(answer);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=139";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}
