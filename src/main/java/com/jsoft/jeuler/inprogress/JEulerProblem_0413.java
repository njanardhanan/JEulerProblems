package com.jsoft.jeuler.inprogress;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class OneChildNumber {

    private static boolean DEBUG_MODE = false;

    static class State {
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

    public static void main(String[] args) {
        long ans = 0;
        for (int i=1; i<=19; i++) {
            if (i == 10) continue;
            long tmp = solve(i);
            ans += tmp;
            System.out.printf("%d = %d, total : %d%n", i, tmp, ans);
        }

        System.out.println("Answer : " + ans);
    }

    private static long solve(int n) {
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

    private static List<State> constructState(int n, boolean allowLeadingZero) {
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
}
