package org.cyberspeed.exception;

/**
 * Exception thrown on parsing the config json file
 */
public class ConfigFileParsingException extends Exception{
    public ConfigFileParsingException(final String errorMessage, final Throwable error){
        super(errorMessage, error);
    }
}
