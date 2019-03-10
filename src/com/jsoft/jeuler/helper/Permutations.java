package com.jsoft.jeuler.helper;

import java.util.*;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Permutations {
    private Permutations() {
    }


    public static boolean isPermutation(int x, int y) {
        return isPermutation((long)x, (long)y);
    }

    public static boolean isPermutation(long x, long y) {
        int[] val = new int[10];
        Arrays.fill(val, 0);
        while(x>0) {
            val[(int)x%10] += 1;
            x = x/10;
        }
        while(y>0) {
            val[(int)y%10] -= 1;
            y = y/10;
        }
        for(int i=0; i<10; i++) {
            if(val[i] > 0) return false;
        }
        return true;
    }

    public static long factorial(int n) {
        if (n > 20 || n < 0) throw new IllegalArgumentException(n + " is out of range");
        return LongStream.rangeClosed(2, n).reduce(1, (a, b) -> a * b);
    }
    public static <T> List<T> permutation(long no, List<T> items) {
        return permutationHelper(no,
                new LinkedList<>(Objects.requireNonNull(items)),
                new ArrayList<>());
    }
    private static <T> List<T> permutationHelper(long no, LinkedList<T> in, List<T> out) {
        if (in.isEmpty()) return out;
        long subFactorial = factorial(in.size() - 1);
        out.add(in.remove((int) (no / subFactorial)));
        return permutationHelper((int) (no % subFactorial), in, out);
    }
    @SafeVarargs
    @SuppressWarnings("varargs") // Creating a List from an array is safe
    public static <T> Stream<Stream<T>> of(T... items) {
        List<T> itemList = Arrays.asList(items);
        return LongStream.range(0, factorial(items.length))
                .mapToObj(no -> permutation(no, itemList).stream());
    }
}
