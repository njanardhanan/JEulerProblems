package com.jsoft.jeuler.helper;

import java.math.BigInteger;
import java.util.*;

public class NumericHelper {

    public static final int ONE_MILLION_INT = 1000000;

    public static String add(String a, String b) {
        BigInteger aBigInt = new BigInteger(a);
        return aBigInt.add(new BigInteger(b)).toString();
    }

    public static long mulmod(long a, long b, long mod) {
        long res = 0; // Initialize result
        a = a % mod;
        while (b > 0) {
            // If b is odd, add 'a' to result
            if (b % 2 == 1) {
                res = (res + a) % mod;
            }

            // Multiply 'a' with 2
            a = (a * 2) % mod;

            // Divide b by 2
            b /= 2;
        }

        // Return result
        return res % mod;
    }

    public static Set<Integer> getDivisors(int n) {
        Set<Integer> listOfDivisors = new HashSet<>();
        for (int i=1; i<=Math.sqrt(n); i++) {
            if (n%i==0) {
                listOfDivisors.add(i);
                listOfDivisors.add(n/i);
            }
        }

        return listOfDivisors;
    }

    public static Set<Long> getDivisors(long n) {
        Set<Long> listOfDivisors = new HashSet<>();
        for (long i=1; i<=Math.sqrt(n); i++) {
            if (n%i==0) {
                listOfDivisors.add(i);
                listOfDivisors.add(n/i);
            }
        }

        return listOfDivisors;
    }

    public static Map<Long, Integer> getPrimeFactors(long n) {
        Map<Long, Integer> primeFactors = new HashMap();

        while (n%2==0) {
            addToMap(primeFactors, 2l);
            n = n/ 2;
        }

        // n must be odd at this point.  So we can skip
        for (long i=3; i<=Math.sqrt(n); i+=2) {
            while (n%i==0) {
                addToMap(primeFactors, i);
                n = n/ i;
            }
        }

        if (n>2) {
            addToMap(primeFactors, n);
        }

        return primeFactors;
    }

    private static Map<Long, Integer> addToMap(Map<Long, Integer> map, long key) {
        if(map.containsKey(key)) {
            map.put(key, map.get(key)+1);
        } else {
            map.put(key, 1);
        }
        return map;
    }

    public static long sumOfDigits(String s) {
        long ans = 0;
        for(int i=0; i<s.length(); i++) {
            ans += (long)Character.getNumericValue(s.charAt(i));
        }
        return ans;
    }

    public static long sumOfDigits(long n) {
        long ans = 0;
        while(n>0) {
            long r = n%10;
            ans += r;
            n /= 10;
        }
        return ans;
    }

    public static int noOfDigits(long n) {
        return Long.toString(n).length();
    }

    public static int gcd(int a, int b) {
        if(a == 0 || b == 0) return a+b; // base case
        return gcd(b,a%b);
    }

    public static long gcd(long a, long b) {
        if(a == 0 || b == 0) return a+b; // base case
        return gcd(b,a%b);
    }

    // method to return LCM of two numbers
    public static long lcm(long a, long b) {
        return (a*b)/gcd(a, b);
    }

    public static int getAmicablePair(int n) {
        if(n==1) return 1;
        Set<Integer> divisorN = getDivisors(n);
        return divisorN.stream().mapToInt(Integer::intValue).sum() - n;
    }

    public static long getAmicablePair(long n) {
        Set<Long> divisorN = getDivisors(n);
        return divisorN.stream().filter(x -> x!=n).mapToLong(Long::longValue).sum();
    }

    public static boolean isAmicableNumber(long n) {
        long x = getAmicablePair(n);
        long y = getAmicablePair(x);
        return n != x && n == y;
    }

    public static boolean isPandigital1to9(String s) {
        List<String> pandigital = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9");
        for(String p : pandigital) {
            if (!s.contains(p)) return false;
        }
        return true;
    }

    public static long fatorial(long num) {
        if (num <=1 )  return 1;
        else  return num * fatorial(num - 1);
    }

    public static int fatorial(int num) {
        if (num <=1 )  return 1;
        else  return num * fatorial(num - 1);
    }

    public static BigInteger factorialBigNumber(int n) {
        BigInteger fact = new BigInteger("1");
        for (int i = 1; i <= n; i++) {
            fact = fact.multiply(new BigInteger(i + ""));
        }
        return fact;
    }

    public static String toBinary(int n) {
        String str = "";
        if(n>1)
            str = toBinary(n/2);
        return str + Integer.toString(n%2);
    }

    public static boolean isTriangleNumber(long x) {
        long n = (long)(Math.sqrt(8*x+1) - 1) / 2;
        long triangle = (n * (n+1))/2;
        return triangle == x;
    }

    public static boolean isPentagonalNumber(long x) {
        long n = (long)(Math.sqrt(24*x+1) + 1) / 6;
        long pentagonal = (n * (3 * n - 1))/2;
        return pentagonal == x;
    }

    public static boolean isHexagonalNumber(long x) {
        long n = (long)(Math.sqrt(8*x+1) + 1) / 4;
        long hexagonal = n * (2 * n - 1);
        return hexagonal == x;
    }

    public static List<Long> getPascalRow(int n) {
        List<Long> row = new ArrayList();
        row.add(1l);

        for(int k=0; k<n; k++) {
            row.add(row.get(k) * (n-k) / (k+1));
        }

        return row;
    }

    public static List<Integer> toDigits(int n) {
        List<Integer> digits = new ArrayList();
        while(n>0) {
            digits.add(n%10);
            n /= 10;
        }
        Collections.reverse(digits);
        return digits;
    }

    public static boolean isBouncyNumber(int n) {
        List<Integer> digits = toDigits(n);
        if(digits.size() <=2) return false;

        boolean increasing = true;
        boolean decreasing = true;

        for(int i=0; i<digits.size()-1; i++) {
            if(digits.get(i) > digits.get(i+1)) {
                increasing = false;
            } else if(digits.get(i) < digits.get(i+1)) {
                decreasing = false;
            }

            if(!increasing && !decreasing) return true;
        }
        return false;

    }

    public static long phi(int x) {
        Map<Long, Integer> coprimes = getPrimeFactors(x);
        double p = x * 1.0;
        for(long n : coprimes.keySet()) {
            p = p * (1 - (1/(n*1.0)));
        }
        return (long) p;
    }

    public static long phi(long x) {
        Map<Long, Integer> coprimes = getPrimeFactors(x);
        double p = x * 1.0;
        for(long n : coprimes.keySet()) {
            p = p * (1 - (1/(n*1.0)));
        }
        return (long) p;
    }

    public static long binomialCoeff(int n, int k) {
        long C[] = new long[k + 1];

        // nC0 is 1
        C[0] = 1;

        for (int i = 1; i <= n; i++)
        {
            // Compute next row of pascal
            // triangle using the previous row
            for (int j = Math.min(i, k); j > 0; j--)
                C[j] = C[j] + C[j-1];
        }
        return C[k];
    }

    public static boolean isSquareFreeNumber(long n) {
        if(n%2 == 0) {
            n = n/2;
        }

        if(n%2 == 0) {
            return false;
        }
        for (long i = 3; i <= (long)Math.sqrt(n); i = i+2) {
            if (n % i == 0) {
                n = n/i;

                // If i again divides, then
                // n is not square free
                if (n % i == 0)
                    return false;
            }
        }
        return true;
    }

    public static long noOfSquareFreeNumber(long n) {
        /***
         * f(n) = Sum_{k=1..floor(sqrt(n))} mobius(k) * floor(n/k^2)
         */
        long squareFreeCount = 0;
        long sqrt = (long)(long)Math.floor(Math.sqrt(n));
        for (long i = 1; i <= sqrt; i++) {
            squareFreeCount += (mobius(i) * (long) Math.floor(n / (i * i)));
        }
        return squareFreeCount;

    }

    //  return array [d, a, b] such that d = gcd(p, q), ap + bq = d
    public static long[] extendedEuclidGCD(long p, long q) {
        if (q == 0)
            return new long[] { p, 1, 0 };

        long[] vals = extendedEuclidGCD(q, p % q);
        long d = vals[0];
        long a = vals[2];
        long b = vals[1] - (p / q) * vals[2];
        return new long[] { d, a, b };
    }

    public static long chineseRemainder(long[] n, long[] a) {

        long prod = Arrays.stream(n).reduce(1, (i, j) -> i * j);

        long p, sm = 0;
        for (int i = 0; i < n.length; i++) {
            if(a[i] == 0) continue;
            p = prod / n[i];
            long[] extendedEuclidValues = extendedEuclidGCD(p, n[i]);
            if(extendedEuclidValues[1] < 0) {
                extendedEuclidValues[1] += n[i];
            }
            sm += a[i] * extendedEuclidValues[1] * p;
        }
        return sm % prod;
    }

    public static int[] sievePrimeFactorRadical(int limit) {
        int[] radicals = new int[limit];
        for(int i=0; i<limit; i++) {
            radicals[i] = 1;
        }

        for(int i=2; i<limit; i++) {
            if(radicals[i] == 1) {
                radicals[i] = i;

                for (int j = i + i; j < limit; j += i) {
                    radicals[j] *= i;

                }
            }
        }
        return radicals;
    }

    public static long getSigma2(long n) {
        Set<Long> divisorN = getDivisors(n);
        return divisorN.stream().mapToLong(x -> x * x).sum();
    }

    public static long mobius(long n) {
        long p = 0;

        if (n == 1L) return 1;
        if (n == 2L) return -1;

        // Handling 2 separately
        if (n % 2 == 0) {
            n = n / 2;
            p++;

            // If 2^2 also divides N
            if (n % 2 == 0)
                return 0;
        }

        // Check for all other prime factors
        for (long i = 3; i <= Math.sqrt(n); i = i+2) {
            // If i divides n
            if (n % i == 0) {
                n = n / i;
                p++;

                // If i^2 also divides N
                if (n % i == 0)
                    return 0;
            }
        }

        return (p % 2 == 0)? -1 : 1;
    }
}
