package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelCaveFisher;
import danger.orespawn.entity.CaveFisher;

public class RenderCaveFisher extends RenderLiving {

    protected ModelCaveFisher model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "CaveFisher.png");

    public RenderCaveFisher(ModelCaveFisher modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelCaveFisher) this.mainModel;
        this.scale = scale2;
    }

    public void renderCaveFisher(CaveFisher entityCaveFisher, double x, double y, double z, float yaw,
        float partialTicks) {
        super.doRender(entityCaveFisher, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderCaveFisher((CaveFisher) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderCaveFisher((CaveFisher) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(CaveFisher entity, float f) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((CaveFisher) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
