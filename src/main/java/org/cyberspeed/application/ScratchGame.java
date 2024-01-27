package org.cyberspeed.application;

import org.cyberspeed.constants.ScratchGameConstants;
import org.cyberspeed.exception.ConfigFileParsingException;
import org.cyberspeed.ScratchGameConfigParser;
import org.cyberspeed.model.GameConfigData;
import org.cyberspeed.model.Probability;
import org.cyberspeed.model.ProbabilityData;
import org.cyberspeed.model.ProbabilitySymbol;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

public class ScratchGame {

    public static void main(String[] args) {

        try {
            GameConfigData gameConfigData = ScratchGameConfigParser.getScratchGameConfigData("C:\\scratchgame\\src\\main\\resources\\configupdated.json");
            loadGameBoardWithStandardSymbol(gameConfigData);
        } catch (ConfigFileParsingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadGameBoardWithStandardSymbol(final GameConfigData gameConfigData){
        generateMatrixWithStandardAndBonusSymbolMerged(gameConfigData);
    }

    public static void populateBoardWithRandomSymbol(final GameConfigData gameConfigData, final String symbolType, final String[][] board){
        gameConfigData.getProbabilities().stream()
                .filter(probalities -> symbolType.equalsIgnoreCase(probalities.getType()))
                .findFirst()
                .map(probalitiy -> {
                    probalitiy.getProbablityData().parallelStream().forEach(probalitiyData -> {
                        board[probalitiyData.getRow()][probalitiyData.getColumn()]=getRandomSymbol(probalitiyData);
                    });
                    return null;
                });
    }

    public static String[][] generateMatrixWithStandardAndBonusSymbolMerged(final GameConfigData gameConfigData){

        final String[][] standardSymbols = new String[gameConfigData.getRows()][gameConfigData.getColumns()];
        final String[][] bonusSymbols = new String[1][1];

        //standard probabilities
        populateBoardWithRandomSymbol(gameConfigData, ScratchGameConstants.STANDARD_SYMBOL, standardSymbols);
        //bonus probabilities
        populateBoardWithRandomSymbol(gameConfigData, ScratchGameConstants.BONUS_SYMBOL, bonusSymbols);

        //replace a standard symbol with a bonus symbol
        standardSymbols[new Random().nextInt((gameConfigData.getRows()))][new Random().nextInt((gameConfigData.getColumns()))]=bonusSymbols[0][0];

        /*IntStream.range(0, gameConfigData.getRows()).forEach(i -> {
            IntStream.range(0, gameConfigData.getColumns()).forEach( j -> {
                System.out.println("standard symbol....("+i + ","+j+") selected symbol.." +standardSymbols[i][j]);
            });
        });*/
        return standardSymbols;
    }

    //Generate random symbol based on the probability percentage
    public static String getRandomSymbol(final ProbabilityData probalitiyData){
        final int totalValue = probalitiyData.getSymbols().stream().mapToInt(ProbabilitySymbol::getValue).sum();
        final int randomNumber = new Random().nextInt(100);
        float cumulativeProbability=0.0f;
        String selectedSymbol=null;
        for (final ProbabilitySymbol probSymbol : probalitiyData.getSymbols()) {
            cumulativeProbability += ((float)probSymbol.getValue()/totalValue)*100;
            System.out.println("cumulativeProbability.."+cumulativeProbability);
            if (randomNumber <= cumulativeProbability) {
                selectedSymbol = probSymbol.getName();
                break;
            }
        }
        return selectedSymbol;
    }
}