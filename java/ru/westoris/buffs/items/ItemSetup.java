package ru.westoris.buffs.items;

import net.minecraft.creativetab.CreativeTabs;
import ru.westoris.buffs.entity.DeadPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

//import net.minecraft.init.Items;

public class ItemSetup extends Items{


    public static final CreativeTabs medical = new CreativeTabs("tabmedical") {

        @Override
        public Item getTabIconItem() {
            // TODO Auto-generated method stub
            return Items.splint;
        }

    };

    public static void setup(){
        splint = new Splint().setUnlocalizedName("buffs.item.splint").setCreativeTab(medical);
        firstAidKit = new FirstAidKit().setUnlocalizedName("buffs.item.firstaidkit").setCreativeTab(medical);
        antiseptic = new Antiseptic().setUnlocalizedName("buffs.item.antiseptic").setCreativeTab(medical);
        Items.registerEntity(DeadPlayer.class, "DeadPlayer", 0x0000ff, 0xff00ff, true);
        Items.registerItems();

    }
}
