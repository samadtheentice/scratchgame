package org.cyberspeed.model;

import com.fasterxml.jackson.annotation.JsonProperty;
//import lombok.Data;

//@Data
public class Symbol {
    @JsonProperty("reward_multiplier")
    private double rewardMultiplier;
    private String type;
    private String impact;
    private double extra;

    public double getRewardMultiplier() {
        return rewardMultiplier;
    }

    public void setRewardMultiplier(double rewardMultiplier) {
        this.rewardMultiplier = rewardMultiplier;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }

    public double getExtra() {
        return extra;
    }

    public void setExtra(double extra) {
        this.extra = extra;
    }
}
