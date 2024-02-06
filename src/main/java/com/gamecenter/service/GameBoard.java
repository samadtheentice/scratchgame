package com.gamecenter.service;

import java.util.List;
import java.util.Map;

/**
 * Game board interface
 */
public interface GameBoard {
    String[][] loadStandardSymbolsInBoard();
    String loadBonusSymbolsInBoard(final String[][] matrix);
    Map<String,List<String>> decideWinningCombination(final String[][] matrix);
    void playGame(final double betAmount);
    double calculateReward(final Map<String, List<String>> appliedWinningCombination, final String bonusSymbol, final double betAmount);
}
