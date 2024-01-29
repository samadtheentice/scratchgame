package org.cyberspeed.model;

import lombok.Data;
import java.util.Map;

@Data
public class StandardSymbol {
    private short column;
    private short row;
    private Map<String,Integer> symbols;
}
