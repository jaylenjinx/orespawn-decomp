package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelRobot3;
import danger.orespawn.entity.Robot3;

public class RenderRobot3 extends RenderLiving {

    protected ModelRobot3 model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "Robot3.png");

    public RenderRobot3(ModelRobot3 modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelRobot3) this.mainModel;
        this.scale = scale2;
    }

    public void renderRobot3(Robot3 entityRobot3, double x, double y, double z, float yaw, float partialTicks) {
        super.doRender(entityRobot3, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderRobot3((Robot3) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderRobot3((Robot3) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(Robot3 entity, float f) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((Robot3) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
