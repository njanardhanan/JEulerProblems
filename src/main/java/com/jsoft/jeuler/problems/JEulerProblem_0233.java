package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JEulerProblem_0233 extends EulerSolver {

    public JEulerProblem_0233(int problemNumber) {
        super(problemNumber);
    }

    private final static long LIMIT = (long)Math.pow(10, 11);
    private final static int PRIME_LIMIT = (int)Math.pow(10, 7);
    private final static int NO_OF_LATTICE_POINTS = 420/4;
    private final static int VALUE = 52; //Solve 2*i + 1 = 420/4 as per Jacobi's Two Square Theorem:

    @Override
    public String solve() {
        /**
         * http://oeis.org/A046109
         *
         * Jacobi's Two Square Theorem:
         * The number of representations of a positive integer as the sum of two squares is equal to four times the difference of the numbers of divisors congruent to 1 and 3 modulo 4.
         *
         * Which also means:
         * (Python)
         *
         * from sympy import factorint
         *
         * def a(n):
         *
         *     r = 1
         *
         *     for p, e in factorint(n).items():
         *
         *         if p%4 == 1: r *= 2*e + 1
         *
         *     return 4*r if n > 0 else 0
         *
         */


        List<Integer> allPrimes = PrimeNumberHelper.sieveOfEratosthenesAsList(PRIME_LIMIT);

        //Sieve list of prime congruent 1 mod 4
        List<Integer> primeCongruentOneModFour = new ArrayList<>();
        for(int n : allPrimes) {
            if (n%4 == 1) {
                primeCongruentOneModFour.add(n);
            }
        }

        /**
         * Find possible exponents such that number of Lattice points on a circle is 420.
         * The ask here is to find N with 420 lattice points.
         * By reverse engineering Jacobi's two square theorem.
         * no. of lattice points = 4 * r, i.e.
         * 420 = 4 * r
         * Therefore, r = 105;
         * Also, r = 2*i + 1
         * therefore, i = 52
         */
        List<List<Integer>> exponentList = new ArrayList<>();
        findPossibleExponents(1, new ArrayList<>(), exponentList);
        System.out.println(exponentList);

        List<Long> result = new ArrayList<>();
        BigInteger ans = BigInteger.ZERO;
        for(List<Integer> exponents : exponentList) {
            if (exponents.size() == 3) {
                long val = 1;
                for(int x=0; x<primeCongruentOneModFour.size()-2; x++) {
                    long xV = (long)Math.pow(primeCongruentOneModFour.get(x), exponents.get(0));
                    if (val * xV > LIMIT) break;
                    if (val * xV * (long)Math.pow(primeCongruentOneModFour.get(x+1), exponents.get(1)) > LIMIT) break;
                    val *= xV;
                    for(int y=x+1; y<primeCongruentOneModFour.size()-1; y++) {
                        long yV = (long)Math.pow(primeCongruentOneModFour.get(y), exponents.get(1));
                        if (val * yV > LIMIT) break;
                        if (val * (long)Math.pow(primeCongruentOneModFour.get(y+1), exponents.get(2)) > LIMIT) break;
                        val *= yV;
                        for(int z=y+1; z<primeCongruentOneModFour.size(); z++) {
                            long zV = (long)Math.pow(primeCongruentOneModFour.get(z), exponents.get(2));
                            if (val * zV > LIMIT) break;
                            result.add(val * zV);
                            ans = ans.add(BigInteger.valueOf(val * zV));
                        }
                        val /= yV;
                    }
                    val /= xV;
                }
            }


            if (exponents.size() == 2) {
                long val = 1;
                for(int x=0; x<primeCongruentOneModFour.size()-2; x++) {
                    long xV = (long)Math.pow(primeCongruentOneModFour.get(x), exponents.get(0));
                    if (val * xV > LIMIT) break;
                    if (val * xV * (long)Math.pow(primeCongruentOneModFour.get(x+1), exponents.get(1)) > LIMIT) break;
                    val *= xV;
                    for(int y=x+1; y<primeCongruentOneModFour.size()-1; y++) {
                        long yV = (long)Math.pow(primeCongruentOneModFour.get(y), exponents.get(1));
                        if (val * yV > LIMIT) break;
                        result.add(val * yV);
                        ans = ans.add(BigInteger.valueOf(val * yV));
                    }
                    val /= xV;
                }
            }

            //This exponent will not contribute to the answer, but added this code for code completion.
            if (exponents.size() == 1) {
                long val = 1;
                for(int x=0; x<primeCongruentOneModFour.size()-2; x++) {
                    long xV = (long)Math.pow(primeCongruentOneModFour.get(x), exponents.get(0));
                    if (val * xV > LIMIT) break;
                    result.add(val * xV);
                    ans = ans.add(BigInteger.valueOf(val * xV));
                }
            }
        }

        //Sort the list so that we can quickly break from the below loop.
        Collections.sort(result);

        //Include non primeOneModFour factors and their multiples.
        for(long n : result) {
            int i=2;
            if (n * i > LIMIT) break;
            while(true) {
                long v = n*i;
                if (v > LIMIT) break;
                if (isValidPrimeMultiple(primeCongruentOneModFour, i)) {
                    ans = ans.add(BigInteger.valueOf(v));
                }
                i++;
            }
        }

        //271204031455541309
        return ans.toString();
    }

    private boolean isValidPrimeMultiple(List<Integer> primeConguventOneModFour, int v) {
        for(int p : primeConguventOneModFour) {
            if (v == p) return false;
            if (v%p == 0) return false;
            if (p > v) break;;
        }
        return true;
    }

    private void findPossibleExponents(int r, List<Integer> list, List<List<Integer>> result) {
        if (r > NO_OF_LATTICE_POINTS) return;
        if (r == NO_OF_LATTICE_POINTS) {
            result.add(new ArrayList<>(list));
            return;
        }
        for (int i=1; i<=VALUE; i++) {
            list.add(i);
            findPossibleExponents(r * (2*i + 1), list, result);
            list.remove(list.size() - 1);
        }
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=233\n" +
                "Let f(N) be the number of points with integer coordinates that are on a circle passing through (0,0), (N,0),(0,N), and (N,N).\n" +
                "\n" +
                "It can be shown that f(10000)=36.\n" +
                "\n" +
                "What is the sum of all positive integers N≤10^11 such that f(N)=420?";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("prime", "jacobi two square theorem", "reverser engineering", "oeis", "A046109");
    }
}
