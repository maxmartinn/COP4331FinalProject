package oop.project.library.lexer;

import org.checkerframework.checker.units.qual.A;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    public static Pattern singleArgument = Pattern.compile("\\S+");//Pattern.compile("\"[^\"]*\"|\\S+");//Commented out because quoted strings are added yet
    public static Pattern literal = Pattern.compile("^-?[^-\\s]\\S*$");
    public static Pattern flag = Pattern.compile("^--[^-\\s]\\S*$");

    public List<String> literals = new ArrayList<>();

    // The 'flags' map stores a key-value pair where each key is a string and each value is a list of literal strings.
    // Currently, only the first element in each list is accessed when retrieving values.
    public HashMap<String, List<String>> flags = new HashMap<>();

    // parses args into its correct token pattern
    public List<String> parse(String args){
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
                    flags.get(currentFlag).add(entry);
                }else{
                    literals.add(entry);
                }

                isFlag = false;
            }else if(flag.matcher(entry).matches()){
                flags.put(entry, new ArrayList<>());
                currentFlag = entry;
                isFlag = true;
            }
        }


        return argList;
    }
}
