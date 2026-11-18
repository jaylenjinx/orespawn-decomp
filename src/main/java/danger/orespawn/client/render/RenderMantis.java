package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelMantis;
import danger.orespawn.entity.Mantis;

public class RenderMantis extends RenderLiving {

    protected ModelMantis model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "Mantistexture.png");

    public RenderMantis(ModelMantis modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelMantis) this.mainModel;
        this.scale = scale2;
    }

    public void renderMantis(Mantis entityMantis, double x, double y, double z, float yaw, float partialTicks) {
        super.doRender(entityMantis, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderMantis((Mantis) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderMantis((Mantis) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(Mantis entity, float f) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((Mantis) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
