package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.helper.NumericHelper;
import com.jsoft.jeuler.solver.EulerSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JEulerProblem_0413 extends EulerSolver {

    public JEulerProblem_0413(int problemNumber) {
        super(problemNumber);
    }

    private boolean DEBUG_MODE = false;

    private class State {
        long count = 0;
        boolean oneChildState = false;
        List<Integer> remainders = new ArrayList<>();

        @Override
        public String toString() {
            return String.format("(%d, %b, %s)", count, oneChildState, Arrays.toString(remainders.toArray()));
        }

        public String stateData() {
            StringBuilder stateData = new StringBuilder();
            stateData.append("[");
            stateData.append(oneChildState);
            stateData.append(", ");
            Collections.sort(remainders);
            stateData.append(Arrays.toString(remainders.toArray()));
            stateData.append("]");
            return stateData.toString();
        }

        public State apply(State state, int num) {
            State newState = new State();
            newState.count = state.count * count;
            if (state.oneChildState || oneChildState) {
                newState.oneChildState = true;
            }
            newState.remainders.addAll(state.remainders);
            for (int p : remainders) {
                for (int c : state.remainders) {
                    int r = (p*10 + c) % num;
                    if (r == 0 && newState.oneChildState) {
                        return null;
                    } else if (r == 0) {
                        newState.oneChildState = true;
                    }
                    newState.remainders.add(r);
                }
            }
            return newState;
        }
    }

    @Override
    public String solve() {
        long ans = 0;
        for (int i=1; i<=19; i++) {
            if (i == 10) continue;
            long tmp = solve(i);
            ans += tmp;
            System.out.printf("%d = %d, total : %d%n", i, tmp, ans);
        }

        return Long.toString(ans);
    }

    private long solve(int n) {
        List<State> states = constructState(n, false);
        List<State> nextStates = constructState(n, true);

        for (int i=1; i<n; i++) {
            System.out.println("Starting : " + i + " with " + states.size() + " states");
            Map<String, State> stateMap = new HashMap<>();
            for (State currState : states) {
                for (State nextState : nextStates) {
                    if ((currState.oneChildState && nextState.oneChildState)) {
                        continue;
                    }
                    State newState = currState.apply(nextState, n);
                    if (DEBUG_MODE) System.out.println(currState + " vs " + nextState + "= " + newState);
                    if (newState == null) {
                        continue;
                    }

                    //Merge the similar states
                    if (stateMap.containsKey(newState.stateData())) {
                        State oldState = stateMap.get(newState.stateData());
                        oldState.count += newState.count;
                    } else {
                        stateMap.put(newState.stateData(), newState);
                    }
                }
            }

            states = new ArrayList<>(stateMap.values());
        }

        long ans = 0;
        for (State s : states) {
            if (s.oneChildState) {
                ans += s.count;
            }
        }
        return ans;
    }

    private List<State> constructState(int n, boolean allowLeadingZero) {
        State[] states = new State[n];
        int start = (allowLeadingZero) ? 0 : 1;
        for (int i = start; i <= 9; i++) {
            int r = i%n;
            if (states[r] == null) {
                states[r] = new State();
                states[r].count = 1;
                if (r == 0) states[r].oneChildState = true;
                states[r].remainders.add(r);
            } else {
                states[r].count = states[r].count + 1;
            }
        }

        List<State> result = new ArrayList<>();
        for (State s : states) {
            if (s != null) {
                result.add(s);
            }
        }
        return result;
    }

    private long solveBruteForce(int n) {
        long end = (long)Math.pow(10, n);
        long start = end / 10;
        long count = 0L;
        for (long i=start; i<end; i++) {
            String s = String.valueOf(i);
            int c = s.length() - s.replace("0", "").length();
            if (c > 1) continue;
            List<Long> list = findSubSets(i);
            int oneChild = 0;
            for (long l : list) {
                if (l%n == 0) {
                    oneChild++;
                }
                if (oneChild > 1) {
                    break;
                }
            }

            if (oneChild == 1) {
                count++;
            }
        }

        System.out.println("Brute force count = " + count);

        return count;
    }

    public static List<Long> findSubSets(long n) {

        // to store all substrings
        List<Long> res = new ArrayList<>();
        List<Long> s = NumericHelper.toDigits(n);
        for (int i = 0; i < s.size(); i++) {
            for (int j = i; j < s.size(); j++) {

                // substr function takes starting index
                // and ending index + 1 as parameters
                res.add(NumericHelper.fromDigitsLong( s.subList(i, j + 1)));
            }
        }
        return res;
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=413\n" +
                "<p>We say that a $d$-digit positive number (no leading zeros) is a one-child number if exactly one of its sub-strings is divisible by $d$.</p>\n" +
                "\n" +
                "<p>For example, $5671$ is a $4$-digit one-child number. Among all its sub-strings $5$, $6$, $7$, $1$, $56$, $67$, $71$, $567$, $671$ and $5671$, only $56$ is divisible by $4$.<br>\n" +
                "Similarly, $104$ is a $3$-digit one-child number because only $0$ is divisible by $3$.<br>\n" +
                "$1132451$ is a $7$-digit one-child number because only $245$ is divisible by $7$.</p>\n" +
                "\n" +
                "<p>Let $F(N)$ be the number of the one-child numbers less than $N$.<br>\n" +
                "We can verify that $F(10) = 9$, $F(10^3) = 389$ and $F(10^7) = 277674$.</p>\n" +
                "\n" +
                "<p>Find $F(10^{19})$.</p>";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("DP", "Dynamic Programming");
    }
}
