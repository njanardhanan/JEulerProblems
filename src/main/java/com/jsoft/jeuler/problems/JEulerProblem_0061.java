package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JEulerProblem_0061 extends EulerSolver {

    public JEulerProblem_0061(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {

        Set<Integer> octo = getFourDigitOctagonalNumber();
        Set<Integer> hepta = getFourDigitHeptagonalNumber();
        Set<Integer> hexa = getFourDigitHexagonalNumber();
        Set<Integer> penta = getFourDigitPentagonalNumber();
        Set<Integer> sq = getFourDigitSquareNumber();
        Set<Integer> tri = getFourDigitTriangleNumber();

        Set<Integer> s = new HashSet(octo);
        s.addAll(hepta);
        s.addAll(hexa);
        s.addAll(penta);
        s.addAll(sq);
        s.addAll(tri);
        //System.out.println(s);

        Set<Set<Integer>> finalSet = new HashSet();

        for(int a : s) {
            for(int b : s) {
                if(a != b && check(a, b)) {
                    for(int c : s) {
                        if(b != c && check(b, c)) {
                            for(int d : s) {
                                if(c != d && check(c, d)) {
                                    for(int e : s) {
                                        if(d != e && check(d, e)) {
                                            for(int f : s) {
                                                if(e != f && check(e, f) && check(f, a)) {
                                                    Set<Integer> tmpSet = new HashSet();
                                                    tmpSet.add(a);
                                                    tmpSet.add(b);
                                                    tmpSet.add(c);
                                                    tmpSet.add(d);
                                                    tmpSet.add(e);
                                                    tmpSet.add(f);

                                                    if(check(tmpSet, octo, hepta, hexa, penta, sq, tri)) {
                                                        finalSet.add(tmpSet);
                                                        //System.out.println(tmpSet);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        int ans = 0;
        for(Set<Integer> ss : finalSet) {
            System.out.println(ss);
            ans = ss.stream().mapToInt(x -> x).sum();
        }

        return Integer.toString(ans);
    }

    private boolean check(Set<Integer> set, Set<Integer> oct, Set<Integer> hep, Set<Integer> hex, Set<Integer> pen, Set<Integer> sq, Set<Integer> tri) {
        if(set.size() != 6) return false;

        Set<Integer> memory = new HashSet();

        Set<Integer> tmp = new HashSet(set);
        tmp.retainAll(oct);

        if(tmp.size() == 0) return false;
        memory.addAll(tmp);

        tmp = new HashSet(set);
        tmp.removeAll(memory);
        tmp.retainAll(hep);

        if(tmp.size() == 0) return false;
        memory.addAll(tmp);

        tmp = new HashSet(set);
        tmp.removeAll(memory);
        tmp.retainAll(hex);

        if(tmp.size() == 0) return false;
        memory.addAll(tmp);

        tmp = new HashSet(set);
        tmp.retainAll(pen);

        if(tmp.size() == 0) return false;
        memory.addAll(tmp);

        tmp = new HashSet(set);
        tmp.removeAll(memory);
        tmp.retainAll(sq);

        if(tmp.size() == 0) return false;
        memory.addAll(tmp);

        tmp = new HashSet(set);
        tmp.removeAll(memory);
        tmp.retainAll(tri);

        if(tmp.size() == 0) return false;

        return true;
    }

    private boolean check(int x, int y) {
        int xb = x % 100;
        int yf = y / 100;

        if(xb == yf) {
            return true;
        }

        return false;
    }

    private Set<Integer> getFourDigitOctagonalNumber() {
        Set<Integer> oct = new HashSet();
        int n=1;
        while(true) {
            int o = n * ( 3 * n - 2);
            n++;
            if (o < 1000) continue;
            if (o > 10000) break;
            oct.add(o);
        }
        return oct;
    }

    private Set<Integer> getFourDigitHeptagonalNumber() {
        Set<Integer> hep = new HashSet();
        int n=1;
        while(true) {
            int o = n * ( 5 * n - 3) / 2;
            n++;
            if (o < 1000) continue;
            if (o > 10000) break;
            hep.add(o);
        }
        return hep;
    }

    private Set<Integer> getFourDigitHexagonalNumber() {
        Set<Integer> hep = new HashSet();
        int n=1;
        while(true) {
            int o = n * ( 2 * n - 1);
            n++;
            if (o < 1000) continue;
            if (o > 10000) break;
            hep.add(o);
        }
        return hep;
    }

    private Set<Integer> getFourDigitPentagonalNumber() {
        Set<Integer> hep = new HashSet();
        int n=1;
        while(true) {
            int o = n * ( 3 * n - 1) / 2;
            n++;
            if (o < 1000) continue;
            if (o > 10000) break;
            hep.add(o);
        }
        return hep;
    }

    private Set<Integer> getFourDigitSquareNumber() {
        Set<Integer> hep = new HashSet();
        int n=1;
        while(true) {
            int o = n * n;
            n++;
            if (o < 1000) continue;
            if (o > 10000) break;
            hep.add(o);
        }
        return hep;
    }

    private Set<Integer> getFourDigitTriangleNumber() {
        Set<Integer> hep = new HashSet();
        int n=1;
        while(true) {
            int o = n * (n + 1) / 2;
            n++;
            if (o < 1000) continue;
            if (o > 10000) break;
            hep.add(o);
        }
        return hep;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=61";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}
