package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelRobot5;
import danger.orespawn.entity.Robot5;

public class RenderRobot5 extends RenderLiving {

    protected ModelRobot5 model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "Robot5texture.png");

    public RenderRobot5(ModelRobot5 modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelRobot5) this.mainModel;
        this.scale = scale2;
    }

    public void renderRobot5(Robot5 entityRobot5, double x, double y, double z, float yaw, float partialTicks) {
        super.doRender(entityRobot5, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderRobot5((Robot5) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderRobot5((Robot5) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(Robot5 entity, float f) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((Robot5) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
