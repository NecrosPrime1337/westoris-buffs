package ru.westoris.buffs.buffs;

import net.minecraft.entity.EntityLivingBase;
import ru.westoris.buffs.entity.BuffsLivingBase;
import ru.westoris.buffs.main.Buff;

public class Regeneration extends Buff {
    /*
     *  this buff is healing a player.
     * When entity have any active buff - buff is still started
     *
     */

    private boolean hasbonebreak, hasheadquake;
    private float scorchpercent, blood;
    public Regeneration(EntityLivingBase entity){
        BuffsLivingBase blb = BuffsLivingBase.get(entity);
        this.getter(blb);
    }

    private void getter(BuffsLivingBase blb) {
        if (blb.isBuffActive(bonebreak.id)) {
            this.hasbonebreak = true;
        }
        else {
            this.hasbonebreak = false;
        }
        if (blb.isBuffActive(headquake.id)) {
            this.hasheadquake = true;
        }
        else {
            this.hasheadquake = false;
        }

        blb.isBuffActive(asphyxia.id);
        blb.isBuffActive(scorch.id);
        blb.isBuffActive(headquake.id);
        blb.isBuffActive(bleeding.id);
    }
}
