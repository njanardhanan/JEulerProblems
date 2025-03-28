package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class JEulerProblem_0838 extends EulerSolver {

    class Data {
        private Set<Integer> primes;
        private double logOfCoPrime;

        public Data(Data d) {
            primes = new HashSet<>(d.primes);
            logOfCoPrime = d.logOfCoPrime;
        }

        public Data(int d) {
            primes = new HashSet<>();
            addPrime(d);
        }

        public void addPrime(int p) {
            if (primes.add(p)) {
                logOfCoPrime += Math.log(p);
            }
        }

        public boolean contains(int p) {
            return primes.contains(p);
        }

        public double getLogOfCoPrime() {
            return logOfCoPrime;
        }

        @Override
        public String toString() {
            return primes.toString() + " - " + logOfCoPrime;
        }
    }

    public JEulerProblem_0838(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {

        int target = 1000000;
        //int target = 2800;

        boolean[] primes = PrimeNumberHelper.sieveOfEratosthenes(target);
        Set<Integer> setPrimeList = new HashSet<>();
        Set<Integer> nonPrimeList = new HashSet<>();
        for(int i=3; i<target; i+=10) {
            if (primes[i]) {
                setPrimeList.add(i);
            } else {
                nonPrimeList.add(i);
            }
        }

        //Remove the non primes if it's prime factors is in primeList
        Set<Integer> removeList = new HashSet<>();
        for (int x : nonPrimeList) {
            for (int y : setPrimeList) {
                if (x > y && x%y == 0) {
                    removeList.add(x);
                    break;
                }
            }
        }
        nonPrimeList.removeAll(removeList);

        //Now find the numbers that has one prime as its factor (N = p^x)
        Set<Integer> singlePrimeFactors = new HashSet<>();
        for(int x : nonPrimeList) {
            Map<Integer, Integer> primeFactor = NumericHelper.getPrimeFactors(x);
            if (primeFactor.size() == 1) {
                singlePrimeFactors.addAll(primeFactor.keySet());
            }
        }
        System.out.println(singlePrimeFactors);
        System.out.println("Count : " + singlePrimeFactors.size());

        ////Remove the non primes if it's prime factors contains only one prime.
        //Also 19 and 29 are top two primes that contributes to nonPrimeList.
//        singlePrimeFactors.add(11);
//        singlePrimeFactors.add(19);
//        singlePrimeFactors.add(29);
//        singlePrimeFactors.add(59);
//        singlePrimeFactors.add(79);
//        singlePrimeFactors.add(89);
//        singlePrimeFactors.add(107);
//        singlePrimeFactors.add(109);
        removeList.clear();
        for (int x : nonPrimeList) {
            for (int y : singlePrimeFactors) {
                if (x > y && x%y == 0) {
                    removeList.add(x);
                    break;
                }
            }
        }
        setPrimeList.addAll(singlePrimeFactors);
        nonPrimeList.removeAll(removeList);

        System.out.println("Prime List : " + setPrimeList.size());
        System.out.println();
        System.out.println("Non Prime List : " + nonPrimeList.size());
        //System.out.println();
        List<List<Integer>> remainingFactors = getPrimeFactors(nonPrimeList);
        System.out.println("Remaining Factors : " + remainingFactors.size());
        System.out.println();

        double ans = 0.0;
        for (int s : setPrimeList) {
            ans += Math.log(s);
        }
        System.out.println(ans);
        System.out.println();

        List<Integer> union = getUnionOfAllCandidate(remainingFactors);
        System.out.println(union);
        System.out.println(union.size());

//        remainingFactors = getPrimeFactors(nonPrimeList);
//        System.out.println("Remaining Factors : " + remainingFactors.size());
//        System.out.println();

        //ans += calculateSmallestCoPrimeWithPQ(remainingFactors);

        ////250611.06845908685
        ////250625.33133968085
        ////250625.33133968085
        //250625.331340

        //250621.902526
        ans += 476.9398532338036;
        System.out.println(ans);

        return "0";
    }

    private double calculateSmallestCoPrime(List<List<Integer>> primes) {
        Queue<Data> queue = new LinkedList<>();
        for (int x : primes.get(0)) {
            queue.add(new Data(x));
        }

        for (int i=1; i<primes.size(); i++) {
            int queueSize = queue.size();
            for (int j=0; j<queueSize; j++) {
                Data existingData = queue.poll();
                boolean contains = false;
                for (int x : primes.get(i)) {
                    if (existingData.contains(x)) {
                        contains = true;
                        continue;
                    }
                    Data d = new Data(existingData);
                    d.addPrime(x);
                    queue.add(d);
                }
                if (contains) {
                    queue.add(existingData);
                }
            }
        }

        System.out.println("Size : " + queue.size());

        double ans = Double.MAX_VALUE;
        Data ansData = null;
        while(queue.size() > 0) {
            Data d = queue.poll();
            if (d.getLogOfCoPrime() <= ans) {
                ans = d.getLogOfCoPrime();
                ansData = d;
            }
        }
        System.out.println(ansData);
        return ans;
    }

    private double calculateSmallestCoPrimeWithPQ(List<List<Integer>> primes) {

        PriorityQueue<Data> queue = new PriorityQueue<>(new Comparator<Data>() {
            @Override
            public int compare(Data x, Data y) {
                if (x.getLogOfCoPrime() > y.getLogOfCoPrime())
                    return -1;
                if (x.getLogOfCoPrime() < y.getLogOfCoPrime())
                    return 1;
                return 0;
            }
        });

        //Queue<Data> queue = new LinkedList<>();
        for (int x : primes.get(0)) {
            queue.add(new Data(x));
        }

        for (int i=1; i<primes.size(); i++) {
            int queueSize = queue.size();
            Queue<Data> tmpQueue = new LinkedList();
            for (int j=0; j<queueSize; j++) {
                Data existingData = queue.poll();
                boolean contains = false;
                for (int x : primes.get(i)) {
                    if (existingData.contains(x)) {
                        contains = true;
                        continue;
                    }
                    Data d = new Data(existingData);
                    d.addPrime(x);
                    tmpQueue.add(d);
                }
                if (contains) {
                    tmpQueue.add(existingData);
                }
            }

            for (Data d : tmpQueue) {
                queue.add(d);
                if (queue.size() > 1000000) queue.poll();
            }
        }

        System.out.println("Size : " + queue.size());

        double ans = Double.MAX_VALUE;
        Data ansData = null;
        while(queue.size() > 0) {
            Data d = queue.poll();
            if (d.getLogOfCoPrime() <= ans) {
                ans = d.getLogOfCoPrime();
                ansData = d;
            }
        }
        List<Integer> ansDataList = new ArrayList<>(ansData.primes);
        Collections.sort(ansDataList);
        System.out.println(ansDataList);
        return ans;
    }

    private List<List<Integer>> getPrimeFactors(Set<Integer> set) {
        List<List<Integer>> primeFactors = new ArrayList<>();
        for (int s : set) {
            Map<Integer, Integer> pFactors = NumericHelper.getPrimeFactors(s);
            List<Integer> p = new ArrayList<>(pFactors.keySet());
            Collections.sort(p);
            primeFactors.add(p);
        }
        return primeFactors;
    }

    private List<Integer> getUnionOfAllCandidate(List<List<Integer>> remainingFactors) {
        Set<Integer> union = new HashSet<>();
        Set<Integer> minUnion = new HashSet<>();
        double product = 0.0;
        for (List<Integer> list : remainingFactors) {
            int m = Integer.MAX_VALUE;
            for (int x : list) {
                union.add(x);
                if (x < m) {
                    m = x;
                }
            }
            if (minUnion.add(m)) {
                product += Math.log(m);
            }
        }

        List<Integer> minUnionList = new ArrayList<>(minUnion);
        Collections.sort(minUnionList);
        System.out.println(minUnionList.size());
        System.out.println(minUnionList);
        System.out.println("Product : " + product);

        List<Integer> unionList = new ArrayList<>(union);
        Collections.sort(unionList);
        return unionList;
    }

    private Map<Integer, Integer> getSortMap(List<List<Integer>> primes) {
        Map<Integer, Integer> map = new HashMap<>();
        for (List<Integer> list : primes) {
            for (int p : list) {
                int c = map.getOrDefault(p, 0);
                map.put(p, c+1);
            }
        }

        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Map<Integer, Integer> result = new LinkedHashMap<>();
        for (int i=list.size()-1; i>=0; i--) {
            result.put(list.get(i).getKey(), list.get(i).getValue());
        }

        return result;
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
