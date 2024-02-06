package com.gamecenter.model;

//import lombok.Data;
import java.util.Map;

//@Data
public class ProbabilitySymbol {
    Map<String,String> symbols;

    public Map<String, String> getSymbols() {
        return symbols;
    }

    public void setSymbols(Map<String, String> symbols) {
        this.symbols = symbols;
    }
}
