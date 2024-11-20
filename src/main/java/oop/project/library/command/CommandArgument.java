package oop.project.library.command;

import oop.project.library.parser.Parser;

public class CommandArgument {
    private Parser<?> parser;//This must not be null
    private Object value;//This is allowed to be null

    CommandArgument(Parser<?> parser){
        this.parser = parser;
    }

    void parse(String input){
        value = parser.parse(input);
    }

    Object value(){
        return value;
    }
}
