package com.jsoft.jeuler.prime;

import java.util.LinkedList;

public class PrimeIndexGenerator {
        private LinkedList<Integer> primeIndex;

        public PrimeIndexGenerator(int size) {
            primeIndex = new LinkedList<>();
            for (int i=0; i<size; i++) {
                primeIndex.add(i);
            }
        }

        public LinkedList<Integer> getPrimeIndex() {
            return primeIndex;
        }

        public void next() {
            int lastIndex = primeIndex.size()-1;
            primeIndex.set(lastIndex, primeIndex.get(lastIndex) + 1);
        }

        public boolean leftShift() {
            return leftShift(primeIndex.size() - 2);
        }

        private boolean leftShift(int index) {
            if (index < 0) {
                return false;
                //primeIndex.set(0, primeIndex.get(0) + 1);
                //return true;
            }
            int v = primeIndex.get(index) + 1;
            if (v == primeIndex.get(index + 1)) {
                return leftShift(index-1);
            } else {
                primeIndex.set(index, v);
                for (int i = index + 1; i < primeIndex.size(); i++) {
                    primeIndex.set(i, primeIndex.get(i - 1) + 1);
                }
            }
            return true;
        }
    }