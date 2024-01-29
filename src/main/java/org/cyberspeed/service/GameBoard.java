package org.cyberspeed.service;

import java.util.List;
import java.util.Map;

/**
 * Game board interface
 */
public interface GameBoard {
    public String[][] loadStandardSymbolsInBoard();
    public String loadBonusSymbolsInBoard(final String[][] matrix);
    public Map<String,List<String>> decideWinningCombination(final String[][] matrix);
    public void playGame(final double betAmount);
    public double calculateReward(final Map<String, List<String>> appliedWinningCombination, final String bonusSymbol, final double betAmount);
}
