package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.List;

public class JEulerProblem_0243 extends EulerSolver {

    public JEulerProblem_0243(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        /**
         * Refer : https://en.wikipedia.org/wiki/Euler%27s_totient_function
         *
         * We shall call a fraction that cannot be cancelled down a resilient fraction.
         * R(12) = 4/11
         * i.e., 4 numbers is relatively prime to 12 that are less than 12.
         * which is 1, 5, 7, and 11.
         *
         * Euler's totient function counts the positive integers up to a given
         * integer n that are relatively prime to n.
         *
         **/

        long answer = 0;
        double limit = 15499.0/94744.0;
        List<Integer> primeList = PrimeNumberHelper.sieveOfEratosthenesAsList(NumericHelper.ONE_MILLION_INT);
        int primeIndex = 0;
        long n = 1;
        double rOfD = 0.0;
        while(true) {
            int nextPrime = primeList.get(primeIndex);
            n = n * nextPrime;
            long phi = NumericHelper.phi(n);
            rOfD = (phi * 1.0)/((n-1) * 1.0);
            if( rOfD < limit) {
                break;
            }
            primeIndex++;
        }

        System.out.println(n + " : " + rOfD + " : " + limit);

        n = n / primeList.get(primeIndex);
        System.out.println(n + " : " + rOfD + " : " + limit);
        //primeIndex--;
        //n = n / primeList.get(primeIndex);
        long currentN = n;
        int i = 2;
        while(true) {
            long phi = NumericHelper.phi(n);
            rOfD = (phi * 1.0)/((n-1) * 1.0);
            if( rOfD < limit) {
                answer = n;
                break;
            }
            n = currentN * i;
            i++;
        }
        System.out.println(answer + " : " + rOfD  + " : " + limit);

        return Long.toString(answer);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/thread=243";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}
