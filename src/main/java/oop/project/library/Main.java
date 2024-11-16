package oop.project.library;

import oop.project.library.parser.DoubleParser;
import oop.project.library.parser.IntegerParser;
import oop.project.library.parser.Parser;
import oop.project.library.scenarios.Result;
import oop.project.library.scenarios.Scenarios;

import java.util.*;

public class Main {

    public static void main(String[] args) {

//        //This is code from the template, do not remove
//        var scanner = new Scanner(System.in);
//        while (true) {
//            var input = scanner.nextLine();
//            if (input.equals("exit")) {
//                break;
//            }
//            try {
//                var result = Scenarios.parse(input);
//                switch (result) {
//                    case Result.Success<Map<String, Object>> success -> System.out.println(success.value());
//                    case Result.Failure<Map<String, Object>> failure -> System.out.println("Error: " + failure.error());
//                }
//            } catch (Exception e) {
//                System.out.println("Unexpected exception: " + e.getClass().getName() + ", " + e.getMessage());
//            }
//        }

        HashMap<String, Parser<?>> hashMap = new HashMap<>();
        hashMap.put(";", new DoubleParser());
        hashMap.put("I", new IntegerParser());
    }



}
