package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.*;

public class JEulerProblem_0248 extends EulerSolver {

    public JEulerProblem_0248(int problemNumber) {
        super(problemNumber);
    }

    private Set<Long> collection = new HashSet();

    private List<Long> getDivisors(long n) {
        List<Long> listOfDivisors = new ArrayList();
        for (long i=1; i<=Math.sqrt(n); i++) {
            if (n%i==0) {
                long d = n/i;
                listOfDivisors.add(i);
                if(d != i) {
                    listOfDivisors.add(d);
                }
            }
        }

        return listOfDivisors;
    }

    private void calculate(long n, long t, long bp, Set<Long> curPrimes) {
        if(t == 1) {
            collection.add(n);
            return;
        } else if(t==0) {
            return;
        }

        //Get all divisors.
        for(long d : getDivisors(t)) {
            //This is reverse of phi(p) = p-1, if p is prime.
            //i.e., if d is a phi value, then d+1=p must be prime.
            long p = d+1;

            //if the current prime number is smaller than or equal to than the one already processed, ignore it.
            if (p <= bp) continue;

            //if the current prime number is already processed, ignore it.
            if(curPrimes.contains(p)) continue;

            //if the current number is not a prime, ignore it.
            if(!PrimeNumberHelper.isPrime(p)) continue;

            int exp = 1;
            while (t % d == 0) {
                long v = (long) Math.pow(p, exp);

                //https://en.wikipedia.org/wiki/Euler's_totient_function#Other_formulae
                //phi(n^m) = n^(m-1) * phi(n) if n is a prime number.
                d = (long) Math.pow(p, exp - 1) * (p - 1);

                if (t % d != 0) break;

                Set<Long> newFactors = new HashSet(curPrimes);
                newFactors.add(p);

                calculate(n * v, t / d, p, newFactors);
                exp++;
            }
        }
    }


    @Override
    public String solve() {

        /**
         * This is solved by using three known identity of Euler's totient function.
         *
         * Refer : https://en.wikipedia.org/wiki/Euler's_totient_function
         *
         * 1) phi(n*m) = phi(n) * phi(m) if n and m are coprimes i.e., gcd(n,m) = 1
         * 2) phi(p) = p-1, where p is a prime.
         * 3) phi(p^q) = p^(q-1) * phi(p) = p^(q-1) * (p-1), where p is a prime
         *
         *
         */

        long thirteenFact = NumericHelper.fatorial(13l);

        calculate(1, thirteenFact, 1L, new HashSet());
        List<Long> numbers = new ArrayList(collection);
        Collections.sort(numbers);
        return Long.toString(numbers.get(150000-1));
    }

    public String solveBruteForce2() {
        long thirteenFact = NumericHelper.fatorial(13l);
        System.out.println("Thirteen fact " + thirteenFact);
        //long thirteenFact = 24L;
        List<Long> fact = getDivisors(thirteenFact);
        Map<Long, List<Long>> eulerTotientMap = new HashMap();
        for(Long l : fact) {
            eulerTotientMap.put(l, new ArrayList());
        }

        int LIMIT = NumericHelper.ONE_MILLION_INT * 150;
        boolean[] primes = PrimeNumberHelper.sieveOfEratosthenes(LIMIT);
        for(int i=1; i<=LIMIT; i++) {
            long phi;
            if(primes[i]) {
                phi = i-1;
            } else {
                phi = NumericHelper.phi(i);
            }
            if(eulerTotientMap.containsKey(phi)) {
                eulerTotientMap.get(phi).add((long)i);
            }
        }

        Collections.sort(fact);
        Set<Long> answer = new HashSet();
        for(int i=0; i<=fact.size()/2; i++) {
            if(eulerTotientMap.containsKey(fact.get(i))) {
                for(long x : eulerTotientMap.get(fact.get(i))) {
                    for(long y : eulerTotientMap.get(thirteenFact/fact.get(i))) {
                        if(NumericHelper.gcd(x, y) == 1) {
                            //System.out.printf("%d & %d : %d - %d = %d\n", fact.get(i), thirteenFact / fact.get(i), x, y, x*y);
                            answer.add(x*y);
                        }
                    }
                }
            }
        }

        List<Long> answerLong = new ArrayList(answer);
        Collections.sort(answerLong);

        //System.out.println(answerLong);

        if(answerLong.contains(6227180929L)) {
            System.out.println("yes " + answerLong.subList(150000-5, 150000+5));
            System.out.println(answer.size());
            System.out.println("1000 :  " + answerLong.get(999) + " : 6359386477" );
        }

        //100M
        //23510278506
        //[23509382862, 23509422270, 23509602396, 23509938960, 23510278506, 23510283384, 23510293758, 23510309610, 23510331630, 23510439090]
        //182714

        //120M
        //23509422270
        //[23508932664, 23508952362, 23508953292, 23509382862, 23509422270, 23509602396, 23509938960, 23510278506, 23510283384, 23510293758]
        //182717

        //150M
        //23508952362
        //[23508468774, 23508527328, 23508554958, 23508932664, 23508952362, 23508953292, 23509382862, 23509422270, 23509602396, 23509938960]
        //182720

        ///////23507044290
        return Long.toString(answerLong.get(150000-1));
    }


    public String solveBruteForce1() {
        long LIMIT = 6227180929L;
        long thirteenFact = NumericHelper.fatorial(13l);

        int i = 1;
        long prev = 0L;
        for(long x = LIMIT; x <= LIMIT; x++) {
            long phi = NumericHelper.phi(x);
            if( phi == thirteenFact) {
                System.out.printf("%02d) equal to %d is %d - %d\n", i, thirteenFact, x, x-prev);
                //System.out.printf("%d,", x);
                prev = x;
                i++;
            }
        }

        return Long.toString(0);
    }




    @Override
    public String getProblemStatement() {
        return  "https://projecteuler.net/problem=248";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}
