package oop.project.library.command;

import oop.project.library.lexer.Lexer;
import oop.project.library.parser.Parser;

import java.util.*;


/**
 * Used to setup the configuration of a command
 */
public class CommandBuilder {
    protected final Map<String, CommandArgument> arguments = new HashMap<>();//This stores the parsers

    //This is how the mapping from flag and positionals to argument names is done
    protected final Map<String, String> flagToName = new HashMap<>();
    protected final List<String> positionalToName = new ArrayList<>();

    protected final Set<String> requiredArguments = new HashSet<>(); // This is list of required argument names

    /**
     * Adds an argument to the command builder.
     * Duplicate names are not allowed.
     * Duplicate flags are not allowed.
     *
     * @param name the name of the argument
     * @param flag the flag for the argument (starting with "--")
     * @param parser the parser for the argument
     * @param required whether the argument is required
     * @return this command builder for chaining methods
     */
    public CommandBuilder addArgument(String name, String flag, Parser<?> parser, boolean required) {
        flag = flag.substring(2);//The -- in the flag is not part of the flag

        if(arguments.containsKey(name)){
            // name already used
            throw new RuntimeException("Duplicate argument: " + name);
        }

        if(flagToName.containsKey(flag)){
            // flag already used
            throw new RuntimeException("Duplicate flag: " + flag);
        }

        if (required) {
            requiredArguments.add(name);
        }

        arguments.put(name, new CommandArgument(parser));
        flagToName.put(flag, name);

        return this;
    }

    /**
     * Adds an argument to the command builder.
     * Duplicate names are not allowed.
     *
     * @param name the name of the argument
     * @param parser the parser for the argument
     * @param required whether the argument is required
     * @return this command builder for chaining methods
     */
    public CommandBuilder addArgument(String name, Parser<?> parser, boolean required) {
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

    /**
     * Creates a Command
     */
    public Command build(){
        return new Command(this);
    }
}
