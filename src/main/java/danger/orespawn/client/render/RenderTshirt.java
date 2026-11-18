package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelTshirt;
import danger.orespawn.entity.Tshirt;

public class RenderTshirt extends RenderLiving {

    protected ModelTshirt model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "Tshirttexture.png");

    public RenderTshirt(ModelTshirt modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelTshirt) this.mainModel;
        this.scale = scale2;
    }

    public void renderTshirt(Tshirt entityTshirt, double x, double y, double z, float yaw, float partialTicks) {
        super.doRender(entityTshirt, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderTshirt((Tshirt) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderTshirt((Tshirt) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(Tshirt entity, float f) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((Tshirt) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
