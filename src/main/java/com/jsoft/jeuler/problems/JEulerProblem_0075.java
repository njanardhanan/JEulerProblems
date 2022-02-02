package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JEulerProblem_0075 extends EulerSolver {

    public JEulerProblem_0075(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        /**
         Refer : https://projecteuler.net/problem=94
         Refer : https://en.wikipedia.org/wiki/Pythagorean_triple
         Euclid's formula states that
         a = 2*m*n
         b = m*m - n*n
         c = m*m + n*n

         The above formula generates Pythagorean triple if m and n are coprime and not both odd.

         **/
        int LIMIT = 1500000;
        Map<Long, Integer> map = new HashMap();
        int mLimit = (int)Math.sqrt(LIMIT/2);

        int answer = 0;
        for(long m=2; m<=mLimit; m++) {
            for(long n=1; n<m; n++) {
                if((m+n) % 2 == 1 && NumericHelper.gcd(m, n) == 1) {
                    long a = m*m - n*n;
                    long b = 2*m*n;
                    long c = m*m + n*n;
                    long p = a + b + c;
                    while (p <= LIMIT) {
                        if(map.containsKey(p)) {
                            map.put(p, map.get(p) + 1);
                            if(map.get(p) == 2) {
                                answer--;
                            }
                        } else {
                            map.put(p, 1);
                            answer++;
                        }
                        p += (a+b+c);
                    }
                }
            }
        }

        return Integer.toString(answer);
    }


    private String solve1() {
        long LIMIT = 1500000;

        //long LIMIT = 150000;
        Map<Long, Integer> map = new HashMap();
        for(long a=1; a<=LIMIT/2; a++) {
            for(long b=a+1; b<=LIMIT/2 &&  (a+b)<=3*LIMIT/4; b++) {
                long c = (long)Math.sqrt((a*a) + (b*b));
                if(a+b+c > LIMIT) break;

                if(a*a + b*b == c*c) {
                    if(map.containsKey(a+b+c)) {
                        map.put(a+b+c, map.get(a+b+c) + 1);
                    } else {
                        map.put(a+b+c, 1);
                    }
                }
            }
        }

        int count = 0;
        for(Map.Entry<Long, Integer> e : map.entrySet()) {
            //System.out.println(e.getKey() + " - " + e.getValue());
            if(e.getValue() == 1) {
                count++;
            //    System.out.println(e.getKey());
            }
        }

        return Integer.toString(count);
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/thread=75";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}
