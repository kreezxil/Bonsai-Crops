package com.kreezcraft.bonsaicrops.soils;

import net.minecraft.item.ItemStack;
import com.kreezcraft.bonsaicrops.api.IBonsaiSoil;
import com.kreezcraft.bonsaicrops.api.IBonsaiTreeType;
import com.kreezcraft.bonsaicrops.api.ISoilCompatibilityHelper;
import com.kreezcraft.bonsaicrops.compat.CraftTweaker2.registries.TagModificationsRegistry;
import com.kreezcraft.bonsaicrops.trees.TreeTypeRegistry;
import com.kreezcraft.bonsaicrops.utility.Logz;

import java.util.*;

public class SoilCompatibility implements ISoilCompatibilityHelper {
    private Map<IBonsaiTreeType, Set<IBonsaiSoil>> soilCompatibility;
    private Map<IBonsaiSoil, Set<IBonsaiTreeType>> treeCompatibility;

    public SoilCompatibility() {
        soilCompatibility = new HashMap<>();
        treeCompatibility = new HashMap<>();
    }

    @Override
    public Set<IBonsaiSoil> getValidSoilsForTree(IBonsaiTreeType tree) {
        return soilCompatibility.getOrDefault(tree, new HashSet<>());
    }

    @Override
    public Set<IBonsaiTreeType> getValidTreesForSoil(IBonsaiSoil soil) {
        return treeCompatibility.getOrDefault(soil, new HashSet<>());
    }

    @Override
    public boolean canTreeGrowOnSoil(IBonsaiTreeType tree, IBonsaiSoil soil) {
        if(!soilCompatibility.containsKey(tree) || soilCompatibility.get(tree) == null) {
            return false;
        }

        return soilCompatibility.get(tree).contains(soil);
    }

    @Override
    public boolean isValidSoil(ItemStack soilStack) {
        for(IBonsaiSoil soil : treeCompatibility.keySet()) {
            if(soil.matchesStack(soilStack)) {
                return true;
            }
        }

        return false;
    }

    private void addCompatEntry(IBonsaiSoil soil, IBonsaiTreeType tree) {
        if(!soilCompatibility.containsKey(tree)) {
            soilCompatibility.put(tree, new HashSet<>());
        }

        soilCompatibility.get(tree).add(soil);

        if(!treeCompatibility.containsKey(soil)) {
            treeCompatibility.put(soil, new HashSet<>());
        }

        treeCompatibility.get(soil).add(tree);
    }

    public void updateCompatibility(BonsaiSoilRegistry soilRegistry, TreeTypeRegistry typeRegistry) {
        soilCompatibility = new HashMap<>();
        treeCompatibility = new HashMap<>();

        Map<String, Set<IBonsaiSoil>> reverseSoilTagMap = new HashMap<>();
        for(IBonsaiSoil soil : soilRegistry.getAllSoils()) {
            for(String tag : TagModificationsRegistry.getModifiedTagList(soil)) {
                if(!reverseSoilTagMap.containsKey(tag)) {
                    reverseSoilTagMap.put(tag, new HashSet<>());
                }

                reverseSoilTagMap.get(tag).add(soil);
            }
        }

        for(IBonsaiTreeType tree : typeRegistry.getAllTypes()) {
            for(String tag : TagModificationsRegistry.getModifiedTagList(tree)) {
                if(!reverseSoilTagMap.containsKey(tag)) {
                    continue;
                }

                for(IBonsaiSoil soil : reverseSoilTagMap.get(tag)) {
                    Logz.debug("Tree %s grows on %s because of %s", tree.getName(), soil.getName(), tag);
                    this.addCompatEntry(soil, tree);
                }
            }
        }
    }
}
