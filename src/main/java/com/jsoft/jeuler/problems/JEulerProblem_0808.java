package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.helper.StringHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class JEulerProblem_0808 extends EulerSolver {

    public JEulerProblem_0808(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        /**
         * A simple bruteforce will work.
         */
        List<Integer> primes = PrimeNumberHelper.sieveOfEratosthenesAsList(40 * NumericHelper.ONE_MILLION_INT);
        Set<String> primeSquare = new HashSet<>();
        TreeSet<Long> reversiblePrime = new TreeSet<>();
        for(int p : primes) {
            //int lastDigit = p%10;
            //if (lastDigit == 1 || lastDigit == 3 || lastDigit == 9) {
                BigInteger b = BigInteger.valueOf(p);
                //char c = b.toString().toCharArray()[0];
                //if (c == '1' || c == '3' || c == '9') {
                    String s = b.pow(2).toString();
                    if (!StringHelper.isPalindrome(s)) {
                        String r = StringHelper.reverse(s);
                        if (primeSquare.contains(s)) {
                            //System.out.println("Found : " + s);
                            reversiblePrime.add(Long.parseLong(s));
                        } else {
                            primeSquare.add(s);
                        }
                        if (primeSquare.contains(r)) {
                            //System.out.println("Found : " + r);
                            reversiblePrime.add(Long.parseLong(r));
                        } else {
                            primeSquare.add(r);
                        }
                    }
                //}
            //}
        }
        System.out.println(reversiblePrime);
        System.out.println(reversiblePrime.size());
        BigInteger b = BigInteger.ZERO;
        for(long l : reversiblePrime) {
            b = b.add(BigInteger.valueOf(l));
        }
        return b.toString();
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=808" +
                "Both 169 and 961 are the square of a prime. 169 is the reverse of 961.\n" +
                "\n" +
                "We call a number a reversible prime square if:\n" +
                "\n" +
                "It is not a palindrome, and\n" +
                "It is the square of a prime, and\n" +
                "Its reverse is also the square of a prime.\n" +
                "169 and 961 are not palindromes, so both are reversible prime squares.\n" +
                "\n" +
                "Find the sum of the first 50 reversible prime squares.";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("brute-force");
    }
}
