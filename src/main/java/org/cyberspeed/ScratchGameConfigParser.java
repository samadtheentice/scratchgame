package org.cyberspeed;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.cyberspeed.constants.ScratchGameConstants;
import org.cyberspeed.model.GameConfigData;
import org.cyberspeed.exception.ConfigFileParsingException;
import org.cyberspeed.objectmapper.ObjectMapperBuilder;

import java.io.FileInputStream;
import java.io.IOException;

public class ScratchGameConfigParser {

    public static GameConfigData getScratchGameConfigData(final String configFile) throws ConfigFileParsingException {

        try(final FileInputStream inputStream = new FileInputStream(configFile)) {
            final ObjectMapper mapper = ObjectMapperBuilder.getObjectMapper();
            final GameConfigData gameConfigData = mapper.readValue(inputStream, GameConfigData.class);
            return gameConfigData;
        }catch (final IOException exception) {
            throw new ConfigFileParsingException(ScratchGameConstants.CONFIG_FILE_PARSING_ERROR, exception);
        }
    }
}
