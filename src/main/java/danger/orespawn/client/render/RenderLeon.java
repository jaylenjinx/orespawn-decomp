package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelLeon;
import danger.orespawn.entity.Leon;

public class RenderLeon extends RenderLiving {

    protected ModelLeon model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "Leon.png");

    public RenderLeon(ModelLeon modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelLeon) this.mainModel;
        this.scale = scale2;
    }

    public void renderLeon(Leon entityLeon, double x, double y, double z, float yaw, float partialTicks) {
        super.doRender(entityLeon, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderLeon((Leon) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderLeon((Leon) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(Leon entity, float f) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((Leon) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
