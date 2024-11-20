package oop.project.library.scenarios;

import oop.project.library.command.Command;
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
                    return new Result.Success<>(Map.of("0", entry.getValue()));
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
        Command add = new Command()
                .addArgument("left", new IntegerParser(), true)
                .addArgument("right", new IntegerParser(), true);;
        try{
            add.parse(arguments);
            int left = (Integer)add.getArgument("left").orElseThrow();//It shouldn't be able to fail here since the argument is required.
            int right = (Integer)add.getArgument("right").orElseThrow();//It shouldn't be able to fail here since the argument is required.

            return new Result.Success<>(Map.of("left", left, "right", right));
        }catch (Exception e){
            return new Result.Failure<>(e.getMessage());
        }


    }

    private static Result<Map<String, Object>> sub(String arguments) {
        Command sub = new Command()
            .addArgument("left", "--left", new DoubleParser(), true)
            .addArgument("right", "--right", new DoubleParser(), true);


        try {
            sub.parse(arguments);
            double left = (Double)sub.getArgument("left").orElseThrow();//It shouldn't be able to fail here since the argument is required.
            double right = (Double)sub.getArgument("right").orElseThrow();//It shouldn't be able to fail here since the argument is required.

            return new Result.Success<>(Map.of("left", left, "right", right));
        } catch (Exception e){
            return new Result.Failure<>(e.getMessage());
        }
    }

    private static Result<Map<String, Object>> fizzbuzz(String arguments) {
        Command fizzbuzz = new Command()
                .addArgument("input", new IntegerParser(1, 100), true);

        try{
            fizzbuzz.parse(arguments);
            int number = (Integer)fizzbuzz.getArgument("input").orElseThrow();//It shouldn't be able to fail here since the argument is required.

            return new Result.Success<>(Map.of("number", number));
        }catch (Exception e){
            return new Result.Failure<>(e.getMessage());
        }

    }

    private static Result<Map<String, Object>> difficulty(String arguments) {
        Command command = new Command()
                .addArgument("difficulty", new StringParser("easy", "normal", "hard", "peaceful"), true);

        try{
            command.parse(arguments);
            String difficulty = (String)command.getArgument("difficulty").orElseThrow();//It shouldn't be able to fail here since the argument is required.

            return new Result.Success<>(Map.of("difficulty", difficulty));
            //return new Result.Failure<>(null);
        }catch (Exception e){
            return new Result.Failure<>(e.getMessage());
        }
    }

    private static Result<Map<String, Object>> echo(String arguments) {
        Command command = new Command()
                .addArgument("message", new StringParser(), false);


        try{
            command.parse(arguments);
            String message = (String)command.getArgument("message").orElse("Echo, echo, echo!");

            return new Result.Success<>(Map.of("message", message));
        }catch (Exception e){
            return new Result.Failure<>(e.getMessage());
        }
    }

    private static Result<Map<String, Object>> search(String arguments) {
        Command command = new Command()
                .addArgument("term", new StringParser(), true)
                .addArgument("case-insensitive", "--case-insensitive", new BooleanParser(), false);

        try{
            command.parse(arguments);
            String term = (String)command.getArgument("term").orElseThrow();//It shouldn't be able to fail here since the argument is required.
            boolean isCaseInsensitive = (boolean)command.getArgument("case-insensitive").orElse(false);

            return new Result.Success<>(Map.of("term", term, "case-insensitive", isCaseInsensitive));
        }catch (Exception e){
            return new Result.Failure<>(e.getMessage());
        }
    }

    private static Result<Map<String, Object>> weekday(String arguments) {
        Command command = new Command()
                .addArgument("date", new LocalDateParser(), true);

        try{
            command.parse(arguments);
            LocalDate date = (LocalDate) command.getArgument("date").orElseThrow();//It shouldn't be able to fail here since the argument is required.

            return new Result.Success<>(Map.of("date", date));
        }catch (Exception e){
            return new Result.Failure<>(e.getMessage());
        }
    }

}
