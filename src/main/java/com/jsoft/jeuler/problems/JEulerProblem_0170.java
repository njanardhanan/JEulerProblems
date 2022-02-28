package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.combinatorics.Generator;
import com.jsoft.jeuler.combinatorics.IGenerator;
import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class JEulerProblem_0170 extends EulerSolver {

    public JEulerProblem_0170(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        /**
         * Given:
         *  numOneX = factor * numOneY
         *  numTwoX = factor * numTwoY
         *  and,
         *      numOneX CONCAT numTwoX is pandigital.
         *      factor CONCAT numOneY CONCAT numTwoY is pandigital.
         *
         * Observation:
         *    1. factor should divide evenly both numOneX and numTwoX
         *    2. Maximum value for the factor will be GCD(numOneX, numTwoX)
         *
         * Plan:
         *   1. Permute a pandigital, and divide it into two parts, numOneX and numTwoX.
         *   2. Find a factor that divides both numOneX and numTwoX.
         *   3. Calculate numOneY and numTwoY.
         *   4. Check if factor CONCAT numOneY CONCAT numTwoY is pandigital.
         *   5. Return the Max value.
         *
         */
        List<Integer> p = Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        Iterator<List<Integer>> permIterator = Generator.permutation(p).simple().iterator();
        long maxPandigital = 0;
        while(permIterator.hasNext()) {
            List<Integer> perm = permIterator.next();
            //Number cannot start with zero
            if (perm.get(0) == 0) continue;
            //System.out.print(perm + " => ");
            for(int i=0; i<perm.size() - 1; i++) {
                //Number cannot start with zero
                if (perm.get(i+1) == 0) continue;
                long numOneX = toNumber(perm, 0, i);
                long numTwoX = toNumber(perm, i+1, perm.size()-1);
                //Get the maximum number that divides both numOne and numTwo.
                long gcd = NumericHelper.gcd(numOneX, numTwoX);

                for(long factor=1; factor<=gcd; factor+=1) {
                    if (numOneX%factor == 0 && numTwoX%factor == 0) {
                        long numOneY = numOneX / factor;
                        long numTwoY = numTwoX / factor;
                        if (isPandigital(factor, numOneY, numTwoY)) {
                            long v = toNumber(perm,0, perm.size()-1);
                            if (maxPandigital < v) {
                                System.out.println(factor + "*("+numOneY + ", " + numTwoY + ") = " + numOneX + numTwoX + " - " + v);
                                maxPandigital = v;
                            }
                        }
                    }
                }
            }
        }
        return Long.toString(maxPandigital);
    }

    private boolean isPandigital(long factor, long numOneY, long numTwoY) {
        String p = Long.toString(factor) + numOneY + numTwoY;
        if (p.length() != 10) return false;
        if (!p.contains("0")) return false;
        if (!p.contains("1")) return false;
        if (!p.contains("2")) return false;
        if (!p.contains("3")) return false;
        if (!p.contains("4")) return false;
        if (!p.contains("5")) return false;
        if (!p.contains("6")) return false;
        if (!p.contains("7")) return false;
        if (!p.contains("8")) return false;
        if (!p.contains("9")) return false;
        return true;
    }

    private long toNumber(List<Integer> n, int fromIndex, int toIndex) {
        long x = 0;
        for(int i=fromIndex; i<=toIndex; i++) {
            x = (x*10) + n.get(i);
        }
        return x;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=170\n" +
                "Take the number 6 and multiply it by each of 1273 and 9854:\n" +
                "\n" +
                "6 × 1273 = 7638\n" +
                "6 × 9854 = 59124\n" +
                "\n" +
                "By concatenating these products we get the 1 to 9 pandigital 763859124. We will call 763859124 the \"concatenated product of 6 and (1273,9854)\". Notice too, that the concatenation of the input numbers, 612739854, is also 1 to 9 pandigital.\n" +
                "\n" +
                "The same can be done for 0 to 9 pandigital numbers.\n" +
                "\n" +
                "What is the largest 0 to 9 pandigital 10-digit concatenated product of an integer with two or more other integers, such that the concatenation of the input numbers is also a 0 to 9 pandigital 10-digit numbers?";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("pandigital", "generate", "counting");
    }
}
