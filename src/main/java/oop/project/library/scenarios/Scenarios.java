package oop.project.library.scenarios;

import oop.project.library.command.Command;
import oop.project.library.lexer.Lexer;
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

        hi.parse(arguments);

        if(!hi.getLiterals().isEmpty()){
            return new Result.Success<>(Map.of("0", hi.getLiterals().getFirst()));
        }

        if(!hi.flags.isEmpty()){
            for(var entry : hi.flags.entrySet()){
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
            int left = (Integer)add.getArgument("left");
            int right = (Integer)add.getArgument("right");
            return new Result.Success<>(Map.of("left", left, "right", right));
        }catch (Exception e){
            return new Result.Failure<>(null);
        }


    }

    private static Result<Map<String, Object>> sub(String arguments) {
        Command sub = new Command()
            .addArgument("left", new DoubleParser(), "--left", true)
            .addArgument("right", new DoubleParser(), "--right", true);


        try {
            sub.parse(arguments);
            double left = (Double)sub.getArgument("left");
            double right = (Double)sub.getArgument("right");

            return new Result.Success<>(Map.of("left", left, "right", right));
        } catch (Exception e){
            return new Result.Failure<>(null);
        }
    }

    private static Result<Map<String, Object>> fizzbuzz(String arguments) {
        Lexer hi = new Lexer();
        hi.parse(arguments);

        if(hi.getLiterals().size() != 1){
            return new Result.Failure<>(null);
        }

        try{
            FizzBuzzParser fizzBuzzParser = new FizzBuzzParser();
            int number = fizzBuzzParser.parse(hi.getLiterals().getFirst());

            return new Result.Success<>(Map.of("number", number));
        }catch (Exception e){
            return new Result.Failure<>(null);
        }

    }

    private static Result<Map<String, Object>> difficulty(String arguments) {
        Lexer hi = new Lexer();
        hi.parse(arguments);

        if(hi.getLiterals().size() != 1){
            return new Result.Failure<>(null);
        }

        try{
            DifficultyParser difficultyParser = new DifficultyParser();
            String difficulty = difficultyParser.parse(hi.getLiterals().getFirst());

            return new Result.Success<>(Map.of("difficulty", difficulty));
        }catch (Exception e){
            return new Result.Failure<>(null);
        }
    }

    private static Result<Map<String, Object>> echo(String arguments) {
        Lexer hi = new Lexer();
        hi.parse(arguments);

        if(hi.getLiterals().size() > 1){
            return new Result.Failure<>(null);
        }else if(hi.getLiterals().isEmpty()){
            return new Result.Success<>(Map.of("message", "Echo, echo, echo!"));
        }

        try{
            StringParser stringParser = new StringParser();
            String message = stringParser.parse(hi.getLiterals().get(0));

            return new Result.Success<>(Map.of("message", message));
        }catch (Exception e){
            return new Result.Failure<>(null);
        }
    }

    private static Result<Map<String, Object>> search(String arguments) {
        Lexer hi = new Lexer();
        hi.parse(arguments);

        if(hi.getLiterals().size() != 1){
            return new Result.Failure<>(null);
        }

        try{
            StringParser stringParser = new StringParser();
            String term = stringParser.parse(hi.getLiterals().get(0));
            boolean isCaseInsensitive = false;
            if(hi.flags.containsKey("--case-insensitive")){
                BooleanParser booleanParser = new BooleanParser();
                isCaseInsensitive = booleanParser.parse(hi.flags.get("--case-insensitive"));
            }

            return new Result.Success<>(Map.of("term", term, "case-insensitive", isCaseInsensitive));
        }catch (Exception e){
            return new Result.Failure<>(null);
        }
    }

    private static Result<Map<String, Object>> weekday(String arguments) {
        Lexer hi = new Lexer();
        hi.parse(arguments);

        if(hi.getLiterals().size() != 1){
            return new Result.Failure<>(null);
        }

        try{
            LocalDateParser localDateParser = new LocalDateParser();
            LocalDate date = localDateParser.parse(hi.getLiterals().get(0));


            return new Result.Success<>(Map.of("date", date));
        }catch (Exception e){
            return new Result.Failure<>(null);
        }
    }

}
