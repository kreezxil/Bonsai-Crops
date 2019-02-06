package com.kreezcraft.bonsaicrops.network;

import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import com.kreezcraft.bonsaicrops.render.TESRBonsaiPot;

public class MessageReloadTreesHandler implements IMessageHandler<MessageReloadTrees, MessageReloadTrees> {
    @Override
    public MessageReloadTrees onMessage(MessageReloadTrees message, MessageContext ctx) {
        TESRBonsaiPot.clearGlLists();
        return null;
    }
}
