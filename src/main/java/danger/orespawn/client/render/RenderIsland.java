package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelIsland;
import danger.orespawn.entity.Island;

public class RenderIsland extends RenderLiving {

    protected ModelIsland model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "Island.png");

    public RenderIsland(ModelIsland modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelIsland) this.mainModel;
        this.scale = scale2;
    }

    public void renderIsland(Island entityIsland, double x, double y, double z, float yaw, float partialTicks) {
        super.doRender(entityIsland, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderIsland((Island) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderIsland((Island) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(Island entity, float f) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((Island) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
