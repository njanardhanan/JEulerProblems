package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.ArrayList;
import java.util.List;

public class JEulerProblem_0051 extends EulerSolver {

    public JEulerProblem_0051(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {

        //FACTS:

        //1. Only 6 digit number with 3 repeating digit will generate 8 family primes, others will
        //divide by 3 because it's digitsum module 3 will be 0.

        //2. The repeating digit must have to be either 0 or 1 or 2 to generate 8 family primes.

        boolean[] primes = PrimeNumberHelper.sieveOfEratosthenes(NumericHelper.ONE_MILLION_INT);

        boolean found = false;
        int answer = 0;
        for(int i=100000; i<999999 && !found; i++) {
            if(primes[i]) {
                for(int d=0; d<3; d++) {
                    if (doesHaveThreeRepeatedDigit(i, d)) {
                        if (isEightPrimeValueFamily(primes, i, d)) {
                            found = true;
                            answer = i;
                            break;
                        }
                    }
                }
            }
        }


        return Integer.toString(answer);
    }

    private boolean doesHaveThreeRepeatedDigit(int number, int digit) {
        int x = number % 10;
        if(x == digit) return false;

        number = number/10;
        long c = Integer.toString(number).chars().filter(ch -> Character.getNumericValue(ch) == digit).count();
        return c == 3;
    }

    private boolean isEightPrimeValueFamily(boolean[] primes, int n, int d) {

        List<Integer> generatedNumbers = new ArrayList();
        String sNumber = Integer.toString(n);
        for(int i=0; i<10; i++) {
            int tmp = Integer.parseInt(sNumber.replace(itoa(d), itoa(i)));
            if(Integer.toString(tmp).length() == 6) { //Check 6 digit number
                generatedNumbers.add(tmp);
            }
        }

        int count = 0;
        for(int p : generatedNumbers) {
            if(primes[p]) {
                count++;
            }
        }

        return count == 8;
    }

    private char itoa(int d) {
        return (char) ('0' + d);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=65";
    }
}
