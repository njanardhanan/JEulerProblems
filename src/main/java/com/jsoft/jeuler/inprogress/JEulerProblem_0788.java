package com.jsoft.jeuler.inprogress;

import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;

public class JEulerProblem_0788 extends EulerSolver {

    public JEulerProblem_0788(int problemNumber) {
        super(problemNumber);
    }

    private final long MOD = 1_000_000_007;
    private final int DIGIT_SIZE = 3;
    private BigInteger[] factorialModBigInt = new BigInteger[DIGIT_SIZE+1];

    @Override
    public String solve() {
        System.out.println(solveBruteForce());
        return "0";
    }

    public String solveByRecursive() {

        sieveFactorial();
        //System.out.println(solveBruteForce());

        long result = 2;

        for(int ds=3; ds<=DIGIT_SIZE; ds++) {
            int minimumDigitsNeeded = ds / 2 + 1;
            //Let's assume first digit as 1
            //And populate with enough digits needed for dominating number
            for(int dominatingDigit = 0; dominatingDigit<10; dominatingDigit++) {
                for (int m = minimumDigitsNeeded; m < ds; m++) {
                    int[] bits = new int[10];
                    int currentDigitSize = m;
                    //Since we assume first digit as 1, we have to subtract 1 from currentDigitSize
                    if (dominatingDigit == 1) {
                        currentDigitSize--;
                    }

                    bits[dominatingDigit] = currentDigitSize;
                    //Subtract one from maxDigitSize since we assume first digit as 1
                    result += calculate(bits, ds-1, currentDigitSize, 0, dominatingDigit);
                    result %= MOD;
                }
            }
            result += 1;
            System.out.printf("Processed digitSize %d = %d\n", ds, result);
        }

        System.out.println(result % MOD);
        System.out.println((result*9) % MOD);
        return "0";
    }

    private long calculate(int[] bits, int maxDigitSize, int currentDigitSize, int currentIndex, int dominatingDigit) {
        if (currentDigitSize > maxDigitSize) {
            System.out.println("This should not happen");
            throw  new IllegalStateException();
        }
        if (currentDigitSize == maxDigitSize) {
            return calculateMultisetPermutation(bits, currentDigitSize);
        }

        long result = 0;
        for(int i=currentIndex; i<10; i++) {
            if (i==dominatingDigit) continue;
            bits[i]++;
            result += calculate(bits, maxDigitSize, currentDigitSize+1, i, dominatingDigit);
            result %= MOD;
            bits[i]--;
        }
        return result;
    }

    private long calculateMultisetPermutation(int[] bits, int digitSize) {

        BigInteger ff = factorialModBigInt[digitSize];
        for(int i=0; i<10; i++) {
            if (bits[i] >= 1) {
                ff = ff.divide(factorialModBigInt[bits[i]]);
            }
        }

        long res = ff.mod(BigInteger.valueOf(MOD)).longValue();
        if (res == 0) {
            System.out.println("Yes");
        }

        return res;
    }

    private void sieveFactorial() {
        factorialModBigInt[0] = BigInteger.ONE;
        for(int i=1; i<factorialModBigInt.length; i++) {
            factorialModBigInt[i] = factorialModBigInt[i-1].multiply(BigInteger.valueOf(i));
        }
    }

    private String solveBruteForce() {
        int digit = 2;
        int limit = (int)Math.pow(10, digit);
        int lowLimit = limit/10;
        int ans = 0;
        Map<String, Integer> map = new HashMap<>();
        for (int i=lowLimit; i<limit; i++) {
            if(isDominatingNumber(i)) {
                String s = toByteArray(i);
                map.put(s, map.getOrDefault(s, 0) + 1);
                ans++;
            }
        }
        System.out.println();
        Map<Integer, Integer> mapmap = new HashMap<>();
        int c = 0;
        for(Map.Entry<String, Integer> e : map.entrySet()) {
            mapmap.put(e.getValue(), mapmap.getOrDefault(e.getValue(), 0) + 1);
            if (e.getKey().contains("[3") && e.getKey().contains(" 1")) {
                System.out.println(e.getKey() + " : " + e.getValue());
                c++;
            }
        }
        System.out.println("c : " + c);
        System.out.println("size : " + map.size());
        System.out.println(mapmap);
        return String.valueOf(ans);
    }

    private String toByteArray(int n) {
        int[] bits = new int[10];
        while(n>0) {
            int r = n%10;
            n /= 10;
            bits[r]++;
        }
        return Arrays.toString(bits);
    }

    private int noOfOnes(int n, int i) {
        int x = 0;
        int digitCount = 0;
        while(n>0) {
            int r = n%10;
            n /= 10;
            if (r == i) x++;
            digitCount++;
        }
        if(x > digitCount/2) return x;
        return 0;
    }

    private boolean isDominatingNumber(int n) {
        int[] bits = new int[10];
        int digitCount = 0;
        while(n>0) {
            int r = n%10;
            n = n/10;
            bits[r]++;
            digitCount++;
        }

        digitCount /= 2;
        for(int i=0; i<10; i++){
            if(bits[i] > digitCount) return true;
        }
        return false;

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
