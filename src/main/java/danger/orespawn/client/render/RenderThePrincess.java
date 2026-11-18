package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelThePrincess;
import danger.orespawn.entity.ThePrincess;

public class RenderThePrincess extends RenderLiving {

    protected ModelThePrincess model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "ThePrincesstexture.png");
    private static final ResourceLocation texture2 = new ResourceLocation("orespawn", "ThePrincesstexture2.png");

    public RenderThePrincess(ModelThePrincess modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelThePrincess) this.mainModel;
        this.scale = scale2;
    }

    public void renderThePrincess(ThePrincess entityThePrincess, double x, double y, double z, float yaw,
        float partialTicks) {
        super.doRender(entityThePrincess, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderThePrincess((ThePrincess) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderThePrincess((ThePrincess) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(ThePrincess entity, float f) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((ThePrincess) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        ThePrincess t = (ThePrincess) entity;
        return t.getAttacking() != 0 ? texture2 : texture;
    }
}
