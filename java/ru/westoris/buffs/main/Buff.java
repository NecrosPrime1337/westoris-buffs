package ru.westoris.buffs.main;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import ru.westoris.buffs.buffs.Asphyxia;
import ru.westoris.buffs.buffs.Bleeding;
import ru.westoris.buffs.buffs.BoneBreak;
import ru.westoris.buffs.buffs.Scorch;
import ru.westoris.buffs.entity.BuffsLivingBase;
import ru.westoris.buffs.network.NetworkHandler;
import ru.westoris.buffs.network.SetStepHeight;
import ru.westoris.buffs.player.BuffsPlayer;

public class Buff {

    public final int id;

    private String name;

    private int iconIndex;

    private boolean keepOnDeath, isPersistent;

    public static List<Buff> buffs = new ArrayList<Buff>();

    public static final Buff
    //potionCooldown = new Buff().setName("buff.Cooldown").setIconIndex(0, 0),
    asphyxia = new Buff().setName("westoris.buffs.asphyxia").setIconIndex(2, 0),
    bonebreak = new Buff().setName("westoris.buffs.bonebreak").setIconIndex(3, 0),
    scorch = new Buff().setName("westoris.buffs.scorch").setIconIndex(4, 0),
    intoxication = new Buff().setName("westoris.buffs.intoxication").setIconIndex(5, 0),
    bleeding = new Buff().setName("westoris.buffs.bleeding").setIconIndex(1, 0),
    headquake = new Buff().setName("westoris.buffs.headquake").setIconIndex(6, 0);
    //exsanguination = new Buff().setName("westoris.buffs.exsanguination").setIconIndex(0, 0);


    public Buff() {

        this.id = buffs.size();

        Buff.buffs.add(this);
    }

    public static Buff of(int buffId) {

        return buffs.get(buffId);
    }

    protected void onActive(EntityLivingBase entityLivingBase, World world, ActiveBuff buff) {

        int
        tier = buff.getTier(),
        duration = buff.getDuration();

        if (buff.getId() == asphyxia.id) {

            if (tier == 0) {

                entityLivingBase.heal(-1.0F);
            } else if (tier == 1) {

                entityLivingBase.heal(-1.5F);
            } else if (tier == 2) {

                entityLivingBase.heal(-1.5F);
            }
        } else if (this.id == scorch.id) {

            if (entityLivingBase instanceof EntityPlayer) {

                if (world.isRemote) {

                    if (entityLivingBase.stepHeight != 1.0F) {

                        entityLivingBase.stepHeight = 1.0F;
                    }
                }
            }
        } else if (this.id == intoxication.id) {

            if (entityLivingBase.getHealth() > 1.0F) {

                entityLivingBase.attackEntityFrom(DamageSource.magic, 1.0F);
            }
        }
    }

    protected boolean isReady(ActiveBuff buff) {

        int
        tier = buff.getTier(),
        duration = buff.getDuration();

        if (this.id == asphyxia.id) {

            int k = 0;

            if (tier == 0) {

                k = 40;
            } else if (tier == 1) {

                k = 30;
            } else if (tier == 2) {

                k = 20;
            }

            return duration % k == 0;
        } else if (this.id == scorch.id)
            return true;
        else if (this.id == intoxication.id)
            return true;
        else if (this.id == bonebreak.id)
            return true;
        else if (this.id == headquake.id)
            return true;
        else if (this.id == bleeding.id)
            return true;

        return false;
    }

    public void applyBuffEffect(EntityLivingBase elb,  World world, ActiveBuff buff) {

        //if (!world.isRemote) {
        int
        id = buff.getId(),
        tier = buff.getTier();


        if (id == bonebreak.id) {
            BoneBreak bb = new BoneBreak(elb, tier, 9999999);
            //NetworkHandler.sendTo(new SetStepHeight(true), (EntityPlayerMP) entityLivingBase);
        }
        else if (id == bleeding.id){
            //entityLivingBase.setHealth(entityLivingBase.getHealth()-100);
            Bleeding bl = new Bleeding(elb, tier);
        }
        else if (id == scorch.id){
            //entityLivingBase.setHealth(entityLivingBase.getHealth()-100);
            Scorch sc  = new Scorch(elb, 9999999);
        }
        else if (id == asphyxia.id){
            //entityLivingBase.setHealth(entityLivingBase.getHealth()-100);
            Asphyxia as = new Asphyxia(elb, tier, 9999999);
        }
        //}
    }


    public void removeBuffEffect(EntityLivingBase elb, World world, ActiveBuff buff)
    {

        //if (!world.isRemote) {

        int
        id = buff.getId(),
        tier = buff.getTier();

        if (id == bonebreak.id) {
            elb.removePotionEffect(2);
            elb.removePotionEffect(18);

        }
        if (id == scorch.id) {
            //ePlayer.setPlayerHealth(player, ePlayer.getMaxHealth() + 1, player.getHealth());
            elb.removePotionEffect(2);
            elb.removePotionEffect(18);
        }
        if (this.id == asphyxia.id) {
            elb.removePotionEffect(2);
            elb.removePotionEffect(18);
        }

        else if (this.id == headquake.id)  {

        }
        else if (this.id == bleeding.id)  {
            elb.removePotionEffect(18);
            elb.removePotionEffect(2);
            elb.removePotionEffect(9);
        }
        //NetworkHandler.sendTo(new SetStepHeight(false), (EntityPlayerMP) entityLivingBase);
        //}

    }





    protected Buff setName(String name) {

        this.name = name;

        return this;
    }

    public String getName() {

        return this.name;
    }

    protected Buff setIconIndex(int x, int y) {

        this.iconIndex = x + y * 8;

        return this;
    }

    public int getIconIndex() {

        return this.iconIndex;
    }

    protected Buff keepOnDeath() {

        this.keepOnDeath = true;

        return this;
    }

    public boolean shouldKeepOnDeath() {

        return this.keepOnDeath;
    }

    protected Buff setPersistent() {

        this.isPersistent = true;

        return this;
    }

    public boolean isPersistent() {

        return this.isPersistent;
    }

    public String getDurationForDisplay(ActiveBuff buff) {

        int
        i = buff.getDuration(),
        j = i / 20,
        k = j / 60;

        j %= 60;

        return Buff.of(buff.getId()).isPersistent ? "-:-" : j < 10 ? String.valueOf(k) + ":0" + String.valueOf(j) : String.valueOf(k) + ":" + String.valueOf(j);
    }
}
