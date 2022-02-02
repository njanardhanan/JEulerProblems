package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JEulerProblem_0357 extends EulerSolver {

    public JEulerProblem_0357(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {


        int LIMIT = 100000000;
        boolean[] primes = PrimeNumberHelper.sieveOfEratosthenes(LIMIT);

        //Initialize 3 because 1 and 2 also holds this property, 1+2=3
        long ans = 3;

        for(int n=4; n<LIMIT; n+=2) {
            if(!primes[n+1]) continue;
            if(!primes[(n/2)+2]) continue;

            Set<Integer> divisors = getDivisors(n);
            boolean isPrime = true;
            for(int d : divisors) {
                int p = d + (n/d);
                if(!primes[p]) {
                    isPrime = false;
                    break;
                }
            }

            if(isPrime) {
                ans += (long)n;
            }

        }
        return Long.toString(ans);
    }

    public Set<Integer> getDivisors(int n) {
        Set<Integer> listOfDivisors = new HashSet<>();
        for (int i=1; i<=Math.sqrt(n); i++) {
            if (n%i==0) {
                listOfDivisors.add(i);
            }
        }

        return listOfDivisors;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/thread=357";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}
