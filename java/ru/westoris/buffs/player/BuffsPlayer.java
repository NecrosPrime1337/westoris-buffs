package ru.westoris.buffs.player;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.MinecraftForge;
import ru.westoris.buffs.entity.BuffsLivingBase;
import ru.westoris.buffs.main.Buff;

public class BuffsPlayer extends BuffsLivingBase implements IExtendedEntityProperties  {

    public final static String EEP_NAME = "BuffsPlayerEEP";


    private EntityPlayer player;

    private World world;
    /*

    private float health;
    private float agility;
    private float strength;
    private float litres;
    private float scorches;
    private float bleeding;
     */
    private boolean crit = false;

    private static final Logger logger = LogManager.getLogger();
    public BuffsPlayer() {
        super();
        this.maxHealth = 1000F;
        this.agility = 10.0F;
        this.strength = 1.0F;
        this.litres = 5.0F;
        this.scorches = 0.0F;
        this.bleeding = 0.0F;
    }



    public static final void register(EntityPlayer player) {

        player.registerExtendedProperties(BuffsPlayer.EEP_NAME, new BuffsPlayer());
    }

    public static final BuffsPlayer get(EntityPlayer player) {

        return (BuffsPlayer) player.getExtendedProperties(EEP_NAME);
    }

    @Override
    public void saveNBTData(NBTTagCompound mainCompound) {

        NBTTagCompound buffsCompound = new NBTTagCompound();
        NBTTagCompound scorches = new NBTTagCompound();
        NBTTagCompound agility = new NBTTagCompound();
        NBTTagCompound strength = new NBTTagCompound();
        NBTTagCompound litres = new NBTTagCompound();
        NBTTagCompound bleeding = new NBTTagCompound();

        mainCompound.setTag(EEP_NAME, buffsCompound);
        buffsCompound.setFloat("maxHealth", this.maxHealth);
        mainCompound.setTag(EEP_NAME, scorches);
        scorches.setFloat("scorches", this.scorches);
        mainCompound.setTag(EEP_NAME, agility);
        agility.setFloat("agility", this.agility);
        mainCompound.setTag(EEP_NAME, strength);
        strength.setFloat("strength", this.strength);
        mainCompound.setTag(EEP_NAME, litres);
        litres.setFloat("litres", this.litres);
        mainCompound.setTag(EEP_NAME, bleeding);
        bleeding.setFloat("bleeding", this.bleeding);
    }

    @Override
    public void loadNBTData(NBTTagCompound mainCompound) {

        NBTTagCompound buffsCompound = (NBTTagCompound) mainCompound.getTag(EEP_NAME);
        NBTTagCompound scorches = (NBTTagCompound) mainCompound.getTag(EEP_NAME);
        NBTTagCompound agility = (NBTTagCompound) mainCompound.getTag(EEP_NAME);
        NBTTagCompound strength = (NBTTagCompound) mainCompound.getTag(EEP_NAME);
        NBTTagCompound litres = (NBTTagCompound) mainCompound.getTag(EEP_NAME);
        NBTTagCompound bleeding = (NBTTagCompound) mainCompound.getTag(EEP_NAME);

        this.maxHealth = buffsCompound.getFloat("maxHealth");
        this.scorches = scorches.getFloat("scorches");
        this.agility = agility.getFloat("agility");
        this.strength = strength.getFloat("strength");
        this.litres = litres.getFloat("litres");
        this.bleeding = litres.getFloat("bleeding");
    }

    @Override
    public void init(Entity entity, World world) {

        this.player = (EntityPlayer) entity;

        this.world = world;
    }

    public EntityPlayer getPlayer() {

        return this.player;
    }

    @Override
    public World getWorld() {

        return this.world;
    }

    public void setPlayerHealth(EntityPlayer player, float maxHealth, float startHealth) {

        player.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(maxHealth);
        player.setHealth(startHealth);

        this.maxHealth = maxHealth;
    }

    public void setMaxHealth(float maxHealth) {

        this.maxHealth = maxHealth;
    }

    public float getMaxHealth() {

        return this.maxHealth;
    }
    public boolean getCrit() {
        return this.crit;
    }
    public float getHealth(EntityPlayer player) {

        this.health = player.getHealth();
        //ChatComponentTranslation message;
        //message = new ChatComponentTranslation("");

        if (this.health < 150.0) {
            this.crit = true;
            //player.mountEntity(player);

        } else {
            this.crit = false;
        }
        //message.appendSibling(new ChatComponentTranslation("trigger: "+(this.crit)));
        //player.addChatMessage(message);
        return this.health;
    }

    @SideOnly(Side.CLIENT)
    public void Crit(EntityPlayer entityLiving, int counter) {
        //if (!entityLiving.worldObj.isRemote) {
        this.getHealth(entityLiving);
        if (this.crit) {
            //entityLiving.setLocationAndAngles(entityLiving.prevPosX, entityLiving.prevPosY, entityLiving.prevPosZ, entityLiving.prevCameraYaw, entityLiving.prevCameraPitch-2F);
            if (counter % 30 == 0) {
                entityLiving.playSound("buffs:heartbeat", 1, 1);
            }
        }
    }

    public boolean getCritical() {
        return this.crit;
    }

    public boolean setCritical(boolean value) {
        this.crit = value;
        return this.crit;
    }
    /*
     *
     * blood section
     *
     */
    @Override
    public float getLitres() {
        if (this.litres >= 2.9) {
            this.crit = false;
        }
        else {this.crit = true;}
        return this.litres;
    }
    @Override
    public void getBleedingInfo(EntityLivingBase elb, int timer) {
        BuffsPlayer blb = (BuffsPlayer) BuffsPlayer.get(elb);

        if (timer % 4000 == 0) {
            if (blb.isBuffActive(Buff.bleeding.id)) {
                //message.appendSibling(new ChatComponentTranslation("buff.scorch.percent "+blb.getScorching()));
                blb.addLitres(-0.5F);
            }
            // increment litres of blood 0.5 every 20 seconds. pseudo-regeneration, her mother =)
        }
    }

    @Override
    public void getScorchInfo(EntityLivingBase elb, int timer) {
        BuffsPlayer blb = (BuffsPlayer) BuffsLivingBase.get(elb);

        // ChatComponentTranslation message;
        //message = new ChatComponentTranslation("");
        if (timer % 4000 == 0) {
            if (blb.isBuffActive(Buff.scorch.id)) {
                //message.appendSibling(new ChatComponentTranslation("buff.scorch.percent "+blb.getScorching()));
                blb.addScorching(-0.5F);
            }
            // decrement level of scorches 0.01 for 2 seconds. pseudo-regeneration, her mother =)
        }
    }
}




