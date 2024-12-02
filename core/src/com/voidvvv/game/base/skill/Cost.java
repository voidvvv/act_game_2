package com.voidvvv.game.base.skill;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * the cost of skill
 */
@Setter
@Getter
public class Cost {

    private Map<Integer, Float> costs = new HashMap<>();

    public Float getFloat(Integer type) {
        return costs.get(type);
    }
}
