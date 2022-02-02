package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JEulerProblem_0094 extends EulerSolver {

    public JEulerProblem_0094(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        /**
         Refer : https://projecteuler.net/problem=75

         Refer : https://en.wikipedia.org/wiki/Pythagorean_triple
         Euclid's formula states that
         a = 2*m*n
         b = m*m - n*n
         c = m*m + n*n

         The above formula generates Pythagorean triple if m and n are coprime and not both odd.

         **/
        long LIMIT = 1000000000;
        long mLimit = (long)Math.sqrt(LIMIT/2) + 1;

        long answer = 0;
        for(long m=2; m<=mLimit; m++) {
            for(long n=1; n<m; n++) {
                if((m+n) % 2 == 1 && NumericHelper.gcd(m, n) == 1) {
                    long a = m*m - n*n;
                    long b = 2*m*n;
                    long c = m*m + n*n;
                    long p = 0;
                    if (Math.abs(2 * b - c) == 1) {
                        p = b + b + c + c;

                    } else if (Math.abs(2 * a - c) == 1) {
                        p = a + a + c + c;
                    }
                    if(p!=0 && p<LIMIT) {
                        System.out.printf("a : %d, b : %d, c : %d , p : %d\n", a, b, c, p);
                        answer += p;
                    } else if (p!=0){
                        System.out.printf("a : %d, b : %d, c : %d , p : %d ( greater than one billion )\n", a, b, c, p);
                    }
                }
            }
        }

        return Long.toString(answer);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=94";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}
