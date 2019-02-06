package com.kreezcraft.bonsaicrops.compat.CraftTweaker2;

import crafttweaker.CraftTweakerAPI;
import com.kreezcraft.bonsaicrops.compat.CraftTweaker2.handlers.SoilCompatibilityHandler;
import com.kreezcraft.bonsaicrops.compat.CraftTweaker2.handlers.SoilStatsHandler;
import com.kreezcraft.bonsaicrops.compat.CraftTweaker2.handlers.TreeDropHandler;
import com.kreezcraft.bonsaicrops.compat.CraftTweaker2.handlers.TreeGrowthHandler;

public class CraftTweakerCompatibility {
    private static boolean registered;

    public static void register() {
        if (registered) {
            return;
        }

        registered = true;

        CraftTweakerAPI.registerClass(TreeDropHandler.class);
        CraftTweakerAPI.registerClass(TreeGrowthHandler.class);
        CraftTweakerAPI.registerClass(SoilCompatibilityHandler.class);
        CraftTweakerAPI.registerClass(SoilStatsHandler.class);
    }
}
