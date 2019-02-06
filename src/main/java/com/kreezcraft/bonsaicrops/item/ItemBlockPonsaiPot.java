package com.kreezcraft.bonsaicrops.item;

import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import com.kreezcraft.bonsaicrops.BonsaiTrees;
import com.kreezcraft.bonsaicrops.base.IMetaBlockName;
import com.kreezcraft.bonsaicrops.block.BlockBonsaiPot;
import com.kreezcraft.bonsaicrops.init.Blockss;
import com.kreezcraft.bonsaicrops.render.PotColorizer;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBlockPonsaiPot extends ItemBlock {
    public ItemBlockPonsaiPot(Block block) {
        super(block);

        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        String superName = super.getUnlocalizedName(stack);
        String specialName = ((IMetaBlockName)this.block).getSpecialName(stack);
        if(specialName.length() > 0) {
            return superName + "." + specialName;
        }

        return superName;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        if(GuiScreen.isShiftKeyDown()) {
            if(stack.hasTagCompound() && stack.getTagCompound().hasKey("color")) {
                EnumDyeColor color = EnumDyeColor.byMetadata(stack.getTagCompound().getInteger("color"));
                tooltip.add(PotColorizer.textFormattingForDye(color) + I18n.format("item.fireworksCharge." + color.getUnlocalizedName()));
            }
            if(Blockss.bonsaiPot.getStateFromMeta(stack.getMetadata()).getValue(BlockBonsaiPot.IS_HOPPING)) {
                tooltip.add(TextFormatting.YELLOW + I18n.format("tooltip.bonsaitrees.autoexport"));
            } else {
                tooltip.add(TextFormatting.YELLOW + I18n.format("tooltip.bonsaitrees.use_jei_for_compatible_sapling"));
            }
        } else {
            tooltip.add(TextFormatting.GRAY + I18n.format("tooltip." + BonsaiTrees.MODID + ".hold_shift_hint"));
        }
    }
}
