package ru.westoris.buffs.items;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.westoris.buffs.items.*;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import ru.westoris.buffs.entity.BuffsLivingBase;
import ru.westoris.buffs.main.Buff;
import ru.westoris.buffs.main.BuffsMain;
import ru.westoris.buffs.player.BuffsPlayer;

public class FirstAidKit extends Item{


    public static final String[] potionNames = new String[] {"firstaidkit"};
    private boolean bonebreak, bleeding, intoxication, scorching, allow;
    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    public FirstAidKit() {
        super();
        this.setHasSubtypes(true);
        this.setMaxDamage(50);
        this.setMaxStackSize(1);
    }
    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.bow; // анимация и звук лука
    }
    private static final Logger logger = LogManager.getLogger();
    @Override
    public ItemStack onItemRightClick (ItemStack itemStack, World world, EntityPlayer player) {
        BuffsLivingBase blb = BuffsLivingBase.get(player);
        //logger.info("bonebreak "+ this.bonebreak+ " to be" + blb.isBuffActive(Buff.bonebreak.id));
        //logger.info("bleeding "+ this.bleeding+" to be" + blb.isBuffActive(Buff.bleeding.id));
        //logger.info("scorching "+ this.scorching+" to be" + blb.isBuffActive(Buff.scorch.id));


        if (blb.isBuffActive(Buff.bonebreak.id) ||
                blb.isBuffActive(Buff.bleeding.id) ||
                blb.isBuffActive(Buff.intoxication.id) ||
                blb.isBuffActive(Buff.scorch.id) || player.getHealth() < player.getMaxHealth() ) {
            //|| (!player.isUsingItem()) ||
            this.bonebreak = blb.isBuffActive(Buff.bonebreak.id);
            this.bleeding = blb.isBuffActive(Buff.bleeding.id);
            this.intoxication = blb.isBuffActive(Buff.intoxication.id);
            this.scorching = blb.isBuffActive(Buff.scorch.id);
            player.setItemInUse(itemStack, 40);
        }
        else {
            ChatComponentTranslation message = new ChatComponentTranslation("");
            message.getChatStyle().setColor(EnumChatFormatting.RED);
            message.appendText("westoris.buffs.firstaidkit.deny");
        }

        return itemStack;
    }
    /*


        ChatComponentTranslation message = new ChatComponentTranslation("");
        if (event.isCanceled()) {
            message.appendSibling(new ChatComponentTranslation("westoris.buffs.healing.cancel"));
            return event.result;

        }

        if (player.capabilities.isCreativeMode || player.inventory.hasItem(Items.firstAidKit)) {
            player.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
        }
        return itemStack;
    }
    BuffsLivingBase bp = BuffsLivingBase.get(player);
        this.bonebreak = bp.isBuffActive(Buff.bonebreak.id);
        this.bleeding = bp.isBuffActive(Buff.bleeding.id);
        this.intoxication = bp.isBuffActive(Buff.intoxication.id);
        if (this.bonebreak|| this.bleeding || this.intoxication ) {

            message.appendSibling(new ChatComponentTranslation("buff.healing"));
            player.addChatComponentMessage(message);
            player.setItemInUse(itemStack, 128);
            if (this.bonebreak) {
                bp.removeBuff(Buff.bonebreak.id);
                player.removePotionEffect(2);
                player.removePotionEffect(9);
                player.removePotionEffect(18);
            }
            if (this.bleeding) {
                bp.removeBuff(Buff.bleeding.id);
            }
            if (this.intoxication) {
                bp.removeBuff(Buff.intoxication.id);
                player.removePotionEffect(15);
            }
            ///this.hashealed = true;
        } else
            return itemStack;
        itemStack.stackSize--;
        return itemStack;
    }
     */
    @Override
    public void onUsingTick (ItemStack stack, EntityPlayer player, int count){
        BuffsLivingBase blb = BuffsLivingBase.get(player);

        ChatComponentTranslation message = new ChatComponentTranslation("");
        message.getChatStyle().setColor(EnumChatFormatting.GREEN);
        //logger.info("healing count "+ count);
        //logger.info("bonebreak "+ this.bonebreak+ " to be " + blb.isBuffActive(Buff.bonebreak.id));
        //logger.info("bleeding "+ this.bleeding+" to be " + blb.isBuffActive(Buff.bleeding.id));
        //logger.info("scorching "+ this.scorching+" to be " + blb.isBuffActive(Buff.scorch.id));
        //logger.info("healing count "+ count+" to be"+ count);
        /*if (count % 5 == 0)
        {
            player.worldObj.playSoundAtEntity(player, "step.cloth", 0.5F, 1f);

        }
        if (count % 50 == 0 ) {
         */
        if (this.bonebreak) {
            blb.removeBuff(Buff.bonebreak.id);
            message.appendSibling(new ChatComponentTranslation("westoris.buffs.firstaidkit.bonebreak.heal"));
            player.addChatMessage(message);
        }
        if (this.bleeding) {
            blb.addBleeding(blb.getBleeding()/10);
            message.appendSibling(new ChatComponentTranslation("westoris.buffs.firstaidkit.bleeding.heal"));
            player.addChatMessage(message);
        }
        if (this.scorching) {
            blb.addScorching(blb.getScorching()/10);
            message.appendSibling(new ChatComponentTranslation("westoris.buffs.firstaidkit.scorching.heal"));
            player.addChatMessage(message);
        }
        if (this.intoxication) {
            blb.removeBuff(Buff.intoxication.id);
        }
        if (player.getHealth()< player.getMaxHealth()) {
            player.heal(20);
        }

        //message.appendSibling(new ChatComponentTranslation("westoris.buffs.firstaidkit.apply"));


        stack.setItemDamage(stack.getItemDamage() - 1);
        player.stopUsingItem();

    }




    /*public void onPlayerStoppedUsing(ItemStack is, World world, EntityPlayer player)
    {
        int j = 10000;

        ArrowLooseEvent event = new ArrowLooseEvent(player, is, j);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled())
            return;
        j = event.charge;


        ChatComponentTranslation message = new ChatComponentTranslation("");
        //message = new ChatComponentTranslation("");
        BuffsLivingBase eLivingBase = BuffsLivingBase.get(player);
        this.getItemUseAction(is);
        if (eLivingBase.isBuffActive(Buff.bonebreak.id)) {
            BuffsPlayer ePlayer = BuffsPlayer.get(player);
            ePlayer.setPlayerHealth(player, ePlayer.getMaxHealth(), ePlayer.getHealth(player)+50f );
            eLivingBase.removeBuff(Buff.bonebreak.id);

            //eLivingBase.addBuff(new ActiveBuff(Buff.potionCooldown.id, 300));
            message.getChatStyle().setColor(EnumChatFormatting.GREEN);
            message.appendSibling(new ChatComponentTranslation("westoris.buffs.firstaidkit.apply"));
            is.stackSize--;
        }
        else  if (eLivingBase.isBuffActive(Buff.scorch.id)) {
            BuffsPlayer ePlayer = BuffsPlayer.get(player);
            ePlayer.setPlayerHealth(player, ePlayer.getMaxHealth(), ePlayer.getHealth(player)+50f );
            eLivingBase.removeBuff(Buff.scorch.id);

            //eLivingBase.addBuff(new ActiveBuff(Buff.potionCooldown.id, 300));
            message.getChatStyle().setColor(EnumChatFormatting.GREEN);
            message.appendSibling(new ChatComponentTranslation("westoris.buffs.firstaidkit.apply"));
            is.stackSize--;
        }
        else  if (eLivingBase.isBuffActive(Buff.bleeding.id)) {
            BuffsPlayer ePlayer = BuffsPlayer.get(player);
            ePlayer.setPlayerHealth(player, ePlayer.getMaxHealth(), ePlayer.getHealth(player)+50f );
            eLivingBase.removeBuff(Buff.bleeding.id);

            //eLivingBase.addBuff(new ActiveBuff(Buff.potionCooldown.id, 300));
            message.getChatStyle().setColor(EnumChatFormatting.GREEN);
            message.appendSibling(new ChatComponentTranslation("westoris.buffs.firstaidkit.apply"));
            is.stackSize--;
        }
        else  if (eLivingBase.isBuffActive(Buff.intoxication.id)) {
            BuffsPlayer ePlayer = BuffsPlayer.get(player);
            ePlayer.setPlayerHealth(player, ePlayer.getMaxHealth(), ePlayer.getHealth(player)+50f );
            eLivingBase.removeBuff(Buff.intoxication.id);

            //eLivingBase.addBuff(new ActiveBuff(Buff.potionCooldown.id, 300));
            message.getChatStyle().setColor(EnumChatFormatting.GREEN);
            message.appendSibling(new ChatComponentTranslation("westoris.buffs.firstaidkit.apply"));
            is.stackSize--;
        }
        else {

            message.getChatStyle().setColor(EnumChatFormatting.RED);
            message.appendSibling(new ChatComponentTranslation("westoris.buffs.firstaidkit.deny"));
            //CommandBase.getCommandSenderAsPlayer(player).addChatMessage("Мне это не нужно. Не зачем тратить бинты просто так");

        }

    }*/

    @Override
    public boolean itemInteractionForEntity(ItemStack is, EntityPlayer player, EntityLivingBase elb) {
        BuffsLivingBase blb = BuffsLivingBase.get(elb);
        if(blb.haveActiveBuffs()) {
            boolean bonebreak = blb.isBuffActive(Buff.bonebreak.id);
            boolean bleeding = blb.isBuffActive(Buff.bleeding.id);
            boolean intoxication = blb.isBuffActive(Buff.intoxication.id);
            if (bonebreak) {
                blb.removeBuff(Buff.bonebreak.id);
            }
            if(bleeding) {
                blb.removeBuff(Buff.bleeding.id);
            }
            if (intoxication) {
                blb.removeBuff(Buff.bonebreak.id);

            }
            is.stackSize--;
            player.addChatMessage(new ChatComponentTranslation("healed:" + "bonebreak:"+bonebreak+" bleeding:"+bleeding));
            return true;
        } else
            return false;
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
