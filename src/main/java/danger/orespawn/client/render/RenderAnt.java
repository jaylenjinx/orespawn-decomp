package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelAnt;
import danger.orespawn.entity.EntityAnt;

public class RenderAnt extends RenderLiving {

    protected ModelAnt model;
    private float scale = 0.25F;

    public RenderAnt(ModelAnt modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelAnt) this.mainModel;
        this.scale = scale2;
    }

    public void renderAnt(EntityAnt entityAnt, double x, double y, double z, float yaw, float partialTicks) {
        super.doRender(entityAnt, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderAnt((EntityAnt) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderAnt((EntityAnt) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(EntityAnt entity, float f) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((EntityAnt) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        EntityAnt a = (EntityAnt) entity;
        return a.getTexture(a);
    }
}
