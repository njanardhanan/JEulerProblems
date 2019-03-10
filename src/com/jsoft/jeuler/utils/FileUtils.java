package com.jsoft.jeuler.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtils {
    public static List<String> getCommaSeparatedWords(String fileName) {
        List<String> list = new ArrayList();
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            list = Arrays.asList(stream.collect(Collectors.joining()).replace("\"", "").split(","));

        } catch (IOException e) {
            System.out.println("Exception while loading " + fileName + ", " + e.getMessage());
        }

        return list;
    }
}
