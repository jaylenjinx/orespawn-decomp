package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelBee;
import danger.orespawn.entity.Bee;

public class RenderBee extends RenderLiving {

    protected ModelBee model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "Beetexture.png");

    public RenderBee(ModelBee modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelBee) this.mainModel;
        this.scale = scale2;
    }

    public void renderBee(Bee entityBee, double x, double y, double z, float yaw, float partialTicks) {
        super.doRender(entityBee, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderBee((Bee) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderBee((Bee) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(Bee entity, float f) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((Bee) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
