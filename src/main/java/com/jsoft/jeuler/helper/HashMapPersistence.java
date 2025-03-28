package com.jsoft.jeuler.helper;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class HashMapPersistence {

    public static void saveHashMap(Map<Long, Long> map, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(map);
            System.out.println("HashMap saved to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<Long, Long> loadHashMap(String filename) {
        Map<Long, Long> map = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            map = (Map<Long, Long>) ois.readObject();
            System.out.println("HashMap loaded from " + filename);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static void main(String[] args) {
        // Example usage:
        HashMap<Long, Long> originalMap = new HashMap<>();
        originalMap.put(11L, 23L);
        originalMap.put(6L, 3L);
        originalMap.put(7L, 4L);

        String filename = "my_map.ser";
        saveHashMap(originalMap, filename);

        Map<Long, Long> loadedMap = loadHashMap(filename);

        if (loadedMap != null) {
            System.out.println("Loaded HashMap contents:");
            loadedMap.forEach((key, value) -> System.out.println(key + ": " + value));
        }
    }
}
