package ru.westoris.buffs.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import ru.westoris.buffs.entity.BuffsLivingBase;
import ru.westoris.buffs.main.ActiveBuff;
import ru.westoris.buffs.main.Buff;
import ru.westoris.buffs.main.BuffsMain;

public class BuffsOverlay {

    private static BuffsOverlay instance = new BuffsOverlay();

    private Minecraft mc = Minecraft.getMinecraft();

    private static final ResourceLocation buffIcons = new ResourceLocation(BuffsMain.MODID, "textures/gui/critOverlay.png");

    private BuffsOverlay() {}

    public static BuffsOverlay getInstance() {

        return instance;
    }

    public void renderBuffs() {

        //if (this.mc.inGameHasFocus) {

        EntityPlayer player = this.mc.thePlayer;

        BuffsLivingBase eLivingBase = BuffsLivingBase.get(player);

        ScaledResolution scaledResolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);

        int
        i = scaledResolution.getScaledWidth() / 2 + 240,
        j = scaledResolution.getScaledHeight() - 30,
        counter = 0,
        index = 0;

        if (eLivingBase.haveActiveBuffs()) {

            for (ActiveBuff buff : eLivingBase.activeBuffsCollection()) {

                index = Buff.of(buff.getId()).getIconIndex();

                counter++;

                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glEnable(GL11.GL_BLEND);

                GL11.glPushMatrix();

                GL11.glTranslatef(i + 5, j + 25 - 24 * counter, 0.0F);
                GL11.glScalef(0.5F, 0.5F, 0.5F);

                this.mc.getTextureManager().bindTexture(buffIcons);
                this.drawTexturedRect(0, 0, index % 8 * 32, index / 8 * 32, 32, 32, 224, 32);

                GL11.glPopMatrix();

                GL11.glPushMatrix();

                GL11.glTranslatef(i + 15, j + 42 - 24 * counter, 0.0F);
                GL11.glScalef(0.7F, 0.7F, 0.7F);

                int durLength = Buff.of(buff.getId()).getDurationForDisplay(buff).length();

                this.mc.fontRenderer.drawStringWithShadow(Buff.of(buff.getId()).getDurationForDisplay(buff), - durLength * 3, 0, 8421504);

                String tier = "";

                if (buff.getTier() == 0) {

                    tier = "I";
                }

                else if (buff.getTier() == 1) {

                    tier = "II";
                }

                else if (buff.getTier() == 2) {

                    tier = "III";
                }

                this.mc.fontRenderer.drawStringWithShadow(tier, 7 - tier.length() * 3, - 23, 8421504);

                GL11.glPopMatrix();

                GL11.glDisable(GL11.GL_BLEND);
                GL11.glEnable(GL11.GL_LIGHTING);
            }
        }
    }
    //}

    private void drawTexturedRect(int x, int y, int u, int v, int width, int height, int textureWidth, int textureHeight) {

        float f = 1.0F / textureWidth;
        float f1 = 1.0F / textureHeight;

        Tessellator tessellator = Tessellator.instance;

        tessellator.startDrawingQuads();

        tessellator.addVertexWithUV((x), y + height, 0, (u) * f, (v + height) * f1);
        tessellator.addVertexWithUV(x + width, y + height, 0, (u + width) * f, (v + height) * f1);
        tessellator.addVertexWithUV(x + width, (y), 0, (u + width) * f, (v) * f1);
        tessellator.addVertexWithUV((x), (y), 0, (u) * f, (v) * f1);

        tessellator.draw();
    }
}
