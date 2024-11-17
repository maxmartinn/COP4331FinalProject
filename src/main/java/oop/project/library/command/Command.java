package oop.project.library.command;

import oop.project.library.lexer.Lexer;
import oop.project.library.parser.Parser;

import java.util.*;

public class Command {
    private final Map<String, String> rawArguments = new HashMap<>();//This stores the unparsed values
    private final Map<String, Parser<?>> argumentParsers = new HashMap<>();//This stores the parsers
    private final Map<String, String> flagToName = new HashMap<>();
    private final List<String> positionalToName = new ArrayList<>();
    private final Set<String> requiredArguments = new HashSet<>(); // This is list of required names

    public Command addArgument(String name, Parser<?> parser, String flag, boolean required) {
        if (argumentParsers.containsKey(name)) {
            // name already used
            throw new RuntimeException("Duplicate argument: " + name);
        }
        if (required) {
            requiredArguments.add(name);
        }
        argumentParsers.put(name, parser);
        flagToName.put(flag, name);

        return this;
    }

    public Command addArgument(String name, Parser<?> parser, boolean required) {
        if (argumentParsers.containsKey(name)) {
            // name already used
            throw new RuntimeException("Duplicate argument: " + name);
        }
        if (required) {
            requiredArguments.add(name);
        }
        argumentParsers.put(name, parser);
        positionalToName.add(name);

        return this;
    }

    public void parse(String input){
        Lexer lexer = new Lexer();
        lexer.parse(input);
        Set<String> remainingRequired = new HashSet<String>(requiredArguments);

        lexer.flags.forEach((flag, rawValue ) -> {

            if(!flagToName.containsKey(flag)){
                //A flag was provided that doesn't exist
                //TODO: Throw an error
                throw new RuntimeException("Unknown flag: " + flag);
            }

            remainingRequired.remove(flagToName.get(flag));
            rawArguments.put(flagToName.get(flag), rawValue);
        });

        for(int i = 0; i < lexer.getLiterals().size(); i++){
            if(i >= positionalToName.size()){
                //Too many positionals provided
                //TODO: Throw an error
                throw new RuntimeException("Too many positonal arguments provided.");
            }

            remainingRequired.remove(positionalToName.get(i));
            rawArguments.put(positionalToName.get(i), lexer.getLiterals().get(i));
        }

        if(!remainingRequired.isEmpty()){
            //Not all the required were provided
            //TODO: Throw an error
            throw new RuntimeException("Not all of the required arguments were provided..");
        }
    }

    public Object getArgument(String name){
        return argumentParsers.get(name).parse(rawArguments.get(name));
    }
}
