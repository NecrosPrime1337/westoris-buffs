package ru.westoris.buffs.buffs;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import ru.westoris.buffs.entity.BuffsLivingBase;
import ru.westoris.buffs.main.ActiveBuff;
import ru.westoris.buffs.main.Buff;
import ru.westoris.buffs.player.BuffsPlayer;

public class Scorch extends Buff {
    public void checkBuff(EntityLivingBase entityLivingBase, ActiveBuff buffs){
        //
    }
    private int tier;
    //private float percent; //percent of damage from scorch. 80% is fatally
    public Scorch(EntityLivingBase entityLivingBase, int duration) {
        BuffsLivingBase blb = BuffsLivingBase.get(entityLivingBase);

        if (blb.getScorching() < 1.0F)
        {
            this.tier = 1;
        }
        else if(blb.getScorching() < 20.0F) {
            this.tier = 2;
        }
        else if(blb.getScorching() < 50.0F) {
            this.tier = 3;
        }
        else if (blb.getScorching() < 80.0F) {
            this.tier = 4;
        }
        else {
            this.tier = 10;
        }

        this.effect(entityLivingBase,this.tier, 1000);

    }
    //this.effect(entityLivingBase, this.tier, duration);


    private void effect (EntityLivingBase entityLivingBase, int tier, int duration){
        if (entityLivingBase instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer) entityLivingBase;
            BuffsPlayer ePlayer = BuffsPlayer.get(player);
            BuffsLivingBase blb = BuffsLivingBase.get(entityLivingBase);
            ChatComponentTranslation message;
            message = new ChatComponentTranslation("");
            if (tier == 1) {
                //ePlayer.setPlayerHealth(player, ePlayer.getMaxHealth() - 5 * (tier), player.getHealth());
                //player.setVelocity(0.5, 0.5, 0.5);
                //entityLivingBase.setSneaking(true);
                //player.addPotionEffect(new PotionEffect(2, 10000, tier + 1));
                //player.addPotionEffect(new PotionEffect(9, tier * 100, 3));
                player.addPotionEffect(new PotionEffect(18, 360));
                message.getChatStyle().setColor(EnumChatFormatting.RED);
                message.appendSibling(new ChatComponentTranslation("buff.scorch.light"));
            }
            else if (tier == 2) {
                //ePlayer.setPlayerHealth(player, ePlayer.getMaxHealth() - 5 * (tier), player.getHealth());
                //player.setVelocity(0.5, 0.5, 0.5);
                //player.addPotionEffect(new PotionEffect(2, 10000000, tier + 1));
                player.addPotionEffect(new PotionEffect(9, 300, 3));
                player.addPotionEffect(new PotionEffect(18, 400));
                message.getChatStyle().setColor(EnumChatFormatting.RED);
                message.appendSibling(new ChatComponentTranslation("buff.scorch.medium"));
            }
            else if (tier == 3) {
                //ePlayer.setPlayerHealth(player, ePlayer.getMaxHealth() - 5 * (tier), player.getHealth());
                //player.setVelocity(0.5, 0.5, 0.5);
                player.addPotionEffect(new PotionEffect(2, 0, tier + 1));
                player.addPotionEffect(new PotionEffect(9, tier * 100, 3));
                player.addPotionEffect(new PotionEffect(18, tier * 10000));

                message.getChatStyle().setColor(EnumChatFormatting.RED);
                message.appendSibling(new ChatComponentTranslation("buff.scorch.hard"));
            }
            else if (tier == 4) {
                //ePlayer.setPlayerHealth(player, ePlayer.getMaxHealth() - 5 * (tier), player.getHealth());
                //player.setVelocity(0.5, 0.5, 0.5);
                player.addPotionEffect(new PotionEffect(2, 10000000, tier + 1));
                player.addPotionEffect(new PotionEffect(9, tier * 100, 3));
                player.addPotionEffect(new PotionEffect(18, tier * 10000));

                message.getChatStyle().setColor(EnumChatFormatting.RED);
                message.appendSibling(new ChatComponentTranslation("buff.scorch.hardest"));
            }
            else if (tier > 4) {
                ePlayer.setPlayerHealth(player,  0, player.getHealth());
            }
            message.appendSibling(new ChatComponentTranslation("buff.scorch.percent "+ blb.getScorching()));
            player.addChatMessage(message);

        }
    }
}



