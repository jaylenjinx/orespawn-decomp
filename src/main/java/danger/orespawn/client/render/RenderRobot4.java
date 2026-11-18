package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelRobot4;
import danger.orespawn.entity.Robot4;

public class RenderRobot4 extends RenderLiving {

    protected ModelRobot4 model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "Robot4.png");

    public RenderRobot4(ModelRobot4 modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelRobot4) this.mainModel;
        this.scale = scale2;
    }

    public void renderRobot4(Robot4 entityRobot4, double x, double y, double z, float yaw, float partialTicks) {
        super.doRender(entityRobot4, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderRobot4((Robot4) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderRobot4((Robot4) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(Robot4 entity, float f) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((Robot4) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
