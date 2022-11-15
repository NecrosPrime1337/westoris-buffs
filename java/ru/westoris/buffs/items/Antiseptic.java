package ru.westoris.buffs.items;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import ru.westoris.buffs.main.BuffsMain;


public class Antiseptic extends Item{


    public static final String[] potionNames = new String[] {"antiseptic"};

    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    public Antiseptic() {
        super();
        this.setHasSubtypes(true);
        this.setUnlocalizedName("westoris.item.antiseptic");
        this.setCreativeTab(CreativeTabs.tabAllSearch);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconregister) {

        this.icons = new IIcon[potionNames.length];

        for (int i = 0; i < this.icons.length; i++) {

            this.icons[i] = iconregister.registerIcon(BuffsMain.MODID + ":" + (this.getUnlocalizedName().substring(5)) + i);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tabs, List list) {

        for (int i = 0; i < potionNames.length; i++) {

            list.add(new ItemStack(item, 1, i));
        }
    }
}

