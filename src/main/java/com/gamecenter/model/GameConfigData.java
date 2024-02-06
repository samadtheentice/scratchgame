package com.gamecenter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
//import lombok.Data;
import java.util.Map;

//@Data
public class GameConfigData {
    private short columns;
    private short rows;
    private Map<String,Symbol> symbols;
    private Probability probabilities;
    @JsonProperty("win_combinations")
    private Map<String,WinCombination> winCombinations;

    public short getColumns() {
        return columns;
    }

    public void setColumns(short columns) {
        this.columns = columns;
    }

    public short getRows() {
        return rows;
    }

    public void setRows(short rows) {
        this.rows = rows;
    }

    public Map<String, Symbol> getSymbols() {
        return symbols;
    }

    public void setSymbols(Map<String, Symbol> symbols) {
        this.symbols = symbols;
    }

    public Probability getProbabilities() {
        return probabilities;
    }

    public void setProbabilities(Probability probabilities) {
        this.probabilities = probabilities;
    }

    public Map<String, WinCombination> getWinCombinations() {
        return winCombinations;
    }

    public void setWinCombinations(Map<String, WinCombination> winCombinations) {
        this.winCombinations = winCombinations;
    }
}
