package com.kreezcraft.bonsaicrops.command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import com.kreezcraft.bonsaicrops.BonsaiTrees;
import com.kreezcraft.bonsaicrops.base.CommandBaseExt;
import com.kreezcraft.bonsaicrops.integration.IntegrationRegistry;
import com.kreezcraft.bonsaicrops.network.MessageReloadTrees;
import com.kreezcraft.bonsaicrops.network.PackageHandler;
import com.kreezcraft.bonsaicrops.trees.TreeShapeRegistry;

public class CommandReloadTrees extends CommandBaseExt {
    @Override
    public String getName() {
        return "reloadTrees";
    }

    @Override
    public boolean isAllowed(EntityPlayer player, boolean creative, boolean isOp) {
        return isOp;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        BonsaiTrees.instance.typeRegistry.clear();
        BonsaiTrees.instance.soilRegistry.clear();

        IntegrationRegistry.registerTreeIntegrations();
        TreeShapeRegistry.init();

        // We can not call this here as this call can only exist on the client, so send a message to all clients
        PackageHandler.instance.sendToAll(new MessageReloadTrees());

        // And also update soil compatibility
        BonsaiTrees.instance.soilCompatibility.updateCompatibility(BonsaiTrees.instance.soilRegistry, BonsaiTrees.instance.typeRegistry);
    }
}
