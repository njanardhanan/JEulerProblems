package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.*;

public class JEulerProblem_0634 extends EulerSolver {

    public JEulerProblem_0634(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
//        System.out.println(solve1());
//        BigInteger LIMIT = BigInteger.valueOf(3).multiply(BigInteger.valueOf(10).pow(6));
//        int count = 0;
//        for(long x=2; ;x++) {
//            BigInteger cube = BigInteger.valueOf(x).pow(3);
//            if(cube.compareTo(LIMIT) == 1) break;
//            BigInteger remaining = LIMIT.divide(cube);
//            if(remaining.compareTo(BigInteger.valueOf(3)) != 1) break;
//            BigInteger n = sqrt(remaining);
//            count += n.intValue() - 1;
//        }
//
//        return Integer.toString(count);
        return solve1();
    }


    public String solve1() {
        BigInteger LIMIT = BigInteger.valueOf(5).multiply(BigInteger.valueOf(10).pow(10));
        Set<BigInteger> myset = new HashSet();
        Set<BigInteger> duplicateSet = new HashSet();
        Map<BigInteger, String> map = new HashMap();
        long duplicateCount = 0;
        long count = 0;
        for(long x=2; ;x++) {
           if(x!=4 && !isSquareFreeNumber(x) && !isPerfectPrimeSquare(x)) {
               //System.out.println("Not allowing : " + x + "^3");
               continue;
           }
            BigInteger cube = BigInteger.valueOf(x).pow(3);
            if(cube.multiply(BigInteger.valueOf(4)).compareTo(LIMIT) == 1) break;
            for(long y=2; ;y++) {
                BigInteger square = BigInteger.valueOf(y).pow(2);
                BigInteger value = cube.multiply(square);
                if(value.compareTo(LIMIT) == 1) {
                    //System.out.println("Breaking at " + y + " size : " + myset.size() + " dpl : " + count);
                    break;
                }

                if(myset.contains(value)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(map.get(value));
                    sb.append("(");
                    sb.append(x);
                    sb.append("^3 " + (!isSquareFreeNumber(x) ? "Not Allowed" : "Allowed"));
                    sb.append(", ");
                    sb.append(y);
                    sb.append("^2),");
                    map.put(value, sb.toString());
                    duplicateSet.add(value);
                    System.out.println("Duplicate at : " + value.toString() + " by " + map.get(value));
                    duplicateCount++;

                } else {
//                    if(x != 4 && !isSquareFreeNumber(x) && !isPerfectPrimeSquare(x)) {
//                        System.out.println("Not allowing : " + x + "^3 * " + y + "^2");
//                    }
                    myset.add(value);
                    StringBuilder sb = new StringBuilder();
                    sb.append("(");
                    sb.append(x);
                    sb.append("^3 " + (!isSquareFreeNumber(x) ? "Not Allowed" : "Allowed"));
                    sb.append(", ");
                    sb.append(y);
                    sb.append("^2),");
                    map.put(value, sb.toString());
                    count++;
                }
            }

        }
        System.out.println("Duplicate count : " + duplicateCount);
        return Long.toString(count);
    }

    private boolean isPerfectPrimeSquare(long n) {
        long x = (long)Math.sqrt(n);
        return (x*x == n && PrimeNumberHelper.isPrime(x));
    }

    private boolean isSquareFreeNumber(long n) {
        if(n%2 == 0) {
            n = n/2;
        }

        if(n%2 == 0) {
            return false;
        }
        for (long i = 3; i <= (long)Math.sqrt(n); i = i+2) {
            if (n % i == 0)
            {
                n = n/i;

                // If i again divides, then
                // n is not square free
                if (n % i == 0)
                    return false;
            }
        }
        return true;
    }

    private BigInteger sqrt(BigInteger n) {
        BigInteger a = BigInteger.ONE;
        BigInteger b = n.shiftRight(5).add(BigInteger.valueOf(8));
        while (b.compareTo(a) >= 0) {
            BigInteger mid = a.add(b).shiftRight(1);
            if (mid.multiply(mid).compareTo(n) > 0) {
                b = mid.subtract(BigInteger.ONE);
            } else {
                a = mid.add(BigInteger.ONE);
            }
        }
        return a.subtract(BigInteger.ONE);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=634";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}
