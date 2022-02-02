package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.*;

public class JEulerProblem_0268 extends EulerSolver {

    public JEulerProblem_0268(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {

        List<Integer> primes = PrimeNumberHelper.sieveOfEratosthenesAsList(100);
        int distinctPrimeCount = 4;

        BigInteger targetBigInteger = BigInteger.valueOf(10).pow(16);

        int[] tetra = new int[primes.size() + 1];

        for(int i=distinctPrimeCount; i<tetra.length; i++) {
            int n = i - distinctPrimeCount + 1;
            tetra[i] = n * (n + 1) * (n + 2) / 6;
        }

        long answer = 0;

        while(true) {
            //System.out.println("Checking : " + distinctPrimeCount);
            List<BigInteger> combinationProduct = iterateCombinations(primes, distinctPrimeCount, targetBigInteger);
            if (combinationProduct.size() == 0) {
                break;
            }

            long tmpTotal = 0;
            for (BigInteger p : combinationProduct) {
                tmpTotal += targetBigInteger.divide(p).longValue();
            }

            tmpTotal *= tetra[distinctPrimeCount];

            if ((distinctPrimeCount & 1) == 1)
                answer -= tmpTotal;
            else
                answer += tmpTotal;

            distinctPrimeCount++;
        }

        return Long.toString(answer);
    }

    public List<BigInteger> iterateCombinations(List<Integer> elems, int r, BigInteger maxValue) {
        int n = elems.size();
        List<BigInteger> combinationProduct = new ArrayList();

        for(int num = 0;num < (1 << n);num++) {

            if(Integer.bitCount(num) != r) continue;

            //List<Integer> tmpCombination = new ArrayList();
            BigInteger bb = BigInteger.ONE;
            for(int ndx = 0;ndx < n;ndx++) {
                // (is the bit "on" in this number?)
                if((num & (1 << ndx)) != 0) {
                    // then it's included in the list
                    //tmpCombination.add(elems.get(ndx));
                    bb = bb.multiply(BigInteger.valueOf(elems.get(ndx)));
                }
            }

            if (maxValue.compareTo(bb) >= 0) {
                combinationProduct.add(bb);
            }
        }
        Collections.sort(combinationProduct);
        return combinationProduct;
    }

    public String solveTry1() {

        /**
         * Try #1
         *
         * This will give wrong answer, because the result contains duplicate values.
         *
         * For example :
         * Lets says you want to find number divisible by atleast 2 distinct prime no below 10.
         *
         * 2 distinct prime no. below 10 are (2,3), (2,5), (2,7), (3,5), (3,7) and (5,7)
         * 3 distinct prime no. below 10 are (2,3,5), (2,3,7), (3,5,7)
         * 4 distinct prime no. below 10 are (2,3,5,7)
         *
         * for example, 30 is divisible by (2,3), (2,5), (3,5) and (2,3,5).
         * but we have to count it only one time only.
         *
         */
        List<Integer> primesLessThan100 = PrimeNumberHelper.sieveOfEratosthenesAsList(100);

        //Generate all combination of length 4.
        //int TARGET = 1000;
        //int total = 0;

        BigInteger TARGET = BigInteger.valueOf(10).pow(16);
        BigInteger total = BigInteger.ZERO;
        int maxLCM = 1;
        for(int i=0; i<primesLessThan100.size()-3; i++) {
            for(int j=i+1; j<primesLessThan100.size()-2; j++) {
                for(int k=j+1; k<primesLessThan100.size()-1; k++) {
                    for(int l=k+1; l<primesLessThan100.size(); l++) {

                        int lcm = primesLessThan100.get(i) *
                                primesLessThan100.get(j) *
                                primesLessThan100.get(k) *
                                primesLessThan100.get(l);

                        if(maxLCM < lcm) {
                            maxLCM = lcm;
                        }

                        //total += TARGET/lcm;
                        total = total.add(TARGET.divide(BigInteger.valueOf(lcm)));
                    }
                }

            }
        }
        System.out.println(maxLCM);
        return total.toString();
    }

    @Override
    public String getProblemStatement() {
        String problem = "\n" +
                "\n" +
                "It can be verified that there are 23 positive integers less than 1000 that are divisible by at least four distinct primes less than 100.\n" +
                "\n" +
                "Find how many positive integers less than 10^16 are divisible by at least four distinct primes less than 100.\n";
        return problem + "https://projecteuler.net/thread=243";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("prime", "sieve");
    }
}
