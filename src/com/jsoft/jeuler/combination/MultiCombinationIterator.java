package com.jsoft.jeuler.combination;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


public class MultiCombinationIterator<T> implements Iterator<List<T>> {

    final List<T> originalVector;
    final int combinationLength;
    private final int lengthN;
    private final int lengthK;
    private final List<T> currentCombination;
    private long currentIndex;
    private final int[] bitVector;


    // Criteria to stop iterating
    private boolean end;


    public MultiCombinationIterator(Collection<T> originalVector,
                             int combinationsLength) {
        this.originalVector = new ArrayList<>(originalVector);
        this.combinationLength = Math.max(combinationsLength, 0);
        lengthN = this.originalVector.size();
        currentCombination = new ArrayList<>();
        bitVector = new int[this.combinationLength];
        lengthK = this.combinationLength - 1;
        for (int i = 0; i < this.combinationLength; i++) {
            bitVector[i] = 0;
        }
        end = false;
        currentIndex = 0;
    }

    private static <T> void setValue(List<T> list, int index, T value) {
        if (index < list.size()) {
            list.set(index, value);
        } else {
            list.add(index, value);
        }
    }

    @Override
    public boolean hasNext() {
        return (!end);
    }

    @Override
    public List<T> next() {
        currentIndex++;

        for (int i = 0; i < this.combinationLength; i++) {
            int index = bitVector[i];
            if (this.originalVector.size() > 0) {
                setValue(currentCombination, i, this.originalVector
                        .get(index));
            }
        }

        if (bitVector.length > 0) {
            bitVector[lengthK]++;

            if (bitVector[lengthK] > lengthN - 1) {
                int index = -1;
                for (int i = 1; i <= bitVector.length; i++) {
                    if (lengthK - i >= 0) {
                        if (bitVector[lengthK - i] < lengthN - 1) {
                            index = lengthK - i;
                            break;
                        }
                    }
                }

                if (index != -1) {
                    bitVector[index]++;

                    for (int j = 1; j < bitVector.length - index; j++) {
                        bitVector[index + j] = bitVector[index];
                    }

                } else {
                    end = true;
                }

            }
        } else {
            end = true;
        }

        // return a copy of the current combination
        return new ArrayList<>(currentCombination);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return "MultiCombinationIterator=[#" + currentIndex + ", " + currentCombination + "]";
    }
}