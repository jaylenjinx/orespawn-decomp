package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelFirefly;
import danger.orespawn.entity.Firefly;

public class RenderFirefly extends RenderLiving {

    protected ModelFirefly model;
    private float scale = 1.0F;

    public RenderFirefly(ModelFirefly modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelFirefly) this.mainModel;
        this.scale = scale2;
    }

    public void renderFirefly(Firefly entityFirefly, double x, double y, double z, float yaw, float partialTicks) {
        super.doRender(entityFirefly, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderFirefly((Firefly) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderFirefly((Firefly) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(Firefly entity, float f) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((Firefly) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        Firefly a = (Firefly) entity;
        return a.getTexture(a);
    }
}
