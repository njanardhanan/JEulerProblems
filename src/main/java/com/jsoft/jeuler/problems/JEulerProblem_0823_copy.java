package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class JEulerProblem_0823_copy extends EulerSolver {

    public JEulerProblem_0823_copy(int problemNumber) {
        super(problemNumber);
    }

    class KeyValuePair {
        int key;
        int value;

        public KeyValuePair(int k, int v) {
            this.key = k;
            this.value = v;
        }

        public int getKey() {
            return this.key;
        }

        public int getValue() {
            return this.value;
        }

        public void increment() {
            this.value++;
        }

        public void decrement() {
            this.value--;
        }

        @Override
        public String toString() {
            return "[" + key + ", " + value + "]";
        }
    }

    public String solve1() {
        int n=10;
        int m=100;
        List<Integer> primeList = PrimeNumberHelper.sieveOfEratosthenesAsList(n);

        LinkedList<Long> list = new LinkedList<>();
        for (long i=2; i<=n; i++) {
            list.add(i);
        }
        System.out.println(list);

        for (int i=1; i<=m; i++) {
            LinkedList<Long> newList = new LinkedList<>();
            long product = 1;
            for(long v : list) {
                int primeIndex = 0;
                while(v % primeList.get(primeIndex) != 0) {
                    primeIndex++;
                }
                if (v <= n && primeList.get(primeIndex) == v) {
                    product *= v;
                } else {
                    long x = v / primeList.get(primeIndex);
                    product *= primeList.get(primeIndex);
                    newList.add(x);
                }
            }
            newList.add(product);
            list = newList;
            System.out.printf("%d : %s\n", i, list);
        }

        return "0";
    }

    private BigInteger MOD = BigInteger.valueOf(1234567891L);

    private long calculate(LinkedList<LinkedList<KeyValuePair>> list, long i) {
        BigInteger product = BigInteger.ZERO;
        for (LinkedList<KeyValuePair> innerList : list) {
            BigInteger tmp = BigInteger.ONE;
            for (KeyValuePair kv : innerList) {
                tmp = tmp.multiply(BigInteger.valueOf(kv.getKey()).modPow(BigInteger.valueOf(kv.getValue()), MOD));
            }
            product = product.add(tmp).mod(MOD);
        }

        //System.out.printf("(%d, %s)\n", i, product);
        return product.longValue();
    }

    private LinkedList<KeyValuePair> getPrimeFactors(int n) {
        LinkedList<KeyValuePair> factors = new LinkedList<>();
        int f = 0;
        while(n%2 == 0) {
            f++;
            n = n / 2;
        }
        if (f > 0) {
            factors.add(new KeyValuePair(2, f));
        }

        for (int i=3; i<=Math.sqrt(n); i+=2) {
            f = 0;
            while(n%i == 0) {
                f++;
                n = n / i;
            }
            if (f > 0) {
                factors.add(new KeyValuePair(i, f));
            }
        }

        if (n>2) {
            factors.add(new KeyValuePair(n, 1));
        }

        return factors;
    }

    @Override
    public String solve() {
        solve1();

        //int n = 10_000;
        //long m = 10_000_000_000_000_000L;
        int n = 10;
        long m = 100L;
        LinkedList<LinkedList<KeyValuePair>> list = new LinkedList<>();
        for (int i=2; i<=n; i++) {
            list.add(getPrimeFactors(i));
        }

        Map<Long, Long> store = new HashMap<>();
        //Map<Long, Long> reverseStore = new HashMap<>();
        long lastHitStartIndex = 0L;
        long lastHitEndIndex = 0L;
        boolean loopDetected = false;
        for (long i=1; i<=m; i++) {
            LinkedList<LinkedList<KeyValuePair>> newList = new LinkedList<>();
            LinkedList<KeyValuePair> product = new LinkedList<>();

            for (LinkedList<KeyValuePair> innerList : list) {
                LinkedList<KeyValuePair> newInnerList = new LinkedList<>();
                boolean firstElement = true;
                for (KeyValuePair kv : innerList) {
                    //First element will be the smallest prime factor
                    if (firstElement) {
                        firstElement = false;

                        if (kv.getValue() > 1) {
                            kv.decrement();
                            newInnerList.add(kv);
                        }
                        addToList(product, kv.getKey());

                    } else {
                        newInnerList.add(kv);
                    }

                }
                if (newInnerList.size() > 0) {
                    newList.add(newInnerList);
                }
            }

            newList.add(product);
            list = newList;
            System.out.printf("%d : %s\n", i, list);
            long sumWithMod = calculate(list, i);
            if (store.containsKey(sumWithMod)) {
                if (lastHitStartIndex+1 == store.get(sumWithMod) && lastHitEndIndex+1 == i) {
                    System.out.printf("Loop detected - %d : %d\n", lastHitStartIndex, lastHitEndIndex);
                    System.out.printf("Loop length : %d\n", (lastHitEndIndex - lastHitStartIndex));
                    loopDetected = true;
                    break;
                } else {
                    lastHitStartIndex = store.get(sumWithMod);
                    lastHitEndIndex = i;
                }
            } else {
                store.put(sumWithMod, i);
                //reverseStore.put(i, sumWithMod);
            }
        }

        if (!loopDetected) {
            System.out.println("Loop not detected");
            return "0";
        }
        long diff = lastHitEndIndex - lastHitStartIndex;
        long finalIndex = lastHitStartIndex + ((m - lastHitEndIndex) % diff);
        System.out.println("Final index : " + finalIndex);
        //System.out.println("Ans : " + reverseStore.get(finalIndex));
        return "0";
    }

    private void addToList(LinkedList<KeyValuePair> p, int v) {
        int index = 0;
        for(KeyValuePair k : p) {
            if (k.getKey() == v) {
                k.increment();
                return;
            } else if (k.getKey() < v) {
                index++;
            }
        }

        //Add it in the ith place.
        p.add(index, new KeyValuePair(v, 1));
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
