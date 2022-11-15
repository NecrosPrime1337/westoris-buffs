package ru.westoris.buffs.proxy;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;
import ru.westoris.buffs.entity.DeadPlayer;
import ru.westoris.buffs.entity.RenderDeadPlayer;

public class ClientProxy extends CommonProxy {

    @Override
    public EntityPlayer getPlayerEntity(MessageContext ctx) {

        return (ctx.side.isClient() ? Minecraft.getMinecraft().thePlayer : super.getPlayerEntity(ctx));
    }
    @Override
    public void registerRenderers()
    {
        RenderingRegistry.registerEntityRenderingHandler(DeadPlayer.class, new RenderDeadPlayer(new ModelBiped(), 0.5F));
    }
}
