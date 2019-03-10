package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JEulerProblem_0104 extends EulerSolver {

    public JEulerProblem_0104(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        //Refer - Problem 25 - JEulerProblem_0025

//        List<BigInteger[]> fibList = Stream.iterate(new BigInteger[]{ BigInteger.ONE, BigInteger.ONE, BigInteger.ONE },
//                p->new BigInteger[]{ p[0].add(BigInteger.ONE), p[2], p[1].add(p[2]) })
//                .filter(p -> p[1].toString().trim().length() >= 100)
//                .filter(p -> NumericHelper.isPandigital1to9(p[1].toString().substring(p[1].toString().length()-9)))
//                .filter(p -> NumericHelper.isPandigital1to9(p[1].toString().substring(0, 9)))
//                .limit(1)
//                .collect(Collectors.toList());
//                //.forEach(p -> System.out.println(p[0] + " - " + p[1].toString().trim().length() + " : " + p[1]));
//                //.forEach(p->System.out.println(p[0] + " : " + p[2]));
//
//        if (fibList.size() != 1) {
//            System.out.println("Something wrong...");
//        }
//
//        System.out.println(fibList.get(0)[1].toString());


        /**
         * NOTE :
         *
         * Log(Fn) = n * Log(Golden ratio) - Log(sqrt(5))
         * Refer -  https://en.wikipedia.org/wiki/Fibonacci_number
         *
         *
         */

        double LOG_OF_GOLDEN_RATIO = 0.20898764024997873;
        double LOG_OF_SQRT_OF_FIVE = 0.3494850021680094;

//        double LOG_OF_F_541 = 2749 * LOG_OF_GOLDEN_RATIO - LOG_OF_SQRT_OF_FIVE;
//        System.out.printf("Log(F-541) = %.20f\n", LOG_OF_F_541);
//
//        System.out.println(LOG_OF_F_541 - (long)LOG_OF_F_541 + 8);
//
//        long val = (long)Math.pow(10, LOG_OF_F_541 - (long)LOG_OF_F_541 + 8);
//        System.out.printf("F-541 = %d\n", val);


        long f1 = 1;
        long f2 = 1;
        int index = 2;

        long last10Digits;
        long tail = 1000000000;


        while(true) {

            index++;
            last10Digits = (f1 + f2) % tail;

            if (NumericHelper.isPandigital1to9(Long.toString(last10Digits))) {
                double LOG_OF_F_INDEX = index * LOG_OF_GOLDEN_RATIO - LOG_OF_SQRT_OF_FIVE;
                long first10Digits = (long)Math.pow(10, LOG_OF_F_INDEX - (long)LOG_OF_F_INDEX + 8);
                if (NumericHelper.isPandigital1to9(Long.toString(first10Digits))) {
                    break;
                }
            }

            f2 = f1;
            f1 = last10Digits;
        }


        return Integer.toString(index);
    }

    @Override
    public String getProblemStatement() {
        return "What is the index of the first term in the Fibonacci sequence to contain 1000 digits?";
    }
}
