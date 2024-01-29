package org.cyberspeed.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.cyberspeed.constants.ScratchGameConstants;
import org.cyberspeed.model.GameConfigData;
import org.cyberspeed.model.ScratchGameResult;
import org.cyberspeed.model.WinCombination;
import org.cyberspeed.objectmapper.ObjectMapperBuilder;
import org.cyberspeed.util.ScratchGameUtil;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.cyberspeed.model.Symbol;

public class ScratchGameBoard implements GameBoard{

    private GameConfigData gameConfigData;

    public ScratchGameBoard(final GameConfigData gameConfigData){
        this.gameConfigData = gameConfigData;
    }

    /**
     * Load the scratch game board
     */
    public void loadStandardSymbolsInBoard(final String[][] matrix){

        //first populate the standard symbols in the matrix
        gameConfigData.getProbabilities().getStandardSymbols().parallelStream().forEach(standardSymbol->{
            matrix[standardSymbol.getRow()][standardSymbol.getColumn()]= ScratchGameUtil.generateRandomSymbol(standardSymbol.getSymbols());
        });
    }

    /**
     * Load the scratch game board
     */
    public String loadBonusSymbolsInBoard(final String[][] matrix){

        //populate the bonus symbol
        final String bonusSymbol = ScratchGameUtil.generateRandomSymbol(gameConfigData.getProbabilities().getBonusSymbols().getSymbols());

        //replace bonus symbol to any random cell in the matrix
        matrix[new Random().nextInt((gameConfigData.getRows()))][new Random().nextInt((gameConfigData.getColumns()))]=bonusSymbol;

        return bonusSymbol;
    }

    /**
     *
     * @param matrix
     * @return
     */
    public Map<String,List<String>> decideWinningCombination(final String[][] matrix){
        final Map<String,WinCombination> winCombMap = this.gameConfigData.getWinCombinations();

        final Function<WinCombination, String> winData = (winCombData) -> {
            return winCombData.getCoveredAreas().stream()
            .flatMap(Collection::stream).collect(Collectors.joining("::"));
        };

        final String horizontalCells = winData.apply(winCombMap.get(ScratchGameConstants.SAME_SYMBL_HORIZONTAL));
        String verticalCells = winData.apply(winCombMap.get(ScratchGameConstants.SAME_SYMBL_VERTICAL));
        final String diagonalLeftToRightCells = winData.apply(winCombMap.get(ScratchGameConstants.SAME_SYMBL_LTR_DIAGONAL));
        final String diagonalRightToLeftCells = winData.apply(winCombMap.get(ScratchGameConstants.SAME_SYMBL_RTL_DIAGONAL));

        verticalCells = new StringBuilder(verticalCells).reverse().toString();

        return mapWinningCombination(matrix, horizontalCells, verticalCells, diagonalLeftToRightCells, diagonalRightToLeftCells);

    }

    /**
     * Iterate the board matrix to find the winning combinations that is applicable
     * @param horizontalCells horizontal
     * @param verticalCells
     * @param diagonalLeftToRightCells
     * @param diagonalRightToLeftCells
     * @return
     */
    private Map<String,List<String>> mapWinningCombination(final String[][] matrix, final String horizontalCells, final String verticalCells, final String diagonalLeftToRightCells, final String diagonalRightToLeftCells){

        final Map<String,List<String>> appliedWinningCombination = new HashMap<>();

        final Map<String,Integer> sameSymbolCounter = new HashMap<>();
        final List<String> horizontalSymbolMatchList = new ArrayList<>();
        final List<String> verticalSymbolMatchList = new ArrayList<>();
        final List<String> diagonalLeftToRightSymbolMatchList = new ArrayList<>();
        final List<String> diagonalRightToLeftSymbolMatchList = new ArrayList<>();

        boolean diagonalLeftToRightSymbolsMatch = false;
        String diagonalLeftToRightSymbol = null;
        boolean diagonalRightToLeftSymbolsMatch = false;
        String diagonalRightToLeftSymbol = null;

        for(int i=0; i < matrix.length; i++){
            boolean horizontalSymbolsMatch = false;
            String horizontalSymbol = null;
            boolean verticalSymbolsMatch = false;
            String verticalSymbol = null;

            for(int j=0; j < matrix[i].length; j++){
                //same symbol count
                sameSymbolCounter.put(matrix[i][j], sameSymbolCounter.getOrDefault(matrix[i][j], 0)+1);

                //match horizontal symbols
                if(horizontalCells.contains(i + ":" + j) ){
                    if(null == horizontalSymbol){
                        horizontalSymbol=matrix[i][j];
                        horizontalSymbolsMatch = true;
                    }else if(!matrix[i][j].equalsIgnoreCase(horizontalSymbol)){
                        horizontalSymbolsMatch = false;
                    }
                }

                //match vertical symbols
                if(verticalCells.contains(j + ":" + i) ){

                    if(null == verticalSymbol){
                        verticalSymbol=matrix[j][i];
                        verticalSymbolsMatch = true;
                    }else if(!matrix[j][i].equalsIgnoreCase(verticalSymbol)){
                        verticalSymbolsMatch = false;
                    }
                }

                //match diagonal LTR symbols
                if(diagonalLeftToRightCells.contains(i + ":" + j) ){
                    if(null == diagonalLeftToRightSymbol){
                        diagonalLeftToRightSymbol=matrix[i][j];
                        diagonalLeftToRightSymbolsMatch = true;
                    }else if(!matrix[i][j].equalsIgnoreCase(diagonalLeftToRightSymbol)){
                        diagonalLeftToRightSymbolsMatch = false;
                    }
                }

                //match diagonal RTL symbols
                if(diagonalRightToLeftCells.contains(i + ":" + j) ){
                    if(null == diagonalRightToLeftSymbol){
                        diagonalRightToLeftSymbol=matrix[i][j];
                        diagonalRightToLeftSymbolsMatch = true;
                    }else if(!matrix[i][j].equalsIgnoreCase(diagonalRightToLeftSymbol)){
                        diagonalRightToLeftSymbolsMatch = false;
                    }
                }
            }

            if(horizontalSymbolsMatch){
                horizontalSymbolMatchList.add(horizontalSymbol);
            }
            if(verticalSymbolsMatch){
                verticalSymbolMatchList.add(verticalSymbol);
            }
        }

        if(diagonalLeftToRightSymbolsMatch){
            diagonalLeftToRightSymbolMatchList.add(diagonalLeftToRightSymbol);
        }
        if(diagonalRightToLeftSymbolsMatch){
            diagonalRightToLeftSymbolMatchList.add(diagonalRightToLeftSymbol);
        }

        sameSymbolCounter.forEach((k,v)->{
            if(gameConfigData.getWinCombinations().containsKey(ScratchGameConstants.SAME_SYMBOL + v + ScratchGameConstants.TIMES)) {
                appliedWinningCombination.put(k, new ArrayList<String>(Arrays.asList(ScratchGameConstants.SAME_SYMBOL + v + ScratchGameConstants.TIMES)));
            }
        });

        ScratchGameUtil.addSymbolToList(horizontalSymbolMatchList,appliedWinningCombination, ScratchGameConstants.SAME_SYMBL_HORIZONTAL);
        ScratchGameUtil.addSymbolToList(verticalSymbolMatchList,appliedWinningCombination, ScratchGameConstants.SAME_SYMBL_VERTICAL);
        ScratchGameUtil.addSymbolToList(diagonalLeftToRightSymbolMatchList,appliedWinningCombination, ScratchGameConstants.SAME_SYMBL_LTR_DIAGONAL);
        ScratchGameUtil.addSymbolToList(diagonalRightToLeftSymbolMatchList,appliedWinningCombination, ScratchGameConstants.SAME_SYMBL_RTL_DIAGONAL);

        //@TBR
        appliedWinningCombination.keySet().forEach(k->{
            System.out.println(k+"..."+appliedWinningCombination.get(k));
        });
        return appliedWinningCombination;
    }

    public double calculateReward(final Map<String, List<String>> appliedWinningCombination, final String bonusSymbol, final double betAmount){
        double totalReward=0;

        for(final String key : appliedWinningCombination.keySet()){
            final List<String> symbolWinComb = appliedWinningCombination.get(key);
            double reward = 0;
            //apply reward for symbol
            double symbolReward = this.gameConfigData.getSymbols().get(key).getRewardMultiplier();
            reward = betAmount * symbolReward;
            for(final String winComb : symbolWinComb){
                //apply reward for winning combination
                reward = reward * this.gameConfigData.getWinCombinations().get(winComb).getRewardMultiplier();
            }
            totalReward = totalReward + reward;
        }

        //apply bonus if rewards more than 0
        if(totalReward > 0){
            final Symbol symbol = this.gameConfigData.getSymbols().get(bonusSymbol);
            System.out.println("bonus chk1.."+bonusSymbol);
            System.out.println("bonus chk2.."+symbol.getImpact());
            if(ScratchGameConstants.MULTIPLY_REWARD.equalsIgnoreCase(symbol.getImpact())){
                totalReward = totalReward * symbol.getRewardMultiplier();
                System.out.println("bonus chk3.."+totalReward);
            }else if(ScratchGameConstants.EXTRA_BONUS.equalsIgnoreCase(symbol.getImpact())){
                totalReward = totalReward + symbol.getExtra();
                System.out.println("bonus chk4.."+totalReward);
            }
        }
        return totalReward;
    }

    public void playGame(){
        final String[][] matrix = new String[gameConfigData.getRows()][gameConfigData.getColumns()];
        loadStandardSymbolsInBoard(matrix);
        String bonusSymbol= loadBonusSymbolsInBoard(matrix);
        Map<String, List<String>> appliedWinningCombination = decideWinningCombination(matrix);
        double rewards = calculateReward(appliedWinningCombination, bonusSymbol, 100f);

        ScratchGameResult scratchGameResult = new ScratchGameResult(matrix,appliedWinningCombination, bonusSymbol, rewards);

        //@TBR
        ObjectMapper ob =ObjectMapperBuilder.getObjectMapper();
        try {
            scratchGameResult.setAppliedWinningCombinations(appliedWinningCombination);
            System.out.println("output..."+ob.writeValueAsString(scratchGameResult));
        }catch(Exception e){

        }

        /*Map<String, List<String>> appliedWinningCombination1 = new HashMap<>();
        //appliedWinningCombination1.put("E",Arrays.asList("same_symbol_3_times","same_symbols_vertically","same_symbols_diagonally_right_to_left"));
        //appliedWinningCombination1.put("F",Arrays.asList("same_symbol_4_times","same_symbols_vertically"));
        appliedWinningCombination1.put("E",Arrays.asList("same_symbol_4_times","same_symbols_horizontally","same_symbols_horizontally","same_symbols_vertically"));

        calculateReward(appliedWinningCombination1,"+500",100f);*/
    }

}
