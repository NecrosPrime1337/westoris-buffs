package ru.westoris.buffs.proxy;

import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayer;

public class CommonProxy {

    public EntityPlayer getPlayerEntity(MessageContext ctx) {

        return ctx.getServerHandler().playerEntity;
    }
    public void registerRenderers()
    {
        // ����� ������, ��� ��� ������ �� �������� ������� ��� �������!
    }
    public void serverStarting(FMLServerStartingEvent event) {}
}
