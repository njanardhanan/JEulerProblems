package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.Map;

public class JEulerProblem_0047 extends EulerSolver {

    public JEulerProblem_0047(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        int LIMIT = 4;
        long[] prevNs = new long[4];
        int nIndex = 0;
        for(long n = 5; n<=1000000; n++) {
            Map<Long, Integer> map = NumericHelper.getPrimeFactors(n);
            if (map.size() == LIMIT) {
                //System.out.println(n);
                if (nIndex == 0) {
                    prevNs[nIndex] = n;
                    nIndex++;
                } else {
                    if (prevNs[nIndex-1] + 1 == n) {
                        prevNs[nIndex] = n;
                        nIndex++;
                        if (nIndex == LIMIT) {
                            break;
                        }
                    } else {
                        nIndex = 0;
                    }
                }

            } else {
                nIndex = 0;
            }
            //map.forEach((k, v) -> System.out.println("Item : " + k + " Count : " + v));
        }

        for(long x : prevNs) {
            System.out.printf("%d, ", x);
        }
        System.out.println();
        return Long.toString(prevNs[0]);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=45";
    }
}
