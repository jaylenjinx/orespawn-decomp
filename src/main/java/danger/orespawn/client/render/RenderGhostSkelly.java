package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelGhostSkelly;
import danger.orespawn.entity.GhostSkelly;

public class RenderGhostSkelly extends RenderLiving {

    protected ModelGhostSkelly model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "GhostSkellytexture.png");

    public RenderGhostSkelly(ModelGhostSkelly modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelGhostSkelly) this.mainModel;
        this.scale = scale2;
    }

    public void renderGhostSkelly(GhostSkelly ghostSkelly, double x, double y, double z, float yaw,
        float partialTicks) {
        super.doRender(ghostSkelly, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderGhostSkelly((GhostSkelly) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderGhostSkelly((GhostSkelly) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(GhostSkelly entity, float f) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((GhostSkelly) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
