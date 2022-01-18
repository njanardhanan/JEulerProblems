package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JEulerProblem_0060 extends EulerSolver {

    public JEulerProblem_0060(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {

        int LIMIT = 10000;
        boolean[] primes = PrimeNumberHelper.sieveOfEratosthenes(LIMIT);
        List<Integer> primeList = new ArrayList();
        for(int i=2; i<LIMIT; i++) {
            if(primes[i]) {
                primeList.add(i);
            }
        }

        int size = primeList.size();
        System.out.println("No. of primes : " + size);
        int answer = Integer.MAX_VALUE;

        for(int a=0; a<size; a++) {
            for(int b=a; b<size; b++) {
                if(!isPrimePairSet(new Integer[]{primeList.get(a)}, primeList.get(b))) continue;

                for(int c=b; c<size; c++) {
                    if(!isPrimePairSet(new Integer[]{primeList.get(a), primeList.get(b)}, primeList.get(c))) continue;

                    for(int d=c; d<size; d++) {
                        if(!isPrimePairSet(new Integer[]{primeList.get(a), primeList.get(b), primeList.get(c)}, primeList.get(d))) continue;

                        for(int e=d; e<size; e++) {

                            Integer[] set = {primeList.get(a), primeList.get(b), primeList.get(c), primeList.get(d)};

                            if(isPrimePairSet(set, primeList.get(e))) {
                                List<Integer> primePairSet = new ArrayList(Arrays.asList(set));
                                primePairSet.add(primeList.get(e));

                                int sum = primePairSet.stream().mapToInt(x -> x).sum();
                                System.out.println(Arrays.toString(primePairSet.toArray()) + " => " + sum);

                                if(answer > sum) {
                                    answer = sum;
                                }

                            }
                        }
                    }
                }
            }
        }

        return Integer.toString(answer);
    }

    private boolean isPrimePairSet(Integer[] set, long d) {
        for(int x : set) {
            long n = Long.parseLong(Long.toString(x) + Long.toString(d));
            if(!PrimeNumberHelper.checkPrime(n)) return false;

            n = Long.parseLong(Long.toString(d) + Long.toString(x));
            if(!PrimeNumberHelper.checkPrime(n)) return false;
        }
        return true;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=60";
    }
}
