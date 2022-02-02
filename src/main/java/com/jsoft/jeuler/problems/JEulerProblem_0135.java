package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.List;

public class JEulerProblem_0135 extends EulerSolver {

    public JEulerProblem_0135(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        /**
         *  Given : x^2 - y^2 - z^2 = n  and x, y and z are consecutive terms of an arithmetic progression.
         *
         *  if the difference is d then
         *
         *  (y+d)^2 - y^2 - (y-d)^2 = n
         *
         *  y^2 + 2yd + d^2 - y^2 - y^2 - d^2 + 2yd = n
         *
         *  4yd - y^2 = n
         *
         *  y(4d-y) = n
         *
         *  i.e., n is factor of y and 4d-y
         *
         *  therefore
         *
         *  y should be 1 <= y <= n
         *
         *  d should be (y/4) <= d <= y
         *
         *
         */
        int LIMIT = NumericHelper.ONE_MILLION_INT;
        int[] nCount = new  int[LIMIT+1];
        for(long y = 1; y<= LIMIT; y++) {
            for(long d= (y+3)/4; d<y; d++) { // +3 is to make d atleast 1
                long xSq = (y+d) * (y+d);
                long ySq = y * y;
                long zSq = (y-d) * (y-d);

                long n = xSq - ySq - zSq;

                if(n<0) {
                    continue;
                }

                if(n>=LIMIT) {
                    break;
                }


                nCount[(int)n]++;

            }
        }

        int ans = 0;
        for(int i = 1; i<LIMIT; i++) {
            if(nCount[i] == 10) {
                ans++;
            }
        }

        System.out.println("N = 27, count = " + nCount[27]);
        System.out.println("N = 1155, count = " + nCount[1155]);

        return Integer.toString(ans);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=135";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}
