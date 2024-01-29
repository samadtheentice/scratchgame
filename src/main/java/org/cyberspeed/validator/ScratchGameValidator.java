package org.cyberspeed.validator;

import org.cyberspeed.constants.ScratchGameConstants;
import org.cyberspeed.exception.ScratchGameValidationException;

/**
 * Validation to check the program arguments
 */
public class ScratchGameValidator {
    public static void validateProgramArgument(final String[] args) throws ScratchGameValidationException {
        if(!(args.length == 4
                && (ScratchGameConstants.PRG_ARG_CONFIG.equalsIgnoreCase(args[0]) && args[1].endsWith(ScratchGameConstants.PRG_ARG_FILE_EXT)
                || ScratchGameConstants.PRG_ARG_CONFIG.equalsIgnoreCase(args[2]) && args[3].endsWith(ScratchGameConstants.PRG_ARG_FILE_EXT))
                && (ScratchGameConstants.PRG_ARG_BET_AMNT.equalsIgnoreCase(args[0]) && args[1].matches("[-+]?[0-9]*\\.?[0-9]+")
                || ScratchGameConstants.PRG_ARG_BET_AMNT.equalsIgnoreCase(args[2]) && args[3].matches("[-+]?[0-9]*\\.?[0-9]+"))
        )){
            throw new ScratchGameValidationException();
        }
    }
}
