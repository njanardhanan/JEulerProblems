package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JEulerProblem_9999 extends EulerSolver {

    public JEulerProblem_9999(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        int limit = (int)Math.pow(10, 4);
        Map<Integer, Integer> primeCounts = new HashMap<>();
        primeCounts.put(10,  17);
        primeCounts.put(100, 1060);
        primeCounts.put(10000, 5736396);

        int limitSqrt = (int)Math.sqrt(limit);
        List<Integer> primes = PrimeNumberHelper.sieveOfEratosthenesAsList(limitSqrt);

        Map<Integer, Integer> map = bruteForce(limit, primes);

        int ans = primeCounts.get(limit) - primeCounts.get(limitSqrt);
        for(int i=0; i<primes.size(); i++) {
            int v = countOnlyOptimized(i, primes, limit);
            System.out.println(primes.get(i) + " = " + v + " => " + map.get(primes.get(i)));
            ans += primes.get(i) * v;
        }


        System.out.println(count(3, primes, limit));

        return Integer.toString(ans);
        //50 * 2 = 100
        //17 * 3 =  51
        //7 * 5 = 35
        //4 * 7 = 28
    }

    private Map<Integer, Integer> bruteForce(int n, List<Integer> primes) {
        int[] a = new int[n+1];
        Map<Integer, Integer> map = new HashMap<>();
        for(int p : primes) {
            int c = 0;
            for(int i=p; i<=n; i+=p) {
                if(a[i] == 0) {
                    a[i] = p;
                    c++;
                }
            }
            map.put(p, c);
        }
        return map;
    }

    public int countOnlyOptimized(int primeIndex, List<Integer> primes, int m) {
        //BigO = n*n
        int total = m/primes.get(primeIndex);
        int exclude = 0;
        for(int i=0; i<primeIndex; i++) {
            int v = total;
            int e = 0;
            for(int j=i; j>=0; j--) {
                int p = primes.get(j);
                if (v >= p) {
                    e = v / p;
                    v /= p;
                }
            }
            exclude += e;
        }
        return total - exclude;
    }

    /**
     * self.prime_sum = {
     *             10**1 : 17,
     *             10**2 : 1060,
     *             10**3 : 76127,
     *             10**4 : 5736396,
     *             10**6 : 37550402023,
     *             10**8 : 279209790387276,
     *             10**12 : 18435588552550705911377,
     *         }
     */

    public int countOnlyOptimized(int x, int a[], int m) {
        //BigO = n*n
        int n = a.length;
        int total = m/x;
        int exclude = 0;
        for(int i=0; i<n; i++) {
            int v = m/x;
            int e = 0;
            for(int j=i; j>=0; j--) {
                int p = a[j];
                if (v >= p) {
                    e = v / p;
                    v /= p;
                } else {
                    System.out.println(p + " " + v);
                }
            }
            exclude += e;
        }
        return total - exclude;
    }

    public int count(int primeIndex, List<Integer> primes, int m) {
        int n = primeIndex;
        int odd = m/primes.get(primeIndex);
        int even = 0;
        int pow_set_size = (1 << n);

        for (int counter = 1; counter < pow_set_size; counter++) {
            int p = primes.get(primeIndex);
            for (int j = 0; j < n; j++) {
                if ((counter & (1 << j)) > 0) {
                    p *= primes.get(j);
                }
            }

            if (Integer.bitCount(counter) % 2 == 0) {
                odd += (m / p);
            }
            else {
                even += (m / p);
            }
        }

        return odd - even;
    }

    public int count(int x, int a[], int m) {
        int n = a.length;
        int odd = m/x;
        int even = 0;
        int pow_set_size = (1 << n);

        for (int counter = 1; counter < pow_set_size; counter++) {
            int p = x;
            for (int j = 0; j < n; j++) {
                if ((counter & (1 << j)) > 0) {
                    p *= a[j];
                }
            }

            if (Integer.bitCount(counter) % 2 == 0) {
                odd += (m / p);
            }
            else {
                even += (m / p);
            }
        }

        return odd - even;
    }

    @Override
    public String getProblemStatement() {
        return "just like that";
    }
}
