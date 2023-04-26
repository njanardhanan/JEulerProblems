package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class JEulerProblem_0618 extends EulerSolver {

    public JEulerProblem_0618(int problemNumber) {
        super(problemNumber);
    }

    public BigInteger MOD = BigInteger.TEN.pow(9);

    @Override
    public String solve() {
        int f1 = 1;
        int f2 = 1;
        int LIMIT = 24;
        BigInteger ans = BigInteger.ZERO;
        for (int i=2; i<=LIMIT; i++) {

            List<Integer> denominations = PrimeNumberHelper.sieveOfEratosthenesAsList(f2+1);
            BigInteger b = generateCombination(denominations, f2);
            System.out.printf("%d - %s\n", f2, b);

            ans = ans.add(b).mod(MOD);

            int tmp = f2;
            f2 = f2 + f1;
            f1 = tmp;
        }
        return ans.toString();
    }

    /**
     * CODE IS SAME AS COIN CHANGE PROBLEM BUT WITH MINOR MODIFICATION.
     * @param denominations - Denominations for coin change - In this case it is primes less than or equal to the target.
     * @param target - Target amount.
     * @return - Returns the answer as per the problem description. Ex S(8) = 49
     */
    public BigInteger generateCombination(List<Integer> denominations, int target) {
        if (target <= 0 || denominations.size() == 0) {
            return BigInteger.ZERO;
        }

        BigInteger[] prevData = new BigInteger[target + 1];
        for (int denomination : denominations) {
            if (denomination > target) {
                if (target < prevData.length) {
                    return prevData[target];
                } else {
                    return BigInteger.ZERO;
                }
            }
            BigInteger[] currData = new BigInteger[target + 1];
            for (int t = 0; t <= target; t++) {
                BigInteger data = BigInteger.ZERO;
                //With including current denomination
                if (t == denomination) {
                    data = BigInteger.valueOf(denomination);
                } else if (t > denomination) {
                    data = BigInteger.valueOf(denomination).multiply(currData[t-denomination]);
                }

                //Without including current denomination
                if (prevData[t] != null) {
                    data = data.add(prevData[t]);
                }

                currData[t] = data.mod(MOD);
            }

            prevData = currData;
        }

        return prevData[target];
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=618";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("coin change", "DP", "dynamic problem", "fibonacci");
    }
}
