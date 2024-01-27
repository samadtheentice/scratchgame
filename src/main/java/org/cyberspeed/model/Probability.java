package org.cyberspeed.model;

import lombok.Data;

import java.util.List;
@Data
public class Probability {
    private String type;
    private List<ProbabilityData> probablityData;
}
