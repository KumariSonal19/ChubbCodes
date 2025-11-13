package com.example;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GroupByOperations {

	public static void main(String[] args) {
		
		Stream<Integer> nums = Stream.of(2,7,89,56,78,498,76);
		
		var averag = nums.collect(Collectors.averagingInt(s->s));
		System.out.println("average of nums "+averag);
		
		var namelist = Arrays.asList("James","Jony","Rohan","Roy");
		
		namelist.stream().map(name->name.toUpperCase()).forEach(System.out::println);
		
		var filtermap = namelist.stream()
				.collect(Collectors.groupingBy(s->s.charAt(0)));
		
		var keyset = filtermap.keySet();
		  
		System.out.println("size of keyset "+keyset.size());
		System.out.println("keyset "+keyset);
		  
		keyset.forEach(
			s-> System.out.println("value of s - " + s)
		);
		  
		filtermap.forEach((letter, group) -> 
          System.out.println(letter + " -> " + group)
        );
	}
}
