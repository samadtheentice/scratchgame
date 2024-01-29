package org.cyberspeed.validator;

import org.cyberspeed.constants.ScratchGameConstants;
import org.cyberspeed.exception.ScratchGameValidationException;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScratchGameValidatorTest {
    @Test
    void programArgValidationExceptionTest() {
        String[] args =  Stream.of("--config", "config", "--betting-amount","100").toArray(String[]::new);
        Exception exception = assertThrows(ScratchGameValidationException.class, () -> ScratchGameValidator.validateProgramArgument(args));

        String expectedMessage = ScratchGameConstants.PROGRAM_ARG_ERR_MSG;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void programArgValidationTest() throws ScratchGameValidationException{
        String[] args =  Stream.of("--config", "config.json", "--betting-amount","100").toArray(String[]::new);
        ScratchGameValidator.validateProgramArgument(args);
        assertTrue(true);
    }
}
