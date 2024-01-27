package org.cyberspeed.exception;

public class ConfigFileParsingException extends Exception{

    public ConfigFileParsingException() {
        super("Error parsing the config file");
    }

    public ConfigFileParsingException(String message) {
        super(message);
    }
    public ConfigFileParsingException(final String errorMessage, final Throwable error){
        super(errorMessage, error);
    }
}
