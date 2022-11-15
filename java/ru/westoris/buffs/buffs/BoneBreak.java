package ru.westoris.buffs.buffs;


import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.EntityEvent;
import ru.westoris.buffs.entity.BuffsLivingBase;
import ru.westoris.buffs.main.ActiveBuff;
import ru.westoris.buffs.main.Buff;
import ru.westoris.buffs.player.BuffsPlayer;

public class BoneBreak extends Buff {
    public void checkBuff(EntityLivingBase entityLivingBase, ActiveBuff buffs){
        //
    }
    public int max=9999999;
    public BoneBreak (EntityLivingBase elb, int tier, int duration) {

        BuffsLivingBase blb = BuffsLivingBase.get(elb);

        if (elb instanceof EntityPlayer) {
            elb.playSound("buffs:bonebreak", 1, 1);
            this.playerEffect(elb, tier);

        } else {
            this.effect(elb, tier);
        }

    }
    public void playerEffect (EntityLivingBase elb, int tier) {

        BuffsPlayer blb = (BuffsPlayer) BuffsPlayer.get(elb);

        ChatComponentTranslation message;
        message = new ChatComponentTranslation("");
        message.getChatStyle().setColor(EnumChatFormatting.RED);
        if (tier == 1) {

            message.appendSibling(new ChatComponentTranslation("buff.bonebreak.light"));
        }
        if (tier == 2) {

            message.appendSibling( new ChatComponentTranslation("buff.bonebreak.medium"));
        }
        if (tier == 3) {

            message.appendSibling(new ChatComponentTranslation("buff.bonebreak.hard"));
        }
        if (tier > 3) {
            //ePlayer.setPlayerHealth(player,  0, player.getHealth());
        }
        elb.addPotionEffect(new PotionEffect(2, this.max, tier + 1));
        elb.addPotionEffect(new PotionEffect(9, this.max, 3));
        elb.addPotionEffect(new PotionEffect(18, this.max));
        EntityPlayer player = (EntityPlayer) elb;
        player.addChatMessage(message);
    }


    public void effect (EntityLivingBase elb, int tier) {

        elb.addPotionEffect(new PotionEffect(2, this.max, tier + 1));
        elb.addPotionEffect(new PotionEffect(9, this.max, 3));
        elb.addPotionEffect(new PotionEffect(18, this.max));

    }
}





