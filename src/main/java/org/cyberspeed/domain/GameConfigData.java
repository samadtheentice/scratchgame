package org.cyberspeed.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class GameConfigData {
    private short columns;
    private short rows;
    private List<Symbol> symbols;
    private List<Probability> probabilities;
    @JsonProperty("win_combinations")
    private List<WinCombination> winCombinations;

}
