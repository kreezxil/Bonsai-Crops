package com.kreezcraft.bonsaicrops.base;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import com.kreezcraft.bonsaicrops.BonsaiTrees;

public class BaseBlock extends Block {
    public BaseBlock(Material material) {
        super(material);
    }

    public BaseBlock() {
        this(Material.ROCK);
    }

    @Override
    public Block setUnlocalizedName(String name) {
        if(!name.startsWith(BonsaiTrees.MODID + ".")) {
            name = BonsaiTrees.MODID + "." + name;
        }
        return super.setUnlocalizedName(name);
    }
}
