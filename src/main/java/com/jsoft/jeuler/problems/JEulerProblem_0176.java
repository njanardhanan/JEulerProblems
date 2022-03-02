package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class JEulerProblem_0176 extends EulerSolver {

    public JEulerProblem_0176(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        /**
         *  https://oeis.org/A046079
         *
         *  As per OEIS A046079
         *  Let n = (2^a0)*(p1^a1)*...*(pk^ak). Then a(n) = [(2*a0 - 1)*(2*a1 + 1)*(2*a2 + 1)*(2*a3 + 1)*...*(2*ak + 1) - 1]/2.
         *  Note that if there is no a0 term, i.e., if n is odd, then the first term is simply omitted. - Temple Keller
         *
         *  So basically
         *  a(n) = 47547, and we need to find n.
         *
         *  We can find n by reverse engineering the above formula, which is what I did in the following code.
         *
         */
        //https://oeis.org/A046079
        int n = 47547;
        int m = n*2 + 1;
        Map<Integer, Integer> map = NumericHelper.getPrimeFactors(m);
        List<Integer> factors = new ArrayList<>(map.keySet());
        Collections.sort(factors, Collections.reverseOrder());
        System.out.println("Factors #1 : " + factors);
        List<Integer> factors2 = new ArrayList<>();
        int x = 1;
        for(int i : factors) {
            factors2.add((i + x)/2);
            x = -1;
        }
        System.out.println("Factors #2 : " + factors2);
        List<Integer> primes = PrimeNumberHelper.sieveOfEratosthenesAsList(20);
        System.out.println("Primes : " + primes);
        long ans = 1;
        int y = 0;
        for(int i : factors2) {
            ans *= (long)Math.pow(primes.get(y), i);
            y++;
        }

        return Long.toString(ans);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=176\n" +
                "The four right-angled triangles with sides (9,12,15), (12,16,20), (5,12,13) and (12,35,37) all have one of the shorter sides (catheti) equal to 12. It can be shown that no other integer sided right-angled triangle exists with one of the catheti equal to 12.\n" +
                "\n" +
                "Find the smallest integer that can be the length of a cathetus of exactly 47547 different integer sided right-angled triangles.";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("prime", "factor", "oeis-A046079", "reverser engineering");
    }
}
