package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.LinkedList;
import java.util.Queue;

public class JEulerProblem_0113 extends EulerSolver {

    public JEulerProblem_0113(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        long ans = 0;
        int noOfDigits = 100;
        /**
         *   Ran the brute force method and found that number of increasing/decreasing numbers are nothing but nCr
         *   Play with the brute force method to understand more.
         *   And also no. of increasing is same as decreasing for numbers starting with 1 to 8.
         *   i.e.,
         *   No. of increasing numbers starting with 0 = No. of decreasing numbers starting with 9
         *   No. of increasing numbers starting with 1 = No. of decreasing numbers starting with 8
         *   No. of increasing numbers starting with 2 = No. of decreasing numbers starting with 7
         *   and so on and so forth...
         */

        //Run nCr and find increasing number count for numbers starting in 1 to 8.
        for(int i=8; i>=1; i--) {
            for (int d = 0; d < noOfDigits; d++) {
                ans += NumericHelper.binomialCoeff(i + d, i);
            }
        }
        //Multiply by 2 to include decreasing number count
        ans *= 2;

        //Since the number start with 0 is not allowed, we are calculating decreasing number for number starting with 9 it separately.
        for (int d = 0; d < noOfDigits; d++) {
            ans += NumericHelper.binomialCoeff(9 + d, 9);
        }
        //Now add number of decreasing number for number starting with 9
        //nCr(9,1)=9, so just add no. of digits.
        ans += noOfDigits;

        //We added numbers with same digits (1, 11, 333.. etc) twice, so subtract it once.
        ans -= (noOfDigits * 9);

        return Long.toString(ans);
    }

    public String solveBruteForce() {
        int ans = 0;
        int d = 3;
        for(int i=1; i<10; i++) {
            int dec = countDecreasingNumber(i, d);
            //System.out.printf("Dec : %d = %d\n", i, dec);
            int inc = countIncreasingNumber(i, d);
            //System.out.printf("Inc : %d = %d\n", i, inc);
            ans +=  dec + inc;
        }
        //System.out.println(ans);
//        ans *= 2;
//        ans += countDecreasingNumber(9, d);
//        ans += countIncreasingNumber(9, d);
        ans -= (d * 9);

        // 8 - 67986
        return Integer.toString(ans);
    }

    private int countDecreasingNumber(int n, int d) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(n);
        int x = String.valueOf(n).length();
        int count = 1;
        while(x < d) {
            int s = queue.size();
            for(int i=0; i<s; i++) {
                int m = queue.poll();
                for (int y=0; y<=m; y++) {
                    queue.add(y);
                    //System.out.println(m * 10 + y);
                }
            }
            x++;
            count += queue.size();
        }
        return count;

    }

    private int countIncreasingNumber(int n, int d) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(n);
        int x = String.valueOf(n).length();
        int count = 1;
        while(x < d) {
            int s = queue.size();
            for(int i=0; i<s; i++) {
                int m = queue.poll();
                for (int y=m; y<=9; y++) {
                    //queue.add(m * 10 + y);
                    queue.add(y);
                    //System.out.println(m * 10 + y);
                }
            }
            x++;
            count += queue.size();
        }
        return count;

    }

    @Override
    public String getProblemStatement() {
        return "Working from left-to-right if no digit is exceeded by the digit to its left it is called an increasing number; for example, 134468.\n" +
                "\n" +
                "Similarly if no digit is exceeded by the digit to its right it is called a decreasing number; for example, 66420.\n" +
                "\n" +
                "We shall call a positive integer that is neither increasing nor decreasing a \"bouncy\" number; for example, 155349.\n" +
                "\n" +
                "As n increases, the proportion of bouncy numbers below n increases such that there are only 12951 numbers below one-million that are not bouncy and only 277032 non-bouncy numbers below 10^10.\n" +
                "\n" +
                "How many numbers below a googol (10^100) are not bouncy?";
    }
}
