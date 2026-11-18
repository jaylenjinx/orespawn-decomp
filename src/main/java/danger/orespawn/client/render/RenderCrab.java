package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelCrab;
import danger.orespawn.entity.Crab;

public class RenderCrab extends RenderLiving {

    protected ModelCrab model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "RobotCrabtexture.png");

    public RenderCrab(ModelCrab modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelCrab) this.mainModel;
        this.scale = scale2;
    }

    public void renderCrab(Crab entityCrab, double x, double y, double z, float yaw, float partialTicks) {
        super.doRender(entityCrab, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderCrab((Crab) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderCrab((Crab) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(Crab entity, float f) {
        float pscale = entity.getCrabScale();
        GL11.glScalef(pscale, pscale, pscale);
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((Crab) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
