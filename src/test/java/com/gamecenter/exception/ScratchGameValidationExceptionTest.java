package com.gamecenter.exception;

import com.gamecenter.constants.ScratchGameConstants;
import com.gamecenter.validator.ScratchGameValidator;
import org.junit.jupiter.api.Test;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScratchGameValidationExceptionTest {
    @Test
    void validationExceptionTest() {
        String[] args =  Stream.of("--config", "config", "--betting-amount","100").toArray(String[]::new);
        Exception exception = assertThrows(ScratchGameValidationException.class, () -> ScratchGameValidator.validateProgramArgument(args));

        String expectedMessage = ScratchGameConstants.PROGRAM_ARG_ERR_MSG;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
