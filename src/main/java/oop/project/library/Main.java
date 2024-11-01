package oop.project.library;

import oop.project.library.lexer.Lexer;
import oop.project.library.lexer.LexerArgument;
import oop.project.library.scenarios.Result;
import oop.project.library.scenarios.Scenarios;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Lexer hi = new Lexer();
        hi.addArgument("Verbosity", LexerArgument.Mode.COUNT, "-v", "--verbose");
        hi.addArgument("Deets", LexerArgument.Mode.SINGLE_PARAMETER, "--deets");

        Lexer bye = new Lexer();
        bye.addArgument("Verbosity", LexerArgument.Mode.COUNT, "-v", "--verbose");
        bye.addArgument("Deets", LexerArgument.Mode.MULTI_PARAMETER, "--deets");
        bye.addArgument("Babel", LexerArgument.Mode.MULTI_PARAMETER);

        List<String> inputs1 = Arrays.asList("SUP", "-vvv", "poopy", "--deets", "This is what's happening");
        List<String> inputs2 = Arrays.asList("SUP", "-vvv", "poopy", "--deets", "This is what's happening", "--deets", "And its happening again");


        var remains1 = hi.parse(inputs1);
        //var remains1 = hi.parse(inputs2);//Should throw an error because two values for deets are given
        hi.printResult();
        System.out.println("Unparsed");
        for(var entry : remains1){
            System.out.println("\t" + entry);
        }
        System.out.println();

        var remains2 = bye.parse(inputs1);
        bye.printResult();
        System.out.println("Unparsed");
        for(var entry : remains2){
            System.out.println("\t" + entry);
        }
        System.out.println();


        //This is code from the template, do not remove
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
