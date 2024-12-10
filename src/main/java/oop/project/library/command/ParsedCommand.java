package oop.project.library.command;

import java.util.Map;
import java.util.Optional;

/**
 * Holds the result of parsing a command.
 */
public class ParsedCommand {
    private final Map<String, Object> objects;

    //Inaccessible to the end user
    ParsedCommand(Map<String, Object> objects){
        this.objects = objects;
    }

    /**
     * Gets the parsed value of an argument by its name.
     *
     * @param name the name of the argument
     * @return the parsed value of the argument, or an empty optional if the argument was not found
     */
    public Optional<Object> getArgument(String name){
        Object result = objects.get(name);

        return Optional.ofNullable(result);//This is an optional to force the user to ensure that the argument is there (only a problem for optional arguments, but must be done regardless)
    }
}
