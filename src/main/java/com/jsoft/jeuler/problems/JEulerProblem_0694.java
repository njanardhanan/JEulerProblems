package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

public class JEulerProblem_0694 extends EulerSolver {

    public JEulerProblem_0694(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        long TARGET = (long)Math.pow(10, 18);
        TreeSet<Long> cubeFullPrime = generateCubeFullPrimes(TARGET);
        System.out.println("cubeFullPrime size : " + cubeFullPrime.size());
        long ans = TARGET;
        for(long p : cubeFullPrime) {
            ans += TARGET/p;
        }

        //1339784153569958487
        return Long.toString(ans);
    }

    private TreeSet<Long> generateCubeFullPrimes(long limit) {
        double target = Math.log(limit);
        List<Integer> primes = PrimeNumberHelper.sieveOfEratosthenesAsList((int)Math.pow(limit, 1.0/3.0));
        System.out.println("Prime count : " + primes.size());
        TreeSet<Long> cubeFullPrime = new TreeSet<>();
        int progress = 1;
        for(int p : primes) {
            progress++;
            if(progress%100 == 0) {
                System.out.printf("Processed prime count : %d, cubeFullPrime count : %d\n", progress, cubeFullPrime.size());
            }
            List<Long> tmp = new ArrayList<>();
            for(int e=3; ;e++) {
                double v = e * Math.log(p);
                if (v <= target) {
                    long currVal = (long)Math.pow(p, e);
                    tmp.add(currVal);
                    for(long x : cubeFullPrime) {
                        double newV = v + Math.log(x);
                        if (newV <= target) {
                            tmp.add(currVal * x);
                        } else {
                            break;
                        }
                    }
                } else {
                    break;
                }
            }
            cubeFullPrime.addAll(tmp);
        }
        return cubeFullPrime;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=694" +
                "<p>\n" +
                "A positive integer $n$ is considered <i>cube-full</i>, if for every prime $p$ that divides $n$, so does $p^3$. Note that $1$ is considered cube-full.\n" +
                "</p>\n" +
                "<p>\n" +
                "Let $s(n)$ be the function that counts the number of cube-full divisors of $n$. For example, $1$, $8$ and $16$ are the three cube-full divisors of $16$. Therefore, $s(16)=3$.\n" +
                "</p>\n" +
                "<p>\n" +
                "Let $S(n)$ represent the summatory function of $s(n)$, that is $S(n)=\\displaystyle\\sum_{i=1}^n s(i)$.\n" +
                "</p>\n" +
                "<p>\n" +
                "You are given $S(16) =  19$, $S(100) = 126$ and $S(10000) = 13344$.\n" +
                "</p>\n" +
                "<p>\n" +
                "Find $S(10^{18})$.\n" +
                "</p>";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("prime", "cubefull", "generation");
    }
}
