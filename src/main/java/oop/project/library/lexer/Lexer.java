package oop.project.library.lexer;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

//This class takes the command line argument strings, and reorganizes them into lists of string that correspond to arguments
public class Lexer {
    //I think we should use python's rules for positional and keyword arguments

    //Keyword arguments can break up positional arguments
    //Only the last positional argument can have variable length
    //Keyword arguments may not be of variable length

    //We should also have presence flags
    //We should also have counted flags

    //We should have mutually exclusive flags
    //We should have mutually inclusive flags

    //Yes this needs to have the LinkedHashMap type for type safety reasons
    //We need to guarantee that the order of the insertion is the order of iteration
    //A linked hash map has this property
    public LinkedHashMap<String, LexerArgument> arguments = new LinkedHashMap<>();
    private boolean haveAddedMultivaluePositional = false;

    public void addArgument(String name, LexerArgument.Mode mode, String ... ids){
        if(arguments.containsKey(name)){
            //Do not redefine arguments
            //TODO: Throw a proper error
            throw new RuntimeException();
        }
        if(ids.length == 0 && mode == LexerArgument.Mode.MULTI_PARAMETER){
            if(haveAddedMultivaluePositional){
                //This is invalid, only the last positional argument is allowed to be a multi value positional argument
                //TODO: throw a proper error
                throw new RuntimeException();
            }
            haveAddedMultivaluePositional = true;//Since we are adding a multi value positional argument, set the boolean correctly
        }

        arguments.put(name, new LexerArgument(mode, ids));
    }

    //This parses the inputs list for arguments that correspond to this argument
    //It removes those inputs from the list and returns what remains
    public List<String> parse(List<String> args){
        for (var entry : arguments.entrySet()) {
            String name = entry.getKey();
            LexerArgument argument = entry.getValue();
            args = argument.extract(args);
        }

        return args;
    }


    //For debug purposes
    public void printResult(){
        for(var argument : arguments.entrySet()){
            System.out.println(argument.getKey());
            System.out.println("\t" + argument.getValue().count);
            for(var entry : argument.getValue().strings){
                System.out.println("\t" + entry);
            }
        }
    }

}
