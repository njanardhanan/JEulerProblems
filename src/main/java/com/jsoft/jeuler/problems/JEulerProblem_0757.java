package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.Arrays;
import java.util.List;

public class JEulerProblem_0757 extends EulerSolver {

    public JEulerProblem_0757(int problemNumber) {
        super(problemNumber);
    }

    static class LongList {
        long[] array = new long[32];
        int size = 0;

        void add(long value) {
            if (size >= array.length) {
                array = Arrays.copyOf(array, array.length * 2);
            }
            array[size] = value;
            size++;
        }

        void sort() {
            Arrays.sort(array, 0, size);
        }

        public void clear() {
            size = 0;
        }
    }

    /**
     *  Stealthly number is same as bipronics number
     *  https://oeis.org/A072389
     *
     *  Example:
     *  12 = 2*6 = 3*4, and also 2+6 = 3+4+1
     *  here
     *  a = 2
     *  b = 6
     *  c = 3
     *  d = 4
     *
     *  Let says x=c-a and y=d-a
     *  x = c-a = 3-2 = 1
     *  y = d-a = 4-2 = 2
     *
     *  bipronics number is
     *    = x * (x+1) * y * (y+1)
     *    = 1 * 2 * 2 * 3
     *    = 12
     */
    @Override
    public String solve() {
        //long ans = getStealthlyNumberCount(1000000);
        long ans = getStealthlyNumberCount(100000000000000L);
        //75737353
        return Long.toString(ans);
    }


    private long getStealthlyNumberCount(long limit) {
        long sqrt = (long) (Math.sqrt(limit) + 1);

        long splits = 100;

        long result = 0;
        LongList list = new LongList();
        for (long s = 0; s < splits; s++) {
            //System.out.println("Split:" + s);
            long min = (limit / splits) * s + 1L;
            long max = (limit / splits) * (s + 1L);

            for (long x = 1; x <= sqrt; x++) {
                long p1 = x * (x + 1L);

                if (p1 * p1 > max) break;
                long minY = Math.max(x, (long) Math.sqrt(min / p1));
                for (long y = minY; y <= sqrt; y++) {
                    long p2 = y * (y + 1L);
                    long prod = (p1 * p2);
                    if (prod < min) continue;
                    if (prod > max) {
                        break;
                    }
                    list.add(prod);
                }
            }

            //Sort and count uniques.
            list.sort();
            long prev = -1;
            long count = 0;
            for (int i = 0; i < list.size; i++) {
                long v = list.array[i];
                if (prev != v) {
                    count++;
                }
                prev = v;
            }

            list.clear();
            result += count;
        }

        return result;
    }

    @Override
    public String getProblemStatement() {
        return "A positive integer N is stealthy, if there exist positive integers a, b, c,d  such that ab=cd=N and a+b=c+d+1.\n" +
                "For example, 36=4*9=6*6 is stealthy.\n" +
                "\n" +
                "You are also given that there are 2851 stealthy numbers not exceeding 10^6.\n" +
                "\n" +
                "How many stealthy numbers are there that don't exceed 10^14?";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}
