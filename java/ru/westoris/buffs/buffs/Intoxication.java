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

public class Intoxication extends Buff {
    public void checkBuff(EntityLivingBase entityLivingBase, ActiveBuff buffs){
        //
    }
    public Intoxication(BuffsLivingBase eEntityLiving, int tier, int duration) {
        eEntityLiving.addBuff(new ActiveBuff(Buff.intoxication.id, tier, duration));
    }
    public void effect (EntityLivingBase entityLivingBase, int tier, ActiveBuff buff, int duration){
        if (entityLivingBase instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer) entityLivingBase;
            BuffsPlayer ePlayer = BuffsPlayer.get(player);
            ChatComponentTranslation message;
            message = new ChatComponentTranslation("");
            if (tier == 1) {
                ePlayer.setPlayerHealth(player, ePlayer.getMaxHealth() - (tier), player.getHealth());
                player.addPotionEffect(new PotionEffect(9, tier * duration, 3));
                player.addPotionEffect(new PotionEffect(18, duration));
                message.getChatStyle().setColor(EnumChatFormatting.RED);
                message.appendSibling(new ChatComponentTranslation("buff.intoxication.light"));
            }
            if (tier == 2) {
                ePlayer.setPlayerHealth(player, ePlayer.getMaxHealth() - 5 * (tier), player.getHealth());
                player.addPotionEffect(new PotionEffect(2, 10000000, tier + 1));
                player.addPotionEffect(new PotionEffect(9, tier * 100, 3));
                player.addPotionEffect(new PotionEffect(18, tier * 10000));
                message.getChatStyle().setColor(EnumChatFormatting.RED);
                message.appendSibling(new ChatComponentTranslation("buff.intoxication.medium"));
            }
            if (tier == 3) {
                ePlayer.setPlayerHealth(player, ePlayer.getMaxHealth() - 5 * (tier), player.getHealth());
                player.addPotionEffect(new PotionEffect(2, 10000000, tier + 1));
                player.addPotionEffect(new PotionEffect(9, tier * 100, 3));
                player.addPotionEffect(new PotionEffect(18, tier * 10000));
                message.getChatStyle().setColor(EnumChatFormatting.RED);
                message.appendSibling(new ChatComponentTranslation("buff.intoxication.hard"));
            }
            if (tier > 3) {
                ePlayer.setPlayerHealth(player,  0, player.getHealth());
            }
        }
    }
}



