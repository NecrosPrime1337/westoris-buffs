package ru.westoris.buffs.utils;
import net.minecraft.util.DamageSource;

public class DamageSources extends net.minecraft.util.DamageSource{

    public static DamageSource bleeding = (new DamageSource("bleeding")).setDamageBypassesArmor();
    public static DamageSource bonebreak = (new DamageSource("bonebreak")).setDamageBypassesArmor();
    public static DamageSource exsanguination = (new DamageSource("exsanguination")).setDamageBypassesArmor();
    public static DamageSource scorches = (new DamageSource("scorches")).setDamageBypassesArmor();
    public static DamageSource asphyxia = (new DamageSource("asphyxia")).setDamageBypassesArmor();
    public static DamageSource headquake = (new DamageSource("headquake")).setDamageBypassesArmor();
    public static DamageSource toxic = (new DamageSource("toxic")).setDamageBypassesArmor();
    public static DamageSource falling = (new DamageSource("falling")).setDamageBypassesArmor();
    public static DamageSource acp45 = (new DamageSource("45acp"));
    public static DamageSource acp45Fire = (new DamageSource("45acpFire"));
    public static DamageSource acp45ArmorPiercing = (new DamageSource("45acpArmorPiercing"));
    public static DamageSource acp45Buckshot = (new DamageSource("45acpBuckshot"));
    public static DamageSource acp45Dragonsbreath = (new DamageSource("45acpDragonsbreath"));
    public static DamageSource acp45Silver = (new DamageSource("45acpSilver"));
    public static DamageSource crushed = (new DamageSource("Crushed"));

    public DamageSources(String source) {
        super(source);
        // TODO Auto-generated constructor stub
    }

}
