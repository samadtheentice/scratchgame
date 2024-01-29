package org.cyberspeed.exception;

import org.cyberspeed.constants.ScratchGameConstants;

/**
 * Exception thrown on improper use of program arguments
 */
public class ScratchGameValidationException extends Exception{
    public ScratchGameValidationException() {
        super(ScratchGameConstants.PROGRAM_ARG_ERR_MSG);
    }
}
