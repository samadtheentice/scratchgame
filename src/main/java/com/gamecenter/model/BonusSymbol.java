package com.gamecenter.model;

//import lombok.Data;
import java.util.Map;

//@Data
public class BonusSymbol {
    private Map<String,Integer> symbols;

    public Map<String, Integer> getSymbols() {
        return symbols;
    }

    public void setSymbols(Map<String, Integer> symbols) {
        this.symbols = symbols;
    }
}
