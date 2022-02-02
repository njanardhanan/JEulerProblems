package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.ArrayList;
import java.util.List;


//Refer Problem 31, 76 and 77
public class JEulerProblem_0078 extends EulerSolver {

    public JEulerProblem_0078(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {

        /**
         * This problem cannot be solved by dynamic programing of coin count/partition.
         * We need generating function to solve this.
         * Refer :
         * https://en.wikipedia.org/wiki/Partition_%28number_theory%29
         * https://en.wikipedia.org/wiki/Pentagonal_number_theorem
         * https://en.wikipedia.org/wiki/Pentagonal_number
         *
         * Formula for generating function:
         * p(n) = p(n-1) + p(n-2) - p(n-5) - p(n-7) + p(n-12) ....
         *
         * where
         * p(n) is partitioning function.
         * n is number of ways to partition.
         * and the number is Generalized pentagonal numbers.
         *
         * pentagonal number, pen(n) = (3n^2 - n)/2
         * with n taking values in the sequence 0, 1, −1, 2, −2, 3, −3, 4...,
         *
         */

        //Generate Generalized pentagonal numbers.
        List<Integer> generalizedPentagonalNumbers = new ArrayList();
        for(int i=0; i<=500; i++) {
            if (i == 0) {
                generalizedPentagonalNumbers.add(getPentagonalNumber(i));
            } else {
                generalizedPentagonalNumbers.add(getPentagonalNumber(i));
                generalizedPentagonalNumbers.add(getPentagonalNumber(i * -1));
            }
        }

        //Generate integer partition.
        List<Integer> integerPartition = new ArrayList();

        //p(0) = 1
        //p(1) = 1
        integerPartition.add(1);
        integerPartition.add(1);

        int answer = 0;

        for(int n=2; ; n++) {
            //assign p(n) = 0 initially
            int noOfPartition = 0;
            for(int k=1; k<=1000; k++) {
                int pentagonalNumber = generalizedPentagonalNumbers.get(k);
                if(pentagonalNumber <= n) {
                    int sign = ((k-1) % 4 > 1) ? -1 : 1;
                    noOfPartition += (sign * integerPartition.get(n - pentagonalNumber));
                } else {
                    break;
                }
            }
            //Module by one million.
            noOfPartition %= NumericHelper.ONE_MILLION_INT;
            integerPartition.add(noOfPartition);
            if(noOfPartition == 0) {
                answer = n;
                break;
            }
        }

        return Integer.toString(answer);
    }

    private int getPentagonalNumber(int n) {
        return (n * (3 * n - 1))/2;
    }



    @Override
    public String getProblemStatement() {
        return "Find the least value of n for which p(n) is divisible by one million, where p(n) in integer partition.";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}
