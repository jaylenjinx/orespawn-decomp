package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelThePrinceTeen;
import danger.orespawn.entity.ThePrinceTeen;

public class RenderThePrinceTeen extends RenderLiving {

    protected ModelThePrinceTeen model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "PrinceTeentexture.png");

    public RenderThePrinceTeen(ModelThePrinceTeen modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelThePrinceTeen) this.mainModel;
        this.scale = scale2;
    }

    public void renderThePrinceTeen(ThePrinceTeen entityThePrinceTeen, double x, double y, double z, float yaw,
        float partialTicks) {
        super.doRender(entityThePrinceTeen, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderThePrinceTeen((ThePrinceTeen) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderThePrinceTeen((ThePrinceTeen) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(ThePrinceTeen entity, float f) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((ThePrinceTeen) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
