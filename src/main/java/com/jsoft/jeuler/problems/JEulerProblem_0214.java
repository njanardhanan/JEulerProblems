package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.List;

public class JEulerProblem_0214 extends EulerSolver {

    public JEulerProblem_0214(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        /**
         * Prime number case.
         *
         * Euler phi function is
         *
         * => phi(p^n) = p^(n-1) * (p-1)   ...(1)
         *
         * => phi(n*m) = phi(n) * phi(m)   ...(2)
         *
         * => phi(12) = 4
         *
         * => phi(2^2 * 3^1) = phi(2^2) * phi(3^1)   ...applying (2)
         *
         * => phi(2^2 * 3^1) = (2^(2-1) * (2-1)) * (3^(1-1) * (3-1))   ...applying (1)
         *
         * => phi(2^2 * 3^1) = 2 * 1 * 1 * 2
         *
         * Therefore :: phi(12) = 4
         *
         *
         */

        int LIMIT = 40 * NumericHelper.ONE_MILLION_INT;

        //Sieve of euler phi
        double[] eulerPhi = new double[LIMIT];
        for(int i=1; i<LIMIT; i++) {
            eulerPhi[i] = i * 1.0;
        }

        for(int i=2; i<LIMIT; i++) {

            if(eulerPhi[i] != i * 1.0)  continue;

            eulerPhi[i] = i-1.0;
            for(int p=i+i; p<LIMIT; p+=i) {
                eulerPhi[p] = eulerPhi[p] * (1.0 - (1.0/(i * 1.0)));
            }
        }

        //Calculating the chain length
        int[] eulerPhiChainLength = new int[LIMIT];
        for(int i=2; i<LIMIT; i++) {
            int currentValue = (int)eulerPhi[i];
            //init the chain length to 2.
            eulerPhiChainLength[i] = 2;

            if (eulerPhiChainLength[currentValue] != 0) {
                eulerPhiChainLength[i] += eulerPhiChainLength[currentValue] - 1;
            } else {

                //System.out.print(i + " = (" + currentValue + ", ");
                while (currentValue != 1) {
                    currentValue = (int) eulerPhi[currentValue];
                    if (eulerPhiChainLength[currentValue] != 0) {
                        eulerPhiChainLength[i] += eulerPhiChainLength[currentValue] - 1;
                        break;
                    } else {
                        eulerPhiChainLength[i]++;
                        //System.out.print(currentValue + ", ");
                    }
                }
            }

            //System.out.println(i + " - " + eulerPhiChainLength[i]);
        }

        List<Integer> primes = PrimeNumberHelper.sieveOfEratosthenesAsList(LIMIT);

        long ans = 0;
        for(int p : primes) {
            if(eulerPhiChainLength[p] == 25) {
                //System.out.printf("%d - %d\n", p, 25);
                ans+=p;
            }
        }



        return Long.toString(ans);
    }


    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=214";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}
