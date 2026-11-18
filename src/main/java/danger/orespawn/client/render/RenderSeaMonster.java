package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelSeaMonster;
import danger.orespawn.entity.SeaMonster;

public class RenderSeaMonster extends RenderLiving {

    protected ModelSeaMonster model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "SeaMonstertexture.png");

    public RenderSeaMonster(ModelSeaMonster modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelSeaMonster) this.mainModel;
        this.scale = scale2;
    }

    public void renderSeaMonster(SeaMonster entitySeaMonster, double x, double y, double z, float yaw,
        float partialTicks) {
        super.doRender(entitySeaMonster, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderSeaMonster((SeaMonster) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderSeaMonster((SeaMonster) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(SeaMonster entity, float f) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((SeaMonster) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
