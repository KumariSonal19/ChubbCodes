package com.example;

import java.util.*;
import java.util.stream.*;

public class StreamSortingDemo {

    public static void main(String[] args) {
        // Sample list of account balances
        List<Double> balances = Arrays.asList(2500.0, 500.0, 7500.0, 1200.0, 4500.0);

        System.out.println("Original List:");
        balances.forEach(System.out::println);

        // Sort balances in ascending order
        List<Double> ascending = balances.stream()
                                         .sorted()
                                         .collect(Collectors.toList());

        System.out.println("\nSorted (Ascending):");
        ascending.forEach(System.out::println);

        // Sort balances in descending order
        List<Double> descending = balances.stream()
                                          .sorted(Comparator.reverseOrder())
                                          .collect(Collectors.toList());

        System.out.println("\nSorted (Descending):");
        descending.forEach(System.out::println);
    }
}
