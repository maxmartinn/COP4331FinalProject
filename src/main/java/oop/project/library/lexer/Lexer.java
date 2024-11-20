package oop.project.library.lexer;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Lexer {
    private final static Pattern singleArgument = Pattern.compile("\\S+");//Pattern.compile("\"[^\"]*\"|\\S+");//Commented out because quoted strings are added yet
    private final static Pattern literal = Pattern.compile("^-?[^-\\s]\\S*$");
    private final static Pattern flag = Pattern.compile("^--[^-\\s]\\S*$");

    //A list of the literals that are found in the order they are found
    private final List<String> literals = new ArrayList<>();

    //A list of the flags that are found alongside their associated values
    private final Map<String, String> flags = new HashMap<>();

    // parses args into its correct token pattern
    public void parse(String args){
        List<String> argList = new ArrayList<>();

        //Clear the stored data before parsing
        literals.clear();
        flags.clear();

        // tokenizes based on pattern defined by regex
        Matcher matcher = singleArgument.matcher(args);
        while (matcher.find()) {
            argList.add(matcher.group()); // Add each match to the list
        }

        //This goes through each chunk of the input and places them in the correct data structures
        String currentFlag = "";//This is used to determine which flag to pair a value with
        boolean isFlag = false;//This is used to determine if the current chunk is the value for a flag
        for(String entry : argList){
            //Use two regexes to determine if we have either a literal or a flag
            if(literal.matcher(entry).matches()){
                //If we have a literal, then we need to know if it is a positional argument or the value for a flag
                if(isFlag){
                    flags.put(currentFlag, entry);
                    isFlag = false;
                }else{
                    literals.add(entry);
                }
            } else if (flag.matcher(entry).matches()){
                //If we have a flag, we need to check if we have already seen the flag
                if(flags.containsKey(entry)){
                    throw new DuplicateFlagException("The following flag was used more than once: " + entry);
                }

                //We also need to set these variables so that the next literal we see is interpreted as a flag value
                currentFlag = entry;
                isFlag = true;
            }
        }

        if(isFlag){
            //If we get here then the last thing in the argument list was a flag and there was no accompanying value
            throw new UnpairedFlagException("No value was provided for this flag: " + currentFlag);
        }
    }


    //Getters
    public List<String> getLiterals() { return this.literals; }
    public Map<String, String> getFlags(){ return flags; }

}
