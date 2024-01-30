package org.cyberspeed.util;

import org.cyberspeed.constants.ScratchGameConstants;
import org.cyberspeed.exception.ConfigFileParsingException;
import org.cyberspeed.model.GameConfigData;
import org.cyberspeed.model.Probability;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScratchGameUtilTest {

    @Test
    void generateRandomSymbolTest() throws IOException {
        GameConfigData gameConfigData = TestDataBuilder.buildConfigData();

        Optional.ofNullable(gameConfigData.getProbabilities())
                .map(Probability::getStandardSymbols)
                .ifPresent(symbolLst -> {
                    symbolLst.parallelStream().forEach(standardSymbol -> {
                        Assertions.assertNotNull(ScratchGameUtil.generateRandomSymbol(standardSymbol.getSymbols()));
                    });
                });
    }

    @Test
    void getFileNameTest() {

        String[] args = {"--config","config.json","--betting-amount","100.1"};
        Assertions.assertEquals(ScratchGameUtil.getFileName(args),"config.json");

        String[] args1 = {"--betting-amount","100.1","--config","config.json"};
        Assertions.assertEquals(ScratchGameUtil.getFileName(args1),"config.json");
    }

    @Test
    void getBettingAmount() {

        String[] args = {"--config","config.json","--betting-amount","100.1"};
        Assertions.assertEquals(ScratchGameUtil.getBettingAmount(args),"100.1");

        String[] args1 = {"--betting-amount","100.1","--config","config.json"};
        Assertions.assertEquals(ScratchGameUtil.getBettingAmount(args1),"100.1");
    }

    @Test
    void getScratchGameConfigDataExceptionTest() throws ConfigFileParsingException {
        Exception exception = assertThrows(ConfigFileParsingException.class, () -> ScratchGameUtil.getScratchGameConfigData("config.json"));
        String expectedMessage = ScratchGameConstants.CONFIG_FILE_PARSING_ERROR;
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

}
