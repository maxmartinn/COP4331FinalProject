package oop.project.library.command;

import oop.project.library.lexer.Lexer;
import oop.project.library.parser.Parser;

import java.util.*;


/**
 * Stores the configuration used for parsing a command.
 * Additionally, it performs command parsing.
 */
public class Command {

    protected final Map<String, CommandArgument> arguments;//This stores the parsers

    //This is how the mapping from flag and positionals to argument names is done
    protected final Map<String, String> flagToName;
    protected final List<String> positionalToName;

    protected final Set<String> requiredArguments; // This is list of required argument names

    /**
     * Package protected constructor to prevent direct instantiation.
     *
     * @param builder the command builder used to construct this command
     */
    Command(CommandBuilder builder){
        this.arguments = builder.arguments;
        this.flagToName = builder.flagToName;
        this.positionalToName = builder.positionalToName;
        this.requiredArguments = builder.requiredArguments;
    }

    /**
     * Parses the given input and returns the parsed command.
     *
     * @param input the input to be parsed
     * @return the parsed command
     */
    public ParsedCommand parse(String input){
        //Use the lexer to separate the input into easily used variables.
        Lexer lexer = new Lexer();
        lexer.parse(input);

        //Make a set of all the arguments that need to be found
        Set<String> remainingRequired = new HashSet<>(requiredArguments);

        HashMap<String, Object> parsedResults = new HashMap<>();

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
            parsedResults.put(name, arguments.get(name).parse(rawValue));
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
            parsedResults.put(name, arguments.get(name).parse(lexer.getLiterals().get(i)));
        }

        if(!remainingRequired.isEmpty()){
            //Not all the required were provided
            throw new MissingRequiredArgumentException("Not all of the required arguments were provided.");
        }

        return new ParsedCommand(parsedResults);
    }


}
