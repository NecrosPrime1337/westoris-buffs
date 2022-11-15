package ru.westoris.buffs.buffs;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import ru.westoris.buffs.entity.BuffsLivingBase;
import ru.westoris.buffs.main.ActiveBuff;
import ru.westoris.buffs.main.Buff;
import ru.westoris.buffs.player.BuffsPlayer;

public class HeadQuake extends Buff {
    private boolean e1,e2,e3 = true;
    public void checkBuff(EntityLivingBase entityLivingBase, ActiveBuff buffs){
        //
    }
    public HeadQuake(EntityLivingBase eEntityLiving, int tier, int duration) {
        this.effect(eEntityLiving, tier, duration);
    }
    public void effect (EntityLivingBase entityLivingBase, int tier, int duration){
        if (entityLivingBase instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer) entityLivingBase;
            BuffsPlayer ePlayer = BuffsPlayer.get(player);
            ChatComponentTranslation message;
            message = new ChatComponentTranslation("");
            message.getChatStyle().setColor(EnumChatFormatting.RED);
            if (tier == 1) {
                this.e3 = false;
                message.appendSibling(new ChatComponentTranslation("buff.headquake.light"));
            }
            else if (tier == 2) {
                message.appendSibling(new ChatComponentTranslation("buff.headquake.medium"));
            }
            else if (tier == 3) {

                message.appendSibling(new ChatComponentTranslation("buff.headquake.hard"));
            }
            else {
                ePlayer.setPlayerHealth(player,  0, player.getHealth());
            }

            if (this.e1) {
                player.addPotionEffect(new PotionEffect(18, duration));
            }
            if (this.e2) {
                player.addPotionEffect(new PotionEffect(2, duration, tier + 1));
            }
            if (this.e3) {
                player.addPotionEffect(new PotionEffect(9, duration, 3));
            }
            player.addChatMessage(message);
        }
        else {
            EntityLiving mob = (EntityLiving) entityLivingBase;

            tier++;
            if (tier == 1) {
                mob.addPotionEffect(new PotionEffect(2, duration, tier + 1));
                mob.addPotionEffect(new PotionEffect(18, tier * 1000));
            }
            if (tier == 2) {
                mob.addPotionEffect(new PotionEffect(2, duration, tier + 1));
                mob.addPotionEffect(new PotionEffect(18, tier * 1000));
            }
            if (tier == 3) {
                mob.addPotionEffect(new PotionEffect(2, duration, tier + 1));
                mob.addPotionEffect(new PotionEffect(18, tier * 10000));


            }
            if (tier > 3) {
                mob.setHealth(0);
            }
        }


    }
}



