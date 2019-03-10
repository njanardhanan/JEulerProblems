package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class JEulerProblem_0087 extends EulerSolver {

    public JEulerProblem_0087(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        int LIMIT = 50000000;
        //int LIMIT = 500;
        int fourthRoot = (int)Math.pow(LIMIT, 1.0/4.0) + 1;
        int cubeRoot = (int)Math.pow(LIMIT, 1.0/3.0) + 1;
        int squareRoot = (int)Math.pow(LIMIT, 1.0/2.0) + 1;

        boolean[] primes = PrimeNumberHelper.sieveOfEratosthenes(squareRoot);
        List<Integer> primeList = new ArrayList();
        for(int i=0; i<squareRoot; i++) {
            if(primes[i]) {
                primeList.add(i);
            }
        }

        Set<Integer> myset = new HashSet();
        for(int a : primeList) {
            if(a>fourthRoot) break;
            for(int b : primeList) {
                if(b>cubeRoot) break;
                for(int c: primeList) {
                    if(c>squareRoot) break;

                    int x = c*c + b*b*b + a*a*a*a;
                    if(x<LIMIT) {
                        myset.add(x);
                        //System.out.printf("%d = %d^2 + %d^3 + %d^4\n", x,c,b,a);
                    }

                }
            }
        }

        return Integer.toString(myset.size());
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=65";
    }
}
