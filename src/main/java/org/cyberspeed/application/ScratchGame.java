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

        final short rows = gameConfigData.getRows();
        final short columns =  gameConfigData.getColumns();

        /*System.out.println(gameConfigData.getProbabilities().stream()
                .filter(probalities -> ScratchGameConstants.STANDARD_SYMBOL.equalsIgnoreCase(probalities.getType())).findFirst().get().getType());

        gameConfigData.getProbabilities().stream()
                .filter(probalities -> ScratchGameConstants.STANDARD_SYMBOL.equalsIgnoreCase(probalities.getType())).findFirst().get().getProbablityData().stream().forEach(pd->{
                    System.out.println("pd..."+pd.getSymbols());
                });

        gameConfigData.getProbabilities().stream()
                .filter(probalities -> ScratchGameConstants.STANDARD_SYMBOL.equalsIgnoreCase(probalities.getType()))
                .findFirst()
                .map(probalitiy -> {
                    System.out.println("probalitiy..."+probalitiy);
                    return probalitiy;
                });*/

        //standard probabilities
        gameConfigData.getProbabilities().stream()
                .filter(probalities -> ScratchGameConstants.STANDARD_SYMBOL.equalsIgnoreCase(probalities.getType()))
                .findFirst()
                .map(probalitiy -> {
                    System.out.println("non parallel start********");
                    long start = System.currentTimeMillis();
                    probalitiy.getProbablityData().stream().forEach(probalitiyData -> {
                        System.out.println("selected symbol....("+probalitiyData.getRow() + ","+probalitiyData.getColumn()+") selected symbol.." +getneareRandomSymbol(probalitiyData));
                    });
                    long end = System.currentTimeMillis();
                    System.out.println("Non Parallel Elapsed Time:::::::::::::::::::: "+ (end-start));

                    System.out.println("parallel start********");
                    long start1 = System.currentTimeMillis();
                    probalitiy.getProbablityData().parallelStream().forEach(probalitiyData -> {
                        System.out.println("selected symbol....("+probalitiyData.getRow() + ","+probalitiyData.getColumn()+") selected symbol.." +getneareRandomSymbol(probalitiyData));
                    });
                    long end1 = System.currentTimeMillis();
                    System.out.println("Parallel Elapsed Time::::::::::::::::::::: "+ (end1-start1));

                    return null;
                });


    }

    //Generate random symbol based on the probability percentage
    public static String getneareRandomSymbol(final ProbabilityData probalitiyData){
        int totalValue = probalitiyData.getSymbols().stream().mapToInt(symbol -> symbol.getValue()).sum();
        final int randomNumber = new Random().nextInt(100);
        float cumulativeProbability=0.0f;
        String selectedSymbol=null;
        for (ProbabilitySymbol probSymbol : probalitiyData.getSymbols()) {
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