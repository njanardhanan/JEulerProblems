package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class JEulerProblem_0025 extends EulerSolver {

    public JEulerProblem_0025(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        List<BigInteger[]> fibList = Stream.iterate(new BigInteger[]{ BigInteger.ONE, BigInteger.ONE, BigInteger.ONE },
                p->new BigInteger[]{ p[0].add(BigInteger.ONE), p[2], p[1].add(p[2]) })
                .filter(p -> p[1].toString().trim().length() >= 1000)
                .limit(1)
                .collect(Collectors.toList());
                //.forEach(p -> System.out.println(p[0] + " - " + p[1].toString().trim().length() + " : " + p[1]));
                //.forEach(p->System.out.println(p[0] + " : " + p[2]));

        if (fibList.size() != 1) {
            System.out.println("Something wrong...");
        }

        return fibList.get(0)[0].toString();
    }

    @Override
    public String getProblemStatement() {
        return "What is the index of the first term in the Fibonacci sequence to contain 1000 digits?";
    }
}
