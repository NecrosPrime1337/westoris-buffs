package ru.westoris.buffs.events;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.eventhandler.EventPriority;

//import com.bioxx.tfc.Core.TFC_Core;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import ru.westoris.buffs.buffs.Asphyxia;
import ru.westoris.buffs.buffs.HeadQuake;
import ru.westoris.buffs.entity.BuffsLivingBase;
import ru.westoris.buffs.entity.DeadPlayer;
import ru.westoris.buffs.gui.BuffsOverlay;
import ru.westoris.buffs.gui.CriticalHud;
import ru.westoris.buffs.main.ActiveBuff;
import ru.westoris.buffs.main.Buff;
import ru.westoris.buffs.network.NetworkHandler;
import ru.westoris.buffs.network.SyncBuff;
import ru.westoris.buffs.network.SyncMaxHealth;
import ru.westoris.buffs.network.SyncTimer;
import ru.westoris.buffs.player.BuffsPlayer;
import ru.westoris.buffs.utils.DamageSources;
import scala.Int;

public class BuffsEvents{
    private int tier;
    private int duration = Int.MaxValue();
    private int chance; // chance to avoid buff formule = 1->100
    public int counter=1; // counter for updater
    private float amplifier=0; //amplifier for fall damage

    private static final Logger logger = LogManager.getLogger();

    @SubscribeEvent
    public void onEntityConstructing(EntityConstructing event) {

        if (event.entity instanceof EntityLivingBase) {

            if (BuffsLivingBase.get((EntityLivingBase) event.entity) == null) {

                BuffsLivingBase.register((EntityLivingBase) event.entity);
            }
        }

        if (event.entity instanceof EntityPlayer) {

            if (BuffsPlayer.get((EntityPlayer) event.entity) == null) {

                BuffsPlayer.register((EntityPlayer) event.entity);
            }
        }
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {

        if (event.entity instanceof EntityPlayer) {

            if (!event.entity.worldObj.isRemote) {

                EntityPlayer player = (EntityPlayer) event.entity;

                BuffsLivingBase eLivingBase = BuffsLivingBase.get(player);

                if (eLivingBase.haveActiveBuffs()) {

                    for (ActiveBuff buff : eLivingBase.activeBuffsCollection()) {

                        NetworkHandler.sendTo(new SyncBuff(buff), (EntityPlayerMP) player);
                    }
                }

                BuffsPlayer ePlayer = BuffsPlayer.get(player);//Дополнительные EEP игрока.

                //NetworkHandler.sendTo(new SyncMaxHealth(ePlayer.getMaxHealth()), (EntityPlayerMP) player);
            }
        }
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingUpdateEvent event) {

        // if (event.entityLiving instanceof EntityPlayerMP) {
        //    this.TimerUpdater(BuffsEvents.counter, (EntityPlayerMP) event.entityLiving);
        //}
        if (event.entityLiving instanceof EntityPlayer) {

            EntityPlayer ep = (EntityPlayer) event.entityLiving;
            //BuffsLivingBase blb = BuffsLivingBase.get(event.entityLiving);
            BuffsPlayer bp = BuffsPlayer.get(ep);
            if (bp.getScorching() > 0.0F && !bp.isBuffActive(Buff.scorch.id)) {
                bp.addBuff(new ActiveBuff(Buff.scorch.id, this.duration));
            }
            if (bp.isBuffActive(Buff.scorch.id) && bp.getScorching() == 0.0F) {
                bp.removeBuff(Buff.scorch.id);
            }
            if (bp.isBuffActive(Buff.scorch.id)) {
                bp.getScorchInfo(event.entityLiving, this.counter);  //this.counter - is a global timer for mod.
            }

            if (bp.getBleeding() > 0.0F && !bp.isBuffActive(Buff.bleeding.id)) {
                bp.addBuff(new ActiveBuff(Buff.bleeding.id, this.duration));
            }
            if (bp.isBuffActive(Buff.bleeding.id) && bp.getBleeding() == 0.0F) {
                bp.removeBuff(Buff.bleeding.id);
            }
            if (bp.isBuffActive(Buff.bleeding.id)) {
                bp.getBleedingInfo(event.entityLiving, this.counter);  //this.counter - is a global timer for mod.
            }
            if (bp.isBuffActive(Buff.headquake.id)) {
                //event.entity.setLocationAndAngles(ep.posX, ep.posY, ep.posZ, ep.prevCameraPitch+0.3F, ep.prevCameraYaw-0.3F);
            }

            bp.updateBuffs();
            if (this.counter == 100000) {this.counter = 0;}
            this.counter++;
        }


    }



    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {

        if (event.entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entity;

            if (!event.entityLiving.worldObj.isRemote) {

                BuffsPlayer.get(event.entityLiving).clearBuffs(true);
                EntityPlayer ep = (EntityPlayer) event.entity;

                //DeadPlayer dp = new DeadPlayer(ep, event.entityLiving.worldObj);
            }
            else {
                BuffsPlayer.get(event.entityLiving).clearBuffs(true);
                //BuffsPlayer ePlayer = BuffsPlayer.get(player);//Дополнительные EEP игрока.
                //NetworkHandler.sendTo(new SyncMaxHealth(ePlayer.getMaxHealth()), (EntityPlayerMP) player);
            }
        }


    }

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event) {

        BuffsLivingBase eLivingBase = BuffsLivingBase.get(event.entityPlayer);

        NBTTagCompound tagCompound = new NBTTagCompound();

        BuffsLivingBase.get(event.original).saveNBTData(tagCompound);
        eLivingBase.loadNBTData(tagCompound);

        BuffsPlayer ePlayer = BuffsPlayer.get(event.entityPlayer);

        ePlayer.setPlayerHealth(event.entityPlayer, ePlayer.getMaxHealth(), ePlayer.getMaxHealth());
    }

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {

        if (event.type == ElementType.CROSSHAIRS) {

            BuffsOverlay.getInstance().renderBuffs();
        }
    }

    @SubscribeEvent
    public void onJump(LivingJumpEvent event) {
        BuffsLivingBase blb = BuffsLivingBase.get(event.entityLiving);

        if (blb.isBuffActive(Buff.bonebreak.id)) {
            event.entity.attackEntityFrom(DamageSources.bonebreak, 60);
        }
    }

    @SubscribeEvent
    public void onRenderCritOverlay(RenderGameOverlayEvent.Post event) {

        //if (event.type == ElementType.CROSSHAIRS ) {
        CriticalHud.getInstance().renderHud();

        //}
    }

    @SubscribeEvent
    public void onFalling (LivingFallEvent event) {
        if (event.distance >= 3.0F) {
            this.chance = 2;
            if (event.distance >= 3.0F && event.distance < 8.0F) {

                this.tier = 1;
                this.duration = 36000;
                this.chance = 7;
                this.amplifier = 1;

            }
            else if (event.distance >= 8.0F && event.distance < 24.0F) {

                this.tier = 2;
                this.duration = 36000;
                this.chance = 30;
                this.amplifier = 2;
            }
            else if (event.distance >= 24.0F && event.distance < 42.0F) {

                this.tier = 3;
                this.duration = 36000;
                this.chance = 1000;
                this.amplifier = 1;
            }
            else {
                this.tier = 10;
                this.duration = 1000;
                this.chance = 100000000; //not bug.
            }


            //logger.info("falling! distance "+ event.distance);
            // BuffsLivingBase eEntityLiving = BuffsLivingBase.get((EntityLivingBase) event.entity);
            EntityLivingBase entityLivingBase = (EntityLivingBase) event.entity;
            float healthMod = entityLivingBase.getMaxHealth() / 1000;
            float ammount = 80*healthMod;
            //entityLivingBase.attackEntityFrom(DamageSources.falling, ammount*event.distance*this.amplifier);
            //logger.info("fall dmg "+ ammount*this.amplifier+this.amplifier*event.distance);
            //logger.info("fall tier "+ this.tier);
            if (random.randomize(this.chance) || this.tier == 10) {

                //BoneBreak bb = new BoneBreak(eEntityLiving, this.tier, this.duration);

                if (entityLivingBase instanceof EntityPlayer) {
                    BuffsPlayer bp = BuffsPlayer.get((EntityPlayer) event.entity);
                    bp.addBuff(new ActiveBuff(Buff.bonebreak.id, this.tier, this.duration));
                }
                else {
                    BuffsLivingBase blb = BuffsLivingBase.get((EntityLivingBase) event.entity);
                    blb.addBuff(new ActiveBuff(Buff.bonebreak.id, this.tier, this.duration));
                }
            }

        }
    }



    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onHealthUpdate(LivingUpdateEvent event) {

        BuffsLivingBase blb = BuffsLivingBase.get(event.entityLiving);
        if (event.entityLiving instanceof EntityPlayer) {
            BuffsPlayer player = BuffsPlayer.get((EntityPlayer) event.entityLiving);
            player.Crit((EntityPlayer) event.entityLiving, this.counter);
            //this.counter++;
        }
        else if (event.entityLiving instanceof EntityOtherPlayerMP) {
            //BuffsPlayer player = BuffsPlayer.get((EntityPlayer) event.entityLiving);
            //player.CritOther((EntityOtherPlayerMP) event.entityLiving, this.counter);
            //this.counter++;
        }


    }




    @SubscribeEvent

    public void onHurt (LivingHurtEvent event) {
        logger.info("damage type: "+ event.source.getDamageType()+"|| ammount "+ event.ammount);
        BuffsLivingBase blb = BuffsLivingBase.get((EntityLivingBase) event.entity);
        EntityLivingBase elb = (EntityLivingBase) event.entity;
        this.tier = 1;
        this.duration = 36000;
        if (event.source == DamageSource.inFire || event.source == DamageSource.onFire) {

            blb.addScorching(3F); //to-do random scorching
            elb.attackEntityFrom(DamageSources.scorches, 50); //to-do random damage from 20 to 100
            event.setCanceled(true);
        }

        else if (event.source.getDamageType() =="drown") {
            this.duration = 150;
            blb.addBuff(new ActiveBuff(Buff.asphyxia.id, this.tier, this.duration));
        }

        else if (event.source.getDamageType() == "arrow" ) {

            blb.addBleeding(0.04F);
            if (random.randomize(5)) {
                blb.addBuff(new ActiveBuff(Buff.bonebreak.id, this.tier, this.duration));
            }
            else if (random.randomize(5)) {
            }
        }
        else if (event.source.getDamageType() == "mob") {


            if (random.randomize(5)) {

                blb.addBuff(new ActiveBuff(Buff.bonebreak.id, this.tier, this.duration));
            }
            else if (random.randomize(4)) {

                blb.addBleeding(0.03F);
            }
            else if (random.randomize(5)) {
                blb.addBuff(new ActiveBuff(Buff.headquake.id, 1, 3000));
            }
            event.ammount = event.ammount/5;

        }
        //section revolver for immersive enginering
        else if (event.source.getDamageType() =="ieRevolver_casull") {
            if (random.randomize(20)){

                blb.addBuff(new ActiveBuff(Buff.bonebreak.id, this.tier, this.duration));
            }
            elb.attackEntityFrom(DamageSources.acp45, event.ammount*40);

            if (random.randomize(4)) {
                blb.addBleeding(0.04F);
            }
        }

        else if (event.source.getDamageType() =="ieRevolver_armorPiercing") {
            if (random.randomize(2)){

                blb.addBuff(new ActiveBuff(Buff.bonebreak.id, this.tier, this.duration));
            }
            if (random.randomize(2)) {
                blb.addBleeding(0.04F);
            }
            elb.attackEntityFrom(DamageSources.acp45ArmorPiercing, event.ammount*45);
        }
        else if (event.source.getDamageType() =="ieRevolver_buckshot") {
            if (random.randomize(200)){

                blb.addBuff(new ActiveBuff(Buff.bonebreak.id, this.tier, this.duration));
            }
            blb.addBleeding(0.09F);
            elb.attackEntityFrom(DamageSources.acp45Buckshot, event.ammount*10);
        }
        else if (event.source.getDamageType() =="ieRevolver_dragonsbreath") {
            if (random.randomize(20000)){

                blb.addBuff(new ActiveBuff(Buff.bonebreak.id, this.tier, this.duration));
            }

            elb.attackEntityFrom(DamageSources.acp45Dragonsbreath, event.ammount*10);
            //bleeding chance 10% to-do
            if (random.randomize(10)){
                blb.addScorching(5F);
            }
        }
        else if (event.source.getDamageType() =="ieRevolver_silver") {
            if (random.randomize(10)){
                blb.addBuff(new ActiveBuff(Buff.bonebreak.id, this.tier, this.duration));
            }
            elb.attackEntityFrom(DamageSources.acp45Silver, event.ammount*40);
            if (random.randomize(10)) {
                blb.addBleeding(0.03F);
            }
        }
        else if (event.source.getDamageType() =="ieCrushed") {
            this.tier = 3;
            BuffsLivingBase eEntityLiving = BuffsLivingBase.get((EntityLivingBase) event.entity);
            eEntityLiving.addBuff(new ActiveBuff(Buff.bonebreak.id, this.tier, this.duration));
            elb.attackEntityFrom(DamageSources.crushed, event.ammount*80);
            event.setCanceled(true);
        }
        else if (event.source.getDamageType() == "CRUSHING") {
            this.tier = 1;
            logger.info("crushing! "+ event.ammount);
            this.duration = 36000;
            if (random.randomize(5)) {
                BuffsLivingBase eEntityLiving = BuffsLivingBase.get((EntityLivingBase) event.entity);
                //BoneBreak bb = new BoneBreak(eEntityLiving, this.tier, this.duration);
                EntityLivingBase entityLivingBase = (EntityLivingBase) event.entity;
                //bb.effect(entityLivingBase, this.tier, this.duration);
            }
            else if (random.randomize(5)) {

            }
        }

        //BuffsLivingBase eEntityLiving = BuffsLivingBase.get((EntityLivingBase) event.entity);
        //eEntityLiving.addBuff(new ActiveBuff(Buff.scorch.id, 1, 100));
    }
    @SideOnly(Side.CLIENT)
    public void setCounter(int count) { //timer request
        this.updCounter(count);
    }

    private void updCounter(int count) {

    }


    @SideOnly(Side.SERVER)
    public void TimerUpdater(int counter, EntityPlayerMP player){
        // NetworkHandler.sendTo(new SyncTimer(this.counter), player);
        //NetworkHandler.sendTo(new SyncMaxHealth(ePlayer.getMaxHealth()), (EntityPlayerMP) player);
    }



    @SubscribeEvent
    public void onPlayerAttack(AttackEntityEvent event) {

        if (!event.entity.worldObj.isRemote) {

            if (event.target instanceof EntityLivingBase) {

                if (event.entityPlayer.getCurrentEquippedItem() != null && event.entityPlayer.getCurrentEquippedItem().getItem() == Items.rotten_flesh) {

                    BuffsLivingBase eEntityLiving = BuffsLivingBase.get((EntityLivingBase) event.target);

                    eEntityLiving.addBuff(new ActiveBuff(Buff.intoxication.id, 100));
                }
            }
        }
    }
}
