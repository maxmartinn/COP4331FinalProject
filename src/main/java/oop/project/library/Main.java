package oop.project.library;

import oop.project.library.lexer.LexerArgument;
import oop.project.library.scenarios.Result;
import oop.project.library.scenarios.Scenarios;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        LexerArgument hi = new LexerArgument(LexerArgument.Mode.PRESENCE, "-f", "--flag");
        LexerArgument hi2 = new LexerArgument(LexerArgument.Mode.MULTI_PARAMETER, "-d", "--deets");

        List<String> inputs = Arrays.asList("-ffff", "Howdy", "-f", "-d", "These are the deets", "--deets", "Ye-haw");

        //LexerArgument hi2 = new LexerArgument(LexerArgument.Mode.SINGLE_PARAMETER, "-d", "--deets");
        //List<String> inputs = Arrays.asList("-ffff", "Howdy", "-f", "-d", "These are the deets", "--deets", "Ye-haw");//Should crash because two values were given to a one parameter argument


        inputs = hi.extract(inputs);

        System.out.println("Remaining Inputs to Lexer");
        for (var remaining : inputs) {
            System.out.println(remaining);
        }


        System.out.println();
        System.out.println("Extracted result");
        System.out.println("Count: " + hi.count);

        for (var bye : hi.strings) {
            System.out.println(bye);
        }

        System.out.println();


        inputs = hi2.extract(inputs);

        System.out.println("Remaining Inputs to Lexer");
        for(var remaining : inputs){
            System.out.println(remaining);
        }


        System.out.println();
        System.out.println("Extracted result");
        System.out.println("Count: " + hi2.count);

        for(var bye : hi2.strings){
            System.out.println(bye);
        }

        //var scanner = new Scanner(System.in);
        //while (true) {
        //    var input = scanner.nextLine();
        //    if (input.equals("exit")) {
        //        break;
        //    }
        //    try {
        //        var result = Scenarios.parse(input);
        //        switch (result) {
        //            case Result.Success<Map<String, Object>> success -> System.out.println(success.value());
        //            case Result.Failure<Map<String, Object>> failure -> System.out.println("Error: " + failure.error());
        //        }
        //    } catch (Exception e) {
        //        System.out.println("Unexpected exception: " + e.getClass().getName() + ", " + e.getMessage());
        //    }
        //}
    }

}
