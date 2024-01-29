package org.cyberspeed.service;

import org.cyberspeed.model.GameConfigData;

import java.util.List;
import java.util.Map;

public interface GameBoard {
    public void loadStandardSymbolsInBoard(final String[][] matrix);
    public String loadBonusSymbolsInBoard(final String[][] matrix);
    public Map<String,List<String>> decideWinningCombination(final String[][] matrix);
    public void playGame();
    public double calculateReward(final Map<String, List<String>> appliedWinningCombination, final String bonusSymbol, final double betAmount);
}
