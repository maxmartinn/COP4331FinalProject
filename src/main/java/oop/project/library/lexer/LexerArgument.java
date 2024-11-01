package oop.project.library.lexer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexerArgument {
    //These are all the ways that a flag can be identified
    //All ids must be valid for this regex ^-[^\s-]$|^--[^\s-]\S+$
    //  An id either has one hyphen and a single non-whitespace character (that isn't '-')
    //  Or an id has two hyphens and two or more non-whitespace characters (The first non-whitespace must not be '-')
    //If no id is given then it is a positional argument
    List<Pattern> flags = new ArrayList<>();

    static Pattern idPattern = Pattern.compile("^-[^\\s-]$|^--[^\\s-]\\S+$");
    static Pattern singlePattern = Pattern.compile("^-[^\\s-]$");
    static Pattern multiPattern = Pattern.compile("^--[^\\s-]\\S+$");

    public enum Mode{
        COUNT,//How many times is the flag there (Both -vvv and -v -v -v, should give the same result)
        SINGLE_PARAMETER,//Get the string that comes after the flag
        MULTI_PARAMETER//The flag must be placed before every input for this argument
    }

    final Mode mode;

    public Integer count = 0;//Presence uses count as well
    public List<String> strings = new ArrayList<>();//If we find the flag multiple times


    public LexerArgument(Mode mode, String... ids){
        if(ids.length == 0 && mode != Mode.SINGLE_PARAMETER && mode != Mode.MULTI_PARAMETER){
            //Invalid configuration
            //In this state the Argument is positional
            //The only mode that positional argument can support is PARAMETER mode
            //TODO: Throw a proper error
            throw new RuntimeException();
        }

        this.mode = mode;

        for(var id : ids){
            Matcher matcher = idPattern.matcher(id);
            if(!matcher.matches()){
                //Invalid id was provided
                //TODO: Throw a proper error
                throw new RuntimeException();
            }
            //The id is valid, add it to the list of ids to look for
            if(singlePattern.matcher(id).matches()){
                //We have a single character flag
                //It needs to match the provided id by exact match, and the last character must occur 1 or more times
                this.flags.add(Pattern.compile(Pattern.compile(id, Pattern.LITERAL).pattern() + "+"));
            }else{
                //We have a multi character flag
                //These are found by exact match
                this.flags.add(Pattern.compile(id, Pattern.LITERAL));
            }

        }
    }

    public List<String> extract(List<String> cliArguments){
        ArrayList<String> toReturn = new ArrayList<>();
        strings.clear();
        count = 0;

        boolean wasPreviousAMatch = false;
        for(String arg : cliArguments){
            if(!flags.isEmpty() && flags.stream().noneMatch(pattern -> pattern.matcher(arg).matches())){
                //The current arg is not an id for this argument
                if(wasPreviousAMatch){
                    wasPreviousAMatch = false;
                    //In this case, what we have is a value for this argument. So add it to the list of strings
                    switch(mode){
                        case SINGLE_PARAMETER -> {
                            if(strings.isEmpty()){
                                strings.add(arg);
                            }else{
                                //Too many values have been given to this argument
                                //TODO: Throw a proper error
                                throw new RuntimeException();
                            }
                        }
                        case MULTI_PARAMETER -> {
                            strings.add(arg);
                        }
                        default -> {
                            //Even though the previous argument was a flag, this LexerArgument does not use what comes after
                            toReturn.add(arg);
                        }
                    }
                }else{
                    //This arg is completely unrelated to this LexerArgument
                    toReturn.add(arg);
                }

            }else {
                //If we get here, then we have found a flag for this argument
                wasPreviousAMatch = true;

                //If no flags are specified then we have a positional argument
                if(flags.isEmpty()){
                    count++;//Increment that we have seen the argument
                    //Since it is positional, we add the current string to the list of strings
                    switch(mode){
                        case SINGLE_PARAMETER -> {
                            if(strings.isEmpty()){
                                strings.add(arg);
                            }else{
                                //Too many values have been given to this argument
                                //TODO: Throw a proper error
                                throw new RuntimeException();
                            }
                        }
                        case MULTI_PARAMETER -> {
                            strings.add(arg);
                        }
                        default -> {
                            //Even though the previous argument was a flag, this LexerArgument does not use what comes after
                            toReturn.add(arg);
                        }
                    }
                }else{
                    //This counts the number of times we see the flag
                    //A flag is guaranteed to have at least 2 characters
                    //If the second character is not a '-' then we have a single letter flag
                    if(arg.charAt(1) != '-'){
                        count += arg.length() - 1;//Subtract off the '-' from the arg, to get the number of times the single letter was repeated
                    }else{
                        count++;//Increment that we have seen the flag again
                    }
                }


            }
        }

        return toReturn;
    }


}
