package oop.project.library.scenarios;

import oop.project.library.command.Command;
import oop.project.library.command.CommandBuilder;
import oop.project.library.command.ParsedCommand;
import oop.project.library.lexer.Lexer;
import oop.project.library.lexer.UnpairedFlagException;
import oop.project.library.parser.*;

import java.time.LocalDate;
import java.util.Map;

public class Scenarios {

    public static Result<Map<String, Object>> parse(String command) {
        //Note: Unlike argparse4j, our library will contain a lexer than can
        //support an arbitrary String (instead of requiring a String[] array).
        //We still need to split the base command from the actual arguments
        //string to know which scenario (aka command) we're trying to parse
        //arguments for. This sounds like something a library should handle...
        var split = command.split(" ", 2);
        var base = split[0];
        var arguments = split.length == 2 ? split[1] : "";
        return switch (base) {
            case "lex" -> lex(arguments);
            case "add" -> add(arguments);
            case "sub" -> sub(arguments);
            case "fizzbuzz" -> fizzbuzz(arguments);
            case "difficulty" -> difficulty(arguments);
            case "echo" -> echo(arguments);
            case "search" -> search(arguments);
            case "weekday" -> weekday(arguments);
            default -> throw new AssertionError("Undefined command " + base + ".");
        };
    }

    private static Result<Map<String, Object>> lex(String arguments) {
        //Note: For ease of testing, this should use your Lexer implementation
        //directly rather and return those values.

        Lexer hi = new Lexer();

        try{
            hi.parse(arguments);
        }catch(UnpairedFlagException e){
            return new Result.Failure<>(e.getMessage());
        }


        if(!hi.getLiterals().isEmpty()){
            return new Result.Success<>(Map.of("0", hi.getLiterals().getFirst()));
        }

        if(!hi.getFlags().isEmpty()){
            for(var entry : hi.getFlags().entrySet()){
                if(!entry.getValue().isEmpty()){
                    return new Result.Success<>(Map.of(entry.getKey(), entry.getValue()));
                }
            }
        }

        return new Result.Failure<>(null);
    }

    private static Result<Map<String, Object>> add(String arguments) {
        //Note: For this part of the project, we're focused on lexing/parsing.
        //The implementation of these scenarios isn't going to look like a full
        //command, but rather some weird hodge-podge mix. For example:
        //var args = Lexer.parse(arguments);
        //var left = IntegerParser.parse(args.positional[0]);
        //This is fine - our goal right now is to implement this functionality
        //so we can build up the actual command system in Part 3.
        Command add = new CommandBuilder()
                .addArgument("left", new IntegerParser(), true)
                .addArgument("right", new IntegerParser(), true)
                .build();
        try{
            ParsedCommand results = add.parse(arguments);
            int left = (Integer)results.getArgument("left").orElseThrow();//It shouldn't be able to fail here since the argument is required.
            int right = (Integer)results.getArgument("right").orElseThrow();//It shouldn't be able to fail here since the argument is required.

            return new Result.Success<>(Map.of("left", left, "right", right));
        }catch (Exception e){
            return new Result.Failure<>(e.getMessage());
        }


    }

    private static Result<Map<String, Object>> sub(String arguments) {
        Command sub = new CommandBuilder()
            .addArgument("left", "--left", new DoubleParser(), true)
            .addArgument("right", "--right", new DoubleParser(), true)
            .build();;


        try {
            ParsedCommand results = sub.parse(arguments);
            double left = (Double)results.getArgument("left").orElseThrow();//It shouldn't be able to fail here since the argument is required.
            double right = (Double)results.getArgument("right").orElseThrow();//It shouldn't be able to fail here since the argument is required.

            return new Result.Success<>(Map.of("left", left, "right", right));
        } catch (Exception e){
            return new Result.Failure<>(e.getMessage());
        }
    }

    private static Result<Map<String, Object>> fizzbuzz(String arguments) {
        Command fizzbuzz = new CommandBuilder()
                .addArgument("input", new IntegerParser(1, 100), true)
                .build();

        try{
            ParsedCommand results = fizzbuzz.parse(arguments);
            int number = (Integer)results.getArgument("input").orElseThrow();//It shouldn't be able to fail here since the argument is required.

            return new Result.Success<>(Map.of("number", number));
        }catch (Exception e){
            return new Result.Failure<>(e.getMessage());
        }

    }

    private static Result<Map<String, Object>> difficulty(String arguments) {
        Command command = new CommandBuilder()
                .addArgument("difficulty", new StringParser("easy", "normal", "hard", "peaceful"), true)
                .build();

        try{
            ParsedCommand results = command.parse(arguments);
            String difficulty = (String)results.getArgument("difficulty").orElseThrow();//It shouldn't be able to fail here since the argument is required.

            return new Result.Success<>(Map.of("difficulty", difficulty));
            //return new Result.Failure<>(null);
        }catch (Exception e){
            return new Result.Failure<>(e.getMessage());
        }
    }

    private static Result<Map<String, Object>> echo(String arguments) {
        Command command = new CommandBuilder()
                .addArgument("message", new StringParser(), false)
                .build();


        try{
            ParsedCommand results = command.parse(arguments);
            String message = (String)results.getArgument("message").orElse("Echo, echo, echo...");

            return new Result.Success<>(Map.of("message", message));
        }catch (Exception e){
            return new Result.Failure<>(e.getMessage());
        }
    }

    private static Result<Map<String, Object>> search(String arguments) {
        Command command = new CommandBuilder()
                .addArgument("term", new StringParser(), true)
                .addArgument("case-insensitive", "--case-insensitive", new BooleanParser(), false)
                .build();

        try{
            ParsedCommand results = command.parse(arguments);
            String term = (String)results.getArgument("term").orElseThrow();//It shouldn't be able to fail here since the argument is required.
            boolean isCaseInsensitive = (boolean)results.getArgument("case-insensitive").orElse(false);

            return new Result.Success<>(Map.of("term", term, "case-insensitive", isCaseInsensitive));
        }catch (Exception e){
            return new Result.Failure<>(e.getMessage());
        }
    }

    private static Result<Map<String, Object>> weekday(String arguments) {
        Command command = new CommandBuilder()
                .addArgument("date", new LocalDateParser(), true)
                .build();

        try{
            ParsedCommand results = command.parse(arguments);
            LocalDate date = (LocalDate) results.getArgument("date").orElseThrow();//It shouldn't be able to fail here since the argument is required.

            return new Result.Success<>(Map.of("date", date));
        }catch (Exception e){
            return new Result.Failure<>(e.getMessage());
        }
    }

}
