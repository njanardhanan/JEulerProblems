package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JEulerProblem_0050 extends EulerSolver {

    public JEulerProblem_0050(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        //int LIMIT = 1000;
        int LIMIT = NumericHelper.ONE_MILLION_INT;
        boolean[] primes = PrimeNumberHelper.sieveOfEratosthenes(LIMIT);
        List<Integer> primeNumbers = new ArrayList();
        Map<Long, Integer> map = new HashMap();

        long sum = 0;
        for(int i=0; i<LIMIT; i++) {
            if(primes[i]) {
                primeNumbers.add(i);
                sum += i;
                map.put(sum, primeNumbers.size()-1);
            }
        }

        int longestLength = 0;
        int longestLengthPrime = 0;
        for(int k=primeNumbers.size()-1; k>=0; k--) {
            int currVal = primeNumbers.get(k);
            long currSum = 0;
            int maxLen = 0;
            for(int i=0; i<k-1; i++) {
                currSum += primeNumbers.get(i);

                //Optimization
                if(currSum > LIMIT) {
                    break;
                }

                if(currSum == currVal) {
                    maxLen = i + 1;
                }

                if(map.containsKey(currSum-currVal)) {
                    if (maxLen < (i-map.get(currSum-currVal))) {
                        maxLen = i - map.get(currSum-currVal);
                    }
                }
            }
            if (longestLength < maxLen) {
                longestLength = maxLen;
                longestLengthPrime = currVal;
            }

            //System.out.printf("%d - %d\n", currVal, maxLen);

        }
        System.out.printf("%d - %d\n", longestLengthPrime, longestLength);

        //System.out.println(map.size());

        return Integer.toString(longestLengthPrime);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=50";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}
