package oop.project.library.lexer;

import org.checkerframework.checker.units.qual.A;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    private final static Pattern singleArgument = Pattern.compile("\\S+");//Pattern.compile("\"[^\"]*\"|\\S+");//Commented out because quoted strings are added yet
    private final static Pattern literal = Pattern.compile("^-?[^-\\s]\\S*$");
    private final static Pattern flag = Pattern.compile("^--[^-\\s]\\S*$");

    private final List<String> literals = new ArrayList<>();

    public List<String> getLiterals() {
        return this.literals;
    }

    // The 'flags' map stores a key-value pair where each key is a string and each value is a list of literal strings.
    // Currently, only the first element in each list is accessed when retrieving values.
    public Map<String, String> flags = new HashMap<>();

    // parses args into its correct token pattern
    public void parse(String args){
        List<String> argList = new ArrayList<>();


        // tokenizes based on pattern defined by regex
        Matcher matcher = singleArgument.matcher(args);
        while (matcher.find()) {
            argList.add(matcher.group()); // Add each match to the list
        }

        String currentFlag = "";
        boolean isFlag = false;
        for(String entry : argList){
            if(literal.matcher(entry).matches()){
                if(isFlag){
                    flags.put(currentFlag, entry);
                }else{
                    literals.add(entry);
                }

                isFlag = false;
            } else if (flag.matcher(entry).matches()){
                if(flags.containsKey(entry)){
                    throw new RuntimeException("A flag was used more than once.");
                }
                currentFlag = entry;
                isFlag = true;
            }
        }
    }
}
