package oop.project.library.command;

import oop.project.library.lexer.Lexer;
import oop.project.library.parser.Parser;

import java.util.*;

public class Command {
    private final Map<String, CommandArgument> arguments = new HashMap<>();//This stores the parsers

    //This is how the mapping from flag and positionals to argument names is done
    private final Map<String, String> flagToName = new HashMap<>();
    private final List<String> positionalToName = new ArrayList<>();

    private final Set<String> requiredArguments = new HashSet<>(); // This is list of required argument names

    public Command addArgument(String name, String flag, Parser<?> parser, boolean required) {
        if(arguments.containsKey(name)){
            // name already used
            throw new RuntimeException("Duplicate argument: " + name);
        }

        if (required) {
            requiredArguments.add(name);
        }

        arguments.put(name, new CommandArgument(parser));
        flagToName.put(flag, name);

        return this;
    }

    public Command addArgument(String name, Parser<?> parser, boolean required) {
        if(arguments.containsKey(name)){
            // name already used
            throw new RuntimeException("Duplicate argument: " + name);
        }

        if (required) {
            requiredArguments.add(name);
        }

        arguments.put(name, new CommandArgument(parser));
        positionalToName.add(name);

        return this;
    }

    public void parse(String input){
        //Use the lexer to separate the input into easily used variables.
        Lexer lexer = new Lexer();
        lexer.parse(input);

        //Make a set of all the arguments that need to be found
        Set<String> remainingRequired = new HashSet<>(requiredArguments);

        //Go through every flag the lexer found
        lexer.getFlags().forEach((flag, rawValue) -> {

            //If the flag that was found does not correspond to any argument that was established with this command, then throw an error
            if(!flagToName.containsKey(flag)){
                //A flag was provided that doesn't exist
                throw new UnknownFlagException("Unknown flag: " + flag);
            }

            //Otherwise, remove this argument from the list of required arguments
            //And parse the value
            String name = flagToName.get(flag);
            remainingRequired.remove(name);
            arguments.get(name).parse(rawValue);
        });

        //Go through every literal the lexer found
        for(int i = 0; i < lexer.getLiterals().size(); i++){
            if(i >= positionalToName.size()){
                //Too many positionals provided
                throw new TooManyPositionalsException("Too many positional arguments provided.");
            }

            //Otherwise, remove this argument from the list of required arguments
            //And parse the value
            String name = positionalToName.get(i);
            remainingRequired.remove(name);
            arguments.get(name).parse(lexer.getLiterals().get(i));
        }

        if(!remainingRequired.isEmpty()){
            //Not all the required were provided
            throw new MissingRequiredArgumentException("Not all of the required arguments were provided.");
        }
    }

    public Optional<Object> getArgument(String name){
        CommandArgument result = arguments.get(name);

        //If the name is not used by any of the arguments, then return an empty optional
        if(result == null){
            return Optional.empty();
        }
        return Optional.ofNullable(result.value());//This is an optional to force the user to ensure that the argument is there (only a problem for optional arguments, but must be done regardless)
    }
}
