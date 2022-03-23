package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class JEulerProblem_0223 extends EulerSolver {

    private final static int MAX_PERIMETER = 25_000_000;

    class Triplet {
        long a;
        long b;
        long c;

        Triplet(long a, long b, long c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        public long getPerimeter() {
            return a+b+c;
        }

        public List<Triplet> generate() {
            /**
             * from a triplet (a,b,c), I get the next 3 triplets
             * (2c + b - 2a, 2c + 2b - a, 3c + 2b - 2a)
             * (2c + b + 2a, 2c + 2b + a, 3c + 2b + 2a)
             * (2c - 2b + a, 2c - b + 2a, 3c - 2b + 2a)
             *
             * Start from (1,1,1) and (1,2,2), be careful about duplicates when a=b, and this is it.
             */

            List<Triplet> list = new ArrayList<>();
            Triplet t1 = new Triplet(2*c +   b - 2*a, 2*c + 2*b -   a, 3*c + 2*b - 2*a);
            Triplet t2 = new Triplet(2*c +   b + 2*a, 2*c + 2*b +   a, 3*c + 2*b + 2*a);
            Triplet t3 = new Triplet(2*c - 2*b +   a, 2*c -   b + 2*a, 3*c - 2*b + 2*a);

            if(t1.getPerimeter() <= MAX_PERIMETER) list.add(t1);
            if (t2.getPerimeter() <= MAX_PERIMETER && !t1.equals(t2)) list.add(t2);
            if (t3.getPerimeter() <= MAX_PERIMETER && !t1.equals(t3) && !t2.equals(t3)) list.add(t3);
            return list;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null)
                return false;
            if (o == this)
                return true;

            Triplet t = (Triplet) o;
            return this.a == t.a &&
                    this.b == t.b &&
                    this.c == t.c;
        }

        @Override
        public int hashCode() {
            return ("" + a + b + c + "").hashCode();
        }
    }

    public JEulerProblem_0223(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {
        /**
         * from a triplet (a,b,c), I get the next 3 triplets
         * (2c + b - 2a, 2c + 2b - a, 3c + 2b - 2a)
         * (2c + b + 2a, 2c + 2b + a, 3c + 2b + 2a)
         * (2c - 2b + a, 2c - b + 2a, 3c - 2b + 2a)
         *
         * Start from (1,1,1) and (1,2,2), be careful about duplicates when a=b, and this is it.
         */

        Triplet one = new Triplet(1,1,1);
        Triplet two = new Triplet(1,2,2);

        Queue<Triplet> queue = new LinkedList();
        queue.add(one);
        queue.add(two);

        long ans = 2; //To include the initial triplet one and two.

        while(!queue.isEmpty()) {
            Triplet t = queue.poll();

            List<Triplet> newTriplet = t.generate();
            if (newTriplet.size() > 0) {
                ans += newTriplet.size();
                queue.addAll(newTriplet);
            }
        }
        return Long.toString(ans);
    }

    @Override
    public String getProblemStatement() {
        String problem = "\n" +
                "Let us call an integer sided triangle with sides a ≤ b ≤ c barely acute if the sides satisfy\n" +
                "a^2 + b^2 = c^2 + 1.\n" +
                "\n" +
                "How many barely acute triangles are there with perimeter ≤ 25,000,000?\n";
        return problem + "\nhttps://projecteuler.net/problem=223";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("almost", "pythagorean", "triplet", "generating");
    }
}
