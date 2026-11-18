package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelCockateil;
import danger.orespawn.entity.Cockateil;

public class RenderCockateil extends RenderLiving {

    protected ModelCockateil model;
    private float scale = 1.0F;

    public RenderCockateil(ModelCockateil modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelCockateil) this.mainModel;
        this.scale = scale2;
    }

    public void renderCockateil(Cockateil entityCockateil, double x, double y, double z, float yaw,
        float partialTicks) {
        super.doRender(entityCockateil, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderCockateil((Cockateil) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderCockateil((Cockateil) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(Cockateil entity, float f) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((Cockateil) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        Cockateil c = (Cockateil) entity;
        return c.getTexture();
    }
}
