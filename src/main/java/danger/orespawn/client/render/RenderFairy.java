package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelFairy;
import danger.orespawn.entity.Fairy;

public class RenderFairy extends RenderLiving {

    protected ModelFairy model;
    private float scale = 1.0F;

    public RenderFairy(ModelFairy modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelFairy) this.mainModel;
        this.scale = scale2;
    }

    public void renderFairy(Fairy entityFairy, double x, double y, double z, float yaw, float partialTicks) {
        super.doRender(entityFairy, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderFairy((Fairy) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderFairy((Fairy) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(Fairy entity, float f) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((Fairy) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        Fairy a = (Fairy) entity;
        return a.getTexture(a);
    }
}
