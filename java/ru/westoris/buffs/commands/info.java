package ru.westoris.buffs.commands;

import java.awt.TextComponent;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import ru.westoris.buffs.entity.BuffsLivingBase;
import ru.westoris.buffs.player.BuffsPlayer;

public class info extends CommandBase{

    private static String NAME = "stats", USAGE = "/stats" ;
    @Override
    public String getCommandName() {
        // TODO Auto-generated method stub
        return NAME;
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        // TODO Auto-generated method stub
        return USAGE;
    }
    @Override
    public boolean canCommandSenderUseCommand(ICommandSender commandSender) {

        //“олько опам или если в мире активны читы.
        //return commandSender instanceof EntityPlayer ? MinecraftServer.getServer().getConfigurationManager().func_152596_g(((EntityPlayer) commandSender).getGameProfile()) : true;
        return true;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {

        if (sender instanceof EntityPlayerMP) {
            EntityPlayerMP epmp = getCommandSenderAsPlayer (sender);
            BuffsPlayer ePlayer =  BuffsPlayer.get(epmp);
            ChatComponentTranslation cm1;
            ChatComponentTranslation cm2;
            ChatComponentTranslation cm11;
            ChatComponentTranslation cm21;
            cm1 = new ChatComponentTranslation("westoris.buff.scorching.percent");
            cm2 = new ChatComponentTranslation("westoris.buff.litres.percent");
            cm11 = new ChatComponentTranslation(" = " + String.valueOf(ePlayer.getScorching()));
            cm21 = new ChatComponentTranslation(" = " + String.valueOf(ePlayer.getLitres()));
            //ChatComponentTranslation scp = new ChatComponentTranslation(" <" + ePlayer.getScorching() + "> ");
            cm1.appendSibling( cm11);
            cm2.appendSibling(cm21);

            sender.addChatMessage(cm1);
            sender.addChatMessage(cm2);
        }

    }

}
