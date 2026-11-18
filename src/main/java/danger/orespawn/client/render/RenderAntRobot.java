package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelAntRobot;
import danger.orespawn.entity.AntRobot;

public class RenderAntRobot extends RenderLiving {

    protected ModelAntRobot model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "AntRobottexture.png");

    public RenderAntRobot(ModelAntRobot modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelAntRobot) this.mainModel;
        this.scale = scale2;
    }

    public void renderAntRobot(AntRobot entityAntRobot, double x, double y, double z, float f, float f2) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x, (float) y, (float) z);
        GL11.glRotatef(180.0F - f, 0.0F, 1.0F, 0.0F);
        this.bindTexture(texture);
        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        this.model.render(entityAntRobot, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderAntRobot((AntRobot) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderAntRobot((AntRobot) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(AntRobot entity, float f) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((AntRobot) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
