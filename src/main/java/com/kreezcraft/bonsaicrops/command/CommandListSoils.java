package com.kreezcraft.bonsaicrops.command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import com.kreezcraft.bonsaicrops.BonsaiTrees;
import com.kreezcraft.bonsaicrops.api.IBonsaiSoil;
import com.kreezcraft.bonsaicrops.base.CommandBaseExt;
import com.kreezcraft.bonsaicrops.compat.CraftTweaker2.registries.TagModificationsRegistry;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class CommandListSoils extends CommandBaseExt {
    @Override
    public String getName() {
        return "listSoils";
    }

    @Override
    public boolean isAllowed(EntityPlayer player, boolean creative, boolean isOp) {
        return true;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        sender.sendMessage(new TextComponentTranslation("commands.bonsaitrees.listSoils.result_title"));
        List<IBonsaiSoil> types = new LinkedList<>(BonsaiTrees.instance.soilRegistry.getAllSoils());
        types.sort(Comparator.comparing(IBonsaiSoil::getName));
        for(IBonsaiSoil type : types) {
            sender.sendMessage(new TextComponentString(" - " + type.getName() + " " + TagModificationsRegistry.getModifiedTagList(type)));
        }
    }
}
