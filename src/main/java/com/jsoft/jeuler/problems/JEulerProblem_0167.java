package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JEulerProblem_0167 extends EulerSolver {

    private final long LIMIT = (long)Math.pow(10, 11);

    public JEulerProblem_0167(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        long ans = 0;
        //https://oeis.org/A100729
        int[] period = {32, 26, 444, 1628, 5906, 80, 126960, 380882, 2097152};

        //https://oeis.org/A100730
        int[] diff = {126, 126, 1778, 6510, 23622, 510, 507842, 1523526, 8388606};
        for(int n=2; n<=10; n++) {
            long r = solve(2,2*n+1, period[n-2], diff[n-2]);
            System.out.println("U(" + 2 + ", " + (2*n+1) + ") = " + r);
            ans += r;
        }

        return Long.toString(ans);
    }

    private long solve(int x, int y, int periodOfFirstDiff, int diffOfUlam) {
        List<Integer> ulam = new ArrayList<>();
        ulam.add(x);
        ulam.add(y);
        generateUlamTillSecondEvenPlusOne(ulam);

        int evenIndex = ulam.size() - 1;
        long d = (LIMIT - evenIndex) / periodOfFirstDiff;
        int generateTill = (int)(LIMIT - (periodOfFirstDiff * d));
        Set<Integer> ulamSet = new HashSet<>(ulam);
        int size = ulam.size();
        int term = findUlamTerm(ulamSet, ulam.get(0), ulam.get(size - 2), ulam.get(size - 1), generateTill);
        long ans = term + (diffOfUlam * d);
        return ans;
    }

    private int findUlamTerm(Set<Integer> ulamSet, int firstEven, int secondEven, int nextValue, int generateTill) {

        while(ulamSet.size() < generateTill) {
            nextValue += 2;
            boolean isFirstEvenMatch = ulamSet.contains(nextValue - firstEven);
            boolean isSecondEvenMatch = ulamSet.contains(nextValue - secondEven);
            //Either firstEven or secondEven should match, not both
            //Using XOR
            if (isFirstEvenMatch ^ isSecondEvenMatch) {
                ulamSet.add(nextValue);
            }
        }
        return nextValue;
    }


    private void generateUlamTillSecondEvenPlusOne(List<Integer> ulam) {
        int nextValue = ulam.get(ulam.size() - 1);
        boolean foundEvenTerm = false;
        while (true) {
            nextValue++;
            if (isUlam(nextValue,  ulam)) {
                ulam.add(nextValue);
                if (foundEvenTerm) {
                    break;
                }
                if (nextValue%2 == 0) {
                    foundEvenTerm = true;
                }
            }
        }
    }

    private boolean isUlam(int n, List<Integer> ulam) {
        int startIndex = 0;
        int endIndex = ulam.size() - 1;
        int count = 0;
        while (startIndex < endIndex) {
            if (count == 2) return false;
            int x = ulam.get(startIndex);
            int y = ulam.get(endIndex);
            if(y<=x) return count == 1;
            if(x + y > n) {
                endIndex--;
            } else if(x + y < n) {
                startIndex++;
            } else {
                count++;
                endIndex--;
                startIndex++;
            }

        }
        return count == 1;
    }

    @Override
    public String getProblemStatement() {
        return "\nhttps://projecteuler.net/problem=167";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}
