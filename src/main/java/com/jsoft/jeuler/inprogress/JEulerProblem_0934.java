package com.jsoft.jeuler.inprogress;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

public class JEulerProblem_0934 extends EulerSolver {

    public JEulerProblem_0934(int problemNumber) {
        super(problemNumber);
    }

    static class State {
        long r, m;
        int i;

        State(long r, long m, int i) {
            this.r = r;
            this.m = m;
            this.i = i;
        }
    }

    @Override
    public String solve() {
        //solveBruteForce();
        //solveByLoop();
        int n=2;
        //System.out.println("AnsXxxx : " + solveByRec(n));
        System.out.println("AnsLoop : " + solveByLoop(n));

        //292137809490441370
        return "";
    }

    private long solveByLoop() {
        long N = (long) Math.pow(10, 3);
        List<Integer> ps = PrimeNumberHelper.sieveOfEratosthenesAsList(10);

        Deque<State> res = new ArrayDeque<>();
        res.add(new State(0L, 2L, 1));
        res.add(new State(1L, 2L, 1));

        long ans = 0L;
        long num = 0;

        while (!res.isEmpty()) {
            State state = res.pollLast();
            long r = state.r;
            long m = state.m;
            int i = state.i;

            num++;
            if (i >= ps.size()) {
                System.out.println("MORE PRIMES?");
                continue;
            }

            if ((r % ps.get(i - 1)) % 7 != 0) {
                ans += (1 + (N - r) / m) * ps.get(i - 1);
            } else {
                for (int j = 0; j < ps.get(i); j++) {
                    long rNew = r + (long) j * m;
                    if (rNew > N) break;
                    long mNew = (long) ps.get(i) * m;
                    res.add(new State(rNew, mNew, i + 1));
                }
            }
        }

        System.out.println(ans);
        System.out.println(num);
        return ans;
    }

    private long solveByRec(int limit) {
        List<Integer> primes = PrimeNumberHelper.sieveOfEratosthenesAsList(100);
        //long result = rec((long) Math.pow(10, limit), primes, 0, 1, 1);
        BigInteger res = rec(BigInteger.TEN.pow(limit), primes, 0, BigInteger.ONE, BigInteger.ONE);
        return res.longValue();
    }

    private long solveByLoop(int limit) {
        List<Integer> primes = PrimeNumberHelper.sieveOfEratosthenesAsList(100);
        long result = recLoopLong((long) Math.pow(10, limit), primes, 0, 1, 1);
        //BigInteger res = recLoop(BigInteger.TEN.pow(limit), primes, 0, BigInteger.ONE, BigInteger.ONE);
        return result;
    }

    public static BigInteger rec(BigInteger end, List<Integer> primes, int idx, BigInteger start, BigInteger step) {
        if (idx >= primes.size()) {
            return BigInteger.ZERO;
        }
        BigInteger p = BigInteger.valueOf(primes.get(idx));
        BigInteger nextStep = p.multiply(step);
        BigInteger q = (end.subtract(start)).divide(nextStep);
        BigInteger r = (end.subtract(start)).mod(nextStep);
        BigInteger total = p.multiply(p.multiply(q).add(r.divide(step)).add(BigInteger.ONE));

        for (BigInteger newStart = start; newStart.compareTo(start.add(nextStep).min(end.add(BigInteger.ONE))) < 0; newStart = newStart.add(step)) {
            if (newStart.mod(p).mod(BigInteger.valueOf(7)).equals(BigInteger.ZERO)) {
                total = total.add(
                        rec(end, primes, idx + 1, newStart, nextStep)
                                .subtract(p.multiply((end.subtract(newStart)).divide(nextStep).add(BigInteger.ONE)))
                );
            }
        }
        return total;
    }

    public static BigInteger recLoop(BigInteger end, List<Integer> primes, int idx, BigInteger start, BigInteger step) {
        BigInteger total = BigInteger.ZERO;
        Stack<Object[]> stack = new Stack<>();
        stack.push(new Object[]{idx, start, step});

        while (!stack.isEmpty()) {
            Object[] params = stack.pop();
            int currentIdx = (int) params[0];
            BigInteger currentStart = (BigInteger) params[1];
            BigInteger currentStep = (BigInteger) params[2];

            if (currentIdx >= primes.size()) {
                continue;
            }

            BigInteger p = BigInteger.valueOf(primes.get(currentIdx));
            BigInteger nextStep = p.multiply(currentStep);
            BigInteger q = (end.subtract(currentStart)).divide(nextStep);
            BigInteger r = (end.subtract(currentStart)).mod(nextStep);
            total = total.add(p.multiply(p.multiply(q).add(r.divide(currentStep)).add(BigInteger.ONE)));

            for (BigInteger newStart = currentStart; newStart.compareTo(currentStart.add(nextStep).min(end.add(BigInteger.ONE))) < 0; newStart = newStart.add(currentStep)) {
                if (newStart.mod(p).mod(BigInteger.valueOf(7)).equals(BigInteger.ZERO)) {
                    stack.push(new Object[]{currentIdx + 1, newStart, nextStep});
                    total = total.subtract(p.multiply((end.subtract(newStart)).divide(nextStep).add(BigInteger.ONE)));
                }
            }
        }

        return total;
    }

    public static long recLoopLong(long end, List<Integer> primes, int idx, long start, long step) {
        long total = 0;
        Stack<Object[]> stack = new Stack<>();
        stack.push(new Object[]{idx, start, step});

        while (!stack.isEmpty()) {
            Object[] params = stack.pop();
            int currentIdx = (int) params[0];
            long currentStart = (long) params[1];
            long currentStep = (long) params[2];

            if (currentIdx >= primes.size()) {
                continue;
            }

            long p = primes.get(currentIdx);
            long nextStep = p * currentStep;
            long q = (end - currentStart) / nextStep;
            long r = (end - currentStart) % nextStep;
            total += p * (p * q + r / currentStep + 1);

            for (long newStart = currentStart; newStart < Math.min(currentStart + nextStep, end + 1); newStart += currentStep) {
                if ((newStart % p) % 7 == 0) {
                    stack.push(new Object[]{currentIdx + 1, newStart, nextStep});
                    total -= p * ((end - newStart) / nextStep + 1);
                }
            }
        }

        return total;
    }

    public String solveBruteForce() {
        /**
         * 2  => n/2
         * 3  => n/3
         * 5  => (n/5) * (4/6) => (n/5) * (2/3) => Every 6 number only 4 are selected.
         * 7  => (n/30)*(6/7)
         * 11 => (n/210)*(9/11)
         * 13 => (100000/2310)*(11/13)*2 => 74
         *
         */

        int LIMIT = 1000;
        List<Integer> primes = PrimeNumberHelper.sieveOfEratosthenesAsList(100);
        int[] values = new int[LIMIT + 1];
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i=1; i<=LIMIT; i++) {
            for (int p : primes) {
                if (i%p != 0 && (i%p)%7 != 0) {
                    values[i] = p;
                    List<Integer> vals = map.getOrDefault(p, new ArrayList<>());
                    vals.add(i);
                    map.put(p, vals);
                    break;
                }
            }
        }
        System.out.println(map);
        int ans = 0;
        for (int i=1; i<=LIMIT; i++) {
            ans += values[i];
        }
        System.out.println(ans);
        return "0";
        //43
        //37
        //74
    }

    @Override
    public String getProblemStatement() {
        return "This is a template file";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList();
    }
}
