package com.gamecenter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
//import lombok.Data;

import java.util.List;

//@Data
public class Probability {
    @JsonProperty("standard_symbols")
    private List<StandardSymbol> standardSymbols;
    @JsonProperty("bonus_symbols")
    private BonusSymbol bonusSymbols;

    public List<StandardSymbol> getStandardSymbols() {
        return standardSymbols;
    }

    public void setStandardSymbols(List<StandardSymbol> standardSymbols) {
        this.standardSymbols = standardSymbols;
    }

    public BonusSymbol getBonusSymbols() {
        return bonusSymbols;
    }

    public void setBonusSymbols(BonusSymbol bonusSymbols) {
        this.bonusSymbols = bonusSymbols;
    }
}
