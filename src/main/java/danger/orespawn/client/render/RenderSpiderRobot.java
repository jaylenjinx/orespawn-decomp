package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelSpiderRobot;
import danger.orespawn.entity.SpiderRobot;

public class RenderSpiderRobot extends RenderLiving {

    protected ModelSpiderRobot model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "SpiderRobottexture.png");

    public RenderSpiderRobot(ModelSpiderRobot modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelSpiderRobot) this.mainModel;
        this.scale = scale2;
    }

    public void renderSpiderRobot(SpiderRobot entitySpiderRobot, double x, double y, double z, float f, float f2) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x, (float) y, (float) z);
        GL11.glRotatef(180.0F - f, 0.0F, 1.0F, 0.0F);
        this.bindTexture(texture);
        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        this.model.render(entitySpiderRobot, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderSpiderRobot((SpiderRobot) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderSpiderRobot((SpiderRobot) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(SpiderRobot entity, float f) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((SpiderRobot) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
