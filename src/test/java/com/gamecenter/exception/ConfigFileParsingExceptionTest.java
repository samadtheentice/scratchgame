package com.gamecenter.exception;

import com.gamecenter.constants.ScratchGameConstants;
import com.gamecenter.util.ScratchGameUtil;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConfigFileParsingExceptionTest {
    @Test
    void configFileParsingExceptionTest() {
        Exception exception = assertThrows(ConfigFileParsingException.class, () -> ScratchGameUtil.getScratchGameConfigData("config.json"));

        String expectedMessage = ScratchGameConstants.CONFIG_FILE_PARSING_ERROR;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
