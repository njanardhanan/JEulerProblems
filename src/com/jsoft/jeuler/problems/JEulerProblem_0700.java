package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

public class JEulerProblem_0700 extends EulerSolver {

    public JEulerProblem_0700(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        /**
         * This algorithm was based on realising that the difference between the first two Eulercoins found is
         * 4503599627370517 mod 1504170715041707
         *
         * Then successive Eulercoins will have the same difference d until Eulercoin < d.
         * At this point the new difference is  d mod EulerCoin .
         *
         * i.e. secondCoin = firstCoin - (MODVALUE % firstCoin)
         * and now firstCoin will become MODVALUE and secondCoin will become firstCoin
         *
         * So it's quite like Euclidean algorithm.
         */

        long firstCoin = 1504170715041707L;
        long modValue = 4503599627370517L;
        //System.out.println("Second Coin " + (firstCoin - (4503599627370517L % firstCoin)));
        long secondCoin = firstCoin - (modValue % firstCoin);
        long ans = firstCoin + secondCoin;
        while(secondCoin > 1) {
            modValue = firstCoin;
            firstCoin = secondCoin;
            secondCoin = firstCoin - (modValue % firstCoin);
            //System.out.println(secondCoin);
            ans += secondCoin;
        }
        return Long.toString(ans);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=700\n\n" +
                "Leonhard Euler was born on 15 April 1707.\n" +
                "\n" +
                "Consider the sequence 1504170715041707 * n mod 4503599627370517.\n" +
                "An element of this sequence is defined to be an Eulercoin if it is strictly smaller than all previously found Eulercoins.\n" +
                "For example, the first term is 1504170715041707 which is the first Eulercoin. The second term is 3008341430083414 which is greater than 1504170715041707 so is not an Eulercoin. However, the third term is 8912517754604 which is small enough to be a new Eulercoin.\n" +
                "The sum of the first 2 Eulercoins is therefore 1513083232796311.\n" +
                "Find the sum of all Eulercoins.";
    }
}
