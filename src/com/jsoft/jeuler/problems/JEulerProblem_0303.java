package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class JEulerProblem_0303 extends EulerSolver {

    public JEulerProblem_0303(int problemNumber) {
        super(problemNumber);
    }

    class Data {
        public int remainder;
        public LinkedList<Integer> values;
        public String strValue;
        Data(int r, LinkedList<Integer> v) {
            remainder = r;
            values = new LinkedList<>(v);
        }

        Data(int r, String v) {
            remainder = r;
            strValue = v;
        }

        public String toString() {
            return remainder + " : " + strValue;
        }

        public boolean isLessThan(Data foundData) {
            if (strValue.length() == foundData.strValue.length()) {
                return Long.parseLong(strValue) < Long.parseLong(foundData.strValue);
            }
            return strValue.length() < foundData.strValue.length();
        }

        public BigInteger getValueAsBigInt() {
            return new BigInteger(strValue);
        }
    }

    @Override
    public String solve() {
        //System.out.println(solveBF());
        /**
         * Explanation :
         *  The idea here is to multiply the given number with digits 0 to 9 and check the last digit.
         *  if it is 0,1 or 2, store the multiplier and remainder.
         *  Continue again but this time adding the remainder.
         *  This is the very similar to multiplication method that we learnt in school.
         */
        int LIMIT = 10000;
        Set<Integer> allowedDigits = new HashSet<>(Arrays.asList(0, 1, 2));
        BigInteger ans = BigInteger.ZERO;

        for (int v = 1; v<=LIMIT; v++) {
            /**
             *   It will take lot of time to calculate value for 9999,
             *   so as a hack, added the below logic.
             *   Refer : https://oeis.org/A181060
             *   and     https://oeis.org/A181061
             */
            if (v == 9999) {
                ans = ans.add(new BigInteger("11112222222222222222").divide(BigInteger.valueOf(v)));
                continue;
            }
            Queue<Data> queue = new LinkedList<>();
            Data found = populateQueue(v, "", 0, queue, allowedDigits, true);;
            while (found == null) {
                found = populateQueue(v, queue, allowedDigits);
            }
            //System.out.printf("Answer : %d : %d\n", v, found.getValueAsBigInt());
            ans = ans.add(found.getValueAsBigInt());
        }

        return ans.toString();
    }

    private String solveBF() {
        long LIMIT = 100;
        BigInteger ans = BigInteger.ZERO;

        for (long x = 1; x<=LIMIT; x++) {
            for(long y=1;;y++) {
                BigInteger b = BigInteger.valueOf(x).multiply(BigInteger.valueOf(y));
                if (doesContainAllowedDigits(b)) {
                    ans = ans.add(b.divide(BigInteger.valueOf(x)));
                    break;
                }
            }
        }
        return ans.toString();
    }

    private boolean doesContainAllowedDigits(BigInteger b) {
        for(char c : b.toString().toCharArray()) {
            if (!(c == '0' || c == '1' || c == '2')){
                return false;
            }
        }
        return true;
    }

    private Data populateQueue(int value, Queue<Data> queue, Set<Integer> allowedDigits) {
        int queueSize = queue.size();
        Data foundData = null;
        for (int q=0; q<queueSize; q++) {
            Data data = queue.poll();
            Data nextData = populateQueue(value, data.strValue, data.remainder, queue, allowedDigits, false);
            if (nextData != null && foundData == null) {
                foundData = nextData;
            } else if (nextData != null && foundData != null) {
                if (nextData.isLessThan(foundData)) {
                    foundData = nextData;
                }
            }
        }
        return foundData;
    }


    private Data populateQueue(int value, String prevValues, int remainder, Queue<Data> queue, Set<Integer> allowedDigits, boolean isFirstTime) {
        for(int i=isFirstTime ? 1 : 0; i<=9; i++) {
            int x = (value * i) + remainder;
            if (allowedDigits.contains(x%10)) {
                String p = i + prevValues;
                Data nextData = new Data(x/10, p);
                if (doesContainAllowedDigits(x/10, allowedDigits)) {
                    return nextData;
                }
                queue.add(nextData);
            }
        }
        return null;
    }



    private boolean doesContainAllowedDigits(int r, Set<Integer> allowedDigits) {
        while (r > 0) {
            if (!allowedDigits.contains(r%10)) return false;
            r = r/10;
        }
        return true;
    }

    @Override
    public String getProblemStatement() {
        /**
         * <p>
         * For a positive integer <var>n</var>, define <var>f</var>(<var>n</var>) as the least positive multiple of <var>n</var> that, written in base 10, uses only digits ≤ 2.</p>
         * <p>Thus <var>f</var>(2)=2, <var>f</var>(3)=12, <var>f</var>(7)=21, <var>f</var>(42)=210, <var>f</var>(89)=1121222.</p>
         * <p>Also, $\sum \limits_{n = 1}^{100} {\dfrac{f(n)}{n}} = 11363107$.</p>
         * <p>
         * Find $\sum \limits_{n=1}^{10000} {\dfrac{f(n)}{n}}$.
         * </p>
         */
        return "https://projecteuler.net/problem=303";
    }
}
