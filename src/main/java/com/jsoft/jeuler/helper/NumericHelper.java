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

    public static long addmod(long a, long b, long mod) {
        //(A + B) mod C = (A mod C + B mod C) mod C
        return (a%mod + b%mod) % mod;
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

    public static SortedSet<Integer> getDivisorSorted(int n) {
        SortedSet<Integer> listOfDivisors = new TreeSet<>();
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

    public static Map<Integer, Integer> getPrimeFactors(int n) {
        Map<Integer, Integer> primeFactors = new HashMap();

        while (n%2==0) {
            addToMap(primeFactors, 2);
            n = n/ 2;
        }

        // n must be odd at this point.  So we can skip
        for (int i=3; i<=Math.sqrt(n); i+=2) {
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

    private static Map<Integer, Integer> addToMap(Map<Integer, Integer> map, int key) {
        if(map.containsKey(key)) {
            map.put(key, map.get(key)+1);
        } else {
            map.put(key, 1);
        }
        return map;
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

    public static Map<Long, Integer> getPrimeFactors(long n, List<Long> primes) {
        Map<Long, Integer> primeFactors = new HashMap();

        for(long p : primes) {
            while (n%p == 0) {
                addToMap(primeFactors, p);
                n = n / p;
            }
            if (n < p) {
                break;
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

    public static int sumOfDigits(int n) {
        int ans = 0;
        while(n>0) {
            int r = n%10;
            ans += r;
            n /= 10;
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
    public static int lcm(int a, int b) {
        return (a*b)/gcd(a, b);
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

    public static int phi(int x) {
        Map<Integer, Integer> coprimes = getPrimeFactors(x);
        double p = x * 1.0;
        for(int n : coprimes.keySet()) {
            p = p * (1 - (1/(n*1.0)));
        }
        return (int) p;
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

    public static long choose(int n, int r) {

        // p holds the value of n*(n-1)*(n-2)...,
        // k holds the value of r*(r-1)...
        long p = 1, k = 1;

        // C(n, r) == C(n, n-r),
        // choosing the smaller value
        if (n - r < r) {
            r = n - r;
        }

        if (r != 0) {
            while (r > 0) {
                p *= n;
                k *= r;

                // gcd of p, k
                long m = gcd(p, k);

                // dividing by gcd, to simplify
                // product division by their gcd
                // saves from the overflow
                p /= m;
                k /= m;

                n--;
                r--;
            }

            // k should be simplified to 1
            // as C(n, r) is a natural number
            // (denominator should be 1 ) .
        }
        else {
            p = 1;
        }

        return p;
    }

    public static long sumOfN(long n) {
        return n * (n+1) / 2;
    }

    /**
     * Returns sum of squares from 1 to n
     * if n=5
     * returns 1^2 + 2^2 + 3^2 + 4^2 + 5^2
     */
    public static BigInteger sumOfSquare(long x) {
        BigInteger n = BigInteger.valueOf(x);
        BigInteger nPlusOne = n.add(BigInteger.ONE);
        BigInteger twoNPlusOne = BigInteger.valueOf(2).multiply(n).add(BigInteger.ONE);
        BigInteger six = BigInteger.valueOf(6);
        return n.multiply(nPlusOne).multiply(twoNPlusOne).divide(six);
    }

    /**
     * Returns sum of squares from s to e
     * if s=3 and e=5
     * returns 3^2 + 4^2 + 5^2
     */
    public static BigInteger sumOfSquare(long s, long e) {
        BigInteger sumOfS = sumOfSquare(s-1);
        BigInteger sumOfE = sumOfSquare(e);
        return sumOfE.subtract(sumOfS);
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
    public static int[] extendedEuclidGCD(int p, int q) {
        if (q == 0)
            return new int[] { p, 1, 0 };

        int[] vals = extendedEuclidGCD(q, p % q);
        int d = vals[0];
        int a = vals[2];
        int b = vals[1] - (p / q) * vals[2];
        return new int[] { d, a, b };
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

    public static int multiplicativeInverse(int a, int modulo) {

        int[] egcdValues = extendedEuclidGCD(a, modulo);

        // since multiplicative inverse exist iff gcd(a,modulo) =1
        // if no inverse exist then return 0
        if (egcdValues[0] != 1)
            return 0;
        if (egcdValues[1] > 0)
            return egcdValues[1];
        else
            return egcdValues[1] + modulo;
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

    public static boolean isHarshadNumber(long n) {
        return n > 0 && n % sumOfDigits(n) == 0;
    }

    public static boolean isTerminatingFraction(int num, int denom) {
        while (denom % 2 == 0) denom /= 2;
        while (denom % 5 == 0) denom /= 5;
        if (num % denom == 0) {
            return true;
        }
        return false;
    }

    public static List<List<List<Integer>>> partitions(List<Integer> inputSet) {
        List<List<List<Integer>>> res = new ArrayList<>();
        if (inputSet.isEmpty()) {
            List<List<Integer>> empty = new ArrayList<>();
            res.add(empty);
            return res;
        }
        int limit = 1 << (inputSet.size() - 1);
        // Note the separate variable to avoid resetting
        // the loop variable on each iteration.
        for (int j = 0; j < limit; ++j) {
            List<List<Integer>> parts = new ArrayList<>();
            List<Integer> part1 = new ArrayList<>();
            List<Integer> part2 = new ArrayList<>();
            parts.add(part1);
            parts.add(part2);
            int i = j;
            for (Integer item : inputSet) {
                parts.get(i&1).add(item);
                i >>= 1;
            }
            for (List<List<Integer>> b : partitions(part2)) {
                List<List<Integer>> holder = new ArrayList<>();
                holder.add(part1);
                holder.addAll(b);
                res.add(holder);
            }
        }
        return res;
    }

    public static int smallestNumberWithDigitSum(int N) {
        return (N % 9 + 1) * (int)Math.pow(10, (N / 9)) - 1;
    }

    // Returns x^y mod m.
    public static int powMod(int x, int y, int m) {
        if (x < 0)
            throw new IllegalArgumentException("Negative base not supported");
        if (y < 0)
            throw new IllegalArgumentException("Modular reciprocal not supported");
        if (m <= 0)
            throw new IllegalArgumentException("Modulus must be positive");
        if (m == 1)
            return 0;

        // Exponentiation by squaring
        int z = 1;
        for (; y != 0; y >>>= 1) {
            if ((y & 1) != 0)
                z = (int)((long)z * x % m);
            x = (int)((long)x * x % m);
        }
        return z;
    }
}
