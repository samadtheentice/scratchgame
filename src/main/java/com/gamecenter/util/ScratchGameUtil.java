package com.gamecenter.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamecenter.constants.ScratchGameConstants;
import com.gamecenter.exception.ConfigFileParsingException;
import com.gamecenter.model.GameConfigData;
import com.gamecenter.objectmapper.ObjectMapperBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Utility methods
 */
public class ScratchGameUtil {

    /**
     * Generate random symbol based on the probability percentage
     * @param symbols symbols
     * @return random symbol
     */
    public static String generateRandomSymbol(final Map<String,Integer> symbols){
        final int totalValue = symbols.values().stream().mapToInt(Integer::intValue).sum();
        final int randomNumber = new Random().nextInt(100);
        float cumulativeProbability=0.0f;
        String selectedSymbol=null;

        for (String key : symbols.keySet()) {
            cumulativeProbability += ((float)symbols.get(key)/totalValue)*100;
            if (randomNumber <= cumulativeProbability) {
                selectedSymbol = key;
                break;
            }
        }
        return selectedSymbol;
    }

    /**
     * Add symbol to List
     * @param symbolMatchLst symbol list
     * @param appliedWinningCombination winning combination
     * @param group group
     */
    public static void addSymbolToList(final List<String> symbolMatchLst,final Map<String,List<String>> appliedWinningCombination, final String group){
        symbolMatchLst.forEach(k->{
            appliedWinningCombination.computeIfAbsent(k, v -> new ArrayList<>()).add(group);
        });
    }

    /**
     * Get file name from program arguments
     * @param args program argument
     * @return file name
     */
    public static String getFileName(String[] args){
        return ScratchGameConstants.PRG_ARG_CONFIG.equalsIgnoreCase(args[0]) ? args[1] : ScratchGameConstants.PRG_ARG_CONFIG.equalsIgnoreCase(args[2]) ? args[3] : null;
    }

    /**
     * Get betting amount from program arguments
     * @param args program argument
     * @return bet amount
     */
    public static String getBettingAmount(String[] args){
        return ScratchGameConstants.PRG_ARG_BET_AMNT.equalsIgnoreCase(args[0]) ? args[1] : ScratchGameConstants.PRG_ARG_BET_AMNT.equalsIgnoreCase(args[2]) ? args[3] : "0.0";
    }

    /**
     * Get the Game Config object from the json format
     * @param configFile config file
     * @return Game config object
     * @throws ConfigFileParsingException parsing exception
     */
    public static GameConfigData getScratchGameConfigData(final String configFile) throws ConfigFileParsingException {

        try(final FileInputStream inputStream = new FileInputStream(configFile)) {
            final ObjectMapper mapper = ObjectMapperBuilder.getObjectMapper();
            return mapper.readValue(inputStream, GameConfigData.class);
        }catch (final IOException exception) {
            throw new ConfigFileParsingException(ScratchGameConstants.CONFIG_FILE_PARSING_ERROR, exception);
        }
    }

}
