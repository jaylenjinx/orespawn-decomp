package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelFlounder;
import danger.orespawn.entity.Flounder;

public class RenderFlounder extends RenderLiving {

    protected ModelFlounder model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "Floundertexture.png");

    public RenderFlounder(ModelFlounder modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelFlounder) this.mainModel;
        this.scale = scale2;
    }

    public void renderFlounder(Flounder entityFlounder, double x, double y, double z, float yaw, float partialTicks) {
        super.doRender(entityFlounder, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderFlounder((Flounder) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderFlounder((Flounder) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(Flounder entity, float f) {
        if (entity != null && entity.isChild()) {
            GL11.glScalef(this.scale / 2.0F, this.scale / 2.0F, this.scale / 2.0F);
        } else {
            GL11.glScalef(this.scale, this.scale, this.scale);
        }
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((Flounder) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
