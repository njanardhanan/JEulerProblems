package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.ArrayList;
import java.util.List;


//Refer Problem 31 and 76
public class JEulerProblem_0077 extends EulerSolver {

    public JEulerProblem_0077(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {

        List<Integer> primes = new ArrayList();
        primes.add(2);
        for(int x=3; x<100; x+=2) {
            if(PrimeNumberHelper.isPrime(x)) {
                primes.add(x);
            }
        }

        int answer = 0;
        for(int target = 10; target<100; target++) {
            //get index
            int index = 0;
            while(primes.get(index) < target) {
                index++;
            }

            int ways = countWays(primes, index, target);
            if(ways > 5000) {
                answer = target;
                break;
            }
        }

        return Integer.toString(answer);
    }

    public int countWays( List<Integer> S, int m, int n ) {
        // table[i] will be storing the number of solutions for
        // value i. We need n+1 rows as the table is constructed
        // in bottom up manner using the base case (n = 0)
        int table[]=new int[n+1];

        // Base case (If given value is 0)
        table[0] = 1;

        // Pick all coins one by one and update the table[] values
        // after the index greater than or equal to the value of the
        // picked coin
        for(int i=0; i<m; i++)
            for(int j=S.get(i); j<=n; j++)
                table[j] += table[j-S.get(i)];

        return table[n];
    }

    @Override
    public String getProblemStatement() {
        return "How many different ways can one hundred be written as a sum of at least two positive integers?";
    }
}
