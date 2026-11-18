package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelTheQueen;
import danger.orespawn.entity.TheQueen;

public class RenderTheQueen extends RenderLiving {

    protected ModelTheQueen model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "TheQueentexture.png");
    private static final ResourceLocation texture2 = new ResourceLocation("orespawn", "TheQueentexture2.png");

    public RenderTheQueen(ModelTheQueen modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelTheQueen) this.mainModel;
        this.scale = scale2;
    }

    public void renderTheQueen(TheQueen entityTheQueen, double x, double y, double z, float yaw, float partialTicks) {
        super.doRender(entityTheQueen, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderTheQueen((TheQueen) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderTheQueen((TheQueen) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(TheQueen entity, float f) {
        if (entity != null && entity.getPlayNicely() != 0) {
            GL11.glScalef(this.scale / 4.0F, this.scale / 4.0F, this.scale / 4.0F);
        } else {
            GL11.glScalef(this.scale, this.scale, this.scale);
        }
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((TheQueen) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        TheQueen q = (TheQueen) entity;
        return q.isHappy() ? texture2 : texture;
    }
}
