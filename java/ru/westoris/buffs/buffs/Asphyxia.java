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

public class Asphyxia extends Buff {
    public void checkBuff(EntityLivingBase entityLivingBase, ActiveBuff buffs){
        //
    }
    public Asphyxia(EntityLivingBase eEntityLiving, int tier, int duration) {
        this.effect(eEntityLiving, tier, duration);
    }

    public void effect (EntityLivingBase entityLivingBase, int tier, int duration){

        if (entityLivingBase instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer) entityLivingBase;
            ChatComponentTranslation message;
            message = new ChatComponentTranslation("");
            tier++;
            //ePlayer.setPlayerHealth(player, ePlayer.getMaxHealth() - 5 * (tier), player.getHealth());
            player.addPotionEffect(new PotionEffect(15, duration));
            message.getChatStyle().setColor(EnumChatFormatting.RED);
            message.appendSibling(new ChatComponentTranslation("westoris.buffs.asphyxia"));
            player.addChatMessage(message);
        }
        else {
            EntityLiving mob = (EntityLiving) entityLivingBase;
            mob.addPotionEffect(new PotionEffect(15, duration));

        }


    }

}





