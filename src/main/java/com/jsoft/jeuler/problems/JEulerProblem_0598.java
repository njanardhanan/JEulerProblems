package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.helper.Pair;
import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class JEulerProblem_0598 extends EulerSolver {

    public JEulerProblem_0598(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        Map<Integer, Integer> primeFactors = PrimeNumberHelper.getPrimeFactorsForFactorial(100);
        System.out.println("Size : " + primeFactors.size());
        System.out.println(primeFactors);

        List<List<Pair<Long, Long>>> expoPairList = new ArrayList<>();
        List<Long> expoList = new ArrayList<>();
        long divisors = 1L;
        for (long p : primeFactors.values()) {
            expoList.add(p);
            divisors *= (p+1);
        }
        System.out.println("Divisors : " + divisors);
        expoList.sort(Comparator.naturalOrder());
        System.out.println(expoList);

        for (long expo : expoList) {
            expoPairList.add(generatePairs(expo));
        }

        long[] maxDivisorCountPerExpo = new long[expoList.size()];
        long preValue = 1;
        for (int i = expoList.size() - 1; i >= 0; i--) {
            maxDivisorCountPerExpo[i] = preValue * (expoList.get(i) + 1);
            preValue = maxDivisorCountPerExpo[i];
        }
        System.out.println(Arrays.toString(maxDivisorCountPerExpo));

        System.out.printf("Executing %d-%d : with pair size %d-%d %n", expoPairList.get(0).get(0).getLeft(), expoPairList.get(1).get(0).getLeft(), expoPairList.get(0).size(), expoPairList.get(1).size());
        Map<Pair<Long, Long>, Long> map = combineExpo(expoPairList.get(0), expoPairList.get(1), maxDivisorCountPerExpo[2]);
        for (int i=2; i<expoPairList.size(); i++) {
            System.out.printf("Executing %d : with pair size %d %n", expoPairList.get(i).get(0).getLeft(), map.size());
            if (i == expoPairList.size() - 1) {
                map = combineExpo(map, expoPairList.get(i), 1);
            } else {
                map = combineExpo(map, expoPairList.get(i), maxDivisorCountPerExpo[i + 1]);
            }
        }
        long ans = countPairs(map) / 2;
        return Long.toString(ans);
    }

    private long countPairs(Map<Pair<Long, Long>, Long> map) {
        long ans = 0;
        for (Map.Entry<Pair<Long, Long>, Long> entry : map.entrySet()) {
            if (Objects.equals(entry.getKey().getLeft(), entry.getKey().getRight())) {
                ans += entry.getValue();
            }
        }
        return ans;
    }

    private Map<Pair<Long, Long>, Long> combineExpo(Map<Pair<Long, Long>, Long> map,
                                                    List<Pair<Long, Long>> right,
                                                    long remainingDivisors) {
        Map<Pair<Long, Long>, Long> map1 = new HashMap<>();

        for (Pair<Long, Long> lp : map.keySet()) {
            for (Pair<Long, Long> rp : right) {
                if (remainingDivisors == 1 && rp.getLeft() < rp.getRight()) {
                    break;
                }
                long l = lp.getLeft() * (rp.getLeft() + 1);
                long r = lp.getRight() * (rp.getRight() + 1);

                long rightMaxDivisors = r * remainingDivisors;
                if (l > rightMaxDivisors) {
                    continue;
                }

                long leftMaxDivisors = l * remainingDivisors;
                if (r > leftMaxDivisors) {
                    continue;
                }

                //Reduce the ratio between the pair
                Pair<Long, Long> np = reduce(l, r);
                long c = map.getOrDefault(lp, 1L);
                long d = map1.getOrDefault(np, 0L);
                map1.put(np,  d + c);
                if (remainingDivisors == 1){
                    break;
                }
            }
        }
        return map1;
    }

    private Map<Pair<Long, Long>, Long> combineExpo(List<Pair<Long, Long>> left, List<Pair<Long, Long>> right, long remainingDivisors) {
        Map<Pair<Long, Long>, Long> map = new HashMap<>();
        for (Pair<Long, Long> lp : left) {
            for (Pair<Long, Long> rp : right) {
                long l = (lp.getLeft() + 1) * (rp.getLeft() + 1);
                long r = (lp.getRight() + 1) * (rp.getRight() + 1);

                long rightMaxDivisors = r * remainingDivisors;
                if (l > rightMaxDivisors) {
                    continue;
                }

                long leftMaxDivisors = l * remainingDivisors;
                if (r > leftMaxDivisors) {
                    continue;
                }

                //Reduce the ratio between the pair
                Pair<Long, Long> np = reduce(l, r);
                map.putIfAbsent(np, 0L);
                map.put(np, map.get(np) + 1L);
            }
        }
        return map;
    }

    private List<Pair<Long, Long>> generatePairs(long n) {
        List<Pair<Long, Long>> pairs = new ArrayList<>();
        for (long i = 0; i <=n; i++) {
            Pair<Long, Long> pair = new Pair<>(n-i, i);
            pairs.add(pair);
        }
        return pairs;
    }

    private Pair<Long, Long> reduce(long left, long right) {
        long gcd = NumericHelper.gcd(left, right);
        long newLeft = left/gcd;
        long newRight = right/gcd;

        Pair<Long, Long> np;
        if (newLeft < newRight) {
            np = new Pair<>(newLeft, newRight);
        } else {
            np = new Pair<>(newRight, newLeft);
        }
        return np;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=598" +
                "<p>\n" +
                "Consider the number $48$.<br>\n" +
                "There are five pairs of integers $a$ and $b$ ($a \\leq b$) such that $a \\times b=48$: $(1,48)$, $(2,24)$, $(3,16)$, $(4,12)$ and $(6,8)$.<br>\n" +
                "It can be seen that both $6$ and $8$ have $4$ divisors.<br>\n" +
                "So of those five pairs one consists of two integers with the same number of divisors.</p>\n" +
                "<p>\n" +
                "In general:<br>\n" +
                "Let $C(n)$ be the number of pairs of positive integers $a \\times b=n$, ($a \\leq b$) such that $a$ and $b$ have the same number of divisors; <br>so $C(48)=1$.\n" +
                "</p>\n" +
                "<p>\n" +
                "You are given $C(10!)=3$: $(1680, 2160)$, $(1800, 2016)$ and $(1890,1920)$.</p><p> \n" +
                "Find $C(100!)$.</p>\n" +
                "\n";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("divisors", "factorials", "prime factors", "gcd");
    }
}
