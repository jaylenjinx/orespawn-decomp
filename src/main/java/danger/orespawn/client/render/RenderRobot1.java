package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelRobot1;
import danger.orespawn.entity.Robot1;

public class RenderRobot1 extends RenderLiving {

    protected ModelRobot1 model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "Robot1.png");

    public RenderRobot1(ModelRobot1 modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelRobot1) this.mainModel;
        this.scale = scale2;
    }

    public void renderRobot1(Robot1 entityRobot1, double x, double y, double z, float yaw, float partialTicks) {
        super.doRender(entityRobot1, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderRobot1((Robot1) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderRobot1((Robot1) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(Robot1 entity, float f) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((Robot1) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
