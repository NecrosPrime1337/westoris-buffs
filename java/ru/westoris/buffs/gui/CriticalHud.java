package ru.westoris.buffs.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import ru.westoris.buffs.main.ActiveBuff;
import ru.westoris.buffs.main.Buff;
import ru.westoris.buffs.player.BuffsPlayer;

public class CriticalHud {

    private static CriticalHud instance = new CriticalHud();
    private Minecraft mc = Minecraft.getMinecraft();
    private CriticalHud() {}

    protected static final ResourceLocation vignetteTexPath = new ResourceLocation("buffs:textures/gui/critoverlays/crit3.png");
    protected static final ResourceLocation BlurTexPath = new ResourceLocation("buffs:textures/gui/blur.png");
    protected static final String[] location={
            "buffs:textures/gui/critoverlays/crit1.png",
            "buffs:textures/gui/critoverlays/crit2.png",
            "buffs:textures/gui/critoverlays/crit3.png",
            "buffs:textures/gui/critoverlays/crit4.png",
            "buffs:textures/gui/critoverlays/crit5.png"
    };


    public static CriticalHud getInstance() {

        return instance;
    }
    private boolean reverse = false;
    private float index = 0.0F;
    private float count=0.0F;

    public void counter(int i, int j) {

        if (!this.reverse) {
            for (this.index = 0.01F; this.index <= 1.0F; this.index+=0.01F ) {
                this.count += this.index;
                this.renderVignette(1.0F, this.count, 0, i, j);
            }
            this.reverse = true;
        }
        else {
            for (this.index = 1.0F; this.index >= 0.01F; this.index-=0.01F ) {
                this.count = this.index;
                this.renderVignette(1.0F, this.count, 0, i, j);
            }
            this.reverse = false;
        }


    }
    private String getCurrentLocation() {
        return null;
    }
    public void renderHud() {

        // (this.mc.inGameHasFocus) {

        EntityPlayer player = this.mc.thePlayer;

        BuffsPlayer eLivingBase = BuffsPlayer.get(player);

        ScaledResolution scaledResolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);

        int
        i = scaledResolution.getScaledWidth(),
        j = scaledResolution.getScaledHeight();
        //counter = 0;
        if (eLivingBase.getCrit()) {
            //this.renderblur(1.0F, 0, 0, i, j);
            //this.counter(i,j);
            this.renderVignette(1.0F, 1.0F, 0, i, j);
        }
    }
    //}


    public float prevVignetteBrightness = 1.0F;
    protected void renderVignette(float p_73829_1_, float index, int index2, int i, int j )
    {
        p_73829_1_ = 1.0F - p_73829_1_;

        if (p_73829_1_ < 0.0F)
        {
            p_73829_1_ = 0.0F;
        }

        if (p_73829_1_ > 1.0F)
        {
            p_73829_1_ = 1.0F;
        }

        this.prevVignetteBrightness = (float)(this.prevVignetteBrightness + (p_73829_1_ - this.prevVignetteBrightness) * 0.01D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(0, 769, 1, 0);
        GL11.glColor4f(0.0F, 1.0F, 1.0F, 0.5F );
        //GL11.glDisable(GL11.GL_BLEND);
        float f = 1.0F / i; //width
        float f1 = 1.0F / j; //height
        this.mc.getTextureManager().bindTexture(vignetteTexPath);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(0.0D, j, -90.0D, 0.0D, 1.0D);
        tessellator.addVertexWithUV(i, j, -90.0D, 1.0D, 1.0D);
        tessellator.addVertexWithUV(i, 0.0D, -90.0D, 1.0D, 0.0D);
        tessellator.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
        //tessellator.setColorRGBA_F(index, 1.0F, 1.0F, 0F);
        tessellator.draw();

        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        //GL11.glDepthMask(true);
        //GL11.glEnable(GL11.GL_ALPHA_TEST);
        //GL11.glEnable(GL11.GL_BLEND);
        //GL11.glColor4f( 1.0F, 1.0F, 1.0F, 0.9F);
        //GL11.glColor4f( 0.9F, 0.9F, 0.9F, 0.8F);
        //GL11.glColor4f( 0.8F, 0.8F, 0.8F, 0.7F);
        //GL11.glColor4f( 0.7F, 0.7F, 0.7F, 0.6F);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        //GL11.glDisable(GL11.GL_BLEND);
        //OpenGlHelper.glBlendFunc(1000,1000,1000,1);

    }
    private int color;
    protected void renderblur(float p_73829_1_, float index, int index2, int i, int j )
    {
        p_73829_1_ = 1.0F - p_73829_1_;

        if (p_73829_1_ < 0.0F)
        {
            p_73829_1_ = 0.0F;
        }

        if (p_73829_1_ > 1.0F)
        {
            p_73829_1_ = 1.0F;
        }


        //this.prevVignetteBrightness = (float)(this.prevVignetteBrightness + (p_73829_1_ - this.prevVignetteBrightness) * 0.01D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);


        //GL11.glClearColor(0, 0, 0, 0);
        OpenGlHelper.glBlendFunc(769, 769, 769, 0);
        //GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.4F);
        float f = 1.0F / i; //width
        float f1 = 1.0F / j; //height
        this.mc.getTextureManager().bindTexture(BlurTexPath);
        GL11.glColorMask(false, true, true, false);
        //.glClearColor(0, 1, 1, 0);

        Tessellator tessellator = Tessellator.instance;
        // this.color = GL11.GL_CURRENT_COLOR;
        //GL11.glColor4d(this.color, this.color, this.color, 1);
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(0.0D, j, -90.0D, 0.0D, 1.0D);
        tessellator.addVertexWithUV(i, j, -90.0D, 1.0D, 1.0D);
        tessellator.addVertexWithUV(i, 0.0D, -90.0D, 1.0D, 0.0D);
        tessellator.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);

        tessellator.draw();

        /*OpenGlHelper.glBlendFunc(50, 500, 50, 0);
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glColor4f(0.9F, 0.9F, 0.9F, 1.0F);
        OpenGlHelper.glBlendFunc(0, 771, 1, 0);
         */
    }
}
