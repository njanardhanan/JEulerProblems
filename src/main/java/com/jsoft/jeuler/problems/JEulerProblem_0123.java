package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class JEulerProblem_0123 extends EulerSolver {

    public JEulerProblem_0123(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {

        /**
         * Refer Problem120
         *
         *  ((pn−1)^n + (pn+1)^n ) / n^2
         *
         *  if n is even then the remainder is always 2
         *  if n in odd then the remainder will be 2*pn*n
         *
         */

        boolean[] primes = PrimeNumberHelper.sieveOfEratosthenes(NumericHelper.ONE_MILLION_INT);
        List<Integer> primeList = new ArrayList();
        for(int i=1; i<NumericHelper.ONE_MILLION_INT; i++) {
            if(primes[i]) {
                primeList.add(i);
            }
        }
        BigInteger target = BigInteger.valueOf(10).pow(10);
        int answer = 0;
        for(int n=1; n<primeList.size(); n+=2) {
            BigInteger x = BigInteger.valueOf(2).multiply(BigInteger.valueOf(primeList.get(n-1))).multiply(BigInteger.valueOf(n));
            if(x.compareTo(target) == 1) {
                answer = n;
                break;
            }
        }

        return Integer.toString(answer);

    }

    public String bruteForceMethod() {
        boolean[] primes = PrimeNumberHelper.sieveOfEratosthenes(NumericHelper.ONE_MILLION_INT);
        List<Integer> primeList = new ArrayList();
        for(int i=1; i<NumericHelper.ONE_MILLION_INT; i++) {
            if(primes[i]) {
                primeList.add(i);
            }
        }
        BigInteger target = BigInteger.valueOf(10).pow(10);
        int answer = 0;
        for(int n=7037; n<primeList.size(); n++) {
            //System.out.println(primeList.get(2));
            int v = primeList.get(n - 1);
            BigInteger b = BigInteger.valueOf(v).pow(2);
            //System.out.println(b.toString());
            BigInteger b1 = BigInteger.valueOf(v - 1).pow(n).mod(b);
            BigInteger b2 = BigInteger.valueOf(v + 1).pow(n).mod(b);
            BigInteger x = b1.add(b2).mod(b);
            if(x.compareTo(target) == 1) {
                answer = n;
                break;
            }
        }
        return Integer.toString(answer);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/thread=112";
    }
}
