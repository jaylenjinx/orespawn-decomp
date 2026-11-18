package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelCloudShark;
import danger.orespawn.entity.CloudShark;

public class RenderCloudShark extends RenderLiving {

    protected ModelCloudShark model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "CloudShark.png");

    public RenderCloudShark(ModelCloudShark modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelCloudShark) this.mainModel;
        this.scale = scale2;
    }

    public void renderCloudShark(CloudShark entityCloudShark, double x, double y, double z, float yaw,
        float partialTicks) {
        super.doRender(entityCloudShark, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderCloudShark((CloudShark) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderCloudShark((CloudShark) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(CloudShark entity, float f) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((CloudShark) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
