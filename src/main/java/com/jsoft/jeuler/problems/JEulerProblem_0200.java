package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class JEulerProblem_0200 extends EulerSolver {

    public JEulerProblem_0200(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        //Found this value by trail and error
        List<Integer> primes = PrimeNumberHelper.sieveOfEratosthenesAsList(170000);
        PriorityQueue<BigInteger> queue = new PriorityQueue<>(new Comparator<BigInteger>() {
            @Override
            public int compare(BigInteger x, BigInteger y) {
                return y.compareTo(x);
            }
        });

        //Optimization - Found this limit by trial and error.
        BigInteger limit = BigInteger.valueOf(1562992005125L);
        for (int i=0; i<primes.size(); i++) {
            for (int j=i+1; j<primes.size(); j++) {

                BigInteger pq = getPSqareQCube(primes.get(i), primes.get(j));
                if (limit.compareTo(pq) >= 0) {
                    if (pq.toString().contains("200") && isPrimeProof(pq)) {
                        queue.add(pq);
                    }
                }

                BigInteger pq1 = getPSqareQCube(primes.get(j), primes.get(i));
                if (limit.compareTo(pq1) >= 0) {
                    if (pq1.toString().contains("200") && isPrimeProof(pq1)) {
                        queue.add(pq1);
                    }
                }

                while (queue.size() > 200) {
                    queue.poll();
                }
            }
        }

        //System.out.println("Count : " + count);
        //System.out.println(queue.size());
        //System.out.println("Ans : " + queue.poll());
        //15499292621200294586399
        //620074325975836
        //200598065832532
        //1562992005125
        //229161792008
        return queue.poll().toString();
    }

    private BigInteger getPSqareQCube(long x, long y) {
        //169249 : 2
        BigInteger p = BigInteger.valueOf(x).pow(2);
        BigInteger q = BigInteger.valueOf(y).pow(3);

        return p.multiply(q);
    }

    private boolean isPrimeProof(BigInteger b) {
        String s = b.toString();
        for(int i=s.length()-1; i>0; i--) {
            int n = Character.getNumericValue(s.charAt(i));
            for(int d=0; d<=9; d++) {
                //Don't check the same digit again.
                if (n == d) {
                    continue;
                }

                //Don't replace first digit with 0
                if (i == 0 && d == 0) continue;

                //Don't replace last digit with even number.
                if (i == s.length()-1 && d%2 == 0) continue;

                String newNum = s.substring(0, i) + d + s.substring(i+1);
                if (new BigInteger(newNum).isProbablePrime(1)) {
                    return false;
                }

            }
        }
        return true;
    }

    @Override
    public String getProblemStatement() {
        return "We shall define a sqube to be a number of the form, p^2q^3, where p and q are distinct primes.\n" +
                "For example, 200 = 5^2*2^3 or 120072949 = 23^26^13.\n" +
                "\n" +
                "The first five squbes are 72, 108, 200, 392, and 500.\n" +
                "\n" +
                "Interestingly, 200 is also the first number for which you cannot change any single digit to make a prime; we shall call such numbers, prime-proof. The next prime-proof sqube which contains the contiguous sub-string \"200\" is 1992008.\n" +
                "\n" +
                "Find the 200th prime-proof sqube containing the contiguous sub-string \"200\".";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("prime", "sieve", "bruteforce", "priorityqueue");
    }
}
