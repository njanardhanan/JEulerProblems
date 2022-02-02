package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.List;

public class JEulerProblem_0234 extends EulerSolver {

    public JEulerProblem_0234(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        long LIMIT = 999966663333L;
        List<Long> primes = PrimeNumberHelper.sieveOfEratosthenesAsLongList(NumericHelper.ONE_MILLION_INT * 2);

        long total = 0;
        boolean done = false;
        for(int i=0; i<primes.size() && !done; i++) {
            long x = primes.get(i);
            long y = primes.get(i+1);

            long from = (x*x)+1;
            long to = (y*y)-1;

            if(to > LIMIT) {
                to = LIMIT;
                done = true;
            }

            long primeOneSum = sumOfTheRange(from, to, x);
            long primeTwoSum = sumOfTheRange(from, to, y);
            long primeOneTwoSum = sumOfTheRange(from, to, x*y);

            long tmpTotal = primeOneSum + primeTwoSum - ( 2 * primeOneTwoSum);

            total += tmpTotal;

        }

        return Long.toString(total);
    }

    public String solveTry() {

        int LIMIT = 1000;
        List<Integer> primes = PrimeNumberHelper.sieveOfEratosthenesAsList((int) Math.sqrt(LIMIT) * 2);


        int total = 0;
        boolean done = false;
        for(int i=0; i<primes.size() && !done; i++) {
            int x = primes.get(i);
            int y = primes.get(i+1);

            int lowerLimit = (x*x)+1;
            int upperLimit = (y*y)-1;

            if(upperLimit > LIMIT) {
                upperLimit = LIMIT;
                done = true;
            }

            int lowerLimitIndexX = lowerLimit - ((lowerLimit % x) == 0 ? x : (lowerLimit % x));
            int lowerLimitIndexY=  lowerLimit - ((lowerLimit % y) == 0 ? y : (lowerLimit % y));

            int tmpTotal = 0;
            while(true) {
                if(lowerLimitIndexX + x <= upperLimit) {
                    lowerLimitIndexX += x;
                    if(lowerLimitIndexX % y != 0) {
                        tmpTotal += lowerLimitIndexX;
                    }
                }

                if(lowerLimitIndexY + y <= upperLimit) {
                    lowerLimitIndexY += y;
                    if(lowerLimitIndexY % x != 0) {
                        tmpTotal += lowerLimitIndexY;
                    }
                }

                if(lowerLimitIndexX + x > upperLimit &&
                        lowerLimitIndexY + y > upperLimit) {
                    break;
                }
            }

            total += tmpTotal;

        }

        return Integer.toString(total);
    }

    private long sumOfTheRange(long from, long to, long diff) {
        //Lowest number that is divisible by 'diff' and greater than 'from'
        if(from % diff != 0) {
            from = from + diff - (from%diff);
        }

        //Greatest number that is divisible by 'diff' and lesser than 'to'
        if(to % diff != 0) {
            to = to - (to%diff);
        }

        //Calculate the number of terms.
        // no. of terms = ((lastTerm - firstTerm)/ difference) + 1
        long noOfTerms = ((to-from) / diff) + 1;

        //Calculate the sum of an arithmetic sequence
        // sum = noOfTerms ( firstTerm + lastTerm) / 2
        long sum = noOfTerms * (to+from) / 2;

        return sum;
    }


    @Override
    public String getProblemStatement() {
        return  "https://projecteuler.net/problem=234";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}
