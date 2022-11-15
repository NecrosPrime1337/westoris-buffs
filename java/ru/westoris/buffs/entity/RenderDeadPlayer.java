package ru.westoris.buffs.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderDeadPlayer extends RenderBiped {

    public RenderDeadPlayer(ModelBiped model, float shadowSize) {
        super(model, 0,5f);
        model.setRotationAngles(90, 90, 90, 0, 0, 0, null);
        //.doRender(ELB, 0d, 0d, 0d, 0f, 0f);
    }
    private static final ResourceLocation textureLocation = new ResourceLocation("textures/entity/steve.png");
    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return textureLocation;
    }
}
