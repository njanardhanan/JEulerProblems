package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;
import org.omg.PortableServer.LIFESPAN_POLICY_ID;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class JEulerProblem_0108 extends EulerSolver {

    public JEulerProblem_0108(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {

        System.out.println("Answer by reverse engineering method : " + solve1());

        /**
         * Couple of observations:
         *
         * 1/X+1/Y=1/N is equivalent to (X-N)(Y-N)=N^2
         *
         * if the prime factorization of N is N = p1^i1*p2^i2*...*pk^ik
         * then no. of divisor for N is (2*i1+1)(2*i2+1)...(2*ik+1)
         *
         * Let N = p1^k1 * p2^k2
         * then N^2 = p1^2k1 * p2^2k2
         * then no. of divisor for N = (k1+1)(k2+1)
         * then no. of divisor for N^2 = (2k1+1)(2k2+1)
         *
         * Refer : https://primes.utm.edu/glossary/page.php?sort=tau
         *
         * Number of distinct solutions = ((Number of divisor of N^2) + 1) / 2
         *
         * Example :
         * N = 4
         * N^2 = 16
         * divisors of N^2 = 1, 2, 4, 8 and 16.
         * which is when grouped = (1,16), (2,8) and 4
         * Therefore, distinct solutions are
         *
         * #1   1/(1+N) + 1/(16+N) = 1/(1+4) + 1/(16+4) = 1/5 + 1/20 = 1/4
         * #2   1/(2+N) + 1/(8+N) = 1/(2+4) + 1/(8+4) = 1/6 + 1/12 = 1/4
         * #3   1/(4+N) + 1/(4+N) = 1/(4+4) + 1/(4+4) = 1/8 + 1/8 = 1/4
         *
         */

        long LIMIT = 1000;

        long n = 4;
        long answer = 0;
        while(true) {
            Map<Long, Integer> primeFactors = NumericHelper.getPrimeFactors(n);

            //no. of distinct solutions
            int noOfDivisors = 1;
            for(int k : primeFactors.values()) {
                noOfDivisors = noOfDivisors * (2*k + 1);
            }

            int noOfSolutions = (noOfDivisors + 1) / 2;

            if(noOfSolutions > LIMIT) {
                System.out.println("No. of solution exceeded " + LIMIT + ", n = " + n);
                answer = n;
                break;
            }
            n++;
        }

        return Long.toString(answer);
    }

    private String solve1() {
        long TARGET = 1000;
        long noOfDivisorsTarget = 2 * TARGET - 1;

        long targetN = 0;

        boolean found = false;

        for (long x = noOfDivisorsTarget; !found && x < noOfDivisorsTarget + 1000; x++) {
            Map<Long, Integer> primeFactors = NumericHelper.getPrimeFactors(x);
            List<Long> exponents = new ArrayList();


            for (Map.Entry<Long, Integer> e : primeFactors.entrySet()) {
                for (int v = 0; v < e.getValue(); v++) {
                    exponents.add(e.getKey());
                }
            }

            if (exponents.size() < 5) continue;
            if (exponents.contains(2l)) continue;

            Collections.sort(exponents, Collections.reverseOrder());

            //Log the exponents
            boolean isFirst = true;
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for (long v : exponents) {
                if (!isFirst) {
                    sb.append(", ");
                }
                sb.append(v);
                isFirst = false;
            }
            sb.append("] - ");

            //Calculate the target N square.
            long targetNSquare = 1;
            int[] primes = {2, 3, 5, 7, 11, 13, 17};
            int primeIndex = 0;
            for (long v : exponents) {
                targetNSquare = targetNSquare * (long)Math.pow(primes[primeIndex], v-1);
                primeIndex++;
            }

            sb.append(targetNSquare);

            targetN = (long) Math.sqrt(targetNSquare);
            if(targetN * targetN == targetNSquare) {
                sb.append(" - Perfect square -");
                sb.append(targetN);
            }

            System.out.printf("%d : %s\n", x, sb.toString());
            found = true;


        }
        return Long.toString(targetN);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/thread=108";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}
