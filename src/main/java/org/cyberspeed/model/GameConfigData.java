package org.cyberspeed.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class GameConfigData {
    private short columns;
    private short rows;
    private List<Symbol> symbols;
    private Probability probabilities;
    @JsonProperty("win_combinations")
    private List<WinCombination> winCombinations;

}
