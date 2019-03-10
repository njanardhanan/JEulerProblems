package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.ArrayList;
import java.util.List;


public class JEulerProblem_0187 extends EulerSolver {

    public JEulerProblem_0187(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {

        int LIMIT = (int)Math.pow(10, 8);
        boolean[] primes = PrimeNumberHelper.sieveOfEratosthenes(LIMIT/2);
        List<Long> primeList = new ArrayList();
        for(int i=0; i<LIMIT/2; i++) {
            if(primes[i]) {
                primeList.add((long)i);
                //System.out.println(i);
            }
        }

        int answer = 0;
        int sz = primeList.size();
        for(int i=0; i<sz; i++) {
            for(int j=i; j<sz; j++) {
                if(primeList.get(i) * primeList.get(j) < LIMIT) {
                    //System.out.println(primeList.get(i) * primeList.get(j));
                    answer++;
                } else {
                    break;
                }
            }
        }

        return Integer.toString(answer);
    }


    @Override
    public String getProblemStatement() {
        return "How many composite integers, n < 10^8, have precisely two, not necessarily distinct, prime factors?";
    }
}
