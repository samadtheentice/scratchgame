package org.cyberspeed.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
@Data
public class WinCombination{
    @JsonProperty("reward_multiplier")
    private double rewardMultiplier;
    private String when;
    private short count;
    private String group;
    @JsonProperty("covered_areas")
    private List<List<String>> coveredAreas;
}
