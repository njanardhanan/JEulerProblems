package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class JEulerProblem_0905 extends EulerSolver {

    private final boolean DEBUG = false;

    public JEulerProblem_0905(int problemNumber) {
        super(problemNumber);
    }

    class Configuration {
        private final long aValue;
        private final long bValue;
        private final long cValue;

        private Configuration next;
        private Configuration prev;

        public Configuration(Configuration config) {
            this.aValue = config.aValue;
            this.bValue = config.bValue;
            this.cValue = config.cValue;

            this.next = config.next;
            this.prev = config.prev;
        }

        public Configuration(long aValue, long bValue, long cValue) {
            this.aValue = aValue;
            this.bValue = bValue;
            this.cValue = cValue;

            this.next = null;
            this.prev = null;
        }

        public boolean isTerminalConfiguration() {
            if (aValue == bValue || aValue == cValue || bValue == cValue) {
                return true;
            }
            return false;
        }

        public Configuration generateConfiguration() {
            if (aValue > bValue && aValue > cValue) {
                if (bValue > cValue) {
                    return new Configuration(bValue - cValue, bValue, cValue);
                } else {
                    return new Configuration(cValue - bValue, bValue, cValue);
                }
            }

            if (bValue > aValue && bValue > cValue) {
                if (aValue > cValue) {
                    return new Configuration(aValue, aValue - cValue, cValue);
                } else {
                    return new Configuration(aValue, cValue - aValue, cValue);
                }
            }

            if (cValue > aValue && cValue > bValue) {
                if (aValue > bValue) {
                    return new Configuration(aValue, bValue, aValue - bValue);
                } else {
                    return new Configuration(aValue, bValue, bValue - aValue);
                }
            }
            return null;
        }

        private boolean doesHaveCue(long x, long y, long z) {
            if (x == y + z) {
                return true;
            }
            return false;
        }

        public boolean doesAHaveCue() {
            return doesHaveCue(aValue, bValue, cValue);
        }

        public boolean doesBHaveCue() {
            return doesHaveCue(bValue, aValue, cValue);
        }

        public boolean doesCHaveCue() {
            return doesHaveCue(cValue, aValue, bValue);
        }

        @Override
        public String toString() {
            return "[" + aValue + "," + bValue + "," + cValue + "]";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Configuration data = (Configuration) o;
            if (aValue != data.aValue) return false;
            if (bValue != data.bValue) return false;
            if (cValue != data.cValue) return false;
            return true;
        }

        @Override
        public int hashCode() {
            List<Long> list = Arrays.asList(aValue, bValue, cValue);
            return list.hashCode();
        }
    }

    class DoubleLinkedList {
        public Configuration head;
        public Configuration tail;

        public DoubleLinkedList(Configuration config) {
            if (config == null) return;
            while (!config.isTerminalConfiguration()) {
                insertFirst(new Configuration(config));
                config = config.generateConfiguration();
            }
            insertFirst(new Configuration(config));
        }

        public void insertFirst(Configuration config) {
            if (head == null) {
                head = config;
                tail = config;
            } else {
                config.next = head;
                head.prev = config;
                head = config;
            }
        }

        public void insertLast(Configuration config) {
            if (tail == null) {
                tail = config;
                head = config;
            } else {
                tail.next = config;
                config.prev = tail;
                tail = config;
            }
        }

        public Configuration removeFirst() {
            if (head == null) {
                return null;
            }

            Configuration temp = head;
            if (head == tail) {
                head = null;
                tail = null;
            } else {
                head = head.next;
                head.prev = null;
                temp.next = null;
            }
            return temp;
        }
        public Configuration removeLast() {
            if (tail == null) {
                return null;
            }

            Configuration temp = tail;
            if (head == tail) {
                head = null;
                tail = null;
            } else {
                tail = tail.prev;
                tail.next = null;
                temp.prev = null;
            }
            return temp;
        }

        public Configuration remove(Configuration node) {
            Configuration temp = head;
            while (!temp.equals(node)) {
                temp = temp.next;
            }

            if (temp == head) {
                return removeFirst();
            }
            if (temp == tail) {
                return removeLast();
            }

            temp.prev.next = temp.next;
            temp.next.prev = temp.prev;

            return temp;
        }

        public boolean isSingleNode() {
            return head == tail && head.next == null && head.prev == null;
        }

        public boolean doesAHaveCue() {
            return head.doesAHaveCue();
        }

        public boolean doesBHaveCue() {
            return head.doesBHaveCue();
        }

        public boolean doesCHaveCue() {
            return head.doesCHaveCue();
        }

        public void print() {
            Configuration temp = head;
            while (temp != null) {
                System.out.printf(temp + ", ");
                temp = temp.next;
            }
            System.out.println();
        }
    }

    public long solve(long a, long b, long c) {
        long turnCount = 0;
        Configuration configA = null;
        Configuration configB = null;
        Configuration configC = null;
        if (a == b+c) {
            configA = new Configuration(a, b, c);
            configB = new Configuration(a, a+c, c);
            configC = new Configuration(a, b, a+b);
        } else if (b == a+c) {
            configA = new Configuration(b+c, b, c);
            configB = new Configuration(a, b, c);
            configC = new Configuration(a, b, a+b);
        } else if (c == a+b) {
            configA = new Configuration(b+c, b, c);
            configB = new Configuration(a, a+c, c);
            configC = new Configuration(a, b, c);
        }

        DoubleLinkedList playerA = new DoubleLinkedList(configA);
        DoubleLinkedList playerB = new DoubleLinkedList(configB);
        DoubleLinkedList playerC = new DoubleLinkedList(configC);

        print(playerA, playerB, playerC);

        int turn = 0;
        while (true) {
            turnCount++;
            if (turn == 0) {
                if (playerA.isSingleNode()) {
                    System.out.println("Player A: Now I know");
                    break;
                }

                if (playerA.doesAHaveCue()) {
                    Configuration cueConfig = playerA.removeFirst();
                    playerB.remove(cueConfig);
                    playerC.remove(cueConfig);
                }
                turn = 1;
            } else if (turn == 1) {
                if (playerB.isSingleNode()) {
                    System.out.println("Player B: Now I know");
                    break;
                }

                if (playerB.doesBHaveCue()) {
                    Configuration cueConfig = playerB.removeFirst();
                    playerA.remove(cueConfig);
                    playerC.remove(cueConfig);
                }

                turn = 2;
            } else if (turn == 2) {
                if (playerC.isSingleNode()) {
                    System.out.println("Player C: Now I know");
                    break;
                }

                if (playerC.doesCHaveCue()) {
                    Configuration cueConfig = playerC.removeFirst();
                    playerA.remove(cueConfig);
                    playerB.remove(cueConfig);
                }

                turn = 0;
            }
            print(playerA, playerB, playerC);
        }
        return turnCount;
    }

    public void print(DoubleLinkedList playerA, DoubleLinkedList playerB, DoubleLinkedList playerC) {
        if (!DEBUG) return;
        playerA.print();
        playerB.print();
        playerC.print();
        System.out.println();
    }

    @Override
    public String solve() {
        long ans = 0;
        for (int a = 1; a <= 7; a++) {
            for (int b = 1; b <= 19; b++) {
                long powerAB = power(a, b);
                long powerBA = power(b, a);
                long tmp = solve(powerAB, powerBA, powerAB + powerBA);
                System.out.printf("[%d, %d] = %d ", a, b, tmp);
                ans += tmp;
            }
        }

        return Long.toString(ans);
    }

    public long power(int a, int b) {
        BigInteger x = BigInteger.valueOf(a).pow(b);
        return x.longValue();
    }

    @Override
    public String getProblemStatement() {
        return "https://projecteuler.net/problem=905\n" +
                "Three epistemologists, known as A, B, and C, are in a room, each wearing a hat with a number on it. They have been informed beforehand that all three numbers are positive and that one of the numbers is the sum of the other two.\n" +
                "Once in the room, they can see the numbers on each other's hats but not on their own. Starting with A and proceeding cyclically, each epistemologist must either honestly state \"I don't know my number\" or announce \"Now I know my number!\" which terminates the game.\n" +
                "For instance, if their numbers are $A=2, B=1, C=1$ then A declares \"Now I know\" at the first turn. If their numbers are $A=2, B=7, C=5$ then \"I don't know\" is heard four times before B finally declares \"Now I know\" at the fifth turn.\n" +
                "Let F(A,B,C) be the number of turns it takes until an epistemologist declares \"Now I know\", including the turn this declaration is made. So F(2,1,1)=1 and F(2,7,5)=5.\n" +
                "Find $\\displaystyle \\sum_{a=1}^7 \\sum_{b=1}^{19} F(a^b, b^a, a^b + b^a)$.</p>";
    }

    @Override
    public List<String> getTags() {
        return Arrays.asList("fibanocci", "three hat problem", "problem905-three-hat.pdf");
    }
}
