package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.combinatorics.Generator;
import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.prime.PrimeIndexGenerator;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class JEulerProblem_0641 extends EulerSolver {

    public JEulerProblem_0641(int problemNumber) {
        super(problemNumber);
    }

    // Method to find all combinations of exponents e1, e2, ..., ek for a given divisor count
    public List<List<Integer>> findExponentCombinations(int divisorCount) {
        List<List<Integer>> results = new ArrayList<>();
        List<Integer> divisors = getDivisors(divisorCount); // Get divisors only once, excluding 1

        // Use optimized recursive method to find combinations
        findCombinations(divisors, new ArrayList<>(), 0, divisorCount, results);

        // Convert divisor combinations to exponent combinations
        for (List<Integer> combination : results) {
            for (int i = 0; i < combination.size(); i++) {
                combination.set(i, combination.get(i) - 1); // Convert divisor factors to exponents (ei + 1 -> ei)
            }
        }

        return results;
    }

    // Get all divisors of the divisorCount excluding 1 to avoid infinite recursion
    private List<Integer> getDivisors(int divisorCount) {
        List<Integer> divisors = new ArrayList<>();
        for (int i = 2; i * i <= divisorCount; i++) { // Start from 2, excluding 1
            if (divisorCount % i == 0) {
                divisors.add(i);
                if (i != divisorCount / i) {
                    divisors.add(divisorCount / i);
                }
            }
        }
        divisors.add(divisorCount); // Add the divisorCount itself
        divisors.sort(Integer::compare); // Sorting the divisors to keep them in increasing order
        return divisors;
    }

    // Optimized recursive method to find combinations of divisors that multiply to divisorCount
    private void findCombinations(List<Integer> divisors, List<Integer> currentCombination, int start, int remainingProduct, List<List<Integer>> results) {
        if (remainingProduct == 1 && !currentCombination.isEmpty()) { // Ensure at least one divisor is chosen
            results.add(new ArrayList<>(currentCombination)); // If product becomes 1, it's a valid combination
            return;
        }

        for (int i = start; i < divisors.size(); i++) {
            int divisor = divisors.get(i);
            if (remainingProduct % divisor == 0) { // Only continue if the divisor divides the remaining product
                currentCombination.add(divisor);
                findCombinations(divisors, currentCombination, i, remainingProduct / divisor, results); // Recursively reduce remaining product
                currentCombination.remove(currentCombination.size() - 1); // Backtrack to explore other paths
            }
        }
    }

    private long countLongRowDiceA(List<Integer> primes, List<Integer> exponents, double target) {
        long count = 0;
        int exponentCount = exponents.size();
        Iterator<List<Integer>> primeSubList = Generator.combination(primes).simple(exponentCount).iterator();
        while (primeSubList.hasNext()) {
            List<Integer> pList = primeSubList.next();
            double currentValue = 0.0;
            for (int i = 0; i < pList.size(); i++) {
                currentValue += exponents.get(i) * Math.log10(pList.get(i));
                if (currentValue > target) {
                    break;
                }
            }
            if (currentValue < target) {
                count++;
            }
        }
        return count;
    }

    private long nthRoot(long n, long e) {
        //return (long)Math.pow(n, 1.0/(e * 1.0));
        double dres = Math.pow(n, 1.0 / (e * 1.0));
        long ires = Math.round(dres);
        long value = (long)Math.pow(ires, e);
        if (value > n) {
            return ires-1;
        }
        return ires;
    }

    private long countLongRowDice(List<Integer> primes, List<Integer> exponents, double target) {
        PrimeIndexGenerator generator = new PrimeIndexGenerator(exponents.size());
        long ans = 0;
        do {
                double l = Math.log10(1.0);
                LinkedList<Integer> primeIndies = generator.getPrimeIndex();
                for(int i=0; i<primeIndies.size()-1; i++) {
                    l += exponents.get(i) * Math.log10(primes.get(primeIndies.get(i)));
                }
                if (l > target) {
                    break;
                }
                double remainingTarget = target - l;
                //nth Root
                remainingTarget = remainingTarget / exponents.get(exponents.size() - 1);

                long maxPossiblePrime = (long) Math.floor(Math.pow(10, remainingTarget));

                long noOfPrimesA;
                if (exponents.size() == 1) {
                    noOfPrimesA = 0;
                } else {
                    //Index zero based, so add plus 1
                    noOfPrimesA = primeIndies.get(primeIndies.size() - 2) + 1;
                }

                long noOfPrimesB = PrimeNumberHelper.getPrimeCount(maxPossiblePrime);

                if (noOfPrimesB > noOfPrimesA) {
                    ans += (noOfPrimesB - noOfPrimesA);
                }
        } while(generator.leftShift());
        return ans;
    }

    private boolean isAnswerPossible(List<Integer> primes, List<Integer> exponents, double target) {
        double l = Math.log10(1.0);
        for(int i=0; i<exponents.size()-1; i++) {
            l += exponents.get(i) * Math.log10(primes.get(i));
        }
        if (l > target) {
            return false;
        }
        return true;
    }


    @Override
    public String solve() {
        System.out.println(solveBruteForce());
        double TARGET = 4 * Math.log10(10);
        int LIMIT = 300;
        List<Integer> primes = PrimeNumberHelper.sieveOfEratosthenesAsList(LIMIT);
        int divisorCount = 7;

        //Initialize with 1 - To include 1 which is also an answer
        long ans = 1;
        for (; divisorCount < 100; divisorCount += 6) {
            //System.out.println("divisorCount = " + divisorCount);
            List<List<Integer>> exponentCombinations = findExponentCombinations(divisorCount);

            StringBuilder sb = new StringBuilder();
            sb.append("Exponent combinations for divisor count ").append(divisorCount).append(":");
            for (List<Integer> exponents : exponentCombinations) {
                Iterator<List<Integer>> permutations = Generator.permutation(exponents).simple().iterator();
                sb.append("E : ").append(exponents);
                boolean hasAns = false;
                while (permutations.hasNext()) {
                    List<Integer> e = permutations.next();
                    long currAns = countLongRowDice(primes, e, TARGET);
                    sb.append(" c: ").append(currAns);
                    ans += currAns;
                    if (currAns > 0) {
                        hasAns = true;
                    }
                }
                sb.append(" Count = ").append(ans).append("\n");
                if (hasAns) {
                    System.out.println(sb);
                }
            }
        }
        return Long.toString(ans);
    }
    public String solveBruteForce() {
        int target = 10000;
        int[] dices = new int[target+1];
        Arrays.fill(dices, 1);
        for(int i=2; i<=target; i++) {
            int x = i;
            while(x<=target) {
                dices[x]++;
                if (dices[x] == 7) {
                    dices[x] = 1;
                }
                x += i;
            }
        }
        int ans = 0;
        int maxPrime = 0;
        for(int i=1; i<=target; i++) {
            if (dices[i] == 1) {
                Map<Integer, Integer> pFactors = NumericHelper.getPrimeFactors(i);
                for (int p : pFactors.keySet()) {
                    if (maxPrime < p) {
                        maxPrime = p;
                    }
                }
                System.out.printf("%d => %s\n", i, pFactors);
                ans++;
            }
        }
        System.out.println("Max prime: " + maxPrime);
        return Integer.toString(ans);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=641";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("problem 578","prime", "sliding window", "sliding", "exponent", "prime power");
    }
}
