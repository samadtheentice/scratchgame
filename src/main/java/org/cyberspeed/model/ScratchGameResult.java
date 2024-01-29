package org.cyberspeed.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ScratchGameResult {
    private String[][] matrix;
    private double reward;
    @JsonProperty("applied_winning_combinations")
    private Map<String, List<String>> appliedWinningCombinations;
    @JsonProperty("applied_bonus_symbol")
    private String appliedBonusSymbol;

    public ScratchGameResult(final String[][] matrix, final Map<String, List<String>> appliedWinningCombinations, final String appliedBonusSymbol, final double reward) {
        this.matrix = matrix;
        this.appliedWinningCombinations = appliedWinningCombinations;
        this.appliedBonusSymbol = appliedBonusSymbol;
        this.reward = reward;
    }
}
