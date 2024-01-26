package org.cyberspeed.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Symbol {
    private String symbolName;
    @JsonProperty("reward_multiplier")
    private float rewardMultiplier;
    private String type;
    private String impact;
    private short extra;

}
