package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelGammaMetroid;
import danger.orespawn.entity.GammaMetroid;

public class RenderGammaMetroid extends RenderLiving {

    protected ModelGammaMetroid model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "GammaMetroid.png");

    public RenderGammaMetroid(ModelGammaMetroid modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelGammaMetroid) this.mainModel;
        this.scale = scale2;
    }

    public void renderGammaMetroid(GammaMetroid entityGammaMetroid, double x, double y, double z, float yaw,
        float partialTicks) {
        super.doRender(entityGammaMetroid, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderGammaMetroid((GammaMetroid) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderGammaMetroid((GammaMetroid) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(GammaMetroid entity, float f) {
        if (entity != null && entity.isChild()) {
            GL11.glScalef(this.scale / 2.0F, this.scale / 2.0F, this.scale / 2.0F);
        } else {
            GL11.glScalef(this.scale, this.scale, this.scale);
        }
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((GammaMetroid) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
