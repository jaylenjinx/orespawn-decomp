package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelBasilisk;
import danger.orespawn.entity.Basilisk;

public class RenderBasilisk extends RenderLiving {

    protected ModelBasilisk model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "basilisk.png");

    public RenderBasilisk(ModelBasilisk modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelBasilisk) this.mainModel;
        this.scale = scale2;
    }

    public void renderBasilisk(Basilisk entityBasilisk, double x, double y, double z, float yaw, float partialTicks) {
        super.doRender(entityBasilisk, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderBasilisk((Basilisk) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderBasilisk((Basilisk) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(Basilisk entity, float f) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((Basilisk) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
