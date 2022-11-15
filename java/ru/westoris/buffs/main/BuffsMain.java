package ru.westoris.buffs.main;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.common.MinecraftForge;
import ru.westoris.buffs.commands.info;
import ru.westoris.buffs.events.BuffsEvents;
import ru.westoris.buffs.items.ItemSetup;
import ru.westoris.buffs.network.NetworkHandler;
import ru.westoris.buffs.proxy.CommonProxy;

@Mod(modid = BuffsMain.MODID, name = BuffsMain.NAME, version = BuffsMain.VERSION)
public class BuffsMain {

    public static final String MODID = "buffs";
    public static final String NAME = "Buffs Westoris TFC addon";
    public static final String VERSION = "1.0B";

    @SidedProxy(clientSide = "ru.westoris.buffs.proxy.ClientProxy", serverSide = "ru.westoris.buffs.proxy.CommonProxy")
    public static CommonProxy proxy;



    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        NetworkHandler.registerPackets();

        ItemSetup.setup();
        proxy.registerRenderers();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        //MinecraftForge.EVENT_BUS.unregister(LivingFallEvent event);
        MinecraftForge.EVENT_BUS.register(new BuffsEvents());

    }
    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {

        event.registerServerCommand(new info());
    }

}
