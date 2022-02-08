package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JEulerProblem_0263 extends EulerSolver {

    public JEulerProblem_0263(int problemNumber) {
        super(problemNumber);
    }

    public class PrimeFactors implements Comparable<PrimeFactors> {

        long prime;
        int factor;

        PrimeFactors(long p, int f) {
            this.prime = p;
            this.factor = f;
        }


        @Override
        public int compareTo(PrimeFactors pf) {
            if(this.prime == pf.prime) {
                return 0;
            }
            if(this.prime > pf.prime) {
                return 1;
            }
            return -1;
        }

        @Override
        public String toString() {
            return "(" + prime + ":" + factor + ")";
        }
    }

    @Override
    public String solve() {

        int TARGET = 4;
        //Found the limit by trail and error, slowly increased this limit till I get the answer.
        long LIMIT = 1150000000L;
        List<Long> primes = PrimeNumberHelper.sieveLargePrimes(1, (long)Math.sqrt(LIMIT));

        int engineersParadiseCount = 0;
        long engineersParadiseSum = 0;
        //Found the increament by trail and error, slowly increased it as I found the answer.
        for (long p=11; p<LIMIT; p+=20) {
            long n = p+9;
            if (isEngineersParadise(n, primes)) {
                //Engineer Paradise should contain Consecutive primes
                if (isConsecutiveTriplePairSexyPrime(n, primes)) {
                    engineersParadiseCount++;
                    engineersParadiseSum += n;
                    System.out.printf("Engineer Paradise #%d: %d\n", engineersParadiseCount, n);
                } else {
                    System.out.println("Engineer Paradise with Non-Consecutive primes : " + n);
                }
            }

            if (engineersParadiseCount == TARGET) {
                break;
            }
        }

        /**
         * Engineer Paradise with Non-Consecutive primes : 20
         * Engineer Paradise #1: 219869980
         * Engineer Paradise with Non-Consecutive primes : 298567940
         * Engineer Paradise #2: 312501820
         * Engineer Paradise #3: 360613700
         * Engineer Paradise #4: 1146521020
         */
        return Long.toString(engineersParadiseSum);
    }

    private boolean isEngineersParadise(long n, List<Long> primes) {

        //(n-9, n-3), (n-3,n+3), (n+3, n+9) form a triple-pair, and
        if (!isTriplePairSexyPrime(n, primes)) {
            return false;
        }

        //the numbers n-8, n-4, n, n+4 and n+8 are all practical
        if (isPracticalNumber(n-8, primes)
                && isPracticalNumber(n-4, primes)
                && isPracticalNumber(n, primes)
                && isPracticalNumber(n+4, primes)
                && isPracticalNumber(n+8, primes)) {
            return true;

        }
        return false;
    }

    private boolean isTriplePairSexyPrime(long n, List<Long> primes) {
        if (PrimeNumberHelper.isPrime(n-9, primes) &&
                PrimeNumberHelper.isPrime(n-3, primes) &&
                PrimeNumberHelper.isPrime(n+3, primes) &&
                PrimeNumberHelper.isPrime(n+9, primes)) {
            //System.out.printf("%d - (%d, %d, %d, %d)\n", n, n-9, n-3, n+3, n+9);
            return true;
        }
        return false;
    }

    private boolean isConsecutiveTriplePairSexyPrime(long n, List<Long> primes) {
        for (long p=n-9; p<=n+9; p++) {
            if (p == n-9) continue;
            if (p == n-3) continue;
            if (p == n+3) continue;
            if (p == n+9) continue;

            //If any number is prime other than top 4 condiition, then it is not Consecutive primes
            if (PrimeNumberHelper.isPrime(p)) {
                return false;
            }
        }
        return true;
    }


    private boolean isPracticalNumber(long n, List<Long> primes) {
        /**
         *   https://en.wikipedia.org/wiki/Practical_number
         *   https://en.wikipedia.org/wiki/Divisor_function
         *   https://www2.math.upenn.edu/~deturck/m170/wk3/lecture/sumdiv.html
         *   https://oeis.org/A005153
         */
        List<PrimeFactors> pFactors = getPrimeFactors(n, primes);

        if (pFactors.get(0).prime != 2) {
            //if n is not divisible by 2, then it is not practical.
            return false;
        }

        if (pFactors.size() == 1) {
            //This means the number n is power of 2, so it is practical number
            return true;
        }

        long totalSumOfDivisors = 1;
        for (int i=1; i<pFactors.size(); i++) {
            //1. Find the sum of the divisors for the primes 0 to i-1
            long sumOfDivisors = 0;
            for (int f=0; f<=pFactors.get(i-1).factor; f++) {
                sumOfDivisors += (long)Math.pow(pFactors.get(i-1).prime, f);
            }
            totalSumOfDivisors *= sumOfDivisors;
            if (pFactors.get(i).prime > (totalSumOfDivisors + 1)) {
                return false;
            }
        }


        return true;
    }

    private List<PrimeFactors> getPrimeFactors(long n, List<Long> primes) {
        List<PrimeFactors> primeFactors = new ArrayList<>();

        for(long p : primes) {
            PrimeFactors pf = new PrimeFactors(0, 0);
            while (n%p == 0) {
                pf.prime = p;
                pf.factor += 1;
                n = n / p;
            }
            if (pf.prime != 0) {
                primeFactors.add(pf);
            }
            if (n < p) {
                break;
            }
        }

        if (n>2) {
            primeFactors.add(new PrimeFactors(n, 1));
        }

        return primeFactors;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=263";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("bruteforce", "primefactors", "practical");
    }
}
