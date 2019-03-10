package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

public class JEulerProblem_0027 extends EulerSolver {

    public JEulerProblem_0027(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {

        boolean[] primes = PrimeNumberHelper.sieveOfEratosthenes(NumericHelper.ONE_MILLION_INT);

        int maxCount = 0, A = 0, B = 0, N = 0;

        for(int a = -999; a<1000; a++) {
            //System.out.printf("%d, ", a);
            for(int b = -1000; b<=1000; b++) {
                int n = 0;
                int count = 0;
                while(true) {
                    int val = Math.abs((n*n) + (a*n) + b);
                    n++;
                    if(primes[val]) {
                        count++;
                    } else {
                        break;
                    }
                }

                if(count>0) {
                    if (maxCount<count) {
                        maxCount = count;
                        A = a;
                        B = b;
                        N = n-1;
                    }
                }

            }
        }

        //System.out.println();

        System.out.printf("A = %d, B = %d, N = %d, Count = %d\n", A, B, N, maxCount);

        return Integer.toString(A*B);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=27";
    }
}
