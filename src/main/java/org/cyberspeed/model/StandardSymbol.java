package org.cyberspeed.model;

//import lombok.Data;
import java.util.Map;

//@Data
public class StandardSymbol {
    private short column;
    private short row;
    private Map<String,Integer> symbols;

    public short getColumn() {
        return column;
    }

    public void setColumn(short column) {
        this.column = column;
    }

    public short getRow() {
        return row;
    }

    public void setRow(short row) {
        this.row = row;
    }

    public Map<String, Integer> getSymbols() {
        return symbols;
    }

    public void setSymbols(Map<String, Integer> symbols) {
        this.symbols = symbols;
    }
}
