package com.gamecenter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gamecenter.constants.ScratchGameConstants;
import com.gamecenter.model.*;
import com.gamecenter.objectmapper.ObjectMapperBuilder;
import com.gamecenter.util.ScratchGameUtil;
import org.cyberspeed.model.*;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Game board implementation
 */
public class ScratchGameBoard implements GameBoard{

    private final GameConfigData gameConfigData;

    public ScratchGameBoard(final GameConfigData gameConfigData){
        this.gameConfigData = gameConfigData;
    }

    /**
     * Load the matrix with standard symbols
     * @return matrix the input matrix
     */
    public String[][] loadStandardSymbolsInBoard(){
        final String[][] matrix = new String[gameConfigData.getRows()][gameConfigData.getColumns()];
        //first populate the standard symbols in the matrix
        Optional.ofNullable(gameConfigData.getProbabilities())
                .map(Probability::getStandardSymbols)
                .ifPresent(symbolLst-> {
                    symbolLst.parallelStream().forEach(standardSymbol->{
                    matrix[standardSymbol.getRow()][standardSymbol.getColumn()]= ScratchGameUtil.generateRandomSymbol(standardSymbol.getSymbols());
                    });
        });
        return matrix;
    }

    /**
     * Load the matrix with bonus symbols
     * @param matrix the input matrix
     * @return bonus symbol
     */
    public String loadBonusSymbolsInBoard(final String[][] matrix){

        //populate the bonus symbol
        final String bonusSymbol = ScratchGameUtil.generateRandomSymbol(gameConfigData.getProbabilities().getBonusSymbols().getSymbols());

        //replace bonus symbol to any random cell in the matrix
        matrix[new Random().nextInt((gameConfigData.getRows()))][new Random().nextInt((gameConfigData.getColumns()))]=bonusSymbol;

        return bonusSymbol;
    }

    /**
     * Decide the winning combination
     * @param matrix decide the winning combinations for this matrix
     * @return winning combinations for the symbols
     */
    public Map<String,List<String>> decideWinningCombination(final String[][] matrix){
        final Map<String, WinCombination> winCombMap = this.gameConfigData.getWinCombinations();

        final Function<WinCombination, String> winData = (winCombData) -> {
            return Optional.ofNullable(winCombData.getCoveredAreas()).orElse(new ArrayList<>(new ArrayList<>())).stream()
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
     * @param horizontalCells horizontal cells to be checked for repeated symbols
     * @param verticalCells vertical cells to be checked for repeated symbols
     * @param diagonalLeftToRightCells diagonal left to right cells to be checked for repeated symbols
     * @param diagonalRightToLeftCells diagonal right to left cells to be checked for repeated symbols
     * @return winning combinations for the symbols
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
                appliedWinningCombination.put(k, new ArrayList<>(Collections.singletonList(ScratchGameConstants.SAME_SYMBOL + v + ScratchGameConstants.TIMES)));
            }
        });

        ScratchGameUtil.addSymbolToList(horizontalSymbolMatchList,appliedWinningCombination, ScratchGameConstants.SAME_SYMBL_HORIZONTAL);
        ScratchGameUtil.addSymbolToList(verticalSymbolMatchList,appliedWinningCombination, ScratchGameConstants.SAME_SYMBL_VERTICAL);
        ScratchGameUtil.addSymbolToList(diagonalLeftToRightSymbolMatchList,appliedWinningCombination, ScratchGameConstants.SAME_SYMBL_LTR_DIAGONAL);
        ScratchGameUtil.addSymbolToList(diagonalRightToLeftSymbolMatchList,appliedWinningCombination, ScratchGameConstants.SAME_SYMBL_RTL_DIAGONAL);

        return appliedWinningCombination;
    }

    /**
     * Calculate the Rewards for the applied winning combination
     * @param appliedWinningCombination selected winning combination
     * @param bonusSymbol selected bonus
     * @param betAmount selected bet amount
     * @return total reward
     */
    public double calculateReward(final Map<String, List<String>> appliedWinningCombination, final String bonusSymbol, final double betAmount){
        double totalReward=0;

        for(final String key : appliedWinningCombination.keySet()){
            final List<String> symbolWinComb = appliedWinningCombination.get(key);
            double reward = 0;
            //apply reward for symbol
            final Symbol symbol = this.gameConfigData.getSymbols().get(key);
            if(null != symbol) {
                double symbolReward = symbol.getRewardMultiplier();
                reward = betAmount * symbolReward;
            }
            for(final String winComb : symbolWinComb){
                //apply reward for winning combination
                reward = reward * this.gameConfigData.getWinCombinations().get(winComb).getRewardMultiplier();
            }
            totalReward = totalReward + reward;
        }

        //apply bonus if rewards more than 0
        if(totalReward > 0){
            final Symbol symbol = this.gameConfigData.getSymbols().get(bonusSymbol);
            if(null != symbol) {
                if (ScratchGameConstants.MULTIPLY_REWARD.equalsIgnoreCase(symbol.getImpact())) {
                    totalReward = totalReward * symbol.getRewardMultiplier();
                } else if (ScratchGameConstants.EXTRA_BONUS.equalsIgnoreCase(symbol.getImpact())) {
                    totalReward = totalReward + symbol.getExtra();
                }
            }
        }
        return totalReward;
    }

    /**
     * Play Game
     * @param betAmount betting amount
     */
    public void playGame(final double betAmount){
        final String[][] matrix = loadStandardSymbolsInBoard();
        final String bonusSymbol= loadBonusSymbolsInBoard(matrix);
        final Map<String, List<String>> appliedWinningCombination = decideWinningCombination(matrix);
        final double rewards = calculateReward(appliedWinningCombination, bonusSymbol, betAmount);

        final ScratchGameResult scratchGameResult = new ScratchGameResult(matrix,appliedWinningCombination, bonusSymbol, rewards);

        //@TBR
        final ObjectMapper ob = ObjectMapperBuilder.getObjectMapper();
        try {
            System.out.println(ob.writeValueAsString(scratchGameResult));
        }catch(IOException e){
            System.out.println(ScratchGameConstants.RESULT_PARSING_ERR_MSG);
        }
    }

}
