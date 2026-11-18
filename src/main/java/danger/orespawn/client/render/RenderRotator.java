package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelRotator;
import danger.orespawn.entity.Rotator;

public class RenderRotator extends RenderLiving {

    protected ModelRotator model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "Rotatortexture.png");

    public RenderRotator(ModelRotator modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelRotator) this.mainModel;
        this.scale = scale2;
    }

    public void renderRotator(Rotator entityRotator, double x, double y, double z, float yaw, float partialTicks) {
        super.doRender(entityRotator, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderRotator((Rotator) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderRotator((Rotator) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(Rotator entity, float f) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((Rotator) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
