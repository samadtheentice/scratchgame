package com.gamecenter.exception;

import com.gamecenter.constants.ScratchGameConstants;

/**
 * Exception thrown on improper use of program arguments
 */
public class ScratchGameValidationException extends Exception{
    public ScratchGameValidationException() {
        super(ScratchGameConstants.PROGRAM_ARG_ERR_MSG);
    }
}
