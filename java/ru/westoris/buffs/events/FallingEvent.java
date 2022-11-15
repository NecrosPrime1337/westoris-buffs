package ru.westoris.buffs.events;

import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingFallEvent;

public class FallingEvent extends LivingFallEvent {

    public FallingEvent(EntityLivingBase entity, float distance) {
        super(entity, distance);


        // TODO Auto-generated constructor stub
    }

}
