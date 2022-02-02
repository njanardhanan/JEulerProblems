package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.Permutations;
import com.jsoft.jeuler.helper.PrimeNumberHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.*;


public class JEulerProblem_0049 extends EulerSolver {

    public JEulerProblem_0049(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        boolean[] primes = PrimeNumberHelper.sieveOfEratosthenes(10000);
        Set<Integer> processed = new HashSet();

        StringBuilder answer = new StringBuilder();

        for(int i=1000; i<10000; i++) {
            if (!processed.contains(i) && primes[i]) {
                List<Integer> list = getNumAsList(i);
                List<Integer> primeList = new ArrayList();
                primeList.add(i);

                //For optimization
                processed.add(i);

                for(int p=1; p<24; p++) {
                    //Permutations.permutation(p, list).forEach(System.out::print);
                    //System.out.println();

                    List<Integer> val = Permutations.permutation(p, list);
                    int num = val.stream().reduce((x,y) -> x*10+y).get();
                    if(num > 999 && primes[num]) {
                        //System.out.println("Prime : " + num);
                        //[1487, 1847, 4817, 4871, 7481, 7841, 8147, 8741]
                        //3330
                        processed.add(num);
                        if(!primeList.contains(num))
                            primeList.add(num);
                    }
                }
                if(primeList.size() >= 3) {
                    primeList.sort(Comparator.naturalOrder());

                    //Check for increasing sequence.
                    int size = primeList.size();
                    for(int x=0; x<size; x++) {
                        for(int y=x+1; y<size; y++) {
                            int diff = primeList.get(y) - primeList.get(x);
                            int nextVal = primeList.get(y) + diff;
                            if (primeList.contains(nextVal)) {

                                if(primeList.get(x) != 1487) {
                                    answer.append(primeList.get(x));
                                    answer.append(primeList.get(y));
                                    answer.append(nextVal);
                                    //System.out.printf("%d - %d - %d\n", primeList.get(x), primeList.get(y), nextVal);
                                }
                            }
                        }
                    }


                    //System.out.println(primeList);
                }
            }
        }
        return answer.toString();
    }

    private List<Integer> getNumAsList(int n) {
        List<Integer> list = new ArrayList();
        char[] nums = Integer.toString(n).toCharArray();
        for(char c : nums) {
            list.add(Character.getNumericValue(c));
        }
        return list;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=45";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}
