package org.cyberspeed.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.Map;

@Data
public class GameConfigData {
    private short columns;
    private short rows;
    private Map<String,Symbol> symbols;
    private Probability probabilities;
    @JsonProperty("win_combinations")
    private Map<String,WinCombination> winCombinations;

}
