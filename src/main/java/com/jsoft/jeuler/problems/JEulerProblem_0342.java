package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JEulerProblem_0342 extends EulerSolver {

    public JEulerProblem_0342(int problemNumber) {
        super(problemNumber);
    }

    private final static long LIMIT = (long)Math.pow(10, 10);

    @Override
    public String solve() {
        long result = 0;
        for(int phi=2; phi<=4_000_000; phi++) {
            Map<Integer, Integer> primeFactors = NumericHelper.getPrimeFactors(phi);

            //Cube it
            Set<Integer> ketSet = primeFactors.keySet();
            for(int p : ketSet) {
                primeFactors.put(p, primeFactors.get(p) * 3);
            }
            long r = findN(primeFactors);
            if (r == 0) continue;
            if (r < LIMIT) {
                //System.out.printf("%d\n", r);
                result += r;
            }
        }

        return String.valueOf(result);
    }

    private long findN(Map<Integer, Integer> factors) {
        /**
         * Reverse engineering Euler's totient function
         * i.e., from phi we have to calculate the n.
         */
        long divider = 1;
        Set<Integer> ketSet = factors.keySet();
        List<Integer> primes = new ArrayList<>(ketSet);
        /**
         * Sorting is necessary to eliminate unnecessary primes.
         * For example
         * for n=225
         * n^2 = 50626 = 3^4 * 5^4
         *
         * when calculating phi
         * phi(3^4 * 5^4) = 3^3 * (3-1) * 5^3 * (5-1)
         *                = 3^3 * 2 * 5^3 * 2^2
         *                = 2^3 * 3^3 * 5^3
         *
         * if you observe, prime 2 is not there in original n
         * so when reverse engineering we have to eliminate 2.
         * so we have to process from higher prime to lower and eliminate lower primes where ever possible.
         *
         */
        Collections.sort(primes, Collections.reverseOrder());
        for(int p : primes) {
            if (factors.get(p) <= 0) continue;
            divider *= (p-1);
            for(int k : ketSet) {
                if (divider == 1) break;
                while (divider % k == 0) {
                    if (factors.get(k) > 0) {
                        factors.put(k, factors.get(k) - 1);
                        divider /= k;
                    } else {
                        return 0;
                    }
                }
            }
            factors.put(p, factors.get(p) + 1);
        }

        //cancel our the divider.
        for(int p : ketSet) {
            if (factors.get(p) <= 0) continue;

            if (factors.get(p)%2 != 0) {
                return 0;
            }
            factors.put(p, factors.get(p)/2);
        }

        if (divider > 1) {
            return 0;
        }

        long result = 1;
        for(int p : ketSet) {
            result *= (long)Math.pow(p, factors.get(p));
        }

        return result;
    }


    @Override
    public String getProblemStatement() {
        return "Consider the number 50.\n" +
                "50^2 = 2500 = 2^2 × 5^4, so φ(2500) = 2 × 4 × 5^3 = 8 × 5^3 = 2^3 × 5^3. 1\n" +
                "So 2500 is a square and φ(2500) is a cube.\n" +
                "\n" +
                "Find the sum of all numbers n, 1 < n < 10^10 such that φ(n^2) is a cube.\n" +
                "\n" +
                "φ denotes Euler's totient function.";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("euler", "totient", "reverse-engineering", "oeis", "A114076");
    }
}
