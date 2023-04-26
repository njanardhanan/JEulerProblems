package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JEulerProblem_0418 extends EulerSolver {

    public JEulerProblem_0418(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        /**
         * To minimize c/a, a, b and c must be close to the cubic root of 43!.
         * Using the prime factorization of 43!, I generate all factors 0.99999 43!^(1/3) < x < 1.00001 43!^(1/3).
         * Then for each pair of factors (x, y), if x and y divides 43! I compute z = 43!/(x*y).
         * I keep the best triplet (x, y, z) based on minimum c/a
         */
        boolean DEBUG = false;
        int LIMIT = 43;
        double offSet = 0.00001d;

        BigInteger fact = NumericHelper.factorialBigNumber(LIMIT);
        if (DEBUG) System.out.printf("%d! is %s\n", LIMIT, fact);

        double d = cubeRootForFactorial(LIMIT);
        if (DEBUG) System.out.printf("CubeRoot of %d! is %f\n", LIMIT, d);
        if (DEBUG) System.out.println(Integer.MAX_VALUE);

        long aMin = (long) (d * (1.0 - offSet));
        long cMax = (long) (d * (1.0 + offSet));
        if (DEBUG) System.out.println("aMin = " + aMin);
        if (DEBUG) System.out.println("cMax = " + cMax);
        if (DEBUG) System.out.println("Diff = " + (cMax - aMin));

        //Calculate the prime factors fo 43!
        Map<Integer, Integer> primeFactors = new HashMap<>();
        for (int i=2; i<=LIMIT; i++) {
            Map<Integer, Integer> p = NumericHelper.getPrimeFactors(i);
            for(Map.Entry<Integer, Integer> e : p.entrySet()) {
                primeFactors.put(e.getKey(), primeFactors.getOrDefault(e.getKey(), 0) + e.getValue());
            }
        }

        //Generate Divisor of 43! using prime factors between aMin to cMax
        List<Long> divisors = new ArrayList<>();
        generateDivisorsBetweenAandC(primeFactors, new ArrayList<>(primeFactors.keySet()), 0, 1L, divisors, aMin, cMax);
        Collections.sort(divisors);
        if (DEBUG) System.out.println("Divisors of " + LIMIT + ": " + divisors);
        if (DEBUG) System.out.println("Divisor size : " + divisors.size());

        Set<Long> divisorSet = new HashSet<>(divisors);
        BigDecimal minimalCDivideA = new BigDecimal(Double.MAX_VALUE);
        long ans = 0;
        for (int i=0; i< divisors.size(); i++) {
            for (int j=i+1; j< divisors.size(); j++) {
                long a = divisors.get(i);
                long c = divisors.get(j);
                long b = fact.divide(BigInteger.valueOf(a).multiply(BigInteger.valueOf(c))).longValue();
                if (a > b) continue;
                if (b > c) continue;
                if (divisorSet.contains(b)) {
                    BigDecimal bd = BigDecimal.valueOf(c).divide(BigDecimal.valueOf(a),10, RoundingMode.HALF_UP);
                    if (minimalCDivideA.compareTo(bd) > 0) {
                        ans = a + b + c;
                        if (DEBUG) System.out.printf("(%d, %d, %d) = %d - %s\n", a, b, c, ans, bd.toPlainString());
                        minimalCDivideA = bd;
                    }
                }
            }
        }

        return Long.toString(ans);
    }

    /**
     * Recursive method to generate divisors of a number using prime factors.
     * I used ChatGPT to get this code. I asked the below questions.
     *  1. how to get all divisors using prime factors of a number?
     *  2. how to do it in java?
     *
     *  I tweaked it little for this problem.
     *
     */
    private static void generateDivisorsBetweenAandC(Map<Integer, Integer> primeFactors, List<Integer> factors, int index, long current, List<Long> divisors, long a, long c) {
        if (index == factors.size()) {
            if (current < a) return;
            if (current > c) return;
            divisors.add(current);
            return;
        }

        int factor = factors.get(index);
        int exponent = primeFactors.get(factor);
        for (int i = 0; i <= exponent; i++) {
            generateDivisorsBetweenAandC(primeFactors, factors, index + 1, current, divisors, a,c);
            current *= factor;
        }
    }

    private double cubeRootForFactorial(int n) {
        double d = 1.0;
        for (int i=2; i<=n; i++) {
            d *= Math.cbrt(i);
        }
        return d;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=418/n" +
                "Let n be a positive integer. An integer triple (a, b, c) is called a factorisation triple of n if:\n" +
                "1 ≤ a ≤ b ≤ c\n" +
                "a·b·c = n.\n" +
                "Define f(n) to be a + b + c for the factorisation triple (a, b, c) of n which minimises c / a. One can show that this triple is unique.\n" +
                "\n" +
                "For example, f(165) = 19, f(100100) = 142 and f(20!) = 4034872.\n" +
                "\n" +
                "Find f(43!).";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("factorial", "prime factors", "divisors", "chatgpt", "cuberoot");
    }
}
