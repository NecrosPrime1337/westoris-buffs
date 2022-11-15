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

public class Bleeding extends Buff {


    private int tier;
    private boolean e1,e2,e3 = true;
    public void checkBuff(EntityLivingBase entityLivingBase, ActiveBuff buffs){
        //
    }

    public Bleeding (EntityLivingBase entity, int tier) {
        if (entity instanceof EntityPlayer) {
            this.playerEffect(entity, tier, 999999);
        }
        //else this.effect(entity, tier, 999999);
    }

    public void playerEffect (EntityLivingBase elb, int tier, int duration){
        if (elb instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer) elb;
            BuffsPlayer ePlayer = BuffsPlayer.get(player);
            ChatComponentTranslation message;
            message = new ChatComponentTranslation("");
            message.getChatStyle().setColor(EnumChatFormatting.RED);
            if (tier == 1) {
                this.e3 = false;
                message.appendSibling(new ChatComponentTranslation("westoris.buffs.bleeding.light"));
            }
            else if (tier == 2) {
                message.appendSibling(new ChatComponentTranslation("westoris.buffs.bleeding.medium"));
            }
            else if (tier == 3) {

                message.appendSibling(new ChatComponentTranslation("westoris.buffs.bleeding.hard"));
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

    }
    public void effect (EntityLivingBase elb, int tier, int duration){


        tier++;
        if (tier == 1) {
            elb.addPotionEffect(new PotionEffect(2, duration, tier + 1));
            elb.addPotionEffect(new PotionEffect(18, tier * 1000));
        }
        if (tier == 2) {
            elb.addPotionEffect(new PotionEffect(2, duration, tier + 1));
            elb.addPotionEffect(new PotionEffect(18, tier * 1000));
        }
        if (tier == 3) {
            elb.addPotionEffect(new PotionEffect(2, duration, tier + 1));
            elb.addPotionEffect(new PotionEffect(18, tier * 10000));


        }
        if (tier > 3) {
            elb.setHealth(0);
        }
    }

}



