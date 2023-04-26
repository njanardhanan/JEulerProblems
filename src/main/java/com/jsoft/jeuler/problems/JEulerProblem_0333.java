package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JEulerProblem_0333 extends EulerSolver {

    class LinkedList {
        private int value;
        private LinkedList next;

        public LinkedList(int value) {
            this.value = value;
            this.next = null;
        }

        public int getValue() {
            return value;
        }

        public LinkedList getNext() {
            return next;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public void setNext(LinkedList next) {
            this.next = next;
        }
    }

    public JEulerProblem_0333(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        /*
            100    =>    233 (took 76 ms)
            1000   =>   4600 (took 104 ms)
            10000  =>  24853 (took 420 ms)
            100000 => 232729 (took 9712 ms)

            With Optimized
            100    =>    233 (took 59 ms)
            1000   =>   4600 (took 59 ms)
            10000  =>  24853 (took 220 ms)
            100000 => 232729 (took 3974 ms)
            1000000 => 232729 (took 5185 ms)

         */
        int target = 1000000;
        List<Integer> primes = PrimeNumberHelper.sieveOfEratosthenesAsList(target);
        target = primes.get(primes.size() - 1);
        System.out.println("Max prime : " + target);
        List<Integer> coins = generateCoins(target);
        Map<Integer, List<Integer>> coinToPrimeMap = mapCoinToPrime(primes, coins);
        int ans = calculateSpecialPartition(target, coins, coinToPrimeMap);

        return Integer.toString(ans);
    }


    private int calculateSpecialPartition(int target, List<Integer> coinList, Map<Integer, List<Integer>> coinToPrimeMap) {
        Map<Integer, Set<Integer>> divisibleMap = new HashMap<>();
        for (int i=0; i<coinList.size(); i++) {
            Set<Integer> s = new HashSet<>();
            for (int j=i+1; j<coinList.size(); j++) {
                if (coinList.get(j) % coinList.get(i) == 0) {
                    s.add(coinList.get(j));
                }
            }
            divisibleMap.put(coinList.get(i), s);
        }

        int[] coins = coinList.stream().mapToInt(Integer::intValue).toArray();
        int ans = coinChangeWaysOptimized(coins, target, divisibleMap, coinToPrimeMap);
        return ans;
    }

    private int coinChangeWaysOptimized(int[] coins, int amount, Map<Integer, Set<Integer>> divisibleMap, Map<Integer, List<Integer>> coinToPrimeMap) {
        List<LinkedList>[] prevRow = new List[amount + 1];
        for(int x=0; x<=amount; x++) {
            prevRow[x] = new ArrayList<>();
        }
        prevRow[0].add(new LinkedList(0));

        int ans = 0;
        System.out.println("Total coins : " + coins.length);
        int maxPossibleAmount = 0;
        for (int i = 0; i <= coins.length-1; i++) {
            System.out.println("Processing coin number : " + i);
            int coin = coins[i];
            maxPossibleAmount += coin;
            int target = amount;
            if (maxPossibleAmount < amount) {
                target = maxPossibleAmount;
            }

            for (int j = target; j >= coin; j--) {
                //With including current coin.
                if (j == coin) {
                    prevRow[j].add(new LinkedList(coin));
                } else if (j > coin){
                    copyData(prevRow[j - coin], coin, prevRow[j], divisibleMap);
                }
            }

            if (coinToPrimeMap.containsKey(coin)) {
                List<Integer> primeListForCurrentCoin = coinToPrimeMap.get(coin);
                for(int p : primeListForCurrentCoin) {
                    if (prevRow[p].size() == 1) {
                        ans += p;
                    }
                }
            }
        }
        return ans;
    }

    private List<Integer> generateCoins(int target) {
        List<Integer> coinSet = new ArrayList<>();
        for(int i=0; ;i++) {
            int t = (int)Math.pow(2, i);
            if (t > target) break;
            for(int j=0; ;j++) {
                int v = t * (int)Math.pow(3, j);
                if (v > target) break;
                coinSet.add(v);
            }
        }
        Collections.sort(coinSet);
        //We don't want the coin 1 at all, since it divides all number. ie. n%1 = 1
        coinSet.remove(0);
        return coinSet;
    }

    private Map<Integer, List<Integer>> mapCoinToPrime(List<Integer> primes, List<Integer> coins) {
        Map<Integer, List<Integer>> coinToPrimeMap = new HashMap<>();
        int primeIndex = 0;
        int coinIndex = 0;
        while (primeIndex<primes.size()) {
            int p = primes.get(primeIndex);
            //Check if next coin matches
            //if yes - move to next coin
            //else - map the current coin to prime.

            if (coinIndex+1 < coins.size() && coins.get(coinIndex+1) <= p) {
                coinIndex++;
            } else {
                List<Integer> list = coinToPrimeMap.getOrDefault(coins.get(coinIndex), new ArrayList<>());
                list.add(p);
                coinToPrimeMap.put(coins.get(coinIndex), list);
                primeIndex++;
            }
        }
        return coinToPrimeMap;
    }

    private void copyData(List<LinkedList> from, int coin, List<LinkedList> to, Map<Integer, Set<Integer>> divisibleMap) {
        for(LinkedList list : from) {
            boolean breakNow = false;
            LinkedList node = list;
            while (node != null) {
                int n = node.getValue();
                if (divisibleMap.containsKey(n) && divisibleMap.get(n).contains(coin)) {
                    breakNow = true;
                    break;
                }
                node = node.getNext();
            }
            if (breakNow) {
                continue;
            }

            LinkedList tmp = new LinkedList(coin);
            tmp.setNext(list);
            to.add(tmp);
        }
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=333";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("coin change", "DP", "dynamic programming", "linkedlist");
    }
}
