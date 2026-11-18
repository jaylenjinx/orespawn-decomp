package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelRobot2;
import danger.orespawn.entity.Robot2;

public class RenderRobot2 extends RenderLiving {

    protected ModelRobot2 model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "Robot2.png");

    public RenderRobot2(ModelRobot2 modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelRobot2) this.mainModel;
        this.scale = scale2;
    }

    public void renderRobot2(Robot2 entityRobot2, double x, double y, double z, float yaw, float partialTicks) {
        super.doRender(entityRobot2, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderRobot2((Robot2) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderRobot2((Robot2) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(Robot2 entity, float f) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((Robot2) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
