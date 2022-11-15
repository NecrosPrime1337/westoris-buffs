package ru.westoris.buffs.items;

import javax.annotation.Nullable;

import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.EntityList;
import net.minecraft.item.Item;


public class Items  {

    //items for healing
    public static Item splint;
    public static Item antiseptic;
    public static Item firstAidKit;

    public static void registerItems(){
        GameRegistry.registerItem(splint, splint.getUnlocalizedName());
        GameRegistry.registerItem(antiseptic, antiseptic.getUnlocalizedName());
        GameRegistry.registerItem(firstAidKit, firstAidKit.getUnlocalizedName());

    }
    public static void registerEntity(Class entityClass, String name, int primaryColor, int secondaryColor, boolean colorize) {
        {
            int entityID = EntityRegistry.findGlobalUniqueEntityId();
            long seed = name.hashCode();

            EntityRegistry.registerGlobalEntityID(entityClass, name, entityID);
            if (colorize) {
                EntityList.entityEggs.put(Integer.valueOf(entityID), new EntityList.EntityEggInfo(entityID, primaryColor, secondaryColor));
            }
            EntityRegistry.registerModEntity(entityClass, name, entityID, "buffs", 64, 1, true); //эта строка не нужна(зачем она?)

        }
    }
}
