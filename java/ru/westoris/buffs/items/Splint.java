package ru.westoris.buffs.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import ru.westoris.buffs.entity.BuffsLivingBase;
import ru.westoris.buffs.main.Buff;
import ru.westoris.buffs.main.BuffsMain;
import ru.westoris.buffs.player.BuffsPlayer;
import ru.westoris.buffs.items.ItemSetup;
public class Splint extends Item {

    public static final String[] potionNames = new String[] {"first"};

    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    public Splint() {
        super();
        this.setHasSubtypes(true);
    }
    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.bow; // анимация и звук лука
    }

    @Override
    public ItemStack onItemRightClick (ItemStack itemStack, World world, EntityPlayer player) {
        ArrowNockEvent event = new ArrowNockEvent(player, itemStack);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled())
            return event.result;
        else {
            ChatComponentTranslation message;
            message = new ChatComponentTranslation("");
            BuffsLivingBase eLivingBase = BuffsLivingBase.get(player);
            this.getItemUseAction(itemStack);
            if (eLivingBase.isBuffActive(Buff.bonebreak.id)) {
                BuffsPlayer ePlayer = BuffsPlayer.get(player);
                //ePlayer.setPlayerHealth(player, ePlayer.getMaxHealth(), ePlayer.getHealth(player)+50f );
                eLivingBase.removeBuff(Buff.bonebreak.id);

                //eLivingBase.addBuff(new ActiveBuff(Buff.potionCooldown.id, 300));
                message.getChatStyle().setColor(EnumChatFormatting.GREEN);
                message.appendSibling(new ChatComponentTranslation("westoris.buffs.splint.apply"));
                itemStack.stackSize--;
            }
            else  if (eLivingBase.isBuffActive(Buff.bleeding.id)) {
                BuffsPlayer ePlayer = BuffsPlayer.get(player);
                //ePlayer.setPlayerHealth(player, ePlayer.getMaxHealth(), ePlayer.getHealth(player)+50f );
                eLivingBase.removeBuff(Buff.bleeding.id);

                //eLivingBase.addBuff(new ActiveBuff(Buff.potionCooldown.id, 300));
                message.getChatStyle().setColor(EnumChatFormatting.GREEN);
                message.appendSibling(new ChatComponentTranslation("westoris.buffs.firstaidkit.apply"));
                itemStack.stackSize--;
            }
            else {

                message.getChatStyle().setColor(EnumChatFormatting.RED);
                message.appendSibling(new ChatComponentTranslation("westoris.buffs.splint.deny"));
                //CommandBase.getCommandSenderAsPlayer(player).addChatMessage("Мне это не нужно. Не зачем тратить бинты просто так");

            }
            player.addChatMessage(message);
            return itemStack;
        }
    }

    public void onPlayerStoppedUsing(ItemStack is, World world, EntityPlayer player)
    {
        int j = 1;

        ArrowLooseEvent event = new ArrowLooseEvent(player, is, 1);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled())
            return;
        j = event.charge;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage) {

        return this.icons[damage];
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
