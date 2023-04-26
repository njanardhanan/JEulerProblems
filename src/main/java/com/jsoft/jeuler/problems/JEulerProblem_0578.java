package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JEulerProblem_0578 extends EulerSolver {



    private long TARGET = (long)Math.pow(10, 13);
    private Map<Long, Long> primeCountMemo = new HashMap<>();

    private boolean DEBUG = false;
    Map<String, Integer> map = new HashMap<>();
    private Set<Long> memo = new HashSet<>();


    class NonDecreasingPrimePower {
        private class NonDecreasingPrimePowerData {
            public int primePower;
            public boolean increasing;
            public int mininumSoFar;

            public NonDecreasingPrimePowerData(int p, boolean i, int m) {
                primePower = p;
                increasing = i;
                mininumSoFar = m;
            }

            @Override
            public String toString() {
                //return String.format("(%d, %s, %d)", primePower, increasing, mininumSoFar);
                return Integer.toString(primePower);
            }
        }
        List<NonDecreasingPrimePowerData> nonDecreasingPrimePower;
        List<Integer> prime;
        BigInteger target;
        List<Integer> maxPossiblePower;

        public NonDecreasingPrimePower(int size, List<Integer> prime, long target) {
            nonDecreasingPrimePower = new ArrayList<>();

            this.prime = prime;
            this.target = BigInteger.valueOf(target);
            this.maxPossiblePower = calculateMaxPossiblePower(size);

            for (int i=1; i<size; i++) {
                nonDecreasingPrimePower.add(new NonDecreasingPrimePowerData(1, false, 1));
            }
            nonDecreasingPrimePower.add(new NonDecreasingPrimePowerData(2, true, 1));
        }

        private List<Integer> calculateMaxPossiblePower(int size) {
            List<Integer> possiblePower = new ArrayList<>();
            BigInteger b = BigInteger.ONE;
            for(int i=0; i<size; i++) {
                b = b.multiply(BigInteger.valueOf(prime.get(i)));
            }
            if (b.compareTo(target) > 0) {
                b = new BigInteger(target.toString());
            }
            for(int i=0; i<size; i++) {
                long v = b.divide(BigInteger.valueOf(prime.get(i))).longValue();
                long vv = target.longValue() / v;
                int power = (int)(Math.log10(vv) / Math.log10(prime.get(i)));
                possiblePower.add(power);
            }
            return possiblePower;
        }

        public List<NonDecreasingPrimePowerData> getPrimePower() {
            return nonDecreasingPrimePower;
        }

        public BigInteger getValue() {
            BigInteger b = BigInteger.ONE;
            int i = nonDecreasingPrimePower.size()-1;
            for (int e=nonDecreasingPrimePower.size()-1; e>=0; e--) {
                b = b.multiply(BigInteger.valueOf(prime.get(i)).pow(nonDecreasingPrimePower.get(e).primePower));
                i--;
            }
            return b;
        }

        public boolean isValid() {
            BigInteger b = BigInteger.ONE;
            int i = nonDecreasingPrimePower.size()-1;
            for (int e=nonDecreasingPrimePower.size()-1; e>=0; e--) {
                b = b.multiply(BigInteger.valueOf(prime.get(i)).pow(nonDecreasingPrimePower.get(e).primePower));
                if (target.compareTo(b) <= 0) {
                    return false;
                }
                i--;
            }
            return true;
        }

        public boolean increment() {
            //return increment(nonDecreasingPrimePower.size() - 1);

            boolean flag = increment(nonDecreasingPrimePower.size() - 1);

            if(flag) {
                return true;
            }
            while(true) {
                int x = -1;
                for (int i = maxPossiblePower.size() - 2; i >= 0; i--) {
                    if (nonDecreasingPrimePower.get(i).primePower <= maxPossiblePower.get(i)) {
                        x = i;
                        break;
                    }
                }
                if (x != -1) {
                    flag = increment(x);
                    if(flag) {
                        return true;
                    }
                } else {
                    return false;
                }
            }
        }

        private int min(int a, int b) {
            if (a<b) return a;
            return b;
        }

        private boolean increment(int index) {
            if (index < 0) {
                return false;
            }

            NonDecreasingPrimePowerData data = nonDecreasingPrimePower.get(index);
            data.primePower++;
            if (index > 0) {
                data.mininumSoFar = min(nonDecreasingPrimePower.get(index-1).mininumSoFar, data.primePower);
                data.increasing = (data.primePower > data.mininumSoFar) || nonDecreasingPrimePower.get(index-1).increasing;
            } else {
                data.mininumSoFar = data.primePower;
                data.increasing = false;
            }


            if (index + 1 == nonDecreasingPrimePower.size()-1) {
                NonDecreasingPrimePowerData d = nonDecreasingPrimePower.get(index + 1);
                if (data.increasing) {
                    d.primePower = 1;
                    d.mininumSoFar = 1;
                    d.increasing = data.increasing;
                } else {
                    d.primePower = data.primePower + 1;
                    d.mininumSoFar = data.mininumSoFar;
                    d.increasing = d.primePower > d.mininumSoFar;
                }
            } else {
                for (int i = index + 1; i < nonDecreasingPrimePower.size(); i++) {
                    NonDecreasingPrimePowerData d = nonDecreasingPrimePower.get(i);
                    d.primePower = 1;
                    d.increasing = data.increasing;
                    d.mininumSoFar = 1;
                }

                if (!data.increasing) {
                    NonDecreasingPrimePowerData d = nonDecreasingPrimePower.get(nonDecreasingPrimePower.size() - 1);
                    d.primePower = d.mininumSoFar + 1;
                    d.mininumSoFar = d.mininumSoFar < data.mininumSoFar ? d.mininumSoFar : data.mininumSoFar;
                    d.increasing = d.primePower > d.mininumSoFar;
                }
            }

            return isValid();
        }

        @Override
        public String toString() {
            //return nonDecreasingPrimePower.toString() + " : " + getValue();
            return nonDecreasingPrimePower.toString();
        }
    }

    class PrimeIndexGenerator {
        private LinkedList<Integer> primeIndex;

        public PrimeIndexGenerator(int size) {
            primeIndex = new LinkedList<>();
            for (int i=0; i<size; i++) {
                primeIndex.add(i);
            }
        }

        public LinkedList<Integer> getPrimeIndex() {
            return primeIndex;
        }

        public void next() {
            int lastIndex = primeIndex.size()-1;
            primeIndex.set(lastIndex, primeIndex.get(lastIndex) + 1);
        }

        public boolean leftShift() {
            return leftShift(primeIndex.size() - 2);
        }

        private boolean leftShift(int index) {
            if (index < 0) {
                return false;
                //primeIndex.set(0, primeIndex.get(0) + 1);
                //return true;
            }
            int v = primeIndex.get(index) + 1;
            if (v == primeIndex.get(index + 1)) {
                return leftShift(index-1);
            } else {
                primeIndex.set(index, v);
                for (int i = index + 1; i < primeIndex.size(); i++) {
                    primeIndex.set(i, primeIndex.get(i - 1) + 1);
                }
            }
            return true;
        }
    }

    public JEulerProblem_0578(int problemNumber) {
        super(problemNumber);
    }

    public long calculate(List<Integer> primes, PrimeIndexGenerator generator, List<NonDecreasingPrimePower.NonDecreasingPrimePowerData> exponents) {
        long ans = 0;
        do {
            while(true) {
                long l = 1;
                int e = 0;
                LinkedList<Integer> primeIndies = generator.getPrimeIndex();
//                if (primeIndies.size() == 2 &&
//                primeIndies.get(0) == 3 &&
//                primeIndies.get(1) == 6) {
//                    System.out.println("here");
//                }
                for(int i : primeIndies) {
                    l *= (long) Math.pow(primes.get(i), exponents.get(e).primePower);
                    e++;
                }
                long v = TARGET / l;
                if (exponents.get(e).primePower >= 2) {
                    v = nthRoot(v, exponents.get(exponents.size() - 1).primePower);
                }

                if (v < primes.get(primeIndies.get(primeIndies.size()-1) + 1)) {
                    break;
                }

                if (DEBUG && exponents.size() == 3 &&
                        exponents.get(0).primePower == 3 &&
                        exponents.get(1).primePower == 1 &&
                        exponents.get(2).primePower == 3) {
                    updateMemo(primes, exponents.get(e).primePower, l, primeIndies.get(primeIndies.size()-1) + 1, v);
                }

                if (v == primes.get(primeIndies.get(primeIndies.size()-1) + 1)) {
                    ans += 1;
                    generator.next();
                } else {
                    //long noOfPrimesA = PrimeNumberHelper.getPrimeCount(primes.get(primeIndies.get(primeIndies.size()-1)));
                    long noOfPrimesA = primeIndies.get(primeIndies.size() - 1) + 1;
                    long noOfPrimesB;
                    if (primeCountMemo.containsKey(v)) {
                        noOfPrimesB = primeCountMemo.get(v);
                    } else {
                        noOfPrimesB = PrimeNumberHelper.getPrimeCount(v);
                        primeCountMemo.put(v, noOfPrimesB);
                    }

                    ans += (noOfPrimesB - noOfPrimesA);
                    generator.next();
                }
            }
        } while(generator.leftShift());
        return ans;
    }

    private void updateMemo(List<Integer> primes, int primePower, long valueSoFar, int startIndex, long maxPrime) {
        for(int i=startIndex; maxPrime >= primes.get(i); i++) {
            int currentPrime = primes.get(i);
            long currValue = valueSoFar * (long)Math.pow(currentPrime, primePower);
            if (memo.contains(currValue)) {
                memo.remove(currValue);
            } else {
                System.out.println("Value not found in Memo : " + currValue);
            }
        }
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

    @Override
    public String solve() {
        //solveBruteForce();

        List<Integer> primes = PrimeNumberHelper.sieveOfEratosthenesAsList(100000000);
        boolean hasValue = true;
        long ans = 0;

        for (int i=2; hasValue; i++) {
            //System.out.println("Reached : " + i);
            NonDecreasingPrimePower n = new NonDecreasingPrimePower(i, primes, TARGET);
            do {
                List<NonDecreasingPrimePower.NonDecreasingPrimePowerData> data = n.getPrimePower();
//                if (data.size() == 3 &&
//                        data.get(0).primePower == 3 &&
//                        data.get(1).primePower == 1 &&
//                        data.get(2).primePower == 3 &&
//                        DEBUG) {
//                    System.out.println("here");
//                }
                PrimeIndexGenerator generator = new PrimeIndexGenerator(i-1);
                System.out.print(n + " - Count : ");
                long v = calculate(primes, generator, n.getPrimePower());
                hasValue = (v > 0);
                ans += v;
                if (DEBUG) {
                    if (map.containsKey(n.toString())) {
                        if (map.get(n.toString()) != v) {
                            System.out.println("Not Equal : " + n + " , " + map.get(n.toString()) + " , " + v);
                        }
                        map.remove(n.toString());
                    } else {
                        System.out.println("Not found : " + n + ", " + v);
                    }
                }
                System.out.println(v + " , ans : " + ans );
                //System.out.printf("Ans : (%d - %d)\n", v, ans);
            } while (n.increment());
        }

        System.out.println("Ans : " + (TARGET - ans));
        System.out.println("Remaining map : " + map);
        System.out.println("Memo : " + memo);

        //ans : 780303200641
        //Ans : 9219696799359
        //Time elapsed : 2990147 ms.

        //Latest ans : 9219696799346
        //Time elapsed : 3159496 ms.

        return "0";
    }

    public String solveBruteForce() {
        long ans = 0;
        for (int i=2; i<=TARGET; i++) {
            if (!DEBUG && i % 100000 == 0) {
                System.out.println("Reached : " + i + " , ans : " + ans + " = " + (TARGET-ans));
            }
            Map<Integer, Integer> primeFactors = NumericHelper.getPrimeFactors(i);
            int f = 0;
            if (primeFactors.size() == 1) continue;
            List<Integer> primes = new ArrayList<>(primeFactors.keySet());
            Collections.sort(primes);
            for (int p : primes) {
                if (primeFactors.containsKey(p)) {
                    int v = primeFactors.get(p);
                    if (f == 0 || f >= v) {
                        f = v;
                    } else {
                        List<Integer> values = new ArrayList<>();
                        for (int pp : primes) {
                            int vv = primeFactors.get(pp);
                            values.add(vv);
                        }
                        if (DEBUG && values.toString().equals("[3, 1, 3]")) {
                            memo.add((long) i);

                            int mapValue = map.getOrDefault(values.toString(), 0);
                            mapValue++;
                            map.put(values.toString(), mapValue);
                        }
                        ans++;
                        break;
                    }
                }
            }
        }

        if (DEBUG) {
            for (Map.Entry<String, Integer> e : map.entrySet()) {
                System.out.printf("%s => (%d)\n", e.getKey(), e.getValue());
            }
            System.out.println();
        }

        System.out.println("Ans BruteForce : " + (TARGET-ans));

        return "0";
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
