package org.cyberspeed.service;

import org.cyberspeed.constants.ScratchGameConstants;
import org.cyberspeed.model.GameConfigData;
import org.cyberspeed.model.WinCombination;
import org.cyberspeed.util.ScratchGameUtil;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ScratchGameBoard implements GameBoard{

    private GameConfigData gameConfigData;

    private String[][] matrix;

    public ScratchGameBoard(final GameConfigData gameConfigData){
        this.gameConfigData = gameConfigData;
        this.matrix = new String[gameConfigData.getRows()][gameConfigData.getColumns()];
    }


    /**
     * Load the scratch game board
     */
    public void loadGameBoard(){

        //first populate the standard symbols in the matrix
        gameConfigData.getProbabilities().getStandardSymbols().parallelStream().forEach(standardSymbol->{
            this.matrix[standardSymbol.getRow()][standardSymbol.getColumn()]= ScratchGameUtil.generateRandomSymbol(standardSymbol.getSymbols());
        });

        //populate the bonus symbol
        final String bonusSymbol = ScratchGameUtil.generateRandomSymbol(gameConfigData.getProbabilities().getBonusSymbols().getSymbols());

        //replace bonus symbol to any random cell in the matrix
        this.matrix[new Random().nextInt((gameConfigData.getRows()))][new Random().nextInt((gameConfigData.getColumns()))]=bonusSymbol;

        //@TBR
        /*IntStream.range(0, gameConfigData.getRows()).forEach(i -> {
            IntStream.range(0, gameConfigData.getColumns()).forEach( j -> {
                System.out.println("standard symbol....("+i + ","+j+") selected symbol.." +matrix[i][j]);
            });
        });*/
    }

    public void decideWinningCombination(){
        final List<WinCombination> winCombLst = this.gameConfigData.getWinCombinations();

        final BiFunction<List<WinCombination>, String, String> winData = (winCombData, winCombGroup) -> {
            return winCombData.stream()
            .filter(winComb -> winCombGroup.equals(winComb.getGroup()))
            .findFirst().get().getCoveredAreas().stream()
            .flatMap(Collection::stream).collect(Collectors.joining("::"));
        };

        String horizontalCells = winData.apply(winCombLst,ScratchGameConstants.WIN_COMB_HORIZONTAL_LINEAR_SYMBL);
        String verticalCells = winData.apply(winCombLst,ScratchGameConstants.WIN_COMB_VERTICAL_LINEAR_SYMBL);
        String diagonalLeftToRightCells = winData.apply(winCombLst,ScratchGameConstants.WIN_COMB_LTR_DIAGONAL_LINEAR_SYMBL);
        String diagonalRightToLeftCells = winData.apply(winCombLst,ScratchGameConstants.WIN_COMB_RTL_DIAGONAL_LINEAR_SYMBL);

        System.out.println("horizontalCells..."+horizontalCells);
        System.out.println("diagonalLeftToRightCells..."+diagonalLeftToRightCells);
        System.out.println("diagonalRightToLeftCells..."+diagonalRightToLeftCells);
        verticalCells = new StringBuilder(verticalCells).reverse().toString();
        System.out.println("verticalCells..."+verticalCells);
        System.out.println("reversed verticalCells..."+verticalCells);

        countSameSymbols(horizontalCells, verticalCells, diagonalLeftToRightCells, diagonalRightToLeftCells);

    }

    /**
     * Iterate the board matrix to find the winning combinations that is applicable
     * @param horizontalCells horizontal
     * @param verticalCells
     * @param diagonalLeftToRightCells
     * @param diagonalRightToLeftCells
     * @return
     */
    public Map<String,Integer> countSameSymbols(final String horizontalCells, final String verticalCells, final String diagonalLeftToRightCells, final String diagonalRightToLeftCells){

        final Map<String,List<String>> appliedWinningCombination = new HashMap<>();

        final Map<String,Integer> sameSymbolCounter = new HashMap<>();
        final List<String> horizontalSymbolMatchList = new ArrayList<>();
        final List<String> verticalSymbolMatchList = new ArrayList<>();
        final List<String> diagonalLeftToRightSymbolMatchList = new ArrayList<>();
        final List<String> diagonalRightToLeftSymbolMatchList = new ArrayList<>();

        boolean diagonalLeftToRightSymbolsMatch=false;
        String diagonalLeftToRightSymbol=null;
        boolean diagonalRightToLeftSymbolsMatch=false;
        String diagonalRightToLeftSymbol=null;

        for(int i=0;i < matrix.length;i++){
            boolean horizontalSymbolsMatch=false;
            String horizontalSymbol=null;
            boolean verticalSymbolsMatch=false;
            String verticalSymbol=null;

            for(int j=0;j < matrix[i].length;j++){
                sameSymbolCounter.put(matrix[i][j], sameSymbolCounter.getOrDefault(matrix[i][j], 0)+1);

                if(horizontalCells.contains(i + ":" + j) ){
                    if(null == horizontalSymbol){
                        horizontalSymbol=matrix[i][j];
                        horizontalSymbolsMatch=true;
                    }else if(!matrix[i][j].equalsIgnoreCase(horizontalSymbol)){
                        horizontalSymbolsMatch=false;
                    }
                }

                if(verticalCells.contains(j + ":" + i) ){

                    if(null == verticalSymbol){
                        verticalSymbol=matrix[j][i];
                        verticalSymbolsMatch=true;
                    }else if(!matrix[j][i].equalsIgnoreCase(verticalSymbol)){
                        verticalSymbolsMatch=false;
                    }
                }

                if(diagonalLeftToRightCells.contains(i + ":" + j) ){
                    if(null == diagonalLeftToRightSymbol){
                        diagonalLeftToRightSymbol=matrix[i][j];
                        diagonalLeftToRightSymbolsMatch=true;
                    }else if(!matrix[i][j].equalsIgnoreCase(diagonalLeftToRightSymbol)){
                        diagonalLeftToRightSymbolsMatch=false;
                    }
                }

                if(diagonalRightToLeftCells.contains(i + ":" + j) ){
                    if(null == diagonalRightToLeftSymbol){
                        diagonalRightToLeftSymbol=matrix[i][j];
                        diagonalRightToLeftSymbolsMatch=true;
                    }else if(!matrix[i][j].equalsIgnoreCase(diagonalRightToLeftSymbol)){
                        diagonalRightToLeftSymbolsMatch=false;
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
            appliedWinningCombination.put(k,new ArrayList<String>(Arrays.asList("same_symbol_"+v+"_times")));
        });

        ScratchGameUtil.addSymbolToList(horizontalSymbolMatchList,appliedWinningCombination, ScratchGameConstants.SAME_SYMBL_HORIZONTAL);
        ScratchGameUtil.addSymbolToList(verticalSymbolMatchList,appliedWinningCombination, ScratchGameConstants.SAME_SYMBL_VERTICAL);
        ScratchGameUtil.addSymbolToList(diagonalLeftToRightSymbolMatchList,appliedWinningCombination, ScratchGameConstants.SAME_SYMBL_LTR_DIAGONAL);
        ScratchGameUtil.addSymbolToList(diagonalRightToLeftSymbolMatchList,appliedWinningCombination, ScratchGameConstants.SAME_SYMBL_RTL_DIAGONAL);

        appliedWinningCombination.keySet().forEach(k->{
            System.out.println(k+"..."+appliedWinningCombination.get(k));
        });

        return sameSymbolCounter;
    }





    public void playGame(){
        loadGameBoard();
        decideWinningCombination();
    }

}
