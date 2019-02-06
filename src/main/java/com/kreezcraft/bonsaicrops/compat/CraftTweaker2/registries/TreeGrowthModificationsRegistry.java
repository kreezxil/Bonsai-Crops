package com.kreezcraft.bonsaicrops.compat.CraftTweaker2.registries;

import com.kreezcraft.bonsaicrops.api.IBonsaiTreeType;
import com.kreezcraft.bonsaicrops.misc.ConfigurationHandler;

import java.util.HashMap;
import java.util.Map;

public class TreeGrowthModificationsRegistry {
    public static Map<String, Float> multipliers = new HashMap<>();

    public static void setMultiplier(String treeName, float multiplier) {
        multipliers.put(treeName, multiplier);
    }

    public static int getModifiedGrowTime(IBonsaiTreeType type) {
        return (int)Math.floor(ConfigurationHandler.GeneralSettings.baseGrowTicks * multipliers.getOrDefault(type.getName(), 1.0f));
    }
}
