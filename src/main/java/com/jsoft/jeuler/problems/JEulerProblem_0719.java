package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.Permutations;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.List;

public class JEulerProblem_0719 extends EulerSolver {

    public JEulerProblem_0719(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        final int LIMIT = 1000000;
        long ans = 0;
        for(long n=2; n<=LIMIT; n++) {

            //Optimization #1
            if (n%9 != 0 && n%9 != 1) continue;

            List<List<Long>> perms = Permutations.splitIntoAllPossiblePermutation(n*n);
            for (List<Long> ll : perms) {
                //Optimization #2
                if (ll.size() < 2) continue;
                long sum = ll.stream().mapToLong(Long::longValue).sum();
                if (sum == n) {
                    //System.out.println(n*n + " " + ll + " : " + n + "*" + n);
                    ans+=(n*n);
                    break;
                }
            }
        }
        return Long.toString(ans);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=719\n\n<p>\n" +
                "We define an $S$-number to be a natural number, $n$, that is a perfect square and its square root can be obtained by splitting the decimal representation of $n$ into 2 or more numbers then adding the numbers.\n" +
                "</p>\n" +
                "<p>\n" +
                "For example, 81 is an $S$-number because $\\sqrt{81} = 8+1$.<br />\n" +
                "6724 is an $S$-number: $\\sqrt{6724} = 6+72+4$. <br />\n" +
                "8281 is an $S$-number: $\\sqrt{8281} = 8+2+81 = 82+8+1$.<br />\n" +
                "9801 is an $S$-number: $\\sqrt{9801}=98+0+1$.\n" +
                "</p>\n" +
                "<p>\n" +
                "Further we define $T(N)$ to be the sum of all $S$ numbers $n\\le N$. You are given $T(10^4) = 41333$.\n" +
                "</p>\n" +
                "<p>\n" +
                "Find $T(10^{12})$\n" +
                "</p>";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}
