package org.cyberspeed.application;

import org.cyberspeed.constants.ScratchGameConstants;
import org.cyberspeed.exception.ConfigFileParsingException;
import org.cyberspeed.ScratchGameConfigParser;
import org.cyberspeed.model.*;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.cyberspeed.service.ScratchGameBoard;
import org.cyberspeed.service.GameBoard;

public class ScratchGame {

    public static void main(String[] args) {

        try {
            GameConfigData gameConfigData = ScratchGameConfigParser.getScratchGameConfigData("C:\\scratchgame\\src\\main\\resources\\configupdated.json");
            //playGame(gameConfigData);

            GameBoard gb = new ScratchGameBoard(gameConfigData);
            gb.playGame();

        } catch (ConfigFileParsingException e) {
            throw new RuntimeException(e);
        }
    }

    /*public static void playGame(final GameConfigData gameConfigData){
        final String[][] matrix = generateMatrixWithStandardAndBonusSymbol(gameConfigData);

        decideWinningCombination(gameConfigData, matrix);
    }*/


    /*public static void decideWinningCombination(final GameConfigData gameConfigData, final String[][] matrix){

        //gameConfigData.getWinCombinations().stream().filter(sameSymbolwincom -> "same_symbols".equals(sameSymbolwincom.getGroup())).findFirst();
        String horizontalCells = gameConfigData.getWinCombinations().stream().filter(sameSymbolwincom -> "horizontally_linear_symbols".equals(sameSymbolwincom.getGroup())).findFirst().get().getCoveredAreas().stream().flatMap(l->l.stream()).collect(Collectors.joining("::"));
        String diagonalLeftToRightCells = gameConfigData.getWinCombinations().stream().filter(sameSymbolwincom -> "ltr_diagonally_linear_symbols".equals(sameSymbolwincom.getGroup())).findFirst().get().getCoveredAreas().stream().flatMap(l->l.stream()).collect(Collectors.joining("::"));
        String diagonalRightToLeftCells = gameConfigData.getWinCombinations().stream().filter(sameSymbolwincom -> "rtl_diagonally_linear_symbols".equals(sameSymbolwincom.getGroup())).findFirst().get().getCoveredAreas().stream().flatMap(l->l.stream()).collect(Collectors.joining("::"));
        String verticalCells = gameConfigData.getWinCombinations().stream().filter(sameSymbolwincom -> "vertically_linear_symbols".equals(sameSymbolwincom.getGroup())).findFirst().get().getCoveredAreas().stream().flatMap(l->l.stream()).collect(Collectors.joining("::"));


        System.out.println("horizontalCells..."+horizontalCells);
        System.out.println("diagonalLeftToRightCells..."+diagonalLeftToRightCells);
        System.out.println("diagonalRightToLeftCells..."+diagonalRightToLeftCells);
        verticalCells = new StringBuilder(verticalCells).reverse().toString();
        System.out.println("verticalCells..."+verticalCells);
        System.out.println("reversed verticalCells..."+verticalCells);

        countSameSymbols(matrix, horizontalCells, diagonalLeftToRightCells, diagonalRightToLeftCells, verticalCells);

    }*/

    /*public static  Map<String,Integer> countSameSymbols(final String[][] matrix, final String horizontalCells, final String diagonalLeftToRightCells,final String diagonalRightToLeftCells, final String verticalCells){
        final Map<String,List<String>> appliedWinningCombination = new HashMap<>();
        //iterate the matrix to find the count of repeated symbol
        Map<String,Integer> sameSymbolCounter = new HashMap<>();
        List<String> horizontalSymbolMatchList = new ArrayList<>();
        List<String> verticalSymbolMatchList = new ArrayList<>();
        List<String> diagonalLeftToRightSymbolMatchList = new ArrayList<>();
        List<String> diagonalRightToLeftSymbolMatchList = new ArrayList<>();

        ///diagonal left to right match start
        boolean diagonalLeftToRightSymbolsMatch=false;
        String diagonalLeftToRightSymbol=null;
        ///diagonal left to right match end

        ///diagonal right to left match start
        boolean diagonalRightToLeftSymbolsMatch=false;
        String diagonalRightToLeftSymbol=null;
        ///diagonal right to left match end

        for(int i=0;i < matrix.length;i++){
            ///horizontal match start
            boolean horizontalSymbolsMatch=false;
            String horizontalSymbol=null;
            ///horizontal match end

            ///vertical match start
            boolean verticalSymbolsMatch=false;
            String verticalSymbol=null;
            ///vertical match end

            for(int j=0;j < matrix[i].length;j++){
                sameSymbolCounter.put(matrix[i][j], sameSymbolCounter.getOrDefault(matrix[i][j], 0)+1);

                System.out.println("reverse...("+j+","+i+")"+matrix[j][i]);
                ///horizontal match start
                //horizontalSymbolsMatch = compute(matrix, horizontalCells,horizontalSymbol,i,j);
                if(horizontalCells.contains(i + ":" + j) ){

                    if(null == horizontalSymbol){
                        horizontalSymbol=matrix[i][j];
                        horizontalSymbolsMatch=true;
                    }else if(!matrix[i][j].equalsIgnoreCase(horizontalSymbol)){
                        horizontalSymbolsMatch=false;
                    }
                }
                ///horizontal match end

                ///vertical match start
                //verticalSymbolsMatch = compute(matrix, verticalCells,verticalSymbol,j,i);
                if(verticalCells.contains(j + ":" + i) ){

                    if(null == verticalSymbol){
                        verticalSymbol=matrix[j][i];
                        verticalSymbolsMatch=true;
                    }else if(!matrix[j][i].equalsIgnoreCase(verticalSymbol)){
                        verticalSymbolsMatch=false;
                    }
                }
                ///vertical match end

                ///diagonal left to right match start
                //diagonalLeftToRightSymbolsMatch = compute(matrix, diagonalLeftToRightCells,diagonalLeftToRightSymbol,i,j);
                if(diagonalLeftToRightCells.contains(i + ":" + j) ){
                    if(null == diagonalLeftToRightSymbol){
                        diagonalLeftToRightSymbol=matrix[i][j];
                        diagonalLeftToRightSymbolsMatch=true;
                    }else if(!matrix[i][j].equalsIgnoreCase(diagonalLeftToRightSymbol)){
                        diagonalLeftToRightSymbolsMatch=false;
                    }
                }
                ///diagonal left to right match end


                ///diagonal right to left match start
                //diagonalRightToLeftSymbolsMatch = compute(matrix, diagonalRightToLeftCells,diagonalRightToLeftSymbol,i,j);
                if(diagonalRightToLeftCells.contains(i + ":" + j) ){
                    if(null == diagonalRightToLeftSymbol){
                        diagonalRightToLeftSymbol=matrix[i][j];
                        diagonalRightToLeftSymbolsMatch=true;
                    }else if(!matrix[i][j].equalsIgnoreCase(diagonalRightToLeftSymbol)){
                        diagonalRightToLeftSymbolsMatch=false;
                    }
                }
                ///diagonal right to left match end
            }

            ///horizontal match start
            if(horizontalSymbolsMatch){
                horizontalSymbolMatchList.add(horizontalSymbol);
            }
            ///horizontal match end

            ///vertical match start
            if(verticalSymbolsMatch){
                verticalSymbolMatchList.add(verticalSymbol);
            }
            ///vertical match end
        }

        ///diagonal left to right match start
        if(diagonalLeftToRightSymbolsMatch){
            diagonalLeftToRightSymbolMatchList.add(diagonalLeftToRightSymbol);
        }
        ///diagonal left to right match end

        ///diagonal right to left match start
        if(diagonalRightToLeftSymbolsMatch){
            diagonalRightToLeftSymbolMatchList.add(diagonalRightToLeftSymbol);
        }
        ///diagonal right to left match end

        horizontalSymbolMatchList.stream().forEach(symb->{
                    System.out.println("horizontal match..."+symb);
        });

        verticalSymbolMatchList.stream().forEach(symb->{
            System.out.println("vertical match..."+symb);
        });

        diagonalLeftToRightSymbolMatchList.stream().forEach(symb-> {
            System.out.println("diagonal left to right match..." + symb);
        });

        diagonalRightToLeftSymbolMatchList.stream().forEach(symb-> {
            System.out.println("diagonal right to left match..." + symb);
        });

        sameSymbolCounter.forEach((k,v)->{
            appliedWinningCombination.put(k,new ArrayList<String>(Arrays.asList("same_symbol_"+v+"_times")));
        });
        horizontalSymbolMatchList.forEach(k->{
            appliedWinningCombination.computeIfAbsent(k, v -> new ArrayList<>()).add(ScratchGameConstants.SAME_SYMBL_HORIZONTAL);
        });

        verticalSymbolMatchList.forEach(k->{
            appliedWinningCombination.computeIfAbsent(k, v -> new ArrayList<>()).add(ScratchGameConstants.SAME_SYMBL_VERTICAL);
        });

        diagonalLeftToRightSymbolMatchList.forEach(k->{
            appliedWinningCombination.computeIfAbsent(k, v -> new ArrayList<>()).add(ScratchGameConstants.SAME_SYMBL_LTR_DIAGONAL);
        });

        diagonalRightToLeftSymbolMatchList.forEach(k->{
            appliedWinningCombination.computeIfAbsent(k, v -> new ArrayList<>()).add(ScratchGameConstants.SAME_SYMBL_RTL_DIAGONAL);
        });

        appliedWinningCombination.keySet().forEach(k->{
            System.out.println(k+"..."+appliedWinningCombination.get(k));
        });
        return sameSymbolCounter;
    }*/


    //generate matrix with standard and bonus symbol
    /*public static String[][] generateMatrixWithStandardAndBonusSymbol(final GameConfigData gameConfigData){

        final String[][] matrix = new String[gameConfigData.getRows()][gameConfigData.getColumns()];

        //first populate the standard symbols in the matrix
        gameConfigData.getProbabilities().getStandardSymbols().parallelStream().forEach(standardSymbol->{
            matrix[standardSymbol.getRow()][standardSymbol.getColumn()]=getRandomSymbol(standardSymbol.getSymbols());
        });

        //populate the bonus symbol
        final String bonusSymbol = getRandomSymbol(gameConfigData.getProbabilities().getBonusSymbols().getSymbols());

        //replace bonus symbol to any random cell in the matrix
        matrix[new Random().nextInt((gameConfigData.getRows()))][new Random().nextInt((gameConfigData.getColumns()))]=bonusSymbol;

        //@TBR
        IntStream.range(0, gameConfigData.getRows()).forEach(i -> {
            IntStream.range(0, gameConfigData.getColumns()).forEach( j -> {
                System.out.println("standard symbol....("+i + ","+j+") selected symbol.." +matrix[i][j]);
            });
        });
        return matrix;
    }*/


    //Generate random symbol based on the probability percentage
    /* static String getRandomSymbol(final Map<String,Integer> symbols){
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
    }*/
}