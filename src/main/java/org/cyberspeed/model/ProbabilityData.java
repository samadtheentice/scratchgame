package org.cyberspeed.model;

import lombok.Data;

import java.util.List;
@Data
public class ProbabilityData {
    private short column;
    private short row;
    private List<ProbabilitySymbol> symbols;
}