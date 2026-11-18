package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelEmperorScorpion;
import danger.orespawn.entity.EmperorScorpion;

public class RenderEmperorScorpion extends RenderLiving {

    protected ModelEmperorScorpion model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "emperorscorpion.png");

    public RenderEmperorScorpion(ModelEmperorScorpion modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelEmperorScorpion) this.mainModel;
        this.scale = scale2;
    }

    public void renderEmperorScorpion(EmperorScorpion entityEmperorScorpion, double x, double y, double z, float yaw,
        float partialTicks) {
        super.doRender(entityEmperorScorpion, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderEmperorScorpion((EmperorScorpion) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderEmperorScorpion((EmperorScorpion) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(EmperorScorpion entity, float f) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((EmperorScorpion) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
