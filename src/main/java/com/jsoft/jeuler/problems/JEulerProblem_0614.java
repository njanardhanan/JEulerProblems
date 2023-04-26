package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JEulerProblem_0614 extends EulerSolver {

    public JEulerProblem_0614(int problemNumber) {
        super(problemNumber);
    }

    public long MOD = BigInteger.TEN.pow(9).longValue() + 7;

    @Override
    public String solve() {
        /*
        sum(P(n), n in [1..10]) % (10^9+7) == 20 (takes 50 ms)
        sum(P(n), n in [1..10^2]) % (10^9+7) == 499397 (takes 50 ms)
        sum(P(n), n in [1..10^3]) % (10^9+7) == 514141877 (takes 69 ms)
        sum(P(n), n in [1..10^4]) % (10^9+7) == 942064925 (takes 653 ms)
        sum(P(n), n in [1..10^5]) % (10^9+7) == 436409146 (takes 56788 ms)
        sum(P(n), n in [1..10^6]) % (10^9+7) == 632194023 (takes  ms)
        sum(P(n), n in [1..10^7]) % (10^9+7) == 130694090 (takes  ms)
         */
        int target = 10000000;
        List<Integer> listCoins = new ArrayList<>();
        for (int i = 1; i <= target; i++) {
            //Add if it Odd
            if (i % 2 == 1) {
                listCoins.add(i);
            } else if (i % 4 == 0) {
                listCoins.add(i);
            }
        }
        System.out.println("Total coin length : " + listCoins.size());

        int[] coins = listCoins.stream().mapToInt(Integer::intValue).toArray();
        int amount = target;
        long ways = coinChangeWays(coins, amount);

        return Long.toString(ways);
    }

    public long coinChangeWays(int[] coins, int amount) {
        int[] prevRow = new int[amount + 1];
        prevRow[0] = 1;
        long maxPossibleAmount = 0;
        long ans = 0;
        for (int i = 0; i <= coins.length-1; i++) {
            if (i%1000 == 0) {
                System.out.println("Reached : " + i);
            }
            int coin = coins[i];
            maxPossibleAmount += coin;
            int target = amount;
            if (maxPossibleAmount < amount) {
                target = (int)maxPossibleAmount;
            }
            for (int j = target; j >= coin; j--) {
                //With including current coin.
                if (j == coin) {
                    prevRow[j] += 1;
                } else if (j > coin){
                    prevRow[j] += prevRow[j - coin];
                    prevRow[j] %= MOD;
                }
            }

            if (coin%2 != 0) {
                ans += prevRow[coin];
                ans %= MOD;

                if ((coin+1)%4 != 0) {
                    ans += prevRow[coin + 1];
                    ans %= MOD;
                }

            } else if (coin%4 == 0) {
                ans += prevRow[coin];
                ans %= MOD;
            }
        }
        return ans;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=614";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("coin change", "DP", "slow", "hard");
    }
}
