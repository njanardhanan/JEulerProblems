package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class JEulerProblem_0183 extends EulerSolver {

    public JEulerProblem_0183(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        long ans = 0;
        int LIMIT = 10000;
        for (int n=5; n<=LIMIT; n++) {
            double pMax = 0.0;
            int kMax = 0;
            for (int k=1; k<=n; k++) {
                // (n/k)^k
                double d = k * (Math.log((n * 1.0) / (k * 1.0)));
                if (pMax <= d) {
                    pMax = d;
                    kMax = k;
                }
            }
            /**
             *  Instead of the above for loop to find max k, we can directly find using the below formula
             *   kMax = n/e
             *   where e is Math.E
             *   https://en.wikipedia.org/wiki/E_(mathematical_constant)
             */
            //System.out.println(kMax + " : " + (int)Math.round((p*1.0)/Math.E));
            if (NumericHelper.isTerminatingFraction(n, kMax)) {
                ans -= n;
            } else {
                ans += n;
            }


        }
        return Long.toString(ans);
    }

    @Override
    public String getProblemStatement() {
        return "Let N be a positive integer and let N be split into k equal parts, r = N/k, so that N = r + r + ... + r.\n" +
                "Let P be the product of these parts, P = r × r × ... × r = rk.\n" +
                "\n" +
                "For example, if 11 is split into five equal parts, 11 = 2.2 + 2.2 + 2.2 + 2.2 + 2.2, then P = 2.25 = 51.53632.\n" +
                "\n" +
                "Let M(N) = Pmax for a given value of N.\n" +
                "\n" +
                "It turns out that the maximum for N = 11 is found by splitting eleven into four equal parts which leads to Pmax = (11/4)4; that is, M(11) = 14641/256 = 57.19140625, which is a terminating decimal.\n" +
                "\n" +
                "However, for N = 8 the maximum is achieved by splitting it into three equal parts, so M(8) = 512/27, which is a non-terminating decimal.\n" +
                "\n" +
                "Let D(N) = N if M(N) is a non-terminating decimal and D(N) = -N if M(N) is a terminating decimal.\n" +
                "\n" +
                "For example, ∑(N) for 5 ≤ N ≤ 100 is 2438.\n" +
                "\n" +
                "Find ∑(N) for 5 ≤ N ≤ 10000.";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("euler number", "e", "gcd", "fraction");
    }
}
