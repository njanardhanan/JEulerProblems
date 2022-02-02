package com.jsoft.jeuler.utils;

import java.util.List;

public class Logger {
    long startTime;

    public void start(int n, String problemStatement) {
        System.out.println("Problem : " + n);
        System.out.println();
        System.out.println("Problem Statement:\n" + problemStatement + "\n");
        startTime = System.currentTimeMillis();
    }

    public void stop() {
        long endTime = System.currentTimeMillis();
        System.out.println("Time elapsed : " + (endTime - startTime) + " ms.\n");
    }

    public void logTags(List<String> tags) {
        if (tags == null) return;
        System.out.println("Tags : " + tags);
        System.out.println();
    }

    public void logAnswer(String answer) {
        System.out.println("Answer : " + answer + "\n");
    }
}
