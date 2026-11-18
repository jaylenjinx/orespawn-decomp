package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelHammerhead;
import danger.orespawn.entity.Hammerhead;

public class RenderHammerhead extends RenderLiving {

    protected ModelHammerhead model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "Hammerheadtexture.png");

    public RenderHammerhead(ModelHammerhead modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelHammerhead) this.mainModel;
        this.scale = scale2;
    }

    public void renderHammerhead(Hammerhead entityHammerhead, double x, double y, double z, float yaw,
        float partialTicks) {
        super.doRender(entityHammerhead, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderHammerhead((Hammerhead) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderHammerhead((Hammerhead) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(Hammerhead entity, float f) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((Hammerhead) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
