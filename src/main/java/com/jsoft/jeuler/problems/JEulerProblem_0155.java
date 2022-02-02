package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JEulerProblem_0155 extends EulerSolver {

    public JEulerProblem_0155(int problemNumber) {
        super(problemNumber);
    }

    class Capacitor {
        private final int numerator;
        private final int denominator;

        Capacitor(int n, int d) {
            this.numerator = n;
            this.denominator = d;
        }

        private Capacitor add(Capacitor c) {
            // check preconditions
            if ((denominator == 0) || (c.denominator == 0))
                throw new IllegalArgumentException("invalid denominator");
            // find lowest common denominator
            int common = lcd(denominator, c.denominator);
            // convert fractions to lcd
            Capacitor commonA = convert(common);
            Capacitor commonB = c.convert(common);
            // create new fraction to return as sum
            Capacitor sum = new Capacitor(commonA.numerator + commonB.numerator,
                    common);
            // reduce the resulting fraction
            sum = sum.reduce();
            return sum;
        }

        private Capacitor reduce() {

            int common = 0;
            // get absolute values for numerator and denominator
            int num = Math.abs(numerator);
            int den = Math.abs(denominator);
            // figure out which is less, numerator or denominator
            if (num > den)
                common = gcd(num, den);
            else if (num < den)
                common = gcd(den, num);
            else  // if both are the same, don't need to call gcd
                common = num;

            // set result based on common factor derived from gcd
            Capacitor result = new Capacitor(numerator / common, denominator / common);
            return result;
        }

        private Capacitor reciprocal() {
            return new Capacitor(denominator, numerator);
        }

        private Capacitor convert(int common) {
            int factor = common / denominator;
            return new Capacitor(numerator * factor, common);
        }

        private int lcd(int x, int y) {
            int factor = x;
            while ((x % y) != 0)
                x += factor;
            return x;
        }

        private int gcd(int x, int y) {
            int factor = y;
            while (y != 0) {
                factor = y;
                y = x % y;
                x = factor;
            }
            return x;
        }

        public Capacitor connectParallel(Capacitor other) {
            Capacitor c1 = reciprocal();
            Capacitor c2 = other.reciprocal();
            return c1.add(c2).reciprocal();
        }

        public Capacitor connectSerial(Capacitor c) {
            return add(c);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null) return false;
            if (this.getClass() != o.getClass()) return false;

            Capacitor c = (Capacitor) o;
            return numerator == c.numerator &&
                    denominator == c.denominator;
        }

        @Override
        public int hashCode() {
            //Append numerator and denominator;
            int len = Integer.toString(numerator).length();
            return numerator * (int) Math.pow(10, len) + denominator;
        }

        @Override
        public String toString() {
            if(denominator == 1) {
                return Integer.toString(numerator);
            }
            return "(" + numerator + "/" + denominator + ")";
        }

        public Capacitor clone() {
            return new Capacitor(numerator, denominator);
        }
    }


    @Override
    public String solve() {

        /**
         * http://oeis.org/A153588
         *
         * f(1) = 1
         * f(2) = f(1) + f(1) - Both serial and parallel connection.
         * f(3) =  f(1) + f(2)
         * f(4) = f(1) + f(3), f(2) + f(2)
         * f(5) = f(1) + f(4), f(2) + f(3)
         * f(6) = f(1) + f(5), f(2) + f(4), f(3) + f(3)
         *
         * so on and so forth...
         *
         * Total combination will be union of {f(1), f(2), f(3) ... f(18)}
         *
         * We need to use set to remove duplicates.
         *
         */

        int TARGET = 18;
        Map<Integer, Set<Capacitor>> map = new HashMap();

        //With 1 capacitor
        Set<Capacitor> one = new HashSet();
        one.add(new Capacitor(1,1));
        map.put(1, one);

        //With 2 capacitor
        Set<Capacitor> two = new HashSet();
        two.add(new Capacitor(1,1).connectSerial(new Capacitor(1,1)));
        two.add(new Capacitor(1,1).connectParallel(new Capacitor(1,1)));
        map.put(2, two);

        for(int i=3; i<=TARGET; i++) {
            int middlePoint = (i/2);
//            if(i%2 == 0) {
//                middlePoint = (i/2)-1;
//            } else {
//                middlePoint = (i/2);
//            }

            //System.out.println("Processing : " + i);
            Set<Capacitor> currentSet = new HashSet();
            for(int j=1; j<=middlePoint; j++) {
                int x = i-j;
                //System.out.printf("Checking %d and %d\n", j, x);

                for(Capacitor cOne : map.get(j)) {
                    for(Capacitor cTwo : map.get(x)) {
                        currentSet.add(cOne.connectSerial(cTwo));
                        currentSet.add(cOne.connectParallel(cTwo));
                    }
                }
            }
            map.put(i, currentSet);

        }

        Set<Capacitor> masterSet = new HashSet();

        for(Map.Entry<Integer, Set<Capacitor>> e : map.entrySet()) {
            masterSet.addAll(e.getValue());
            System.out.printf("Combination with %d capacitors is %d, total is %d\n" , e.getKey(), e.getValue().size(), masterSet.size());
//            for(Capacitor c : e.getValue()) {
//                System.out.printf("%s,", c.toString());
//            }
            //System.out.println("\n");
        }


        return Integer.toString(masterSet.size());
    }


    @Override
    public String getProblemStatement() {
        return  "https://projecteuler.net/problem=155";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}
