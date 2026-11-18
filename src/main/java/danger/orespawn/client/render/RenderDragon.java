package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelDragon;
import danger.orespawn.entity.Dragon;

public class RenderDragon extends RenderLiving {

    protected ModelDragon model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "Dragon.png");
    private static final ResourceLocation texture2 = new ResourceLocation("orespawn", "WhiteDragon.png");

    public RenderDragon(ModelDragon modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelDragon) this.mainModel;
        this.scale = scale2;
    }

    public void renderDragon(Dragon entityDragon, double x, double y, double z, float yaw, float partialTicks) {
        super.doRender(entityDragon, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderDragon((Dragon) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderDragon((Dragon) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(Dragon entity, float f) {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((Dragon) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        Dragon d = (Dragon) entity;
        return d.getDragonType() != 0 ? texture2 : texture;
    }
}
