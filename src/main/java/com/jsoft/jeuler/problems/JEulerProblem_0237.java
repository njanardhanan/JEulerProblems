package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JEulerProblem_0237 extends EulerSolver {

    public JEulerProblem_0237(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {

        /**
         * https://oeis.org/A181688
         *
         * a(n) = 2*a(n-1) + 2*a(n-2) - 2*a(n-3) + a(n-4), n > 4
         *
         * where:
         * a(1) = 1
         * a(2) = 1
         * a(3) = 4
         * a(4) = 8
         *
         * https://projecteuler.net/thread=237&page=5#last
         *
         * for Better method, refer
         * https://discuss.codechef.com/t/rng-editorial/10068
         *
         */

        long LIMIT = (long) Math.pow(10, 12);

        //From brute force, we can find that the pattern repeats every for 3100000000, which is 31 x 10^8
        long modLIMIT = 3100000000l;
        long actualLIMIT = LIMIT % modLIMIT;

        long longMOD = (long) Math.pow(10, 8);

        // From oeis A181688
        long[] a = {1, 1, 4, 8, 0};

        for(long n=5; n<=actualLIMIT; n++) {
            a[4] = 2*a[3] + 2*a[2] - 2*a[1] + a[0];

            for(int i=0; i<5; i++){
                //The values may go in -ve, so fix it.
                if(a[i] < 0) {
                    a[i] = longMOD + a[i];
                }
            }

            a[4] %= longMOD;

            //Move the array
            for(int i=0; i<4; i++){
                a[i] = a[i+1];
            }

            if(n % longMOD == 0) {
                System.out.printf("%d : {%d, %d, %d, %d, %d}\n", n, a[0],a[1],a[2],a[3],a[4]);
            }
        }

        return Long.toString(a[4]);
    }


    @Override
    public String getProblemStatement() {
        return  "https://projecteuler.net/problem=237";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}
