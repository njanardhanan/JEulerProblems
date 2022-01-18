package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class JEulerProblem_0034 extends EulerSolver {

    public JEulerProblem_0034(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        List<Long> factos = LongStream
                .range(0,10)
                .map(x -> NumericHelper.fatorial(x))
                .boxed()
                .collect(Collectors.toList());

        long sum = 0;

        for(long i=3; i<100000; i++) {
            long n = i;
            long tmpSum = 0;
            while(n>0) {
                tmpSum += factos.get((int)n%10);
                n = n/10;
            }
            if(tmpSum == i) {
                //System.out.println(i)   ;
                sum += i;
            }
        }

        return Long.toString(sum);
    }

    @Override
    public String getProblemStatement() {
        return "Find the sum of all numbers which are equal to the sum of the factorial of their digits.";
    }
}
