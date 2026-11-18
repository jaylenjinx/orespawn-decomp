package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelEnderReaper;
import danger.orespawn.entity.EnderReaper;

public class RenderEnderReaper extends RenderLiving {

    protected ModelEnderReaper model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "EnderReapertexture.png");

    public RenderEnderReaper(ModelEnderReaper modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelEnderReaper) this.mainModel;
        this.scale = scale2;
    }

    public void renderEnderReaper(EnderReaper entityEnderReaper, double x, double y, double z, float yaw,
        float partialTicks) {
        super.doRender(entityEnderReaper, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderEnderReaper((EnderReaper) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderEnderReaper((EnderReaper) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(EnderReaper entity, float f) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((EnderReaper) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
