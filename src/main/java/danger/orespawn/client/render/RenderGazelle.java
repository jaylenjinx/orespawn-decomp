package danger.orespawn.client.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import danger.orespawn.client.model.ModelGazelle;
import danger.orespawn.entity.Gazelle;

public class RenderGazelle extends RenderLiving {

    protected ModelGazelle model;
    private float scale = 1.0F;
    private static final ResourceLocation texture = new ResourceLocation("orespawn", "Gazelletexture.png");

    public RenderGazelle(ModelGazelle modelBase, float f, float scale2) {
        super(modelBase, f * scale2);
        this.model = (ModelGazelle) this.mainModel;
        this.scale = scale2;
    }

    public void renderGazelle(Gazelle entityGazelle, double x, double y, double z, float yaw, float partialTicks) {
        super.doRender(entityGazelle, x, y, z, yaw, partialTicks);
    }

    public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderGazelle((Gazelle) entity, x, y, z, yaw, partialTicks);
    }

    public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks) {
        this.renderGazelle((Gazelle) entity, x, y, z, yaw, partialTicks);
    }

    protected void preRenderScale(Gazelle entity, float f) {
        if (entity != null && entity.isChild()) {
            GL11.glScalef(this.scale / 2.0F, this.scale / 2.0F, this.scale / 2.0F);
        } else {
            GL11.glScalef(this.scale, this.scale, this.scale);
        }
    }

    protected void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        this.preRenderScale((Gazelle) entity, partialTicks);
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}
