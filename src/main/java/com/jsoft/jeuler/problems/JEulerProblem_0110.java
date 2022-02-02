package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class JEulerProblem_0110 extends EulerSolver {

    public JEulerProblem_0110(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        long TARGET = 4 * NumericHelper.ONE_MILLION_INT;
        long noOfDivisorsTarget = 2 * TARGET - 1;
        int LIMIT = 50000;

        BigInteger targetNAnswer = BigInteger.ZERO;

        for (long x = noOfDivisorsTarget; x < noOfDivisorsTarget + LIMIT; x++) {
            Map<Long, Integer> primeFactors = NumericHelper.getPrimeFactors(x);
            List<Long> exponents = new ArrayList();

            for (Map.Entry<Long, Integer> e : primeFactors.entrySet()) {
                for (int v = 0; v < e.getValue(); v++) {
                    exponents.add(e.getKey());
                }
            }

            if (exponents.size() < 9) continue;
            if (exponents.contains(2l)) continue;

            Collections.sort(exponents, Collections.reverseOrder());

            //Log the exponents
//            boolean isFirst = true;
//            StringBuilder sb = new StringBuilder();
//            sb.append("[");
//            for (long v : exponents) {
//                if (!isFirst) {
//                    sb.append(", ");
//                }
//                sb.append(v);
//                isFirst = false;
//            }
//            sb.append("] - ");

            //Calculate the target N square.
            BigInteger targetN = BigInteger.ONE;
            int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47};
            int primeIndex = 0;
            for (long v : exponents) {
                BigInteger primePower = BigInteger.valueOf(primes[primeIndex]).pow((int)(v-1)/2);
                targetN = targetN.multiply(primePower);
                primeIndex++;
            }

//            sb.append(targetN.toString());
//            System.out.printf("%d : %s\n", x, sb.toString());

            if(targetNAnswer.toString().equals("0")) {
                targetNAnswer = new BigInteger(targetN.toString());
            } else if(targetNAnswer.compareTo(targetN) == 1) {
                targetNAnswer = new BigInteger(targetN.toString());
            }
        }
        return targetNAnswer.toString();
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/thread=108";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}
