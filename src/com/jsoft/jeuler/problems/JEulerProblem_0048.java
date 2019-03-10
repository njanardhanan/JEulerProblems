package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.stream.LongStream;

public class JEulerProblem_0048 extends EulerSolver {

    public JEulerProblem_0048(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        BigInteger output = BigInteger.ZERO;
        for(int i=1;i<=1000; i++) {
            BigInteger n = new BigInteger(Integer.toString(i));
            output = output.add(n.pow(i));
        }

//        BigInteger output=
//                LongStream
//                        .rangeClosed(1,1000)
//                        .mapToObj(BigInteger::valueOf)
//                        .map(a->a.pow(a.intValue()))
//                        .reduce(BigInteger::add).get();


        int len = output.toString().length();
        return output.toString().substring(len-10, len);
    }

    @Override
    public String getProblemStatement() {
        return "Find the last ten digits of the series, 1^1 + 2^2 + 3^3 + ... + 1000^1000";
    }
}
