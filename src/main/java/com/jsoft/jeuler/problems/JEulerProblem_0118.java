package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.combinatorics.Generator;
import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JEulerProblem_0118 extends EulerSolver {

    public JEulerProblem_0118(int problemNumber) {
        super(problemNumber);
    }

    private final String[] PANDIGITAL = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};

    @Override
    public String solve() {
        List<Integer> primes = PrimeNumberHelper.sieveOfEratosthenesAsList(100000000);
        List<Integer> primesWithoutZeroOrRepeatedDigits = new ArrayList<>();
        for (int p : primes) {
            if (!doesContainZeroOrRepeatedDigit(p)) {
                primesWithoutZeroOrRepeatedDigits.add(p);
            }
        }
        Collections.reverse(primesWithoutZeroOrRepeatedDigits);
        int ans = count(primesWithoutZeroOrRepeatedDigits, 0, "");

        return Integer.toString(ans);
    }

    private int count(List<Integer> primes, int index, String pandigital) {
        if (pandigital.length() > PANDIGITAL.length) {
            return 0;
        }
        if (pandigital.length() == PANDIGITAL.length) {
            for(String s : PANDIGITAL) {
                if (!pandigital.contains(s)) {
                    return 0;
                }
            }
            return 1;
        }
        int result = 0;
        for(int i=index; i<primes.size(); i++) {
            result += count(primes, i+1, pandigital + primes.get(i));
        }
        return result;
    }

    private boolean doesContainZeroOrRepeatedDigit(int p) {
        boolean[] flags = new boolean[10];
        flags[0] = true;
        while(p > 0) {
            int n = p%10;
            if (flags[n]) return true;
            flags[n] = true;
            p /= 10;
        }
        return false;
    }

    public String solveBruteForce() {
        List<Integer> nums = Arrays.asList(1,2,3,4,5,6,7,8,9);
        boolean[] primes = PrimeNumberHelper.sieveOfEratosthenes(1000000000);
        Iterator<List<Integer>> permIterator = Generator.permutation(nums).simple().iterator();
        Map<List<Integer>, Integer> map = new HashMap<>();
        int ans = 0;
        while(permIterator.hasNext()) {
            List<List<List<Integer>>> part = NumericHelper.partitions(permIterator.next());
            for(List<List<Integer>> p : part) {
                List<Integer> pandigitalSet = new ArrayList<>();
                boolean isAllPrime = true;
                for(List<Integer> n : p) {
                    int l = toNumber(n);
                    pandigitalSet.add(l);
                    if (!primes[l]) {
                        isAllPrime = false;
                        break;
                    }
                }
                if (isAllPrime) {
                    Collections.sort(pandigitalSet);
                    if (!map.containsKey(pandigitalSet)) {
                        ans++;
                        //System.out.println(pandigitalSet);
                        map.put(pandigitalSet, 0);
                    }
                }
            }
        }

        System.out.println(ans);
        return "0";
    }

    private int toNumber(List<Integer> n) {
        int x = 0;
        Collections.reverse(n);
        for(int i : n) {
            x = (x*10) + i;
        }
        return x;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=118\n" +
                "Using all of the digits 1 through 9 and concatenating them freely to form decimal integers, different sets can be formed. Interestingly with the set {2,5,47,89,631}, all of the elements belonging to it are prime.\n" +
                "\n" +
                "How many distinct sets containing each of the digits one through nine exactly once contain only prime elements?";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("pandigital", "prime", "generate", "counting");
    }
}
