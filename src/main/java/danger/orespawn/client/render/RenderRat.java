package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelRat;
import danger.orespawn.entity.Rat;

public class RenderRat extends RenderLiving {

    protected ModelRat model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "Rattexture.png");

    public RenderRat(ModelRat modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelRat) this.mainModel;
        this.scale = scale2;
    }

    public void renderRat(Rat entityRat, double x, double y, double z, float yaw, float partialTicks) {
        super.doRender(entityRat, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderRat((Rat) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderRat((Rat) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(Rat entity, float f) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((Rat) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
