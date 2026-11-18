package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelPointysaurus;
import danger.orespawn.entity.Pointysaurus;

public class RenderPointysaurus extends RenderLiving {

    protected ModelPointysaurus model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "Pointysaurustexture.png");

    public RenderPointysaurus(ModelPointysaurus modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelPointysaurus) this.mainModel;
        this.scale = scale2;
    }

    public void renderPointysaurus(Pointysaurus entityPointysaurus, double x, double y, double z, float yaw,
        float partialTicks) {
        super.doRender(entityPointysaurus, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderPointysaurus((Pointysaurus) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderPointysaurus((Pointysaurus) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(Pointysaurus entity, float f) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((Pointysaurus) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
